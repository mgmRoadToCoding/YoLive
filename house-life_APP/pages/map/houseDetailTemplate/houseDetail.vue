<template>
	<view class="detail-container">
		<!-- 图片滑动区 + 全景跳转按钮（仅ID为1的房源显示） -->
		<view class="image-wrapper"> <!-- 新增：包裹swiper和按钮，用于定位 -->
			<swiper class="image-swiper" indicator-dots autoplay circular>
				<swiper-item v-for="(img, index) in houseImages" :key="index">
					<image class="swiper-image" :src="img" mode="aspectFill" />
				</swiper-item>
			</swiper>

			<!-- 全景跳转按钮（仅ID为1的房源显示） -->
			<!-- <view v-if="houseInfo.id === 1" class="pano-jump-btn" @click="gotoPanoPage">
				<text>360° Image</text>
			</view> -->
		</view>

		<!-- 房源信息区 -->
		<view class="info-section">
			<view class="house-title">{{ houseInfo.title }}</view>
			<view class="price-section">
				<text class="price">${{ houseInfo.price }}/{{ $t('month') }}</text>

				<!-- 价格信息 -->
				<view class="price-info">
					<view class="info-title">
						<view class="decorative-line"></view>
						<text>{{ $t('priceInfo') }}</text>
					</view>
					<view class="info-labels">
						<text class="label">{{ $t('paymentMethod') }}</text>
						<text class="label">{{ $t('price') }}</text>
						<text class="label">{{ $t('deposit') }}</text>
						<text class="label">{{ $t('fee') }}</text>
					</view>
					<view class="info-values">
						<text class="value">{{ houseInfo.paymentMethod }}</text>
						<text class="value">${{ houseInfo.price }}</text>
						<text class="value">${{ houseInfo.deposit }}</text>
						<text class="value">${{ houseInfo.fee }}</text>
					</view>
				</view>
			</view>

			<!-- 房屋类型 -->
			<view class="house-type">
				<view class="type-item" v-for="(type, index) in houseTypes" :key="index">
					<image class="type-icon" :src="type.icon" />
					<text class="type-text">{{ type.text }}</text>
				</view>
			</view>
		</view>

		<!-- Google地图区 -->
		<view class="map-section">
			<view class="location-info">
				<view class="decorative-line"></view>
				<text>{{ houseInfo.address }}</text>
			</view>
			<!-- 这里替换为实际的地图组件 -->
			<view class="map-placeholder">
				<image class="map-image"
					:src="'https://maps.googleapis.com/maps/api/staticmap?center=' + houseInfo.latitude + ',' + houseInfo.longitude + '&zoom=15&size=600x300&maptype=roadmap&markers=color:red%7C' + houseInfo.latitude + ',' + houseInfo.longitude + '&key=AIzaSyA8nCCFuQvMJHagDBqjlxxWJtRqQETZ-nc'" />

			</view>
		</view>
		<view class="contact-agent">
			<button class="contact-button" type="primary" @click="showAgentPhone">
				{{ $t('contactAgent') }}
			</button>
		</view>


		<!-- 推荐区 -->
		<view class="recommend-section">
			<view class="section-title">
				<view class="decorative-line"></view>
				<text>{{ $t('recommendHouses') }}</text>
			</view>
			<scroll-view class="recommend-list" scroll-x>
				<view class="recommend-item" v-for="(item, index) in recommendedHouses" :key="index"
					@click="navigateToDetail(item.id)">
					<image class="recommend-image" :src="item.image" mode="aspectFill" />
					<text class="recommend-title">{{ item.title }}</text>
					<text class="recommend-price">${{ item.price }}/{{ $t('month') }}</text>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				houseImages: [],
				houseInfo: {},
				houseTypes: [],
				recommendedHouses: [],
				localHouseDatabase: {
					1: {
						houseImages: [
							'/static/img/house/house_img/house1_1.png',
							'/static/img/house/house_img/house1_2.png',
							'/static/img/house/house_img/house1_3.png'
						],
						pano: 'https://yolivepanoimages.oss-ap-northeast-2.aliyuncs.com/quanjing.jpg', // 新增全景图
						houseInfo: {
							id: 1,
							title: this.$t('house1Title'),
							price: 240000,
							address: '경북 경산시 청1로 32-2',
							latitude: 35.83893,
							longitude: 128.7536,
							paymentMethod: this.$t('wechatPay'),
							deposit: 700000,
							fee: 0,
							description: this.$t('house1Desc')
						},
						houseTypes: [{
								icon: '/static/img/house/icons/bedroom.png',
								text: this.$t('house1Type1')
							},
							{
								icon: '/static/img/house/icons/area.png',
								text: this.$t('house1Type2')
							},
							{
								icon: '/static/img/house/icons/direction.png',
								text: this.$t('house1Type3')
							}
						],
						recommendedHouses: [{
								id: 2,
								title: this.$t('house2Title'),
								price: 350000,
								image: '/static/img/house/house_img/house2_1.png'
							},
							{
								id: 3,
								title: this.$t('house3Title'),
								price: 400000,
								image: '/static/img/house/house_img/house3_1.png'
							}
						]
					},
					2: {
						houseImages: [
							'/static/img/house/house_img/house2_1.png',
							'/static/img/house/house_img/house2_2.png',
							'/static/img/house/house_img/house2_3.png',
							'/static/img/house/house_img/house2_4.png',
							'/static/img/house/house_img/house2_5.png'
						],
						houseInfo: {
							id: 2,
							title: this.$t('house2Title'),
							price: 350000,
							address: '경북 경산시 청운1로66',
							latitude: 35.84110,
							longitude: 128.7563,
							paymentMethod: this.$t('alipay'),
							deposit: 2000000,
							fee: 0,
							description: this.$t('house2Desc')
						},
						houseTypes: [{
								icon: '/static/img/house/icons/bedroom.png',
								text: this.$t('house2Type1')
							},
							{
								icon: '/static/img/house/icons/area.png',
								text: this.$t('house2Type2')
							},
							{
								icon: '/static/img/house/icons/direction.png',
								text: this.$t('house2Type3')
							}
						],
						recommendedHouses: [{
								id: 1,
								title: this.$t('house1Title'),
								price: 240000,
								image: '/static/img/house/house_img/house1_1.png'
							},
							{
								id: 3,
								title: this.$t('house3Title'),
								price: 400000,
								image: '/static/img/house/house_img/house3_1.png'
							}
						]
					},
					3: {
						houseImages: [
							'/static/img/house/house_img/house3_1.png',
							'/static/img/house/house_img/house3_2.png',
							'/static/img/house/house_img/house3_3.png',
							'/static/img/house/house_img/house3_4.png'
						],
						houseInfo: {
							id: 3,
							title: this.$t('house3Title'),
							price: 400000,
							address: '경북 경산시 압량읍 대학로67길 24-16',
							latitude: 35.84216,
							longitude: 128.7567,
							paymentMethod: this.$t('bankTransfer'),
							deposit: 2000000,
							fee: 0,
							description: this.$t('house3Desc')
						},
						houseTypes: [{
								icon: '/static/img/house/icons/bedroom.png',
								text: this.$t('house3Type1')
							},
							{
								icon: '/static/img/house/icons/area.png',
								text: this.$t('house3Type2')
							},
							{
								icon: '/static/img/house/icons/direction.png',
								text: this.$t('house3Type3')
							}
						],
						recommendedHouses: [{
								id: 1,
								title: this.$t('house1Title'),
								price: 240000,
								image: '/static/img/house/house_img/house1_1.png'
							},
							{
								id: 2,
								title: this.$t('house2Title'),
								price: 350000,
								image: '/static/img/house/house_img/house2_1.png'
							}
						]
					}
				}
				/*
				// 以下是原有的API调用方法，已注释掉
				// API接口 - 获取房屋详情
				fetchHouseDetail(houseId) {
					// 这里替换为实际的API调用
					console.log('获取房屋详情，ID:', houseId)
					// uni.request({
					//   url: 'https://your-api.com/houses/' + houseId,
					//   success: (res) => {
					//     this.houseInfo = res.data
					//     this.houseImages = res.data.images
					//   }
					// })
				},
				
				// API接口 - 获取推荐房源
				fetchRecommendedHouses(houseId) {
					// 这里替换为实际的API调用
					console.log('获取推荐房源，基于房屋ID:', houseId)
					// uni.request({
					//   url: 'https://your-api.com/houses/recommended',
					//   data: { currentHouseId: houseId },
					//   success: (res) => {
					//     this.recommendedHouses = res.data
					//   }
					// })
				}
				*/

			}
		},
		onLoad(options) {
			const houseId = options.id || 1 // 默认显示ID为1的房屋
			this.loadHouseData(houseId)
		},
		// 监听语言变化
		onShow() {
			// 重新加载数据以更新语言
			if (this.houseInfo.id) {
				this.loadHouseData(this.houseInfo.id)
			}
		},
		methods: {
			showAgentPhone() {
				// 这里你可以根据房源信息显示不同号码，这里演示写死一个号码
				const phoneNumber = '010-7921-8222'

				uni.showModal({
					title: this.$t('agentPhoneTitle') || '중개자',
					content: `${this.$t('agentPhone')}：${phoneNumber}`,
					showCancel: false,
					confirmText: this.$t('confirm') || '확인'
				})
			},

			// 加载房屋数据
			loadHouseData(houseId) {
				const houseData = this.localHouseDatabase[houseId]
				if (houseData) {
					this.houseImages = houseData.houseImages
					this.houseInfo = houseData.houseInfo
					this.houseTypes = houseData.houseTypes
					this.recommendedHouses = houseData.recommendedHouses
				} else {
					// 如果找不到对应ID，显示默认数据或错误提示
					uni.showToast({
						title: '未找到该房源信息',
						icon: 'none'
					})
					// 加载默认数据(ID为1)
					this.loadHouseData(1)
				}
			},

			// 跳转到其他房屋详情
			navigateToDetail(houseId) {
				uni.navigateTo({
					url: '/pages/map/houseDetailTemplate/houseDetail?id=' + houseId
				})
			},
			// 新增：跳转全景图页面的方法
			gotoPanoPage() {
				// 跳转到你的全景图页面（需确保页面路径与你的项目实际路径一致）
				uni.navigateTo({
					url: '/pages/pano/index/index' // 全景图页面的实际路径
				})
			}
		}
	}
</script>

<style lang="scss">
	// 新增：图片容器和跳转按钮样式
	.image-wrapper {
		position: relative; // 父容器相对定位，让按钮绝对定位生效
	}

	.pano-jump-btn {
		position: absolute;
		bottom: 30rpx;
		right: 30rpx;
		z-index: 99; // 确保按钮在图片上方显示
		background-color: rgba(41, 121, 255, 0.9);
		color: #fff;
		padding: 14rpx 28rpx;
		border-radius: 40rpx;
		font-size: 26rpx;
		font-weight: 500;
		touch-action: manipulation; // 防止移动端双击缩放
	}

	.contact-agent {
		margin-top: 20rpx;
		text-align: center;
	}

	.contact-button {
		background-color: #2979ff;
		color: #fff;
		padding: 10rpx 30rpx;
		font-size: 30rpx;
		border-radius: 10rpx;
	}

	.detail-container {
		padding-bottom: 40rpx;
		background-color: #f5f5f5;
	}

	/* 图片滑动区 */
	.image-swiper {
		width: 100%;
		// height: 500rpx;
		height: 500px;
	}

	.swiper-image {
		width: 100%;
		height: 100%;
	}

	/* 房源信息区 */
	.info-section {
		background-color: #fff;
		padding: 30rpx;
		margin: 20rpx;
		border-radius: 10rpx;
	}

	.house-title {
		font-size: 36rpx;
		font-weight: bold;
		margin-bottom: 20rpx;
	}

	.price {
		font-size: 32rpx;
		color: #f40;
		font-weight: bold;
		margin-bottom: 30rpx;
		display: block;
	}

	/* 价格信息 */
	.price-info {
		margin-top: 30rpx;
	}

	.info-title {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.decorative-line {
		width: 8rpx;
		height: 30rpx;
		background-color: #2979ff;
		margin-right: 15rpx;
		border-radius: 4rpx;
	}

	.info-labels {
		display: flex;
		justify-content: space-between;
		margin-bottom: 10rpx;
		color: #999;
		font-size: 26rpx;
	}

	.info-values {
		display: flex;
		justify-content: space-between;
		font-weight: bold;
		font-size: 28rpx;
	}

	.label,
	.value {
		flex: 1;
		text-align: center;
	}

	/* 房屋类型 */
	.house-type {
		display: flex;
		margin-top: 30rpx;
		flex-wrap: wrap;
	}

	.type-item {
		display: flex;
		align-items: center;
		background-color: #f0f7ff;
		padding: 10rpx 20rpx;
		border-radius: 30rpx;
		margin-right: 20rpx;
		margin-bottom: 15rpx;
	}

	.type-icon {
		width: 30rpx;
		height: 30rpx;
		margin-right: 10rpx;
	}

	.type-text {
		font-size: 26rpx;
		color: #2979ff;
	}

	/* 地图区 */
	.map-section {
		background-color: #fff;
		padding: 30rpx;
		margin: 20rpx;
		border-radius: 10rpx;
	}

	.location-info {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
		font-size: 28rpx;
	}

	.map-placeholder {
		width: 100%;
		height: 300rpx;
		background-color: #eee;
		border-radius: 10rpx;
		overflow: hidden;
	}

	.map-image {
		width: 100%;
		height: 100%;
	}

	/* 推荐区 */
	.recommend-section {
		background-color: #fff;
		padding: 30rpx;
		margin: 20rpx;
		border-radius: 10rpx;
	}

	.section-title {
		display: flex;
		align-items: center;
		margin-bottom: 30rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.recommend-list {
		white-space: nowrap;
	}

	.recommend-item {
		display: inline-block;
		width: 250rpx;
		margin-right: 20rpx;
	}

	.recommend-image {
		width: 250rpx;
		height: 180rpx;
		border-radius: 10rpx;
	}

	.recommend-title {
		display: block;
		font-size: 26rpx;
		margin-top: 10rpx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.recommend-price {
		display: block;
		font-size: 24rpx;
		color: #f40;
		margin-top: 5rpx;
	}
</style>