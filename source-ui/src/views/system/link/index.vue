<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:link:add']"
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
          v-hasPermi="['system:link:edit']"
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
          v-hasPermi="['system:link:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:link:export']"
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
          label="站点名称"
          prop="linkName"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.linkName"
            placeholder="请输入站点名称"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item
          label="联系方式"
          prop="linkWay"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.linkWay"
            placeholder="请输入联系方式"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
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
      :height="tableHeight"
      v-loading="loading"
      :data="linkList"
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
      <el-table-column
        label="站点名称"
        align="center"
        prop="linkName"
        width="80"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="站点链接"
        align="center"
        prop="linkUrl"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="站点Logo"
        align="center"
        prop="linkLogo"
        width="240"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="展示地址"
        align="center"
        prop="linkShow"
        width="240"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="联系方式"
        align="center"
        prop="linkWay"
        width="140"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="备注" align="center" prop="remark" width="80" />
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
            v-hasPermi="['system:link:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:link:remove']"
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

    <!-- 添加或修改友情链接对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="站点名称" prop="linkName">
          <el-input v-model="form.linkName" placeholder="请输入站点名称" />
        </el-form-item>
        <el-form-item label="站点链接" prop="linkUrl">
          <el-input v-model="form.linkUrl" placeholder="请输入站点链接" />
        </el-form-item>
        <el-form-item label="站点Logo" prop="linkLogo">
          <el-input v-model="form.linkLogo" placeholder="请输入站点Logo" />
        </el-form-item>
        <el-form-item label="展示地址" prop="linkShow">
          <el-input v-model="form.linkShow" placeholder="请输入展示地址" />
        </el-form-item>
        <el-form-item label="联系方式" prop="linkWay">
          <el-input v-model="form.linkWay" placeholder="请输入联系方式" />
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
  listLink,
  getLink,
  delLink,
  addLink,
  updateLink,
} from "@/api/system/link";

export default {
  name: "Link",
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
      // 友情链接表格数据
      linkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        linkName: null,
        linkWay: null,
        orderByColumn: "create_time",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        linkName: [
          { required: true, message: "站点名称不能为空", trigger: "blur" },
        ],
        linkUrl: [
          { required: true, message: "站点链接不能为空", trigger: "blur" },
        ],
        linkLogo: [
          { required: true, message: "站点Logo不能为空", trigger: "blur" },
        ],
        linkShow: [
          { required: true, message: "展示地址不能为空", trigger: "blur" },
        ],
        linkWay: [
          { required: true, message: "联系方式不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询友情链接列表 */
    getList() {
      this.loading = true;
      listLink(this.queryParams).then((response) => {
        this.linkList = response.rows;
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
        linkName: null,
        linkUrl: null,
        linkLogo: null,
        linkShow: null,
        linkWay: null,
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
      this.title = "添加友情链接";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getLink(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改友情链接";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateLink(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLink(this.form).then((response) => {
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
          return delLink(ids);
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
        "system/link/export",
        {
          ...this.queryParams,
        },
        `link_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
