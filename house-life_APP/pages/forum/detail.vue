<template>
  <view class="forum-detail">
    <view v-if="loading" class="loading">加载中...</view>
    <view v-else>
      <view class="card">
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

      <u-section :title="$t('comment')" :right="false" class="section"></u-section>

      <view class="comment-list">
        <view v-if="commentLoading" class="loading">评论加载中...</view>
        <u-empty v-else-if="!comments.length" text="暂无评论"></u-empty>
        <view v-else>
          <view class="comment-item" v-for="c in comments" :key="c.id">
            <view class="c-header">
              <text class="c-user">{{ c.user || '用户' }}</text>
              <text class="c-time">{{ c.time }}</text>
            </view>
            <view class="c-content">{{ c.content }}</view>
          </view>
        </view>
      </view>
    </view>

    <!-- 评论弹窗 -->
    <u-popup v-model="commentPopup" mode="bottom" border-radius="16">
      <view class="comment-popup">
        <u-input
          v-model="commentText"
          type="textarea"
          :border="true"
          :placeholder="$t('commentPlaceholder')"
          height="140"
          :auto-height="true"
        />
        <view class="popup-actions">
          <u-button type="primary" size="small" class="btn" :loading="submitting" @click="submitComment">{{ $t('submit') }}</u-button>
        </view>
      </view>
    </u-popup>

    <!-- 底部操作栏 -->
    <view class="bottom-bar safe-area-bottom">
      <view class="bar-item" @click="sharePost">
        <u-icon name="share" size="36"></u-icon>
        <text class="label">{{ $t('share') || '分享' }}</text>
      </view>
      <view class="bar-item" @click="openCommentPopup">
        <u-icon name="chat" size="36"></u-icon>
        <text class="label">{{ $t('comment') }}</text>
      </view>
      <view class="bar-item" @click="toggleFavorite">
        <u-icon :name="isFavorited ? 'star-fill' : 'star'" size="36" :color="isFavorited ? '#fa6400' : '#606266'"></u-icon>
        <text class="label">{{ isFavorited ? $t('unfavorite') : $t('favorite') }}</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      id: '',
      post: { title: '', content: '', images: [], time: '' },
      loading: false,
      isFavorited: false,
      commentText: '',
      comments: [],
      commentLoading: false,
      submitting: false,
      commentPopup: false,
    }
  },
  onLoad(options) {
    this.id = options?.id || ''
  },
  onShow() {
    // 设置导航标题为当前语言的“帖子详情”
    if (typeof uni !== 'undefined' && uni.setNavigationBarTitle) {
      uni.setNavigationBarTitle({ title: this.$t('postDetail') })
    }
    this.init()
  },
  onPullDownRefresh() {
    this.init(true)
  },
  methods: {
    async init(isRefresh = false) {
      await this.fetchDetail(isRefresh)
      await this.fetchComments(isRefresh)
      await this.checkFavorited()
      if (isRefresh) uni.stopPullDownRefresh()
    },
    normalizePost(item) {
      const imagesStr = item.images || ''
      const images = imagesStr
        ? imagesStr.split(',').map((s) => s.trim()).filter(Boolean)
        : Array.isArray(item.images) ? item.images : []
      return {
        id: item.id || item.postId || item.ids,
        title: item.title || '',
        content: item.content || '',
        images,
        time: item.createTime || item.time || '',
      }
    },
    async fetchDetail(isRefresh = false) {
      if (!this.id) return
      this.loading = !isRefresh
      try {
        const res = await this.$u.http.get(`/forum/posts/${this.id}`)
        const data = res?.data || res
        this.post = this.normalizePost(data || {})
      } catch (e) {
        this.$mytip.toast('获取详情失败')
      } finally {
        this.loading = false
      }
    },
    async fetchComments(isRefresh = false) {
      if (!this.id) return
      this.commentLoading = !isRefresh
      try {
        const res = await this.$u.http.get(`/forum/posts/${this.id}/comments`)
        const rows = res?.data || res?.rows || res?.list || []
        this.comments = (Array.isArray(rows) ? rows : []).map((x) => ({
          id: x.id || x.commentId || x.ids,
          content: x.content || '',
          user: x.userName || x.nickName || x.user || '',
          time: x.createTime || x.time || '',
        }))
      } catch (e) {
        this.comments = []
      } finally {
        this.commentLoading = false
      }
    },
    async checkFavorited() {
      // 若未登录，直接返回未收藏状态
      if (!this.$checkAuth()) return (this.isFavorited = false)
      try {
        const res = await this.$u.http.get('/forum/favorites')
        const rows = res?.data || res?.rows || []
        const ids = (Array.isArray(rows) ? rows : []).map((p) => p.id || p.postId || p.ids)
        this.isFavorited = ids.includes(this.post.id)
      } catch (e) {
        this.isFavorited = false
      }
    },
    async toggleFavorite() {
      if (!this.$checkAuth()) return
      try {
        if (this.isFavorited) {
          await this.$u.http.delete(`/forum/posts/${this.post.id}/favorite`)
          this.isFavorited = false
          this.$mytip.toast('已取消收藏')
        } else {
          await this.$u.http.post(`/forum/posts/${this.post.id}/favorite`)
          this.isFavorited = true
          this.$mytip.toast('已收藏')
        }
      } catch (e) {
        this.$mytip.toast('操作失败，请稍后重试')
      }
    },
    openCommentPopup() {
      this.commentPopup = true
    },
    sharePost() {
      // #ifdef H5
      try {
        const url = window.location.href
        if (navigator.clipboard) {
          navigator.clipboard.writeText(url)
          this.$mytip.toast('链接已复制，可分享给好友')
        } else {
          this.$mytip.toast('请使用浏览器分享功能')
        }
      } catch (e) {
        this.$mytip.toast('分享失败')
      }
      // #endif
    },
    async submitComment() {
      if (!this.$checkAuth()) return
      const content = (this.commentText || '').trim()
      if (!content) return this.$mytip.toast('请输入评论内容')
      this.submitting = true
      try {
        await this.$u.http.post(
          `/forum/posts/${this.post.id}/comments`,
          { content },
          { 'Content-Type': 'application/json' }
        )
        this.$mytip.toast('评论成功')
        this.commentText = ''
        this.commentPopup = false
        await this.fetchComments()
      } catch (e) {
        this.$mytip.toast('发表评论失败')
      } finally {
        this.submitting = false
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.forum-detail {
  background: #fff;
  min-height: 100vh;
  padding-bottom: 120rpx; // 预留底部操作栏空间
}
.loading {
  padding: 40rpx;
  color: #909399;
}
.card {
  border-bottom: 1px solid #ebeef5;
  padding: 20rpx 24rpx;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.title {
  font-size: 32rpx;
  font-weight: 600;
}
.time {
  font-size: 24rpx;
  color: #909399;
}
.content {
  margin-top: 12rpx;
  color: #606266;
  line-height: 1.7;
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
  width: 220rpx;
  height: 220rpx;
  border-radius: 8rpx;
  background: #f5f7fa;
}
.actions {
  padding: 0 24rpx 12rpx;
}
.section {
  padding: 0 24rpx;
  padding-top: 24rpx;
}
.comment-box {
  padding: 12rpx 24rpx 0;
}
.comment-actions {
  margin-top: 12rpx;
}
.comment-list {
  padding: 0 24rpx 24rpx;
}
.comment-item {
  padding: 16rpx 0;
  border-bottom: 1px solid #f0f2f5;
}
.c-header {
  display: flex;
  justify-content: space-between;
  color: #909399;
  font-size: 24rpx;
}
.c-content {
  margin-top: 8rpx;
  color: #303133;
}

/* 弹窗样式 */
.comment-popup {
  padding: 24rpx;
}
.popup-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 12rpx;
}
.btn {
  width: 120rpx;
  padding: 12rpx 0;
  font-size: 24rpx;
}



/* 底部操作栏 */
.bottom-bar {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 96rpx;
  background: #fff;
  border-top: 1px solid #ebeef5;
  z-index: 10;
  padding-bottom: env(safe-area-inset-bottom);
}
.bar-item {
  display: flex;
  align-items: center;
}
.label {
  margin-left: 8rpx;
  color: #606266;
  font-size: 26rpx;
}
</style>
