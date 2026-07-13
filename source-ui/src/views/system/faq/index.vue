<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button plain icon="el-icon-plus" size="mini" @click="handleAdd"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <el-table
      :height="tableHeight"
      v-loading="loading"
      :data="faqList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="#" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="图标" align="center" prop="icon" width="60">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.faqIcon"
            :src="scope.row.faqIcon"
            :preview-src-list="[scope.row.faqIcon]"
            fit="cover"
            style="width: 36px; height: 36px; border-radius: 4px"
          >
            <div slot="error" style="font-size: 12px; color: #999">-</div>
          </el-image>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column
        label="名称"
        align="center"
        prop="faqName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="链接"
        align="center"
        prop="faqLink"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <a
            :href="scope.row.faqLink"
            target="_blank"
            rel="noopener noreferrer"
            >{{ scope.row.faqLink }}</a
          >
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" width="50" />
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
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增常见问题对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="50px">
        <el-form-item label="图标" prop="iconFile">
          <el-upload
            :auto-upload="false"
            :limit="1"
            list-type="picture-card"
            accept="image/*"
            :file-list="iconFileList"
            :on-change="handleIconChange"
            :on-remove="handleIconRemove"
            :on-exceed="handleIconExceed"
            :multiple="false"
            :class="{ hide: iconFileList.length >= 1 }"
          >
            <i class="el-icon-plus"></i>
            <div slot="tip" class="el-upload__tip">
              仅支持图片文件，大小≤2MB
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="名称" prop="faqName">
          <el-input v-model="form.faqName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="链接" prop="faqLink">
          <el-input v-model="form.faqLink" placeholder="请输入链接" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number
            v-model="form.sort"
            :min="0"
            :max="999999"
            controls-position="right"
          />
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
import { listFaq, addFaq, delFaq } from "@/api/system/faq";

export default {
  name: "Faq",
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
      // 列表数据
      faqList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {
        id: null,
        iconFile: null,
        faqName: "",
        faqLink: "",
        sort: 0,
      },
      // 上传文件列表（用于展示缩略图）
      iconFileList: [],
      // 表单校验
      rules: {
        iconFile: [
          {
            validator: (rule, value, callback) => {
              if (!this.form.iconFile) {
                callback(new Error("图标不能为空"));
              } else {
                callback();
              }
            },
            trigger: "change",
          },
        ],
        faqName: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        faqLink: [
          { required: true, message: "链接不能为空", trigger: "blur" },
          {
            pattern: /^(https?:\/\/)[^\s]+$/i,
            message: "链接需以 http:// 或 https:// 开头",
            trigger: "blur",
          },
        ],
        sort: [{ required: true, message: "排序不能为空", trigger: "change" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询常见问题列表 */
    getList() {
      this.loading = true;
      listFaq()
        .then((response) => {
          // 兼容不同返回结构：rows / data / array
          const rows = Array.isArray(response)
            ? response
            : response?.rows || response?.data || [];
          // 按 sort 字段展示（若后端已排序则此步不会改变）
          this.faqList = rows
            .slice()
            .sort((a, b) => (a.sort || 0) - (b.sort || 0));
          this.loading = false;
        })
        .catch(() => {
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
        iconFile: null,
        faqName: "",
        faqLink: "",
        sort: 0,
      };
      this.iconFileList = [];
      this.resetForm("form");
    },
    // 选择图标文件
    handleIconChange(file, fileList) {
      const isImage =
        file.raw && file.raw.type && file.raw.type.indexOf("image") > -1;
      const isLt2M = file.raw && file.raw.size / 1024 / 1024 < 2;
      if (!isImage) {
        this.$message.error("请上传图片文件");
        fileList.splice(0, fileList.length);
        this.form.iconFile = null;
      } else if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB");
        fileList.splice(0, fileList.length);
        this.form.iconFile = null;
      } else {
        this.form.iconFile = file.raw;
      }
      this.iconFileList = fileList.slice(0, 1);
      this.$nextTick(() => {
        this.$refs["form"] && this.$refs["form"].validateField("iconFile");
      });
    },
    // 超出上传数量
    handleIconExceed(files, fileList) {
      this.$message.error("最多上传 1 张图片");
      // 保留最新选择的一张
      const latest = files && files.length ? files[files.length - 1] : null;
      if (latest && latest.raw) {
        this.form.iconFile = latest.raw;
        this.iconFileList = [latest];
      } else {
        this.iconFileList = fileList.slice(0, 1);
      }
    },
    // 删除图标文件
    handleIconRemove(file, fileList) {
      this.form.iconFile = null;
      this.iconFileList = [];
      this.$nextTick(() => {
        this.$refs["form"] && this.$refs["form"].validateField("iconFile");
      });
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
      this.title = "新增常见问题";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (!valid) return;
        addFaq(this.form).then(() => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row?.id ? [row.id] : this.ids;
      if (!ids || ids.length === 0) return;
      this.$modal
        .confirm("是否确认删除记录？")
        .then(() => {
          // 逐个删除，兼容后端不支持批量删除的情况
          const queue = ids.map((id) => delFaq(id));
          return Promise.all(queue);
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

<style scoped>
.app-container {
  padding-bottom: 10px;
}
/* 达到上传数量上限时隐藏加号按钮 */
::v-deep .hide .el-upload--picture-card {
  display: none;
}
</style>
