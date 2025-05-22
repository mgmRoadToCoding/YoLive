<template>
	<view>
		<u-navbar :is-back="true" title="我的委托" :border-bottom="false"></u-navbar>
		<view class="wrap">
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#2979ff" ref="tabs" :list="list" :current="current" @change="change" :is-scroll="false" swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish">
				<swiper-item class="swiper-item" v-for="(swiper,i) in orderList" :key="i">
					<scroll-view scroll-y style="height: 100%;width: 100%;">
						<view class="page-box">
							<view v-if="swiper.length === 0">
								<view class="centre">
									<image :src="empty" mode=""></image>
									<view class="explain">
										您还没有相关的房源
										<view class="tips">可以去委托房源</view>
									</view>
									<view class="btn" @click="publishHouse">委托房源</view>
								</view>
							</view>
							<view v-else
							 class="order" v-for="(item, index) in swiper" :key="index">
								<view class="top">
									<view class="left">
										<view class="store">{{ item.code }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>
								</view>
								<view class="item">
									<view class="left"><image :src="item.image" mode="aspectFill"></image></view>
									<view class="content">
										<view class="title u-line-2">
											{{item.villageName}} {{item.type == '整租' ? item.houseNum + item.houseHall + item.toiletNum : item.roomType}}
										</view>
										<view class="price">¥{{item.price}}</view>
										<view class="type">
											{{item.type}} | {{item.type == '整租' ? item.houseArea : item.roomArea}}㎡ | {{item.decoration}}
										</view>
									</view>
								</view>
								<view class="bottom">
									<view v-if="item.state == 0" class="evaluate btn"  @click.stop="clickImage(item.id)">修改</view>
									<view v-else class="evaluate btn"  @click.stop="viewImage(item.id)">查看</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
		<view class="buttom">
			<view class="loginType">
				<view class="wechat item">
					<view class="icon"><u-icon size="60" name="server-man" color="#999" @click="server"></u-icon></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import config from "@/common/config.js" // 全局配置文件
	export default {
		data() {
			return {
				empty:'/static/empty/default.png',
				orderList: [[], [], [], []],
				list: [
					{
						name: '待审核'
					},
					{
						name: '待出租',
						// count: 1
					},
					{
						name: '已出租'
					},
					{
						name: '已下架',
					}
				],
				current: 0,
				swiperCurrent: 0,
				tabsHeight: 0,
				dx: 0,
				pageNum: 1,
				pageSize: 100,
			};
		},
		onLoad() {
			this.getOrderList(0);
			this.getOrderList(1);
			this.getOrderList(2);
			this.getOrderList(3);
		},
		methods: {
			login(){
				this.$u.route('/pages/login/login')
			},
			// 页面数据
			getOrderList(idx) {
				this.findHouseList(idx)
			},
			// tab栏切换
			change(index) {
				this.swiperCurrent = index;
				this.orderList[index] = []
				this.getOrderList(index);
			},
			transition({ detail: { dx } }) {
				this.$refs.tabs.setDx(dx);
			},
			animationfinish({ detail: { current } }) {
				this.$refs.tabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},
			findHouseList(idx) {
				let url = "/api/houseApi/findHouseRoomList";
				let defaultData = {
					state:idx,
					publishId:uni.getStorageSync('lifeData').vuex_user.user.userId,
					villageCity:uni.getStorageSync('lifeData').vuex_city,
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					orderByColumn: 'update_time,create_time',
					isAsc: 'desc'
				}
				this.$u.get(url, {...defaultData,...this.searchData}).then(result => {
					const data = result.rows;
					this.houseList = data;
					for (let i = 0; i < this.houseList.length; i++) {
						// 先转成字符串再转成对象，避免数组对象引用导致数据混乱
						let item = this.houseList[i]
						if(!item.faceUrl.includes(config.staticUrl)){
							item.image = config.staticUrl+item.faceUrl
						}else{
							item.image = item.faceUrl
						}
						if(item.type == 0){
							item.type = '整租'
						}else if(item.type == 1){
							item.type = '合租'
						}
						if(item.roomType == 1){
							item.roomType = '主卧'
						}else if(item.roomType == 2){
							item.roomType = '次卧'
						}else{
							item.roomType = '未知'
						}
						if(this.$u.test.isEmpty(item.houseNum)){
							item.houseNum = ''
						}
						if(this.$u.test.isEmpty(item.houseHall)){
							item.houseHall = ''
						}
						if(this.$u.test.isEmpty(item.toiletNum)){
							item.toiletNum = ''
						}
						this.orderList[idx].push(item);
					}
				});
			},
			publishHouse(){
				this.$u.route('/pages/detail/preHouse');
			},
			server(){
				// window.open ('https://sourcebyte.cn')
				uni.makePhoneCall({
				  phoneNumber: "18720989281",
				});
			},
			clickImage(houseId) {
				this.$u.route({
					url: '/pages/detail/preHouse',
					params: {
						houseId: houseId
					}
				})
			},
			viewImage(houseId){
				this.$u.route({
					url: '/pages/detail/detail',
					params: {
						houseId: houseId
					}
				})
			}
		}
	};
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
	.container{
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		text-align: center;
		.empty{
			overflow: hidden;
			.tip{
				color: #909399  ;
			}
			.btn {
				color:#fff ;
				background-color: #2979ff;
				width:200rpx;
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
				.price{
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
				// color: #2979ff;
				// border-color: #2979ff;
				color: $u-tips-color;
				border-color: $u-tips-color;
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
			background-image:linear-gradient(to left,#2979ff,rgba(#2979ff,0.6));
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
			-webkit-box-shadow: 0px 1px 20px 0px rgba(0,0,0,0.1),inset 0px -1px 0px 0px rgba(0,0,0,0.1);
			box-shadow: 0px 1px 20px 0px rgba(0,0,0,0.1),inset 0px -1px 0px 0px rgba(0,0,0,0.1);
		}
	}
</style>
