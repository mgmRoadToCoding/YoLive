<template>
  <view class="faq-manage">
    <view class="faq-top">
      <view class="faq-grid">
        <view
          class="faq-item"
          v-for="item in sortedFaqs"
          :key="item.id"
          @click="openLink(item)"
        >
          <view class="icon-wrap">
            <u-image
              :src="item.faqIcon"
              width="100rpx"
              height="100rpx"
              mode="aspectFill"
              :lazy-load="true"
            ></u-image>
          </view>
          <view class="name">{{ item.faqName }}</view>
        </view>
      </view>
    </view>

    <u-section
      title="Featured Posts"
      @click="gotoForumList"
      sub-title="More"
      class="section"
    ></u-section>
    <view class="forum-top">
      <view v-if="forumLoading" class="loading">加载中...</view>
      <view v-else-if="!forumTop.length" class="empty">暂无帖子</view>
      <view v-else class="list">
        <u-card
          v-for="(p, i) in forumTop"
          :key="p.id || i"
          @click="gotoDetail(p)"
          :title="p.title"
          class="list-card"
        >
          <view slot="body">
            <view class="summary">{{ p.content }}</view>
            <scroll-view
              v-if="p.images && p.images.length"
              scroll-x
              class="images"
            >
              <view class="img-wrap" v-for="(img, j) in p.images" :key="j">
                <image :src="img" mode="aspectFill" class="img" />
              </view>
            </scroll-view>
          </view>
        </u-card>
      </view>
    </view>
  </view>
</template>

<script>
const faqApi = {
  list() {
    return uni.$u.http.get(BASE_FAQ)
  },
}
export default {
  data() {
    return {
      faqs: [],
      defaultIcon: '/static/empty/default.png',
      forumTop: [],
      forumLoading: false,
    }
  },
  computed: {
    sortedFaqs() {
      return [...this.faqs].sort((a, b) => (a.sort || 0) - (b.sort || 0))
    },
  },
  created() {
    this.loadFaqs()
    this.loadForumTop()
  },
  methods: {
    isUrl(str) {
      return /^https?:\/\//.test(str || '')
    },
    async loadFaqs() {
      const res = await this.$u.http.get('/system/faq/list')
      const list = res?.data || res?.list || []
      this.faqs = Array.isArray(list) ? list : []
      if (!this.faqs.length) throw new Error('empty')
    },
    normalizePost(item) {
      const imagesStr = item.images || ''
      const images = imagesStr
        ? imagesStr
            .split(',')
            .map((s) => s.trim())
            .filter(Boolean)
        : []
      return {
        id: item.id || item.postId || item.ids,
        title: item.title || '',
        content: item.content || '',
        images,
      }
    },
    async loadForumTop() {
      this.forumLoading = true
      try {
        const res = await this.$u.http.get('/forum/posts')
        const rows = res?.data || res?.rows || res?.list || []
        const list = Array.isArray(rows) ? rows : []
        this.forumTop = list.slice(0, 10).map(this.normalizePost)
      } catch (e) {
        this.forumTop = []
      } finally {
        this.forumLoading = false
      }
    },
    gotoForumList() {
      this.$u.route('/pages/forum/list')
    },
    gotoDetail(p) {
      console.log(p)

      const id = p.id
      if (!id) return
      this.$u.route('/pages/forum/detail', { id })
    },
    openLink(item) {
      const url = item.faqLink || item.link
      if (!url) return
    
      // H5
      // #ifdef H5
      window.open(url, '_blank')
      return
      // #endif
    
      // APP-PLUS
      // #ifdef APP-PLUS
      if (typeof plus !== 'undefined') {
        plus.runtime.openURL(url)
      }
      // #endif
    },
  },
}
</script>

<style lang="scss" scoped>
.faq-top {
  padding: 16rpx 24rpx;
}
.faq-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr); /* 一排五个 */
  gap: 12rpx; /* 行列间隙 */
}
.faq-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10rpx 8rpx; /* 适度内间隙 */
  background: transparent; /* 无背景 */
}
.icon-wrap {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100rpx;
  height: 100rpx;
  background: transparent; /* 去除背景色 */
  border-radius: 0; /* 无圆角 */
  margin: 0 auto 12rpx;
}
.name {
  text-align: center;
  font-size: 24rpx;
  margin-top: 8rpx;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.section {
  padding: 0 24rpx;
  margin-top: 12rpx;
}
.list-card {
  margin: 0 !important;
  margin-top: 24rpx !important;
}
.forum-top {
  padding: 0 24rpx 24rpx;
}
.loading,
.empty {
  padding: 12rpx 0 24rpx;
  color: #909399;
}
.summary {
  color: #606266;
}
.images {
  margin-top: 12rpx;
  white-space: nowrap;
}
.img-wrap {
  display: inline-block;
  margin-right: 12rpx;
}
.img {
  width: 180rpx;
  height: 180rpx;
  border-radius: 8rpx;
  background: #f5f7fa;
}
.article-list {
  padding: 0 24rpx 24rpx;
}
.card-footer {
  display: flex;
  justify-content: flex-end;
}
.ml10 {
  margin-left: 16rpx;
}
</style>
