<template>
	<view>
		<u-navbar :is-back="true" title="通知公告" :border-bottom="false"></u-navbar>
		<view class="wrap">
			<scroll-view scroll-y style="height: 100%;width: 100%;">
				<view class="page-box">
					<view class="tabSwiper" v-for="(item, index) in dataList" :key="item.id" @click="clickContent(item)">
						<view class="top">
							<view class="left">
								<u-icon name="bell" :size="35" color="#2979ff"></u-icon>
								<view class="title">{{ item.title }}</view>
								<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
							</view>
							<view class="right">{{ item.date }}</view>
						</view>
						<view class="item">
							<view class="content">
								<view class="title u-line-2">{{ item.content }}</view>
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			pageNum:1,
			pageSize:50,
			dataList: [],
		};
	},
	onLoad() {
		this.getNoticecList();
	},
	methods: {
		clickContent(item){
			if(item.id){
				this.$u.route('/pages/content/content', {
					id: item.id
				});
			}
		},
		getNoticecList(){
			let url = "/api/notice/findNoticeList";
			this.$u.get(url,{
				pageNum:this.pageNum,
				pageSize:this.pageSize,
				orderByColumn:'create_time',
				isAsc:'desc'
			}).then(obj => {
				let data = obj.rows
				data.filter(item=>{
					this.dataList.push(
						{
							id:item.noticeId,
							title: item.noticeTitle,
							date:item.createTime,
							content: item.remark,
						}
					)
				})
			});
		}
	}
};
</script>
<style lang="scss" scoped>
.tabSwiper {
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
			.title {
				margin: 0 10rpx;
				font-size: 32rpx;
				font-weight: bold;
			}
		}
		.right {
			color: $u-tips-color;
		}
	}
	.item {
		display: flex;
		margin: 20rpx 0 0;
		.left {
			margin-right: 20rpx;
			image {
				width: 200rpx;
				height: 200rpx;
				border-radius: 10rpx;
			}
		}
		.content {
			.title {
				font-size: 28rpx;
				line-height: 50rpx;
			}
		}
		.right {
			margin-left: 10rpx;
			padding-top: 20rpx;
			text-align: right;
		}
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
</style>
