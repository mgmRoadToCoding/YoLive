<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:spec:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:spec:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:spec:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:spec:export']"
          >导出</el-button
        >
      </el-col>
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        label-width="68px"
        class="el-form-search"
      >
        <el-form-item
          label="规格名称"
          prop="specName"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.specName"
            placeholder="请输入规格名称"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item
          label="是否启用"
          prop="normalDisable"
          class="el-form-search-item"
        >
          <el-select
            v-model="queryParams.normalDisable"
            placeholder="请选择是否启用"
            clearable
            size="mini"
          >
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item class="el-form-search-item">
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重置</el-button
          >
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      :height="tableHeight"
      v-loading="loading"
      :data="specList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="#" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="规格名称" align="center" prop="specName" />
      <el-table-column
        label="规格图片"
        align="center"
        prop="specUrl"
        width="100"
      >
        <template slot-scope="scope">
          <image-preview :src="scope.row.specUrl" :width="30" :height="30" />
        </template>
      </el-table-column>
      <el-table-column label="销售价" align="center" prop="specPrice" />
      <el-table-column label="库存" align="center" prop="specStock" />
      <el-table-column label="排序" align="center" prop="sortNo" />
      <el-table-column label="是否启用" align="center" prop="normalDisable">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_normal_disable"
            :value="scope.row.normalDisable"
          />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="150"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:spec:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:spec:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改商品规格对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父id" />
        </el-form-item>
        <el-form-item label="规格名称" prop="specName">
          <el-input v-model="form.specName" placeholder="请输入规格名称" />
        </el-form-item>
        <el-form-item label="规格图片">
          <image-upload v-model="form.specUrl" />
        </el-form-item>
        <el-form-item label="销售价" prop="specPrice">
          <el-input v-model="form.specPrice" placeholder="请输入销售价" />
        </el-form-item>
        <el-form-item label="库存" prop="specStock">
          <el-input v-model="form.specStock" placeholder="请输入库存" />
        </el-form-item>
        <el-form-item label="排序" prop="sortNo">
          <el-input v-model="form.sortNo" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="是否启用">
          <el-radio-group v-model="form.normalDisable">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listSpec,
  getSpec,
  delSpec,
  addSpec,
  updateSpec,
} from "@/api/mall/spec";

export default {
  name: "Spec",
  dicts: ["sys_normal_disable"],
  data() {
    return {
      // 表格高度
      tableHeight: document.documentElement.clientHeight - 180,
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
      // 商品规格表格数据
      specList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        orderByColumn: "create_time",
        isAsc: "desc",
        specName: null,
        normalDisable: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        specName: [
          { required: true, message: "规格名称不能为空", trigger: "blur" },
        ],
        specUrl: [
          { required: true, message: "规格图片不能为空", trigger: "blur" },
        ],
        specPrice: [
          { required: true, message: "销售价不能为空", trigger: "blur" },
        ],
        specStock: [
          { required: true, message: "库存不能为空", trigger: "blur" },
        ],
        sortNo: [{ required: true, message: "排序不能为空", trigger: "blur" }],
        normalDisable: [
          { required: true, message: "是否启用不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品规格列表 */
    getList() {
      this.loading = true;
      listSpec(this.queryParams).then((response) => {
        this.specList = response.rows;
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
        parentId: null,
        specName: null,
        specUrl: null,
        specPrice: null,
        specStock: null,
        sortNo: null,
        normalDisable: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品规格";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSpec(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品规格";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateSpec(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpec(this.form).then((response) => {
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
      this.$modal
        .confirm("是否确认删除记录？")
        .then(function () {
          return delSpec(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/spec/export",
        {
          ...this.queryParams,
        },
        `spec_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
