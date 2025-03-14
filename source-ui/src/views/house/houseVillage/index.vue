<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:houseVillage:add']"
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
          v-hasPermi="['system:houseVillage:edit']"
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
          v-hasPermi="['system:houseVillage:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:houseVillage:export']"
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
        <el-form-item label="名称" prop="name" class="el-form-search-item">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入名称"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="代号" prop="code" class="el-form-search-item">
          <el-input
            v-model="queryParams.code"
            placeholder="请输入代号"
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
      :data="houseVillageList"
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
      <el-table-column label="名称" align="center" prop="name" width="180" />
      <el-table-column
        label="区域"
        align="center"
        prop="areaCode"
        width="200"
      />
      <el-table-column label="年份" align="center" prop="year" width="80" />
      <el-table-column
        label="建筑类型"
        align="center"
        prop="type"
        width="120"
      />
      <el-table-column label="绿化率" align="center" prop="green" width="80" />
      <el-table-column
        label="介绍"
        align="center"
        prop="introduce"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="备注" align="center" prop="remark" width="120" />
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
            v-hasPermi="['system:houseVillage:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:houseVillage:remove']"
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

    <!-- 添加或修改房源小区对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="省" prop="province">
          <el-input v-model="form.province" placeholder="请输入省" />
        </el-form-item>
        <el-form-item label="市" prop="city">
          <el-input v-model="form.city" placeholder="请输入市" />
        </el-form-item>
        <el-form-item label="区" prop="area">
          <el-input v-model="form.area" placeholder="请输入区" />
        </el-form-item> -->
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="区域">
          <el-cascader
            style="width: 100%"
            :options="options"
            v-model="selectedOptions"
            @change="addressChoose"
            placeholder="请选择区域"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="年份" prop="year">
          <el-input v-model="form.year" placeholder="请输入年份" />
        </el-form-item>
        <el-form-item label="建筑类型" prop="green">
          <el-input v-model="form.type" placeholder="请输入建筑类型" />
        </el-form-item>
        <el-form-item label="绿化率" prop="green">
          <el-input v-model="form.green" placeholder="请输入绿化率" />
        </el-form-item>
        <el-form-item label="介绍" prop="introduce">
          <el-input
            v-model="form.introduce"
            type="textarea"
            placeholder="请输入介绍"
            :autosize="{ minRows: 4, maxRows: 4 }"
            :style="{ width: '100%' }"
          ></el-input>
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
  provinceAndCityData,
  regionData,
  provinceAndCityDataPlus,
  regionDataPlus,
  CodeToText,
  TextToCode,
} from "element-china-area-data";
import {
  listHouseVillage,
  getHouseVillage,
  delHouseVillage,
  addHouseVillage,
  updateHouseVillage,
} from "@/api/house/houseVillage";

export default {
  name: "HouseVillage",
  dicts: ["sys_common_status"],
  data() {
    return {
      // 省市区级联
      options: regionDataPlus,
      selectedOptions: [],
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
      // 房源小区表格数据
      houseVillageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        name: null,
        code: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        province: [{ required: true, message: "省不能为空", trigger: "blur" }],
        city: [{ required: true, message: "市不能为空", trigger: "blur" }],
        area: [{ required: true, message: "区不能为空", trigger: "blur" }],
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /**省市区三级联动 */
    addressChoose(value) {
      console.log(
        "省市区：",
        CodeToText[value[0]],
        CodeToText[value[1]],
        CodeToText[value[2]]
      );
      this.form.province = CodeToText[value[0]];
      this.form.city = CodeToText[value[1]];
      this.form.area = CodeToText[value[2]];
    },
    /** 查询房源小区列表 */
    getList() {
      this.loading = true;
      listHouseVillage(this.queryParams).then((response) => {
        this.houseVillageList = response.rows;
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
        province: null,
        city: null,
        area: null,
        name: null,
        code: null,
        year: null,
        type: null,
        green: null,
        introduce: null,
        wayState: 0,
        wayCode: null,
        waySpace: null,
        lon: null,
        lat: null,
        state: 0,
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
      this.selectedOptions = [];
      this.reset();
      this.open = true;
      this.title = "添加小区";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getHouseVillage(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改小区";
        // 省市区转换
        let province = this.form.province;
        let city = this.form.city;
        let area = this.form.area;
        /* console.log(this.selectedOptions);
        console.log(province);
        console.log(city);
        console.log(area);
        console.log(TextToCode[province].code);
        console.log(TextToCode[province][city].code);
        console.log(TextToCode[province][city][area].code); */
        let arg1 = TextToCode[province].code;
        let arg2 = TextToCode[province][city].code;
        let arg3 = TextToCode[province][city][area].code;
        this.selectedOptions = [];
        this.selectedOptions.push(arg1, arg2, arg3);
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.selectedOptions.length != 3) {
            return this.$modal.msgWarning("请选择区域");
          }
          if (this.form.id != null) {
            updateHouseVillage(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHouseVillage(this.form).then((response) => {
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
          return delHouseVillage(ids);
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
        "system/houseVillage/export",
        {
          ...this.queryParams,
        },
        `houseVillage_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
