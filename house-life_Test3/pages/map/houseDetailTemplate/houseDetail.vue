<template>
	<view class="detail-container">
		<!-- 图片滑动区 -->
		<swiper class="image-swiper" indicator-dots autoplay circular>
			<swiper-item v-for="(img, index) in houseImages" :key="index">
				<image class="swiper-image" :src="img" mode="aspectFill" />
			</swiper-item>
		</swiper>

		<!-- 房源信息区 -->
		<view class="info-section">
			<view class="house-title">{{ houseInfo.title }}</view>
			<view class="price-section">
				<text class="price">${{ houseInfo.price }}/月</text>

				<!-- 价格信息 -->
				<view class="price-info">
					<view class="info-title">
						<view class="decorative-line"></view>
						<text>价格信息</text>
					</view>
					<view class="info-labels">
						<text class="label">支付方式</text>
						<text class="label">价格</text>
						<text class="label">保证金</text>
						<text class="label">手续费</text>
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
					:src="'https://maps.googleapis.com/maps/api/staticmap?center=' + houseInfo.latitude + ',' + houseInfo.longitude + '&zoom=15&size=600x300&maptype=roadmap&markers=color:red%7C' + houseInfo.latitude + ',' + houseInfo.longitude" />
			</view>
		</view>

		<!-- 推荐区 -->
		<view class="recommend-section">
			<view class="section-title">
				<view class="decorative-line"></view>
				<text>推荐房源</text>
			</view>
			<scroll-view class="recommend-list" scroll-x>
				<view class="recommend-item" v-for="(item, index) in recommendedHouses" :key="index"
					@click="navigateToDetail(item.id)">
					<image class="recommend-image" :src="item.image" mode="aspectFill" />
					<text class="recommend-title">{{ item.title }}</text>
					<text class="recommend-price">${{ item.price }}/月</text>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 从API获取的房屋图片
				houseImages: [
					'https://example.com/house1.jpg',
					'https://example.com/house2.jpg',
					'https://example.com/house3.jpg'
				],
				// 房屋详细信息
				houseInfo: {
					id: 1,
					title: '朝阳区精装一居室',
					price: 2500,
					address: '北京市朝阳区建国路88号',
					latitude: 39.9042,
					longitude: 116.4074,
					paymentMethod: '微信支付',
					deposit: 5000,
					fee: 200,
					description: '朝阳区精装一居室，交通便利，周边设施齐全'
				},
				// 房屋类型标签
				houseTypes: [{
						icon: 'https://example.com/icon1.png',
						text: '1室1厅'
					},
					{
						icon: 'https://example.com/icon2.png',
						text: '45㎡'
					},
					{
						icon: 'https://example.com/icon3.png',
						text: '朝南'
					}
				],
				// 推荐房源
				recommendedHouses: [{
						id: 2,
						title: '海淀区温馨两居',
						price: 3800,
						image: 'https://example.com/house2.jpg'
					},
					{
						id: 3,
						title: '东城区豪华三居',
						price: 6800,
						image: 'https://example.com/house3.jpg'
					},
					{
						id: 4,
						title: '西城区简约一居',
						price: 2800,
						image: 'https://example.com/house4.jpg'
					}
				]
			}
		},
		onLoad(options) {
			// 从路由参数获取房屋ID
			const houseId = options.id
			// 调用API获取房屋详情
			this.fetchHouseDetail(houseId)
			// 获取推荐房源
			this.fetchRecommendedHouses(houseId)
		},
		methods: {
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
			},

			// 跳转到其他房屋详情
			navigateToDetail(houseId) {
				uni.navigateTo({
					url: '/pages/houseDetail?id=' + houseId
				})
			}
		}
	}
</script>

<style lang="scss">
	.detail-container {
		padding-bottom: 40rpx;
		background-color: #f5f5f5;
	}

	/* 图片滑动区 */
	.image-swiper {
		width: 100%;
		height: 500rpx;
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