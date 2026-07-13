<template>
	<view class="container">
		<!-- 添加语言切换组件 -->
		<language-switcher></language-switcher>

		<!-- 70% 背景轮播图区域（固定高度） -->
		<view class="background-section">
			<u-swiper :list="swiperList" height="550" @click="moreInfo" class="background-swiper" :autoplay="true"
				:interval="3000" :circular="true"></u-swiper>

			<!-- 悬浮在轮播图上的导航按钮 -->
			<view class="floating-nav">
				<view class="nav-buttons">
					<view v-for="(item, index) in navList" :key="index" class="nav-button" @tap="clickNav(item)"
						hover-class="hoverClass">
						<image :src="item.src" mode="aspectFit"></image>
						<view class="tabName">{{ $t(item.nameKey) }}</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 下半内容区域：使用 scroll-view 单独滚动 -->
		<scroll-view class="content-section" scroll-y="true" :scroll-top="scrollTop" @scroll="onScroll"
			@scrolltolower="onScrollToLower" enable-back-to-top="true">
			<!-- 今日推荐title -->
			<view class="recommend-title">
				<text>{{ $t('todayRecommend') }}</text>
			</view>

			<!-- 今日推荐内容 -->
			<view class="news-section">
				<view v-for="(item, index) in newsList" :key="index" class="news-item" @click="goToNewsDetail(item.id)">
					<view class="news-content">
						<image :src="item.image" class="news-image" mode="aspectFill" v-if="item.image"></image>
						<view class="news-text">
							<view class="news-title">{{ $t(`news.${item.id}.title`) }}</view>
							<view class="news-brief">{{ $t(`news.${item.id}.brief`) }}</view>
							<view class="news-time">{{ item.time }}</view>
						</view>
					</view>
				</view>
			</view>
		</scroll-view>

		<u-back-top :scroll-top="scrollTop" top="1000"></u-back-top>
		<u-no-network></u-no-network>
	</view>
</template>

<script>
	import config from "@/common/config.js" // 全局配置文件
	export default {
		data() {
			return {
				keyword: "",
				pageNum: 1,
				pageSize: 20,
				scrollTop: 0,
				houseList: [],
				swiperList: [{
						image: "/static/img/index/swiper/swiper.jpg",
						title: this.$t("swiperTitle2"),
					},
					{
						image: "/static/img/index/swiper/swiper3.png",
						title: this.$t("swiperTitle2"),
					},
				],
				navList: [{
						nameKey: "findRoom",
						src: "/static/img/index/cover_2022/index_cover1.png",
						url: "/pages/map/MapSelection/index",
					},
					{
						nameKey: "studentJobs",
						src: "/static/img/index/cover_2022/index_cover2.png",
						url: "/pages/chat/index",
					},
					{
						nameKey: "studyKorea",
						src: "/static/img/index/cover_2022/index_cover3.png",
						url: "/pages/faq/index",
					},
				],
				newsList: [{
						id: 1,
						image: "/static/img/news/demo.jpg",
						time: "2025-4-15"
					},
					{
						id: 2,
						image: "/static/img/news/demo2.png",
						time: "2025-4-16"
					},
					{
						id: 3,
						time: "2025-4-17"
					},
					{
						id: 4,
						image: "/static/img/news/demo4.jpg",
						time: "2025-4-18"
					},
					{
						id: 5,
						time: "2025-4-19"
					},
				],
				loadStatus: "loadmore",
				flowList: [],
				uvCode: uni.getStorageSync("uvCode"),
			};
		},
		// 页面生命周期：真机/小程序都能使用
		onLoad() {
			// 彻底禁用下拉刷新（运行时）
			if (uni && uni.disablePullDownRefresh) {
				try {
					uni.disablePullDownRefresh();
				} catch (e) {
					// ignore
				}
			}
		},
		methods: {
			// scroll-view 滑动事件
			onScroll(e) {
				// 小程序/真机 scroll-view 的 scroll 事件在 detail 里
				if (e && e.detail && typeof e.detail.scrollTop !== "undefined") {
					this.scrollTop = e.detail.scrollTop;
				}
			},
			onScrollToLower() {
				// 到底部触发：可以做下拉加载更多
				// 例如： this.loadMoreNews()
			},

			// 今日推荐 part
			goToNewsDetail(newsId) {
				this.$u.route({
					url: "/pages/news/index/index", // 详情页路径
					params: {
						id: newsId, // 传递新闻ID
					},
				});
			},
			clickSearch() {
				this.$u.route("/pages/search/search");
			},
			clickImage(houseId) {
				this.$u.route({
					url: "/pages/detail/detail",
					params: {
						houseId: houseId,
					},
				});
			},
			clickNav(item) {
				if (item.url) {
					return this.$u.route(item.url);
				}
				if (item.type === "2") {
					let token = uni.getStorageSync("lifeData")?.vuex_token;
					if (!token) {
						return uni.reLaunch({
							url: "../login/login",
						});
					}
					// 直接跳转，不再重复验证 Token（由全局拦截器处理）
					return this.$u.route("/pages/detail/preHouse");
				} else if (item.type) {
					this.$u.route({
						url: "/pages/search/searchList",
						params: {
							type: item.type,
						},
					});
				}
			},
			code() {
				this.$mytip.toast("请咨询作者");
			},
			appSysFlowInfo() {
				// 流量统计
				let uvCode = uni.getStorageSync("uvCode");
				let url = "https://sourcebyte.cn/api/flow/upFlow?type=MINI&uvCode=" + uvCode;
				uni.request({
					url: url,
					method: "POST",
					success: (res) => {
						uni.setStorageSync("uvCode", res.data.data);
					},
				});
			},
			server() {
				uni.makePhoneCall({
					phoneNumber: "18720989281",
				});
			},
			getNoticecList() {
				let url = "/api/notice/findNoticeList";
				this.$u.get(url, {
					pageNum: 1,
					pageSize: 50,
					orderByColumn: "create_time",
					isAsc: "desc",
				}).then((obj) => {
					this.noticeList = []; // 先清空数组
					let data = obj.rows;
					data.filter((item) => {
						this.noticeList.push(item.noticeTitle);
					});
				});
			},
			moreInfo() {
				uni.navigateToMiniProgram({
					appId: "wxbca64173e772915e", // 此为开源字节appid
					path: "/pages/index/index", // 此为开源字节首页路径
					envVersion: "release",
					success: (res) => {
						// 打开成功
						console.log("打开成功", res);
					},
					fail: (err) => {
						console.log(err);
					},
				});
			},
		},
	};
</script>

<style lang="scss">
	page {
		height: 100%;
	}

	/* 重置部分样式 */
	.container {
		display: flex;
		flex-direction: column;
		height: 100vh;
	}

	/* 顶部背景区域：固定高度（按需调整高度） */
	.background-section {
		width: 100%;
		height: 35vh;
		/* 顶部固定高度：可根据设计改为 65vh 等 */
		position: relative;
	}

	.background-swiper {
		width: 100%;
		height: 100%;
	}

	/* 导航按钮定位调整 */
	.floating-nav {
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 10;
		padding: 0 30rpx;
		transform: translateY(40%);
	}

	.nav-buttons {
		display: flex;
		justify-content: space-around;
		background: white;
		border-radius: 24rpx;
		padding: 20rpx 0;
		box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.15);
		margin: 0 10rpx;
	}

	.nav-button {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 28%;

		image {
			width: 90rpx;
			height: 100rpx;
		}

		.tabName {
			font-size: 26rpx;
			color: #333;
			font-weight: 500;
			text-align: center;
		}
	}

	/* 下半内容区域：使用 scroll-view，需要显式高度（100vh - 顶部高度） */
	.content-section {
		/* 用 calc 保证在不同设备上底部区域高度恰好等于剩余高度 */
		height: calc(100vh - 35vh);
		// padding: 60rpx 30rpx 20rpx;
		padding: 100rpx 30rpx 20rpx;
		background: #f8f9fa;
		box-sizing: border-box;
		-webkit-overflow-scrolling: touch;
	}

	/* 优化新闻列表样式 */
	.recommend-title {
		font-size: 34rpx;
		font-weight: bold;
		color: #333;
		padding-bottom: 20rpx;
		margin-bottom: 20rpx;
		border-bottom: 1px solid #eee;
		text-align: center;
	}

	.news-item {
		background-color: #fff;
		border-radius: 16rpx;
		margin-bottom: 30rpx;
		padding: 24rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.08);

		.news-image {
			width: 100%;
			height: 240rpx;
			border-radius: 12rpx;
			margin-bottom: 20rpx;
		}

		.news-title {
			font-size: 32rpx;
			font-weight: bold;
			color: #1a1a1a;
			margin-bottom: 12rpx;
		}

		.news-brief {
			font-size: 28rpx;
			color: #666;
			margin-bottom: 10rpx;
		}

		.news-time {
			font-size: 24rpx;
			color: #999;
		}
	}

	/* 其余样式保持不变（你的原样式） */
	.news-item {
		background-color: #f8f9fa;
		border-radius: 12rpx;
		margin-bottom: 20rpx;
		padding: 24rpx;
		box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
		color: #6f6f6f;

		&:active {
			background-color: #f1f5ff;
		}
	}

	.news-title {
		font-size: 34rpx;
		font-weight: bold;
		color: #1a1a1a;
		margin-bottom: 12rpx;
		position: relative;
		padding-left: 20rpx;

		&::before {
			content: "";
			position: absolute;
			left: 0;
			top: 8rpx;
			height: 60%;
			width: 6rpx;
			background-color: #2979ff;
			border-radius: 3rpx;
		}
	}

	.news-image {
		width: 100%;
		height: 200rpx;
		border-radius: 8rpx;
		margin-bottom: 15rpx;
		object-fit: cover;
	}

	.nomore {
		background-color: $u-bg-color;
	}

	.search {
		width: 54px;
		height: 44px;

		&:active {
			background-color: $u-bg-color;
		}
	}

	.rowClass {
		border-radius: 8px;
		background-color: rgb(255, 255, 255);
		margin-top: 10rpx;
	}

	.hoverClass {
		background-color: #e4e7ed;
	}

	.tabName {
		font-size: 28rpx;
		color: $u-main-color;
	}

	.demo-warter {
		border-radius: 8px;
		margin-top: 3px;
		background-color: #ffffff;
		padding: 3px;
		position: relative;
	}

	.u-close {
		position: absolute;
		top: 20rpx;
		right: 20rpx;
	}

	.item-cover {
		font-size: 55rpx;
		color: $u-type-warning;
	}

	.item-title {
		font-size: 28rpx;
		color: $u-main-color;
		font-weight: bold;
		padding-top: 5rpx;
		padding-left: 10rpx;
	}

	.item-price {
		font-weight: normal;
		font-size: 32rpx;
		color: $u-type-warning;
	}

	.item-desc {
		font-weight: normal;
		font-size: 26rpx;
		color: $u-tips-color;
		padding-bottom: 5rpx;
		padding-left: 10rpx;
	}

	.item-tag {
		font-size: 24rpx;
		color: $u-tips-color;
		margin-top: 3px;
	}

	.buttom {
		.loginType {
			font-size: 14px;
			position: fixed;
			right: 30rpx;
			bottom: 300rpx;
			width: 60px;
			height: 60px;
			padding: 4px;
			cursor: pointer;
			background: #fff;
			text-align: center;
			line-height: 60px;
			border-radius: 100%;
			-webkit-box-shadow: 0px 1px 20px 0px rgba(0, 0, 0, 0.1), inset 0px -1px 0px 0px rgba(0, 0, 0, 0.1);
			box-shadow: 0px 1px 20px 0px rgba(0, 0, 0, 0.1), inset 0px -1px 0px 0px rgba(0, 0, 0, 0.1);
		}
	}
</style>

<style scoped>
	/* 强制覆盖 flex 宽度 */
	.u-col.u-col-3 {
		flex: 0 0 33% !important;
	}
</style>
