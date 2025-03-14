<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:work:add']"
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
          v-hasPermi="['system:work:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleFeed"
          v-hasPermi="['system:work:edit']"
          >处理</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:work:remove']"
          >删除</el-button
        >
      </el-col>
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
        class="el-form-search"
      >
        <el-form-item
          label="类型"
          prop="wrokType"
          class="el-form-search-item"
          label-width="40px"
        >
          <el-select
            v-model="queryParams.wrokType"
            placeholder="请选择类型"
            clearable
            size="mini"
          >
            <el-option
              v-for="dict in dict.type.sys_work_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="优先级"
          prop="workPrioity"
          class="el-form-search-item"
          label-width="58px"
        >
          <el-select
            v-model="queryParams.workPrioity"
            placeholder="请选择优先级"
            clearable
            size="mini"
          >
            <el-option
              v-for="dict in dict.type.sys_priority_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="处理结果"
          prop="handleResult"
          class="el-form-search-item"
          label-width="68px"
        >
          <el-select
            v-model="queryParams.handleResult"
            placeholder="请选择处理结果"
            clearable
            size="mini"
          >
            <el-option key="待处理" label="待处理" value="待处理"></el-option>
            <el-option key="已处理" label="已处理" value="已处理"></el-option>
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
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      ref="list"
      :height="tableHeight"
      v-loading="loading"
      :data="workList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
      highlight-current-row
      :default-sort="defaultSort"
      @sort-change="handleSortChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="#" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工单编号" align="center" prop="workNo">
        <template slot-scope="scope">
          <el-link
            type="primary"
            :underline="false"
            @click="handleUpdate(scope.row)"
          >
            {{ scope.row.workNo }}</el-link
          >
        </template>
      </el-table-column>
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="类型" align="center" prop="wrokType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_work_type"
            :value="scope.row.wrokType"
          />
        </template>
      </el-table-column>
      <el-table-column label="描述" align="left" prop="workDesc" width="500">
        <template slot-scope="scope">
          <el-tooltip placement="top-start">
            <div slot="content" class="text_warp">{{ scope.row.workDesc }}</div>
            <div class="text_els">
              {{ scope.row.workDesc }}
            </div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="workPrioity">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_priority_level"
            :value="scope.row.workPrioity"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="计划完成日期"
        align="center"
        prop="planDate"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"
        width="130"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="principal" />
      <el-table-column label="处理结果" align="center" prop="handleResult" />
      <el-table-column
        label="处理日期"
        align="center"
        prop="handleDate"
        width="100"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handleDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handler" />
      <el-table-column
        label="提交日期"
        align="center"
        prop="createTime"
        width="100"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提交人" align="center" prop="createBy" />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        width="100"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="180"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDown(scope.row)"
            v-hasPermi="['system:work:edit']"
            v-show="scope.row.workAttach"
            >下载附件</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:work:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:work:remove']"
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

    <!-- 添加或修改工单管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="产品名称" prop="productName">
          <el-select
            v-model="form.productName"
            placeholder="请选择类型"
            style="width: 100%"
            filterable
            clearable
            allow-create
          >
            <el-option
              v-for="product in productList"
              :key="product.productBigTitle"
              :label="product.productBigTitle"
              :value="product.productBigTitle"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="wrokType">
          <el-select
            v-model="form.wrokType"
            placeholder="请选择类型"
            style="width: 100%"
          >
            <el-option
              v-for="dict in dict.type.sys_work_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="workDesc">
          <el-input
            type="textarea"
            :rows="5"
            v-model="form.workDesc"
            placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="优先级" prop="workPrioity">
          <el-select
            v-model="form.workPrioity"
            placeholder="请选择优先级"
            style="width: 100%"
          >
            <el-option
              v-for="dict in dict.type.sys_priority_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计划完成日期" prop="planDate">
          <el-date-picker
            clearable
            size="mini"
            v-model="form.planDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择计划完成日期"
            style="width: 100%"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="负责人" prop="principal">
          <el-input
            v-model="form.principal"
            placeholder="请输入负责人"
            autocomplete="on"
            name="principal"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="附件" prop="workAttach">
          <file-upload v-model="form.workAttach" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改工单管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="feedOpen"
      width="600px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="feedForm" :model="form" label-width="80px">
        <el-form-item label="处理结果" prop="handleResult">
          <el-select
            v-model="form.handleResult"
            placeholder="请选择处理结果"
            style="width: 100%"
          >
            <el-option key="待处理" label="待处理" value="待处理"></el-option>
            <el-option key="已处理" label="已处理" value="已处理"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFeedForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWork,
  getWork,
  delWork,
  addWork,
  updateWork,
  feedWork,
} from "@/api/system/work";
import { listProduct } from "@/api/system/product";

export default {
  name: "Work",
  dicts: ["sys_work_type", "sys_priority_level"],
  data() {
    return {
      productList: [],
      // 表格高度
      tableHeight: document.documentElement.clientHeight - 180,
      // 遮罩层
      loading: false,
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
      // 工单管理表格数据
      workList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      feedOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        productName: null,
        workNo: null,
        wrokType: null,
        workDesc: null,
        workAttach: null,
        workPrioity: null,
        planDate: null,
        principal: null,
        handleResult: "待处理",
        handleDate: null,
        handler: null,
        orderByColumn: "create_time",
        isAsc: "desc",
      },
      // 默认排序
      defaultSort: { prop: "createTime", order: "descending" },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" },
        ],
        workNo: [
          { required: true, message: "工单编号不能为空", trigger: "blur" },
        ],
        wrokType: [
          { required: true, message: "类型不能为空", trigger: "change" },
        ],
        workDesc: [
          { required: true, message: "描述不能为空", trigger: "blur" },
        ],
        workPrioity: [
          { required: true, message: "优先级不能为空", trigger: "change" },
        ],
        planDate: [
          { required: true, message: "计划完成日期不能为空", trigger: "blur" },
        ],
        principal: [
          { required: true, message: "负责人不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工单管理列表 */
    getList() {
      this.loading = true;
      listWork(this.queryParams).then((response) => {
        this.workList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.feedOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        productName: null,
        workNo: null,
        wrokType: null,
        workDesc: null,
        workAttach: null,
        workPrioity: null,
        planDate: null,
        principal: null,
        handleResult: null,
        handleDate: null,
        handler: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
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
      this.$refs.list.sort(this.defaultSort.prop, this.defaultSort.order);
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
      this.title = "添加工单";
      listProduct({
        pageNum: 1,
        pageSize: 20,
        orderByColumn: "create_time",
        isAsc: "desc",
        isBuild: 0,
      }).then((response) => {
        this.productList = response.rows;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWork(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工单";
      });
      listProduct({
        pageNum: 1,
        pageSize: 20,
        orderByColumn: "create_time",
        isAsc: "desc",
        isBuild: 0,
      }).then((response) => {
        this.productList = response.rows;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWork(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWork(this.form).then((response) => {
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
          return delWork(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 批量处理按钮操作 */
    handleFeed() {
      this.reset();
      this.feedOpen = true;
      this.title = "处理工单";
      this.form.handleResult = "已处理";
    },
    /** 提交处理按钮 */
    submitFeedForm() {
      feedWork(this.ids, this.form.handleResult).then((response) => {
        this.$modal.msgSuccess("处理成功");
        this.feedOpen = false;
        this.getList();
      });
    },
    /** 下载附件 */
    handleDown(row) {
      this.$download.resource(row.workAttach);
    },
    handleRowClick(row, column, event) {
      this.$refs.list.toggleRowSelection(row);
    },
    /** 排序触发事件 */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
  },
};
</script>

<style lang="scss" scoped>
.text_warp {
  display: block;
  width: 500px;
  max-width: 500px;
  // white-space: wrap;
  white-space: pre-wrap;
  line-height: 1.8;
}
.text_els {
  width: 100%;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
}
</style>
