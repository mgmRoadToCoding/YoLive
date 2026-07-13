<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="70px"
        class="el-form-search"
      >
        <el-form-item label="标题" prop="title" class="el-form-search-item">
          <el-input
            v-model="queryParams.title"
            placeholder="请输入标题关键字"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status" class="el-form-search-item">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="mini">
            <el-option :value="0" label="待审核" />
            <el-option :value="1" label="已通过" />
            <el-option :value="2" label="已驳回" />
          </el-select>
        </el-form-item>
        <el-form-item class="el-form-search-item">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      :height="tableHeight"
      v-loading="loading"
      :data="postList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="#" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title" width="240" />
      <el-table-column label="内容" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="120">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row)">{{ statusLabel(scope.row) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" prop="images" width="120">
        <template slot-scope="scope">
          <image-preview v-if="firstImage(scope.row)" :src="firstImage(scope.row)" :width="40" :height="40" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <template v-if="isPending(scope.row)">
            <el-button size="mini" type="text" icon="el-icon-circle-check" @click="handleApprove(scope.row)">审核通过</el-button>
            <el-button size="mini" type="text" icon="el-icon-close" @click="handleReject(scope.row)">审核驳回</el-button>
          </template>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
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
  </div>
 </template>

<script>
import { listAdminPosts, approvePost, rejectPost, deletePost } from '@/api/system/forum'

export default {
  name: 'ForumAdmin',
  data() {
    return {
      tableHeight: document.documentElement.clientHeight - 180,
      loading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      postList: [],
      // 取消仅待审核开关，统一用 status 筛选
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        orderByColumn: 'create_time',
        isAsc: 'desc',
        title: null,
        status: null,
      },
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 列表查询 */
    getList() {
      this.loading = true
      listAdminPosts(this.queryParams).then((response) => {
        // 与后端分页结构保持一致（rows/total）
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    /** 搜索 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        orderByColumn: 'create_time',
        isAsc: 'desc',
        title: null,
        status: null,
      }
      this.$refs['queryForm'] && this.$refs['queryForm'].resetFields()
      this.getList()
    },
    /** 多选 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 审核通过 */
    handleApprove(row) {
      const id = row.id || this.ids
      this.$modal
        .confirm('是否确认审核通过？')
        .then(() => approvePost(id))
        .then(() => {
          this.$modal.msgSuccess('已通过')
          this.getList()
        })
        .catch(() => {})
    },
    /** 审核驳回 */
    handleReject(row) {
      const id = row.id || this.ids
      this.$modal
        .confirm('是否确认审核驳回？')
        .then(() => rejectPost(id))
        .then(() => {
          this.$modal.msgSuccess('已驳回')
          this.getList()
        })
        .catch(() => {})
    },
    /** 删除 */
    handleDelete(row) {
      const id = row.id || this.ids
      this.$modal.confirm('是否确认删除该帖子？').then(() => {
        return deletePost(id)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      }).catch(() => {})
    },
    /** 解析首图 */
    firstImage(row) {
      if (!row || !row.images) return null
      const list = String(row.images).split(',').filter(Boolean)
      return list.length ? list[0] : null
    },
    /** 状态标签 */
    statusLabel(row) {
      const s = row && (row.status === 0 || row.status === '0' ? 0 : row.status === 1 || row.status === '1' ? 1 : row.status === 2 || row.status === '2' ? 2 : row.status)
      if (s === 0) return '待审核'
      if (s === 1) return '已通过'
      if (s === 2) return '已驳回'
      return s !== undefined && s !== null ? String(s) : '-'
    },
    statusTagType(row) {
      const s = row && (row.status === 0 || row.status === '0' ? 0 : row.status === 1 || row.status === '1' ? 1 : row.status === 2 || row.status === '2' ? 2 : row.status)
      if (s === 0) return 'warning'
      if (s === 1) return 'success'
      if (s === 2) return 'danger'
      return 'info'
    },
    /** 是否待审核，仅待审核显示操作按钮 */
    isPending(row) {
      return row && (row.status === 0 || row.status === '0')
    }
  }
}
 </script>

<style scoped>
.el-form-search { margin-bottom: 10px; }
.el-form-search-item { margin-right: 10px; }
</style>
