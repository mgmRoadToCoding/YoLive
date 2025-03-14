<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:goods:add']"
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
          v-hasPermi="['system:goods:edit']"
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
          v-hasPermi="['system:goods:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:goods:export']"
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
          label="商品分类"
          prop="goodsClassify"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.goodsClassify"
            placeholder="请输入商品分类"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item
          label="商品名称"
          prop="goodsName"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.goodsName"
            placeholder="请输入商品名称"
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
      :data="goodsList"
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
        label="商品分类"
        align="center"
        prop="goodsClassify"
        width="150"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="商品名称"
        align="center"
        prop="goodsName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="卖点"
        align="center"
        prop="goodsFeature"
        width="150"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="销售价"
        align="center"
        prop="newPrice"
        width="100"
      />
      <el-table-column
        label="划线价"
        align="center"
        prop="oldPrice"
        width="100"
      />
      <el-table-column
        label="库存"
        align="center"
        prop="goodsStock"
        width="100"
      />
      <el-table-column
        label="主图"
        align="center"
        prop="goodsFaceUrl"
        width="100"
      >
        <template slot-scope="scope">
          <image-preview
            :src="scope.row.goodsFaceUrl"
            :width="30"
            :height="30"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="浏览量"
        align="center"
        prop="goodsView"
        width="80"
      />
      <el-table-column
        label="是否上架"
        align="center"
        prop="status"
        width="100"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="是否热门"
        align="center"
        prop="hotStatus"
        width="100"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.hotStatus"
            active-value="0"
            inactive-value="1"
            @change="handleHotStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
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
            v-hasPermi="['system:goods:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:goods:remove']"
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

    <!-- 添加或修改商品管理对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品分类" prop="goodsClassify">
              <treeselect
                v-model="form.goodsClassify"
                :options="classifyOptions"
                :normalizer="normalizer"
                placeholder="请选择商品分类"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="商品名称" prop="goodsName">
              <el-input
                v-model="form.goodsName"
                placeholder="请输入商品名称"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="卖点" prop="goodsFeature">
              <el-input
                v-model="form.goodsFeature"
                placeholder="产品卖点或简要介绍，10-30字最佳"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="销售价" prop="newPrice">
              <el-input v-model="form.newPrice" placeholder="请输入销售价" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12"
            ><el-form-item label="划线价" prop="oldPrice">
              <el-input v-model="form.oldPrice" placeholder="请输入划线价" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存" prop="goodsStock">
              <el-input
                v-model="form.goodsStock"
                placeholder="请输入库存"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-form-item label="主图" prop="goodsFaceUrl">
          <image-upload v-model="form.goodsFaceUrl" :limit="1" />
        </el-form-item>
        <el-form-item label="轮播图" prop="goodsItemUrl">
          <image-upload v-model="form.goodsItemUrl" :limit="8" />
        </el-form-item>
        <el-form-item label="图文详情">
          <editor v-model="form.articleContent" :min-height="192" />
        </el-form-item>
        <el-form-item label="是否上架">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.mall_goods_shelf"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否热门">
          <el-radio-group v-model="form.hotStatus">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <!-- <el-divider content-position="center">商品规格信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAddMallGoodsSpec"
              >添加</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="handleDeleteMallGoodsSpec"
              >删除</el-button
            >
          </el-col>
        </el-row>
        <el-table
          :data="mallGoodsSpecList"
          :row-class-name="rowMallGoodsSpecIndex"
          @selection-change="handleMallGoodsSpecSelectionChange"
          ref="mallGoodsSpec"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="名称" prop="specName" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.specName" placeholder="请输入名称" />
            </template>
          </el-table-column>
          <el-table-column label="销售价" prop="specPrice" align="center">
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.specPrice"
                placeholder="请输入销售价"
              />
            </template>
          </el-table-column>
          <el-table-column label="库存" prop="specStock" align="center">
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.specStock"
                placeholder="请输入库存"
              />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注" />
            </template>
          </el-table-column>
        </el-table> -->
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
  listGoods,
  getGoods,
  delGoods,
  addGoods,
  updateGoods,
  changeStatus,
  changeHotStatus,
} from "@/api/mall/goods";
import { listClassify } from "@/api/mall/classify";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Goods",
  dicts: ["mall_goods_shelf", "sys_yes_no"],
  components: {
    Treeselect,
  },
  data() {
    return {
      // 分类管理树选项
      classifyOptions: [],
      // 表格高度
      tableHeight: document.documentElement.clientHeight - 180,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMallGoodsSpec: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品管理表格数据
      goodsList: [],
      // 商品规格表格数据
      mallGoodsSpecList: [],
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
        goodsClassify: null,
        goodsName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        goodsClassify: [
          { required: true, message: "商品分类不能为空", trigger: "blur" },
        ],
        goodsName: [
          { required: true, message: "商品名称不能为空", trigger: "blur" },
        ],
        newPrice: [
          { required: true, message: "销售价不能为空", trigger: "blur" },
        ],
        goodsStock: [
          { required: true, message: "库存不能为空", trigger: "blur" },
        ],
        goodsFaceUrl: [
          { required: true, message: "主图不能为空", trigger: "blur" },
        ],
        goodsItemUrl: [
          { required: true, message: "轮播图不能为空", trigger: "blur" },
        ],
        articleContent: [
          { required: true, message: "图文详情不能为空", trigger: "blur" },
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
    getClassifyList() {
      listClassify().then((response) => {
        this.classifyOptions = this.handleTree(response.data, "id", "parentId");
      });
    },
    /** 转换分类管理数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.classifyName,
        label: node.classifyName,
        children: node.children,
      };
    },
    /** 查询商品管理列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then((response) => {
        this.goodsList = response.rows;
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
        goodsClassify: null,
        goodsName: null,
        goodsFeature: null,
        newPrice: null,
        oldPrice: null,
        goodsStock: null,
        goodsFaceUrl: null,
        goodsItemUrl: null,
        articleContent: null,
        sortNo: null,
        status: "0",
        hotStatus: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
      };
      this.mallGoodsSpecList = [];
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
      this.getClassifyList();
      this.open = true;
      this.title = "添加商品管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getClassifyList();
      const id = row.id || this.ids;
      getGoods(id).then((response) => {
        this.form = response.data;
        this.mallGoodsSpecList = response.data.mallGoodsSpecList;
        this.open = true;
        this.title = "修改商品管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.mallGoodsSpecList = this.mallGoodsSpecList;
          if (this.form.id != null) {
            updateGoods(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGoods(this.form).then((response) => {
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
          return delGoods(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 商品规格序号 */
    rowMallGoodsSpecIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 商品规格添加按钮操作 */
    handleAddMallGoodsSpec() {
      let obj = {};
      obj.specName = "";
      obj.specUrl = "";
      obj.specPrice = "";
      obj.specStock = "";
      obj.sortNo = "";
      obj.normalDisable = "";
      obj.remark = "";
      this.mallGoodsSpecList.push(obj);
    },
    /** 商品规格删除按钮操作 */
    handleDeleteMallGoodsSpec() {
      if (this.checkedMallGoodsSpec.length == 0) {
        this.$modal.msgError("请先选择要删除的商品规格数据");
      } else {
        const mallGoodsSpecList = this.mallGoodsSpecList;
        const checkedMallGoodsSpec = this.checkedMallGoodsSpec;
        this.mallGoodsSpecList = mallGoodsSpecList.filter(function (item) {
          return checkedMallGoodsSpec.indexOf(item.index) == -1;
        });
      }
    },
    /** 复选框选中数据 */
    handleMallGoodsSpecSelectionChange(selection) {
      this.checkedMallGoodsSpec = selection.map((item) => item.index);
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/goods/export",
        {
          ...this.queryParams,
        },
        `goods_${new Date().getTime()}.xlsx`
      );
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "上架" : "下架";
      this.$modal
        .confirm("确认要" + text + "吗？")
        .then(function () {
          return changeStatus(row.id, row.status);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 状态修改
    handleHotStatusChange(row) {
      let text = row.hotStatus === "0" ? "上热门" : "下热门";
      this.$modal
        .confirm("确认要" + text + "吗？")
        .then(function () {
          return changeHotStatus(row.id, row.hotStatus);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.hotStatus = row.hotStatus === "0" ? "1" : "0";
        });
    },
  },
};
</script>
