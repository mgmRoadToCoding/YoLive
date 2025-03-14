<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:classify:add']"
          >新增</el-button
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
          label="分类名称"
          prop="classifyName"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.classifyName"
            placeholder="请输入分类名称"
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
      v-loading="loading"
      :data="classifyList"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column
        label="分类名称"
        align="left"
        prop="classifyName"
        width="400"
      />
      <el-table-column label="分类图片" align="left" prop="classifyUrl">
        <template slot-scope="scope">
          <image-preview
            :src="scope.row.classifyUrl"
            :width="30"
            :height="30"
          />
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sortNo" width="200" />
      <el-table-column label="备注" align="center" prop="remark" width="200" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:classify:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:classify:add']"
            >新增</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:classify:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改分类管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级分类" prop="parentId">
          <treeselect
            v-model="form.parentId"
            :options="classifyOptions"
            :normalizer="normalizer"
            placeholder="请选择上级分类"
          />
        </el-form-item>
        <el-form-item label="分类名称" prop="classifyName">
          <el-input v-model="form.classifyName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类图片" prop="classifyUrl">
          <image-upload v-model="form.classifyUrl" :limit="1" />
        </el-form-item>
        <el-form-item label="排序" prop="sortNo">
          <el-input v-model="form.sortNo" placeholder="请输入排序" />
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
  listClassify,
  getClassify,
  delClassify,
  addClassify,
  updateClassify,
} from "@/api/mall/classify";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Classify",
  components: {
    Treeselect,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 分类管理表格数据
      classifyList: [],
      // 分类管理树选项
      classifyOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        classifyName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "父id不能为空", trigger: "blur" },
        ],
        ancestors: [
          { required: true, message: "祖级列表不能为空", trigger: "blur" },
        ],
        classifyName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" },
        ],
        classifyUrl: [
          { required: true, message: "分类图片不能为空", trigger: "blur" },
        ],
        sortNo: [{ required: true, message: "排序不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询分类管理列表 */
    getList() {
      this.loading = true;
      listClassify(this.queryParams).then((response) => {
        this.classifyList = this.handleTree(response.data, "id", "parentId");
        this.loading = false;
      });
    },
    /** 转换分类管理数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.classifyName,
        children: node.children,
      };
    },
    /** 查询分类管理下拉树结构 */
    getTreeselect() {
      listClassify().then((response) => {
        this.classifyOptions = [];
        const data = { id: 0, classifyName: "顶级节点", children: [] };
        data.children = this.handleTree(response.data, "id", "parentId");
        this.classifyOptions.push(data);
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
        ancestors: null,
        classifyName: null,
        classifyUrl: null,
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
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.parentId = row.id;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.id;
      }
      getClassify(row.id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateClassify(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClassify(this.form).then((response) => {
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
      this.$modal
        .confirm("是否确认删除记录？")
        .then(function () {
          return delClassify(row.id);
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
