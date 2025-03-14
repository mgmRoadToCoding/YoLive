<template>
  <el-dialog
    title="评价列表"
    :visible.sync="visible"
    width="800px"
    style="top: 12%"
    append-to-body
  >
    <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:evaluate:remove']"
        >删除</el-button>
      </el-col>
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" class="el-form-search">

              <el-form-item label="评价人" prop="evalUser" class="el-form-search-item">
                  <el-input
                          v-model="queryParams.evalUser"
                          placeholder="请输入评价人"
                          clearable
                          size="mini"
                          @keyup.enter.native="handleQuery"
                  />
              </el-form-item>
              <el-form-item label="状态" prop="state" class="el-form-search-item">
                <el-select
                  v-model="queryParams.state"
                  placeholder="请选择状态"
                  clearable
                  size="mini"
                >
                  <el-option label="显示" value=1></el-option>
                  <el-option label="删除" value=2></el-option>
                </el-select>
              </el-form-item>
            <el-form-item class="el-form-search-item">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
    </el-row>

    <el-table :height="tableHeight" v-loading="loading" :data="evaluateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="#" type="index" width="50" align="center">
            <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
            </template>
        </el-table-column>
      <el-table-column label="评价" align="center" prop="evaluate" />
      <el-table-column label="评价人" align="center" prop="evalUser" />
      <el-table-column label="状态" align="center" prop="state" >
      <template slot-scope="scope">
        <span v-if="scope.row.state === 1">显示</span>
        <span v-else ="scope.row.state === 2">删除</span>
      </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:evaluate:edit']"
          >查看</el-button>
          <el-button
            v-if="scope.row.state === 1"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:evaluate:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改房源评价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" v-dialogDrag style="top: 22%" >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" disabled="disabled">
<!--        <el-form-item label="房屋id" prop="houseId">-->
<!--          <el-input v-model="form.houseId" placeholder="请输入房屋id" />-->
<!--        </el-form-item>        -->
        <el-form-item label="评价" prop="evaluate">
          <el-input v-model="form.evaluate" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-input v-model="form.state" placeholder="请输入状态 1显示 2删除" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
<!--        <el-form-item label="评价人id" prop="evalUserId">-->
<!--        <el-input v-model="form.evalUserId" placeholder="请输入评价人id" />-->
<!--      </el-form-item>-->
        <el-form-item label="评价人" prop="evalUser">
          <el-input v-model="form.evalUser" placeholder="请输入评价人" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
  </el-dialog>
</template>

<script>
import { listEvaluate, getEvaluate, delEvaluate, addEvaluate, updateEvaluate } from "@/api/house/evaluate";

export default {
  name: "Evaluate",
  props: {
    id: Number
  },
  data() {
    return {
      // 表格高度
      tableHeight: document.documentElement.clientHeight - 460,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 房源评价表格数据
      evaluateList: [],
      // 弹出层标题
      title: "",
      visible:false,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        orderByColumn: "create_time",
        isAsc: "desc",
        houseId: null,
        evalUserId: null,
        evalUser: null,
        evaluate: null,
        state: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        evaluate: [
          { required: true, message: "评价不能为空", trigger: "blur" }
        ]

      }
    };
  },
  created() {
    //this.getList();
  },

  methods: {
    show(val) {
        this.queryParams.houseId=val
        this.getList();
        this.visible = true;
    },
    /** 查询房源评价列表 */
    getList() {
      this.loading = true;
      listEvaluate(this.queryParams).then(response => {
        this.evaluateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        houseId: null,
        evalUserId: null,
        evalUser: null,
        evaluate: null,
        state: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.houseId = this.id
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加房源评价";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEvaluate(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看房源评价";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEvaluate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEvaluate(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除记录？').then(function() {
        return delEvaluate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/evaluate/export', {
        ...this.queryParams
      }, `evaluate_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
