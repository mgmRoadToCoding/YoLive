<template>
  <view class="forum-list">
    <view v-if="loading" class="loading">加载中...</view>
    <view v-else-if="!list.length" class="empty">暂无帖子</view>

    <view v-else class="list-wrap">
      <view class="post-card" v-for="post in list" :key="post.id" @click="openDetail(post)">
        <view class="header">
          <text class="title">{{ post.title }}</text>
          <text v-if="post.time" class="time">{{ post.time }}</text>
        </view>
        <view class="content">{{ post.content }}</view>
        <scroll-view v-if="post.images && post.images.length" scroll-x class="images">
          <view class="img-wrap" v-for="(img, i) in post.images" :key="i">
            <image :src="img" mode="aspectFill" class="img" />
          </view>
        </scroll-view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      list: [],
      loading: false,
    }
  },
  onShow() {
    this.fetchApprovedPosts()
  },
  onPullDownRefresh() {
    this.fetchApprovedPosts(true)
  },
  methods: {
    normalizePost(item) {
      const imagesStr = item.images || ''
      const images = imagesStr
        ? imagesStr.split(',').map((s) => s.trim()).filter(Boolean)
        : []
      return {
        id: item.id || item.postId || item.ids,
        title: item.title || '',
        content: item.content || '',
        images,
        time: item.createTime || item.time || '',
      }
    },
    async fetchApprovedPosts(isRefresh = false) {
      this.loading = !isRefresh
      try {
        // 后端接口按文档已审核列表：GET /forum/posts
        const res = await this.$u.http.get('/forum/posts')
        if (res && res.code === 200) {
          const rows = res.data || res.rows || res.list || []
          this.list = rows.map(this.normalizePost)
        } else {
          this.$mytip.toast(res?.msg || '获取失败')
        }
      } catch (err) {
        this.$mytip.toast('获取异常，请稍后重试')
      } finally {
        if (isRefresh) uni.stopPullDownRefresh()
        this.loading = false
      }
    },
    openDetail(post) {
      const id = post.id
      if (!id) return
      this.$u.route('/pages/forum/detail', { id })
    },
  },
}
</script>

<style lang="scss" scoped>
.forum-list {
  background: #fff;
  min-height: 100vh;
}
.loading,
.empty {
  padding: 40rpx;
  color: #909399;
}
.list-wrap {
  padding: 20rpx 30rpx;
}
.post-card {
  border: 1px solid #ebeef5;
  border-radius: 12rpx;
  padding: 20rpx;
  margin-bottom: 24rpx;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.title {
  font-size: 30rpx;
  font-weight: 500;
}
.time {
  font-size: 24rpx;
  color: #909399;
}
.content {
  margin-top: 12rpx;
  color: #606266;
  line-height: 1.6;
}
.images {
  margin-top: 16rpx;
  white-space: nowrap;
}
.img-wrap {
  display: inline-block;
  margin-right: 12rpx;
}
.img {
  width: 200rpx;
  height: 200rpx;
  border-radius: 8rpx;
  background: #f5f7fa;
}
</style>