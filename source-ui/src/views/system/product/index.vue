<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:product:add']"
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
          v-hasPermi="['system:product:edit']"
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
          v-hasPermi="['system:product:remove']"
          >删除</el-button
        >
      </el-col>
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="48px"
        class="el-form-search"
      >
        <el-form-item
          label="行业"
          prop="productIndustry"
          class="el-form-search-item"
        >
          <el-select
            v-model="queryParams.productIndustry"
            placeholder="请选择行业"
            clearable
            size="mini"
          >
            <el-option
              v-for="dict in dict.type.sys_product_industry"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="标签"
          prop="productTag"
          class="el-form-search-item"
        >
          <el-select
            v-model="queryParams.productTag"
            placeholder="请选择标签"
            clearable
            size="mini"
          >
            <el-option
              v-for="dict in dict.type.sys_product_tag"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="标题"
          prop="productBigTitle"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.productBigTitle"
            placeholder="请输入标题"
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
      ref="list"
      :height="tableHeight"
      v-loading="loading"
      :data="productList"
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
      <el-table-column label="行业" align="center" prop="productIndustry">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_product_industry"
            :value="scope.row.productIndustry"
          />
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" prop="productTag">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_product_tag"
            :value="scope.row.productTag"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="大标题"
        align="center"
        prop="productBigTitle"
        width="120"
        show-overflow-tooltip
      />
      <el-table-column
        label="小标题"
        align="center"
        prop="productSmallTitle"
        width="160"
        show-overflow-tooltip
      />
      <el-table-column
        label="描述"
        align="center"
        prop="productDesc"
        width="240"
        show-overflow-tooltip
      />
      <el-table-column
        label="图标"
        align="center"
        prop="productIcon"
        width="100"
      >
        <template slot-scope="scope">
          <image-preview
            :src="scope.row.productIcon"
            :width="30"
            :height="30"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="链接"
        align="center"
        prop="prodcutUrl"
        width="160"
        show-overflow-tooltip
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        width="160"
        show-overflow-tooltip
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="100"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:product:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:product:remove']"
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

    <!-- 添加或修改产品管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="880px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="行业" prop="productIndustry">
              <el-select
                v-model="form.productIndustry"
                placeholder="请选择行业"
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.sys_product_industry"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签" prop="productTag">
              <el-select
                v-model="form.productTag"
                placeholder="请选择标签"
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.sys_product_tag"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="大标题" prop="productBigTitle">
              <el-input
                v-model="form.productBigTitle"
                placeholder="请输入大标题"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="小标题" prop="productSmallTitle">
              <el-input
                v-model="form.productSmallTitle"
                placeholder="请输入小标题"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="描述" prop="productDesc">
              <el-input
                v-model="form.productDesc"
                type="textarea"
                :rows="7"
                placeholder="请输入描述"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图标">
              <image-upload v-model="form.productIcon" :limit="1" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="主图">
              <image-upload
                v-model="form.productThImg"
                :limit="1"
              /> </el-form-item
          ></el-col>
          <el-col :span="12"
            ><el-form-item label="背景图">
              <image-upload
                v-model="form.productBgImg"
                :limit="1"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-form-item label="详情">
          <editor v-model="form.productContent" :min-height="192" />
        </el-form-item>
        <el-form-item label="链接" prop="prodcutUrl">
          <el-input
            v-model="form.prodcutUrl"
            placeholder="请输入链接"
            style="width: 100%"
          />
        </el-form-item>
        <el-row :gutter="30">
          <el-col :span="12"
            ><el-form-item label="在建">
              <el-radio-group v-model="form.isBuild">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                  >{{ dict.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item></el-col
          >
          <el-col :span="12">
            <el-form-item label="热点">
              <el-radio-group v-model="form.isHot">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                  >{{ dict.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item></el-col
          >
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12"
            ><el-form-item label="联系人" prop="contact">
              <el-input
                v-model="form.contact"
                placeholder="请输入联系人"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactCode">
              <el-input
                v-model="form.contactCode"
                placeholder="请输入联系电话"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12"
            ><el-form-item label="排序" prop="sortNo">
              <el-input-number
                v-model="form.sortNo"
                controls-position="right"
                :min="1"
                style="width: 100%"
              /> </el-form-item
          ></el-col>
          <el-col :span="12"
            ><el-form-item label="备注" prop="remark">
              <el-input
                v-model="form.remark"
                placeholder="请输入备注"
              /> </el-form-item
          ></el-col>
        </el-row>
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
  listProduct,
  getProduct,
  delProduct,
  addProduct,
  updateProduct,
} from "@/api/system/product";

export default {
  name: "Product",
  dicts: [
    "sys_product_tag",
    "sys_product_industry",
    "sys_yes_no",
    "sys_audit_status",
  ],
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
      // 产品管理表格数据
      productList: [],
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
        productIndustry: null,
        productTag: null,
        productBigTitle: null,
        productSmallTitle: null,
        productIcon: null,
        productThImg: null,
        productBgImg: null,
        productDesc: null,
        productContent: null,
        prodcutUrl: null,
        isBuild: null,
        isOpen: null,
        isShow: null,
        isHot: null,
        contact: null,
        contactCode: null,
        sortNo: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productIndustry: [
          { required: true, message: "行业不能为空", trigger: "change" },
        ],
        productTag: [
          { required: true, message: "标签不能为空", trigger: "change" },
        ],
        productBigTitle: [
          { required: true, message: "大标题不能为空", trigger: "blur" },
        ],
        productSmallTitle: [
          { required: true, message: "小标题不能为空", trigger: "blur" },
        ],
        isBuild: [{ required: true, message: "在建不能为空", trigger: "blur" }],
        isOpen: [{ required: true, message: "开源不能为空", trigger: "blur" }],
        isShow: [{ required: true, message: "展示不能为空", trigger: "blur" }],
        isHot: [{ required: true, message: "热点不能为空", trigger: "blur" }],
        sortNo: [{ required: true, message: "排序不能为空", trigger: "blur" }],
        status: [{ required: true, message: "状态不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询产品管理列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then((response) => {
        this.productList = response.rows;
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
        productIndustry: null,
        productTag: null,
        productBigTitle: null,
        productSmallTitle: null,
        productIcon: null,
        productThImg: null,
        productBgImg: null,
        productDesc: null,
        productContent: null,
        prodcutUrl: null,
        isBuild: "0",
        isOpen: "0",
        isShow: "0",
        isHot: "0",
        contact: null,
        contactCode: null,
        sortNo: null,
        status: "0",
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
      this.title = "添加产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getProduct(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateProduct(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduct(this.form).then((response) => {
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
          return delProduct(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    handleRowClick(row, column, event) {
      this.$refs.list.toggleRowSelection(row);
    },
  },
};
</script>
