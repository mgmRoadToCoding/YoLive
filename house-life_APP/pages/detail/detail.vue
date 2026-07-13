<template>
	<view class="u-margin-left-20 u-margin-right-20">
		<u-navbar :is-back="true" :title="$t('houseDetail.title')" :border-bottom="false" :back-text="$t('common.back')"
			:custom-back="goHome"></u-navbar>
		<view>
			<u-swiper :list="swiperlist" :height="750" mode="number" effect3d effect3d-previous-margin="20"
				:interval="5000" @click="clickImg"></u-swiper>
		</view>
		<view class="item u-margin-top-5">
			<!-- <view class="item-title">
				{{room.type === $t('houseDetail.wholeRent') ? room.houseNum + room.houseHall + room.toiletNum : 
				room.roomType === '主卧' ? $t('houseDetail.masterBedroom') : 
				room.roomType === '次卧' ? $t('houseDetail.secondaryBedroom') : $t('houseDetail.unknown')}}
			</view> -->
			<view class="item-title">
				<!-- 如果是整租，显示户型；如果是合租，显示卧室类型 -->
				{{ room.type === 0
			    ? transformRoomNum(room.houseNum) + transformHallNum(room.houseHall) + transformToiletNum(room.toiletNum)
			    : room.roomType === 1
			      ? $t('houseDetail.masterBedroom')
			      : room.roomType === 2
			        ? $t('houseDetail.secondaryBedroom')
			        : $t('houseDetail.unknown')
			  }}
			</view>



			<view class="item-price">₩{{room.price}}/{{$t('houseDetail.month')}}</view>
		</view>
		<view class="item" style="display: flex;align-items: center;flex-wrap: wrap;">
			<view v-for="(item,index) in tagList" :key="index" style="background-color: #f5f5f5;color: #606266;margin-right: 15rpx;
			  margin-top: 15rpx;padding: 0 10rpx;height: 60rpx;line-height: 60rpx;">
				{{item.title}}
			</view>
		</view>
		<view v-if="room.introduce">
			<u-gap height="10" bg-color="#f8f8f8"></u-gap>
			<u-card :title="$t('houseDetail.houseIntro')" :border="false" :head-border-bottom="false" padding="0"
				title-size="38">
				<view class="u-padding-top-45 item" slot="body">
					{{room.introduce}}
				</view>
			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card :title="$t('houseDetail.communityIntro')" :border="false" :head-border-bottom="false" padding="0"
				title-size="38">
				<view class="u-padding-top-45" slot="body">
					<view class="u-flex" style="justify-content: space-between;">
						<view class="item">
							<view>{{$t('houseDetail.buildingYear')}}：{{village.year}}</view>
							<view>{{$t('houseDetail.buildingType')}}：{{village.type}}</view>
							<view>{{$t('houseDetail.greeningRate')}}：{{village.green}}%</view>
						</view>
						<!-- <image src="/static/img/index/img1.png" mode="widthFix" style="width: 250rpx;height: 200rpx;"> -->
						</image>
					</view>
				</view>
			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card :title="room.villageName" :border="false" :head-border-bottom="false" padding="0" title-size="38">
				<view class="u-padding-top-45" slot="body">
					<!-- 替换为Google Maps iframe -->
					<iframe width="100%" height="350rpx" frameborder="0" style="border:0"
						:src="'https://www.google.com/maps/embed/v1/place?key=AIzaSyBAF0EP73NCtVm8ZmyyIzFXR1cnDZQ_TpI&q='+latitude+','+longitude+'&zoom=15'"
						allowfullscreen>
					</iframe>
					<view style="margin-top: 20rpx;margin-left: 10rpx;font-size: 10px;
					  color: #909399">
						{{$t('houseDetail.referenceOnly')}}
					</view>

					<view @click="clickMap" style="margin-top: 30rpx;padding-left: 10rpx;height:80rpx;
				  line-height: 80rpx;background-color: #fdfdfd;border-radius: 6px;
				  display: flex;justify-content: space-between;
				  padding-right: 10rpx;color: #909399;
				  ">
						<!-- <view>
							<u-icon name="map" color="#909399" size="30"></u-icon>
							<text style="margin-left: 8rpx;">
								{{ $t('houseDetail.straightDistance') }} {{ distance }} km
							</text>
						</view>
						<view>
							<text style="margin-right: 3rpx;">
								{{$t('houseDetail.navigation')}}
							</text>
							<u-icon name="arrow-right" color="#909399" size="30"></u-icon>
						</view> -->
					</view>

				</view>

			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card :title="$t('serviceItems.serviceIntro')" :border="false" :head-border-bottom="false" padding="0"
				title-size="38">
				<view class="u-padding-top-35" slot="body">
					<u-cell-group :border="false">
						<u-cell-item icon="heart" :title="$t('serviceItems.promise')" :arrow="false" hover-class="none"
							:border-bottom="false"></u-cell-item>
						<u-cell-item icon="level" :title="$t('serviceItems.quality')" :arrow="false" hover-class="none"
							:border-bottom="false"></u-cell-item>
						<u-cell-item icon="star" :title="$t('serviceItems.service')" :arrow="false" hover-class="none"
							:border-bottom="false"></u-cell-item>
						<u-cell-item icon="rmb" :title="$t('serviceItems.payment')" :arrow="false" hover-class="none"
							:border-bottom="false"></u-cell-item>
					</u-cell-group>
				</view>
			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card :title="$t('houseDetail.houseEvaluation')" :border="false" :head-border-bottom="false" padding="0"
				title-size="38">
				<view class="u-padding-top-50 u-padding-bottom-50" slot="body">
					<u-cell-group :border="false">
						{{evaluate}}
						<text style="float: right;color: #909399;font-size: 13px;">{{credt}}</text>
					</u-cell-group>
				</view>
				<view slot="foot" style="padding-top: 30rpx;float: right;">
					<u-icon name="chat-fill"></u-icon>
					<text @click="moreEval"
						style="text-align: center;font-size: 12px;margin-left: 5rpx;">{{$t('houseDetail.moreEvaluation')}}（{{evalsize}}）</text>
				</view>
			</u-card>
		</view>
		<u-gap height="10" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;padding-bottom: 50rpx;">
			<u-card :title="$t('houseDetail.agent')" :border="false" :head-border-bottom="false" padding="0"
				title-size="38">
				<view class="u-padding-top-45" slot="body">
					<view class="agent-section">
						<u-avatar :src="room.agentAvatar" size="80" class="agent-avatar"></u-avatar>
						<view class="agent-info">
							<view class="agent-phone" @click="clickItem">
								<u-icon name="phone" size="30"></u-icon>
							</view>
							<view class="agent-name">{{room.agentName}}</view>
						</view>
					</view>
				</view>
			</u-card>
			<view class="item">
				<u-divider>{{$t('houseDetail.companyService')}}</u-divider>
			</view>
		</view>
		<view class="navigation">
			<view class="left">
				<button type="default" open-type="contact" class="clearBtn" style="font-size: 14px;color: #6a6a6a;">
					<view class="item">
						<u-icon name="server-fill" :size="40"></u-icon>
						<view class="text">{{$t('houseDetail.onlineConsult')}}</view>
					</view>
				</button>
				<view class="item" @click="heartHouse">
					<u-icon name="heart" :size="40" v-if="!room.heart"></u-icon>
					<u-icon name="heart-fill" color="#ff9900" :size="40" v-if="room.heart"></u-icon>
					<view class="text">{{$t('houseDetail.favorite')}}</view>
				</view>
				<button type="default" open-type="share" class="clearBtn" style="font-size: 14px;color: #6a6a6a;">
					<view class="item">
						<u-icon name="zhuanfa" :size="40"></u-icon>
						<view class="text">{{$t('houseDetail.share')}}</view>
					</view>
				</button>
			</view>
			<view class="right" @click="clickItem">
				<view class="btn">{{$t('houseDetail.contactAgent')}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	import config from "@/common/config.js" // 全局配置文件
	import numberToChinese from '@/common/utils/numberToChinese.js' // 数字转中文
	export default {
		data() {
			return {
				googleMapsApiKey: 'AIzaSyBAF0EP73NCtVm8ZmyyIzFXR1cnDZQ_TpI',
				room: {
					villageName: '',
					houseNum: '',
					houseHall: '',
					toiletNum: '',
					roomType: '',
					direction: '',
					price: '',
					introduce: '',
					heart: false,
				},
				village: {
					year: '',
					type: '',
					green: '',
				},
				swiperlist: [],
				tagList: [],
				//评价列表
				evaluList: [],
				user: {},
				longitude: 120.14,
				latitude: 30.35,
				distance: 0,
				//自己的评论
				ownevalu: '',
				houseId: null,
				evalsize: 0,
				havem: false,
				evaluate: '',
				credt: ''
			}
		},
		onLoad: function(option) {
			let houseId = option.houseId //上个页面传递的参数。
			this.houseId = houseId
			// 渲染当前房源信息
			this.findHouseById(houseId);
		},
		onShow() {
			// 监听语言变化
			// this.$i18n.locale = uni.getStorageSync('language') || 'kor';
			// 如果houseId存在，重新加载数据
			if (this.houseId) {
				this.findHouseById(this.houseId);
			}
		},
		methods: {
			transformRoomNum(num) {
				if(!num) {
					return ''
				}
				return numberToChinese(num) + '室'
			},
			transformHallNum(num) {
				if(!num) {
					return ''
				}
				return numberToChinese(num) + '厅'
			},
			transformToiletNum(num) {
				if(!num) {
					return ''
				}
				return numberToChinese(num) + '卫'
			},
			heartHouse() {
				// 判断是否有userId
				let lifeData = uni.getStorageSync('lifeData');
				let vuex_user = lifeData.vuex_user
				if (!vuex_user) {
					// 没有userId 则跳转到登录
					return uni.reLaunch({
						url: '../login/login'
					})
				}
				// 收藏
				let url = "api/houseApi/saveHeart";
				this.$u.post(url, {
					heart: this.room.heart,
					houseId: this.room.id,
					userId: vuex_user.user.userId,
				}).then(result => {
					this.room.heart = !this.room.heart
					this.$mytip.toast(result.msg)
				});
			},
			goHome() {
				uni.reLaunch({
					url: '../index/index'
				})
			},
			clickItem() {
				//拨打固定电话
				uni.makePhoneCall({
					phoneNumber: this.room.agentPhone,
				});
			},
			findHouseById(houseId) {
				let url = "api/houseApi/findHouseById";
				this.$u.get(url, {
					id: houseId
				}).then(result => {
					let room = result.data
					// if (room.type == 0) {
					// 	room.type = '整租'
					// } else if (room.type == 1) {
					// 	room.type = '合租'
					// }
					// if (room.roomType == 1) {
					// 	room.roomType = '主卧'
					// } else if (room.roomType == 2) {
					// 	room.roomType = '次卧'
					// } else {
					// 	room.roomType = '未知'
					// }

					if (this.$u.test.isEmpty(room.houseNum)) {
						room.houseNum = ''
					}
					if (this.$u.test.isEmpty(room.houseHall)) {
						room.houseHall = ''
					}
					if (this.$u.test.isEmpty(room.toiletNum)) {
						room.toiletNum = ''
					}
					if (this.$u.test.isEmpty(room.floor)) {
						room.floor = ''
					} else {
						room.floor = room.floor + '层'
					}
					this.swiperlist = room.imageList.map(val => {
						let imgUrl = val.imgUrl
						if (!imgUrl.includes(config.staticUrl)) {
							imgUrl = config.staticUrl + val.imgUrl
						} else {
							imgUrl = val.imgUrl
						}
						return {
							title: val.imageName,
							image: imgUrl
						}
					})
					this.tagList = room.featureList.map(val => {
						return {
							title: val.feature,
						}
					})
					if (!room.agentAvatar.includes(config.staticUrl)) {
						room.agentAvatar = config.staticUrl + room.agentAvatar
					}
					this.user = room.user
					this.village = room.village
					this.room = room
					// 判断是否收藏
					this.selectHouseHeart(houseId);
					//查询房源评价
					this.selectHouseEvals(houseId);

					// 分享自定义标题与图片
					let shareTitle = ''
					if (room.type == '整租') {
						shareTitle = this.village.name + " " + this.transformRoomNum(this.room.houseNum) + this.transformHallNum(this.room.houseHall) + this.transformToiletNum(this.room.toiletNum) + " " + this.room.decoration + " ¥" + this.room.price + "/月"
					} else {
						shareTitle = this.village.name + " " + room.roomType + " " + this.room.decoration + " ¥" +
							this.room.price + "/月"
					}
					this.$u.mpShare = {
						title: shareTitle, // 默认为小程序名称，可自定义
						// 支持PNG及JPG，默认为当前页面的截图
						imageUrl: this.room.faceUrl,
					}

					// 添加到浏览历史
					let houseHistory = uni.getStorageSync('houseHistory')
					if (!houseHistory) {
						houseHistory = []
					}
					// 如果超过20个了，则删除最后一个
					if (houseHistory.length >= 20) {
						houseHistory.pop()
					}
					houseHistory.push(this.room)
					// 数据去重
					const keyArr = [];
					houseHistory.forEach((element, index) => {
						keyArr.push(element.code); // 通过code来判断
					});
					const newArr = [];
					const newKey = new Set(keyArr); // key去重
					newKey.forEach(item => {
						const index = keyArr.findIndex(item2 => item2 === item);
						newArr.push(houseHistory[index]);
					})
					uni.setStorageSync('houseHistory', newArr)

					// 经纬度
					this.longitude = room.longitude
					this.latitude = room.latitude
					// 计算距离
					let lat1 = 35.8386675
					let lng1 = 128.7537384
					let lat2 = this.latitude
					let lng2 = this.longitude
					uni.getLocation({
						type: 'gcj02',
						success: res => {
							lat1 = res.latitude
							lng1 = res.longitude
							this.distance = this.getDistance(lat1, lng1, lat2, lng2);
						}
					})
				});
			},
			selectHouseHeart(houseId) {
				// 判断是否有userId
				let lifeData = uni.getStorageSync('lifeData');
				let vuex_user = lifeData.vuex_user
				if (vuex_user) {
					let url = "api/houseApi/selectHouseHeart";
					this.$u.get(url, {
						houseId: houseId,
						userId: vuex_user.user.userId,
					}).then(result => {
						this.room.heart = result.data
					});
				}
			},
			selectHouseEvals(houseId) {
				let url = "api/houseApi/selectHouseEvals";
				this.$u.get(url, {
					houseId: houseId,
					pageNum: 1,
					pageSize: 1
				}).then(result => {
					if (result.total > 0) {
						this.evaluate = result.rows[0].evaluate;
						if (this.evaluate.length > 30) {
							this.evaluate = this.evaluate.substring(0, 30) + "......";
						}
						this.credt = result.rows[0].createTime;
						this.havem = true;
						this.evalsize = result.total;
					}
				});
			},
			moreEval() {
				this.$u.route({
					url: '/pages/detail/evalList',
					params: {
						houseId: this.houseId
					}
				})
			},
			clickImg(index) {
				let imgArr = this.swiperlist.map(val => {
					return val.image
				})
				console.log(imgArr);
				// 预览图片
				uni.previewImage({
					current: index,
					urls: imgArr
				});
			},
			clickMap(e) {
				// 校验坐标
				if (!this.latitude || !this.longitude) {
					uni.showToast({
						title: '位置信息无效',
						icon: 'none'
					});
					return;
				}

				const url =
					`https://www.google.com/maps/dir/?api=1&destination=${this.latitude},${this.longitude}&travelmode=driving`;

				// #ifdef H5
				window.open(url, '_blank');
				// #endif

				// #ifdef APP-PLUS
				plus.runtime.openURL(url);
				// #endif

				// #ifdef MP-WEIXIN
				uni.showModal({
					title: 'Google导航',
					content: '是否要打开Google Maps导航？',
					success: (res) => {
						if (res.confirm) {
							uni.setClipboardData({
								data: url,
								success: () => {
									uni.showToast({
										title: '链接已复制，请粘贴到浏览器打开',
										icon: 'none'
									});
								}
							});
						}
					}
				});
				// #endif
			},
			getDistance(lat1, lng1, lat2, lng2) {
				lat1 = lat1 || 0;
				lng1 = lng1 || 0;
				lat2 = lat2 || 0;
				lng2 = lng2 || 0;
				var rad1 = lat1 * Math.PI / 180.0;
				var rad2 = lat2 * Math.PI / 180.0;
				var a = rad1 - rad2;
				var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
				var r = 6378137;
				var distance = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) *
					Math.pow(Math.sin(b / 2), 2)));
				return (distance / 1000).toFixed(2);
			}
		}
	}
</script>

<style>
	/* page不能写带scope的style标签中，否则无效 */
	page {
		background-color: rgb(255, 255, 255);
	}
</style>


<style lang="scss" scoped>
	/* 基础样式 */
	.u-margin-left-20,
	.u-margin-right-20 {
		padding-bottom: 120rpx;
		/* 为底部导航栏留出空间 */
	}

	iframe {
		border-radius: 12rpx;
		margin-bottom: 20rpx;
		height: 350rpx;
		width: 100%;
	}

	/* 地图样式 */
	.map-container {
		width: 100%;
		height: 350rpx;
		border-radius: 12rpx;
		overflow: hidden;
		position: relative;
	}

	/* 项目信息样式 */
	.item {
		padding: 20rpx 25rpx;
		line-height: 1.6;
	}

	.item-title {
		font-size: 38rpx;
		color: $u-main-color;
		font-weight: bold;
		margin-bottom: 10rpx;
	}

	.item-price {
		font-size: 42rpx;
		color: $u-type-warning;
		font-weight: bold;
		margin-bottom: 20rpx;
	}

	.item-desc {
		font-size: 30rpx;
		color: $u-tips-color;
	}

	/* 标签样式 */
	.tag-container {
		display: flex;
		flex-wrap: wrap;
		margin-top: 10rpx;
	}

	.tag-item {
		background-color: #f5f5f5;
		color: #606266;
		margin-right: 15rpx;
		margin-bottom: 15rpx;
		padding: 0 20rpx;
		height: 60rpx;
		line-height: 60rpx;
		border-radius: 30rpx;
		font-size: 26rpx;
	}

	/* 经纪人信息样式 */
	/* 经纪人信息样式 - 修改后 */
	.agent-section {
		display: flex;
		align-items: center;
		padding: 20rpx 0;
	}

	.agent-avatar {
		margin-right: 20rpx;
	}

	.agent-info {
		flex: 1;
		display: flex;
		align-items: center;
	}

	.agent-name {
		font-size: 32rpx;
		color: $u-main-color;
		margin-right: 20rpx;
	}

	.agent-phone {
		width: 60rpx;
		height: 60rpx;
		border-radius: 50%;
		background-color: #f5f5f5;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-right: 15rpx;

		.u-icon {
			color: $u-type-primary;
		}
	}

	/* 底部导航栏 */
	.navigation {
		display: flex;
		justify-content: space-between;
		align-items: center;
		position: fixed;
		left: 0;
		right: 0;
		bottom: 0;
		padding: 15rpx 30rpx;
		background-color: #ffffff;
		box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);
		z-index: 9999;
		height: 100rpx;

		.left {
			display: flex;
			align-items: center;
			flex: 1;
			justify-content: space-around;
		}

		.right {
			margin-left: 30rpx;
		}

		.nav-item {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: 24rpx;
			color: #6a6a6a;

			.text {
				margin-top: 8rpx;
			}
		}

		.contact-btn {
			height: 80rpx;
			line-height: 80rpx;
			padding: 0 40rpx;
			color: #ffffff;
			border-radius: 40rpx;
			background-color: $u-type-primary;
			font-size: 30rpx;
			font-weight: bold;
			white-space: nowrap;
		}
	}

	/* 评价样式 */
	.evaluation-section {
		position: relative;

		.evaluation-content {
			padding: 30rpx 0;
			line-height: 1.8;
			color: $u-content-color;
		}

		.evaluation-meta {
			color: $u-tips-color;
			font-size: 24rpx;
			text-align: right;
			margin-top: 20rpx;
		}

		.more-link {
			display: flex;
			align-items: center;
			justify-content: flex-end;
			color: $u-type-primary;
			font-size: 26rpx;
			margin-top: 20rpx;
		}
	}

	/* 分隔线 */
	.divider-section {
		padding: 40rpx 0;
		text-align: center;
		color: $u-tips-color;
		font-size: 26rpx;
	}

	/* 卡片样式增强 */
	.u-card {
		border-radius: 12rpx;
		overflow: hidden;
		margin-bottom: 20rpx;
		background-color: #fff;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.03);
	}

	/* 清除按钮默认样式 */
	.clearBtn {
		margin: 0;
		padding: 0;
		background-color: transparent;
		border: none;
		line-height: 1;
	}

	.clearBtn::after {
		border: none;
	}

	/* 图片轮播样式增强 */
	.u-swiper {
		border-radius: 12rpx;
		overflow: hidden;
	}
</style>
