<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:tool:add']"
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
          v-hasPermi="['system:tool:edit']"
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
          v-hasPermi="['system:tool:remove']"
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
        <el-form-item label="工具分类" prop="catId" class="el-form-search-item">
          <el-select
            v-model="queryParams.catId"
            placeholder="请选择工具分类"
            clearable
            size="mini"
          >
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="工具名称" prop="name" class="el-form-search-item">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入工具名称"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工具类型" prop="type" class="el-form-search-item">
          <el-select
            v-model="queryParams.type"
            placeholder="请选择工具类型"
            clearable
            size="mini"
          >
            <el-option
              v-for="dict in dict.type.sys_tool_type"
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
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      ref="list"
      :height="tableHeight"
      v-loading="loading"
      :data="toolList"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
      highlight-current-row
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
        label="所属分类"
        width="80"
        align="left"
        prop="catName"
      />
      <el-table-column label="分类图标" align="center" prop="thumb" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.icon" :width="30" :height="30" />
        </template>
      </el-table-column>
      <el-table-column label="工具名称" align="left" prop="name" />
      <el-table-column
        label="工具版本"
        width="80"
        align="left"
        prop="version"
      />
      <el-table-column label="工具类型" width="80" align="left" prop="type">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_tool_type"
            :value="scope.row.type"
          />
        </template>
      </el-table-column>
      <el-table-column label="链接/网盘地址" align="left" prop="url" />
      <el-table-column
        label="提取码"
        width="80"
        align="left"
        prop="extractedCode"
      />
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
            v-hasPermi="['system:tool:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:tool:remove']"
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

    <!-- 添加或修改工具管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="所属分类" prop="catId">
          <el-select
            v-model="form.catId"
            placeholder="请选择所属分类"
            style="width: 100%"
          >
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="工具图标" prop="icon">
          <image-upload v-model="form.icon" />
        </el-form-item>
        <el-form-item label="工具名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入工具名称" />
        </el-form-item>
        <el-form-item label="工具简介" prop="intro">
          <el-input
            type="textarea"
            v-model="form.intro"
            placeholder="请输入工具简介"
          />
        </el-form-item>
        <el-form-item label="工具版本" prop="version">
          <el-input v-model="form.version" placeholder="请输入工具版本" />
        </el-form-item>
        <el-form-item label="工具类型" prop="type">
          <el-select
            v-model="form.type"
            placeholder="请选择工具类型"
            style="width: 100%"
          >
            <el-option
              v-for="dict in dict.type.sys_tool_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="链接/网盘地址" prop="url">
          <el-input v-model="form.url" placeholder="请输入链接/网盘地址" />
        </el-form-item>
        <el-form-item label="提取码" prop="extractedCode">
          <el-input v-model="form.extractedCode" placeholder="请输入提取码" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入工具排序" />
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
  listTool,
  getTool,
  delTool,
  addTool,
  updateTool,
} from "@/api/system/tool";
import { listCategory } from "@/api/system/toolCategory";
import IconSelect from "@/components/IconSelect";
export default {
  name: "Tool",
  dicts: ["sys_tool_type"],
  components: { IconSelect },
  data() {
    return {
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
      // 工具管理表格数据
      toolList: [],
      // 工具分类列表
      categoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        orderByColumn: "sort",
        isAsc: "asc",
        catId: null,
        thumb: null,
        name: null,
        intro: null,
        version: null,
        type: null,
        url: null,
        extractedCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        catId: [{ required: true, message: "请选择所属分类", trigger: "blur" }],
        name: [
          { required: true, message: "工具名称不能为空", trigger: "blur" },
        ],
        type: [
          { required: true, message: "工具类型不能为空", trigger: "change" },
        ],
        url: [
          { required: true, message: "链接/网盘地址不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    // 获取分类列表
    this.getCatList();
  },
  methods: {
    // 选择图标
    selected(name) {
      this.form.thumb = name;
    },
    /** 查询工具管理列表 */
    getList() {
      this.loading = true;
      listTool(this.queryParams).then((response) => {
        this.toolList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询工具分类列表 */
    getCatList() {
      listCategory().then((response) => {
        this.categoryList = response.rows;
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
        catId: null,
        thumb: null,
        name: null,
        sort: 0, // 排序默认为0
        intro: null,
        version: null,
        type: null,
        url: null,
        panType: null,
        extractedCode: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
    // 单击行选中数据
    handleRowClick(row, column, event) {
      this.$refs.list.toggleRowSelection(row);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工具";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTool(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工具";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTool(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTool(this.form).then((response) => {
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
          return delTool(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
  },
};
</script>
