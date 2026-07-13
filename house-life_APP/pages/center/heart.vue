<template>
  <view class="forum-favorites">
    <view v-if="loading" class="loading">加载中...</view>
    <view v-else-if="!list.length" class="empty">暂无收藏</view>
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
    this.fetchFavorites()
  },
  onPullDownRefresh() {
    this.fetchFavorites(true)
  },
  methods: {
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
    async fetchFavorites(isRefresh = false) {
      this.loading = !isRefresh
      try {
        const res = await this.$u.http.get('/forum/favorites')
        const rows = res?.data || res?.rows || res?.list || []
        const list = Array.isArray(rows) ? rows : []
        this.list = list.map(this.normalizePost)
      } catch (e) {
        this.list = []
        this.$mytip.toast('获取收藏失败')
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

<style>
	/* #ifndef H5 */
	page {
		height: 100%;
		background-color: #f2f2f2;
	}

	/* #endif */
</style>

<style lang="scss" scoped>
	.container {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		text-align: center;

		.empty {
			overflow: hidden;

			.tip {
				color: #909399;
			}

			.btn {
				color: #fff;
				background-color: #2979ff;
				width: 200rpx;
				padding: 15rpx 28rpx;
				border-radius: 130rpx;
				margin: 30rpx 0 0 100rpx;
			}
		}
	}

	.order {
		width: 710rpx;
		background-color: #ffffff;
		margin: 20rpx auto;
		border-radius: 20rpx;
		box-sizing: border-box;
		padding: 20rpx;
		font-size: 28rpx;

		.top {
			display: flex;
			justify-content: space-between;

			.left {
				display: flex;
				align-items: center;

				.store {
					// margin: 0 10rpx;
					font-size: 32rpx;
				}
			}

			.right {
				color: #2979ff;
			}
		}

		.item {
			display: flex;
			margin: 20rpx 0 0 0;

			.left {
				margin-right: 30rpx;

				image {
					width: 150rpx;
					height: 150rpx;
					border-radius: 10rpx;
				}
			}

			.content {
				.title {
					font-weight: bold;
					font-size: 28rpx;
					line-height: 50rpx;
				}

				.price {
					margin: 10rpx 0;
					font-size: 30rpx;
				}

				.type {
					margin: 10rpx 0;
					font-size: 24rpx;
					color: $u-tips-color;
				}

				.desc {
					margin: 10rpx 0;
					font-size: 24rpx;
					color: $u-tips-color;
				}
			}
		}

		.bottom {
			display: flex;
			margin-top: 20rpx;
			padding: 0 10rpx;
			justify-content: flex-end;
			align-items: center;

			.btn {
				margin-left: 20rpx;
				line-height: 52rpx;
				width: 160rpx;
				border-radius: 26rpx;
				border: 2rpx solid $u-border-color;
				font-size: 26rpx;
				text-align: center;
				color: $u-type-info-dark;
			}

			.evaluate {
				color: $u-tips-color;
			}
		}
	}

	.centre {
		text-align: center;
		margin: 200rpx auto;
		font-size: 32rpx;

		image {
			width: 164rpx;
			height: 164rpx;
			border-radius: 50%;
			margin-bottom: 20rpx;
		}

		.tips {
			font-size: 24rpx;
			color: #999999;
			margin-top: 20rpx;
		}

		.btn {
			margin: 80rpx auto;
			width: 200rpx;
			border-radius: 32rpx;
			line-height: 64rpx;
			color: #ffffff;
			font-size: 26rpx;
			background-image: linear-gradient(to left, #2979ff, rgba(#2979ff, 0.6));
		}
	}

	.wrap {
		display: flex;
		flex-direction: column;
		height: calc(100vh - var(--window-top));
		width: 100%;
	}

	.swiper-box {
		flex: 1;
	}

	.swiper-item {
		height: 100%;
	}

	.buttom {
		.loginType {
			font-size: 14px;
			position: fixed;
			right: 30rpx;
			bottom: 120rpx;
			width: 60px;
			height: 60px;
			padding: 4px;
			cursor: pointer;
			background: #FFF;
			text-align: center;
			line-height: 60px;
			border-radius: 100%;
			-webkit-box-shadow: 0px 1px 20px 0px rgba(0, 0, 0, 0.1), inset 0px -1px 0px 0px rgba(0, 0, 0, 0.1);
			box-shadow: 0px 1px 20px 0px rgba(0, 0, 0, 0.1), inset 0px -1px 0px 0px rgba(0, 0, 0, 0.1);
		}
	}
</style>
<style lang="scss" scoped>
.forum-favorites {
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
