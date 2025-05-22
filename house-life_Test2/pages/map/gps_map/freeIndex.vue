<template>
	<view class="container">
		<!-- 顶部导航栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="icon">←</text>
				<text>返回</text>
			</view>
			<text class="title">选择位置</text>
		</view>

		<!-- 地图容器 -->
		<map id="map" class="map-container" :latitude="latitude" :longitude="longitude" :scale="scale"
			:markers="markers" :circles="circles" @tap="handleMapTap" show-location></map>

		<!-- 圆形半径控制 -->
		<view class="radius-control" v-if="showRadiusControl">
			<text class="label">圆形区域半径</text>
			<slider :value="circleRadius" min="100" max="2000" step="50" @change="updateCircleRadius"
				activeColor="#4285F4" />
			<text class="radius-value">{{ circleRadius }}米</text>
		</view>

		<!-- 控制按钮 -->
		<view class="control-buttons">
			<button @click="startDrawingCircle" class="control-btn" :class="{active: isDrawing}">
				{{ isDrawing ? '请点击地图选择圆心' : '绘制圆形区域' }}
			</button>
			<button @click="clearCircle" class="control-btn">清除圆形</button>
		</view>

		<!-- 房源信息弹窗 -->
		<uni-popup ref="housePopup" type="bottom">
			<view class="house-popup">
				<view class="popup-header">
					<text class="popup-title">房源信息</text>
					<text class="close-btn" @click="closePopup">×</text>
				</view>

				<swiper class="house-swiper" autoplay interval="3000">
					<swiper-item v-for="(img, index) in currentHouse.images" :key="index">
						<image :src="img" mode="aspectFill" class="house-image"></image>
					</swiper-item>
				</swiper>

				<view class="house-info">
					<text class="house-name">{{ currentHouse.name }}</text>
					<view class="house-type">
						<text class="type-icon">🏠</text>
						<text>{{ formatHouseType(currentHouse.type) }}</text>
					</view>
					<text class="house-price">¥{{ currentHouse.price.toLocaleString() }}/月</text>
					<text class="house-time">上架时间: {{ formatDate(currentHouse.postTime) }}</text>
				</view>

				<button class="contact-btn" @click="contactOwner">联系房东</button>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				latitude: 31.2304, // 默认上海纬度
				longitude: 121.4737, // 默认上海经度
				scale: 15, // 地图缩放级别
				isDrawing: false, // 是否正在绘制圆形
				markers: [], // 地图标记点
				circles: [], // 地图圆形覆盖物
				circleRadius: 500, // 默认半径500米
				showRadiusControl: false, // 是否显示半径控制
				houses: [{
						id: 1,
						latitude: 31.2304,
						longitude: 121.4737,
						name: "浦东新区豪华公寓",
						type: "twoRoom",
						price: 6500,
						postTime: "2023-10-15",
						images: [
							"https://via.placeholder.com/300x200?text=房源1",
							"https://via.placeholder.com/300x200?text=客厅"
						]
					},
					{
						id: 2,
						latitude: 31.2354,
						longitude: 121.4787,
						name: "静安区温馨一居室",
						type: "oneRoom",
						price: 4800,
						postTime: "2023-11-02",
						images: [
							"https://via.placeholder.com/300x200?text=房源2",
							"https://via.placeholder.com/300x200?text=卧室"
						]
					}
				],
				currentHouse: {
					images: [],
					name: '',
					type: '',
					price: 0,
					postTime: new Date()
				}
			}
		},
		onReady() {
			this.initMap();
		},
		methods: {
			// 初始化地图
			initMap() {
				// 添加房源标记
				this.markers = this.houses.map(house => ({
					id: house.id,
					latitude: house.latitude,
					longitude: house.longitude,
					title: house.name,
					iconPath: '/static/marker.png', // 确保项目中有这个图片
					width: 30,
					height: 30,
					callout: {
						content: house.name,
						color: '#333',
						fontSize: 14,
						borderRadius: 5,
						bgColor: '#fff',
						padding: 5,
						display: 'ALWAYS'
					}
				}));
			},

			onReady() {
				// 如果不是微信小程序，给出提示
				if (process.env.VUE_APP_PLATFORM !== 'mp-weixin') {
					uni.showToast({
						title: '圆形区域仅支持在小程序中显示',
						icon: 'none'
					});
				} else {
					this.initMap();
				}
			},

			// 开始绘制圆形区域
			startDrawingCircle() {
				this.isDrawing = true;
				this.showRadiusControl = true;
				uni.showToast({
					title: '请点击地图选择圆心位置',
					icon: 'none',
					duration: 2000
				});
			},

			// 处理地图点击事件
			handleMapTap(e) {
				if (this.isDrawing) {
					// 绘制圆形(有问题)
					// this.circles = [{
					// 	latitude: e.detail.latitude,
					// 	longitude: e.detail.longitude,
					// 	radius: this.circleRadius,
					// 	color: '#4285F4', // 边框颜色
					// 	fillColor: '#4285F4', // 填充颜色带透明度
					// 	strokeWidth: 2
					// }];
					
					//手动设定一个圆形，
					this.circles = [{
					  latitude: 31.2304,
					  longitude: 121.4737,
					  radius: 1000,
					  color: '#FF0000',
					  fillColor: '#FF000088',
					  strokeWidth: 3
					}];

					this.isDrawing = false;
					uni.showToast({
						title: '圆形区域已绘制',
						icon: 'success',
						duration: 1500
					});
				} else if (this.circles.length > 0) {
					// 检查点击是否在圆形区域内
					const circle = this.circles[0];
					const distance = this.getDistance(
						e.detail.latitude,
						e.detail.longitude,
						circle.latitude,
						circle.longitude
					);

					if (distance <= circle.radius) {
						this.showHousesInCircle(circle.latitude, circle.longitude, circle.radius);
					}
				}
			},

			// 更新圆形半径
			updateCircleRadius(e) {
				this.circleRadius = e.detail.value;
				if (this.circles.length > 0) {
					// 创建新数组触发响应式更新
					this.circles = [{
						...this.circles[0],
						radius: this.circleRadius
					}];
				}
			},

			// 清除圆形区域
			clearCircle() {
				this.circles = [];
				this.isDrawing = false;
				this.showRadiusControl = false;
			},

			// 计算两点间距离(米)
			getDistance(lat1, lng1, lat2, lng2) {
				const radLat1 = lat1 * Math.PI / 180.0;
				const radLat2 = lat2 * Math.PI / 180.0;
				const a = radLat1 - radLat2;
				const b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
				let s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
					Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
				s = s * 6378.137; // 地球半径(千米)
				s = Math.round(s * 10000) / 10; // 转为米
				return s;
			},

			// 显示圆形区域内的房源
			showHousesInCircle(latitude, longitude, radius) {
				const housesInCircle = this.houses.filter(house => {
					const distance = this.getDistance(
						latitude,
						longitude,
						house.latitude,
						house.longitude
					);
					return distance <= radius;
				});

				if (housesInCircle.length > 0) {
					this.showHouseInfo(housesInCircle[0]);
				} else {
					uni.showToast({
						title: '该区域内没有房源',
						icon: 'none'
					});
				}
			},

			// 显示房源信息
			showHouseInfo(house) {
				this.currentHouse = house;
				this.$refs.housePopup.open();
			},

			// 关闭弹窗
			closePopup() {
				this.$refs.housePopup.close();
			},

			// 联系房东
			contactOwner() {
				uni.makePhoneCall({
					phoneNumber: '13800138000'
				});
			},

			// 格式化房屋类型
			formatHouseType(type) {
				const types = {
					oneRoom: '一居室',
					twoRoom: '两居室',
					threeRoom: '三居室',
					apartment: '公寓',
					villa: '别墅'
				};
				return types[type] || type;
			},

			// 格式化日期
			formatDate(dateStr) {
				const date = new Date(dateStr);
				return `${date.getFullYear()}年${date.getMonth()+1}月${date.getDate()}日`;
			},

			// 返回
			goBack() {
				uni.navigateBack();
			}
		}
	}
</script>

<style scoped>
	.container {
		display: flex;
		flex-direction: column;
		height: 100vh;
		position: relative;
	}

	.nav-bar {
		height: 60px;
		background-color: #fff;
		display: flex;
		align-items: center;
		padding: 0 15px;
		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		position: relative;
		z-index: 100;
	}

	.back-btn {
		display: flex;
		align-items: center;
	}

	.icon {
		margin-right: 5px;
		font-size: 20px;
	}

	.title {
		font-size: 18px;
		font-weight: bold;
		flex: 1;
		text-align: center;
	}

	.map-container {
		flex: 1;
		width: 100%;
		z-index: 1;
	}

	.radius-control {
		position: absolute;
		bottom: 100px;
		left: 20px;
		right: 20px;
		background: rgba(255, 255, 255, 0.9);
		padding: 15px;
		border-radius: 10px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		z-index: 1000;
	}

	.radius-control .label {
		font-weight: bold;
		margin-bottom: 8px;
		display: block;
	}

	.radius-value {
		display: block;
		text-align: center;
		margin-top: 5px;
		color: #666;
	}

	.control-buttons {
		position: absolute;
		top: 70px;
		right: 10px;
		z-index: 1000;
		display: flex;
		flex-direction: column;
	}

	.control-btn {
		margin-bottom: 10px;
		background: #fff;
		border: 1px solid #ddd;
		border-radius: 4px;
		padding: 8px 12px;
		font-size: 14px;
		box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
	}

	.control-btn.active {
		background-color: #4285f4;
		color: white;
		border-color: #4285f4;
	}

	.house-popup {
		padding: 20px;
		background: #fff;
		border-radius: 16px 16px 0 0;
	}

	.popup-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 15px;
	}

	.popup-title {
		font-size: 18px;
		font-weight: bold;
	}

	.close-btn {
		font-size: 24px;
		padding: 5px 15px;
		color: #999;
	}

	.house-swiper {
		height: 200px;
		margin-bottom: 15px;
		border-radius: 8px;
		overflow: hidden;
	}

	.house-image {
		width: 100%;
		height: 100%;
	}

	.house-info {
		margin-bottom: 20px;
	}

	.house-name {
		font-size: 16px;
		font-weight: bold;
		display: block;
		margin-bottom: 10px;
	}

	.house-type {
		display: flex;
		align-items: center;
		margin-bottom: 8px;
		color: #666;
	}

	.type-icon {
		margin-right: 5px;
	}

	.house-price {
		color: #f56c6c;
		font-size: 18px;
		font-weight: bold;
		display: block;
		margin: 10px 0;
	}

	.house-time {
		color: #999;
		font-size: 12px;
		display: block;
	}

	.contact-btn {
		background-color: #4285f4;
		color: white;
		border-radius: 5px;
		padding: 12px;
		text-align: center;
		border: none;
		width: 100%;
		margin-top: 10px;
	}
</style>