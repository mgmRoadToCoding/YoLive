<template>
  <view class="my-container">
    <view class="header">
      <text class="header-title">My Posts</text>
    </view>

    <view v-if="loading" class="loading">加载中...</view>
    <view v-else-if="!list.length" class="empty">暂无发布内容</view>

    <view v-else class="list">
      <view class="post-card" v-for="post in list" :key="post.id">
        <view class="post-header">
          <text class="title">{{ post.title }}</text>
          <text v-if="post.statusText" class="status">{{ post.statusText }}</text>
        </view>
        <view class="content">{{ post.content }}</view>

        <scroll-view v-if="post.images && post.images.length" scroll-x class="images">
          <view class="img-wrap" v-for="(img, i) in post.images" :key="i">
            <image :src="img" mode="aspectFill" class="img" />
          </view>
        </scroll-view>

        <view class="ops">
          <u-button type="error" size="mini" @click="confirmDelete(post.id)">Delete</u-button>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import UButton from '@/uview-ui/components/u-button/u-button.vue'

export default {
  components: {
    'u-button': UButton,
  },
  data() {
    return {
      list: [],
      loading: false,
    }
  },
  onShow() {
    this.init()
  },
  onPullDownRefresh() {
    this.fetchMyPosts(true)
  },
  methods: {
    async init() {
      const authed = await this.$checkAuth()
      if (!authed) return
      this.fetchMyPosts()
    },
    async fetchMyPosts(isRefresh = false) {
      this.loading = !isRefresh
      try {
        const res = await this.$u.http.get('/forum/my/posts')
        if (res && res.code === 200) {
          const rows = res.data || res.rows || res.list || []
          this.list = rows.map(this.normalizePost)
        } else {
          this.$mytip.toast(res?.msg || '获取列表失败')
        }
      } catch (err) {
        this.$mytip.toast('获取异常，请稍后重试')
      } finally {
        if (isRefresh) uni.stopPullDownRefresh()
        this.loading = false
      }
    },
    normalizePost(item) {
      const imagesStr = item.images || ''
      const images = imagesStr
        ? imagesStr.split(',').map((s) => s.trim()).filter(Boolean)
        : []
      const statusText = this.mapStatus(item.status)
      return {
        id: item.id || item.postId || item.ids,
        title: item.title || '',
        content: item.content || '',
        images,
        statusText,
      }
    },
    mapStatus(st) {
      if (st === 1 || st === '1') return 'Approved'
      if (st === 0 || st === '0') return 'Pending Review'
      if (st === 2 || st === '2') return 'Rejected'
      return ''
    },
    confirmDelete(id) {
      uni.showModal({
        title: 'Delete Confirmation',
        content: 'Are you sure you want to delete this post?',
        success: async (res) => {
          if (res.confirm) {
            try {
              const r = await this.$u.http.delete(`/forum/posts/${id}`)
              if (r && r.code === 200) {
                this.$mytip.toast('Deleted Successfully')
                this.list = this.list.filter((p) => p.id !== id)
              } else {
                this.$mytip.toast(r?.msg || 'Delete Failed')
              }
            } catch (e) {
              this.$mytip.toast('删除异常，请稍后重试')
            }
          }
        },
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.my-container {
  background: #fff;
  min-height: 100vh;
}
.header {
  padding: 20rpx 30rpx;
  border-bottom: 1px solid #f0f0f0;
}
.header-title {
  font-size: 32rpx;
  font-weight: 600;
}
.loading,
.empty {
  padding: 40rpx;
  color: #909399;
}
.list {
  padding: 20rpx 30rpx;
}
.post-card {
  border: 1px solid #ebeef5;
  border-radius: 12rpx;
  padding: 20rpx;
  margin-bottom: 24rpx;
}
.post-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.title {
  font-size: 30rpx;
  font-weight: 500;
}
.status {
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
.ops {
  margin-top: 16rpx;
  display: flex;
  justify-content: flex-end;
}
</style>