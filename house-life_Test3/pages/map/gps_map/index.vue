<template>
	<view class="container">
		<!-- 顶部导航栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="icon">←</text>
				<text>返回</text>
			</view>
			<text class="title">地图找房</text>
			<!-- <view class="add-btn" @click="showAddHousePanel = true">
				<text class="icon">＋</text>
				<text>添加</text>
			</view> -->
		</view>

		<!-- 搜索框 -->
		<view class="search-container">
			<view class="search-box">
				<uni-icons type="search" size="18" color="#999"></uni-icons>
				<input v-model="searchQuery" placeholder="搜索地点或地址" @focus="showSearchResults = true"
					@input="handleSearchInput" />
				<uni-icons v-if="searchQuery" type="clear" size="18" color="#999" @click="clearSearch"></uni-icons>
			</view>

			<!-- 搜索结果列表 -->
			<scroll-view v-if="showSearchResults && searchResults.length > 0" class="search-results" scroll-y>
				<view v-for="(result, index) in searchResults" :key="index" class="result-item"
					@click="selectSearchResult(result)">
					<uni-icons type="location" size="16" color="#4285F4"></uni-icons>
					<view class="result-text">
						<text class="main-text">{{ result.main_text }}</text>
						<text class="secondary-text">{{ result.secondary_text }}</text>
					</view>
				</view>
			</scroll-view>
		</view>

		<!-- 地图容器 -->
		<view class="map-container" id="map-container"></view>

		<!-- 添加房源面板 -->
		<uni-popup ref="addHousePopup" type="bottom" :mask-click="false">
			<view class="add-house-panel">
				<view class="panel-header">
					<text class="title">添加房源信息</text>
					<text class="close-btn" @click="showAddHousePanel = false">×</text>
				</view>

				<view class="form-item">
					<text class="label">房源名称:</text>
					<input v-model="newHouse.name" placeholder="请输入房源名称" />
				</view>

				<view class="form-item">
					<text class="label">房源类型:</text>
					<picker @change="onTypeChange" :value="typeIndex" :range="houseTypes">
						<view class="picker">{{houseTypes[typeIndex]}}</view>
					</picker>
				</view>

				<view class="form-item">
					<text class="label">月租金:</text>
					<input v-model="newHouse.price" type="number" placeholder="请输入月租金" />
				</view>

				<view class="form-item">
					<text class="label">坐标位置:</text>
					<view class="coord-inputs">
						<input v-model="newHouse.lat" placeholder="纬度" />
						<input v-model="newHouse.lng" placeholder="经度" />
					</view>
				</view>

				<button class="confirm-btn" @click="addHouse">确认添加</button>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				map: null,
				geocoder: null,
				houseMarkers: [], // 存储所有房源标记

				// 房源类型数据
				houseTypes: ['一居室', '两居室', '三居室', '公寓', '别墅'],
				typeIndex: 0,

				// 添加房源面板控制
				showAddHousePanel: false,

				// 新房源数据
				newHouse: {
					name: '',
					type: 'oneRoom',
					price: 0,
					lat: null,
					lng: null,
					count: 1 // 默认数量为1
				},

				// 当前定位坐标
				currentPosition: {
					lat: null,
					lng: null
				},

				//API Part
				// 模拟房源数据
				housesData: [{
						id: 1,
						name: '한빛원룸',
						type: 'oneRoom',
						price: 240.000,
						lat: 35.83928, 
						lng: 128.7537,
						count: 1
					},
					{
						id: 2,
						name: '城市花园',
						type: 'oneRoom',
						price: 5000,
						lat: 35.84097,
						lng: 128.7563,
						count: 1
					},
					{
						id: 3,
						name: '城市花园',
						type: 'twoRoom',
						price: 5000,
						lat: 35.84256, 
						lng: 128.7543,
						count: 1
					}
				],
      searchQuery: '',
      searchResults: [],
      showSearchResults: false,
      searchService: null,
      placesService: null,
      autocompleteService: null,
      debounceTimer: null
			}
		},
		onLoad() {
			this.loadGoogleMapsAPI().then(() => {
				this.getCurrentPosition().then(pos => {
					this.currentPosition = pos;
					this.initMap(pos.lat, pos.lng);
					this.initPlacesService(); // 初始化Places服务
					this.loadHousesData();
				}).catch(() => {
					// 默认영남대역坐标
					this.initMap(35.83656, 128.7536);
					this.initPlacesService(); // 初始化Places服务
					this.loadHousesData();
				});
			});
		},
		methods: {
			// 加载Google地图API
			loadGoogleMapsAPI() {
				return new Promise((resolve) => {
					if (window.google && window.google.maps) {
						resolve();
						return;
					}

					const script = document.createElement('script');
					script.src =
						`https://maps.googleapis.com/maps/api/js?key=AIzaSyBAF0EP73NCtVm8ZmyyIzFXR1cnDZQ_TpI&libraries=places`;
					script.onload = resolve;
					document.head.appendChild(script);
				});
			},

			// 获取当前位置
			getCurrentPosition() {
				return new Promise((resolve, reject) => {
					// #ifdef H5
					if (navigator.geolocation) {
						navigator.geolocation.getCurrentPosition(
							position => resolve({
								lat: position.coords.latitude,
								lng: position.coords.longitude
							}),
							reject, {
								enableHighAccuracy: true,
								timeout: 5000
							}
						);
					} else {
						reject(new Error('浏览器不支持定位'));
					}
					// #endif

					// #ifdef APP-PLUS
					uni.getLocation({
						type: 'gcj02',
						success: res => resolve({
							lat: res.latitude,
							lng: res.longitude
						}),
						fail: reject
					});
					// #endif
				});
			},

			// 初始化地图
			initMap(lat, lng) {
				const mapContainer = document.getElementById('map-container');

				this.map = new google.maps.Map(mapContainer, {
					center: {
						lat,
						lng
					},
					zoom: 15,
					mapTypeControl: false,
					streetViewControl: false
				});

				this.geocoder = new google.maps.Geocoder();

				// 添加地图点击事件，用于添加新房源
				this.map.addListener('click', (e) => {
					this.newHouse.lat = e.latLng.lat();
					this.newHouse.lng = e.latLng.lng();
					this.showAddHousePanel = true;
				});
			},

			// 加载房源数据并在地图上显示
			loadHousesData() {
				// 清除现有标记
				this.clearHouseMarkers();

				// 为每个房源创建标记
				this.housesData.forEach(house => {
					this.createHouseMarker(house);
				});
			},

			// 创建房源标记（半透明蓝色圆圈带数量）
			createHouseMarker(house) {
				// 创建圆形覆盖物
				const circle = new google.maps.Circle({
					strokeColor: '#4285F4',
					strokeOpacity: 0.8,
					strokeWeight: 2,
					fillColor: '#4285F4',
					fillOpacity: 0.35,
					map: this.map,
					center: {
						lat: house.lat,
						lng: house.lng
					},
					radius: 100, // 100米半径
					houseData: house // 存储关联数据
				});

				// 创建数量标签
				const label = new google.maps.Marker({
					position: {
						lat: house.lat,
						lng: house.lng
					},
					map: this.map,
					label: {
						text: house.count.toString(),
						color: 'white',
						fontSize: '12px',
						fontWeight: 'bold'
					},
					icon: {
						url: 'data:image/svg+xml;charset=UTF-8;base64,PHN2ZyB3aWR0aD0iMzAiIGhlaWdodD0iMzAiIHZpZXdCb3g9IjAgMCAzMCAzMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48Y2lyY2xlIGN4PSIxNSIgY3k9IjE1IiByPSIxNSIgZmlsbD0iIzQyODVGNCIvPjwvc3ZnPg==',
						scaledSize: new google.maps.Size(30, 30),
						anchor: new google.maps.Point(15, 15)
					},
					houseData: house
				});

				// 添加点击事件
				circle.addListener('click', () => this.showHouseDetail(house));
				label.addListener('click', () => this.showHouseDetail(house));

				// 保存标记引用
				this.houseMarkers.push(circle, label);
			},

			// 清除所有房源标记
			clearHouseMarkers() {
				this.houseMarkers.forEach(marker => {
					marker.setMap(null);
				});
				this.houseMarkers = [];
			},

			//跳转到房屋List Part
			// 显示房源详情
			showHouseDetail(house) {
				uni.navigateTo({
					url: `/pages/map/houseDetailTemplate/houseDetail?id=${house.id}`
				});
			},

			// 房源类型选择变化
			onTypeChange(e) {
				this.typeIndex = e.detail.value;
				const types = ['oneRoom', 'twoRoom', 'threeRoom', 'apartment', 'villa'];
				this.newHouse.type = types[this.typeIndex];
			},

			// 添加新房源
			addHouse() {
				if (!this.newHouse.name || !this.newHouse.price || !this.newHouse.lat || !this.newHouse.lng) {
					uni.showToast({
						title: '请填写完整信息',
						icon: 'none'
					});
					return;
				}

				// 检查是否已有相同位置的房源
				const existingHouse = this.housesData.find(house =>
					house.lat === this.newHouse.lat && house.lng === this.newHouse.lng
				);

				if (existingHouse) {
					// 已有房源，增加数量
					existingHouse.count += 1;
				} else {
					// 新房源，添加到数据
					this.housesData.push({
						id: Date.now(),
						name: this.newHouse.name,
						type: this.newHouse.type,
						price: parseFloat(this.newHouse.price),
						lat: parseFloat(this.newHouse.lat),
						lng: parseFloat(this.newHouse.lng),
						count: 1
					});
				}

				// 重新加载房源数据
				this.loadHousesData();

				// 重置表单
				this.newHouse = {
					name: '',
					type: 'oneRoom',
					price: 0,
					lat: null,
					lng: null,
					count: 1
				};
				this.typeIndex = 0;

				// 关闭面板
				this.showAddHousePanel = false;

				uni.showToast({
					title: '添加成功'
				});
			},

			goBack() {
				uni.navigateBack();
			},

			// 初始化Places服务
			initPlacesService() {
				if (!window.google || !window.google.maps) return;

				this.autocompleteService = new google.maps.places.AutocompleteService();
				this.placesService = new google.maps.places.PlacesService(this.map);
			},

			// 处理搜索输入
			handleSearchInput() {
				if (!this.searchQuery.trim()) {
					this.searchResults = [];
					return;
				}

				// 防抖处理
				clearTimeout(this.debounceTimer);
				this.debounceTimer = setTimeout(() => {
					this.searchPlaces();
				}, 300);
			},

			// 搜索地点
			searchPlaces() {
				if (!this.autocompleteService || !this.searchQuery.trim()) return;

				const request = {
					input: this.searchQuery,
					location: new google.maps.LatLng(
						this.currentPosition.lat || 39.90923,
						this.currentPosition.lng || 116.397428
					),
					radius: 50000, // 50公里半径
					componentRestrictions: {
						country: 'KR'
					} // 限制中国地区
				};

				this.autocompleteService.getPlacePredictions(request, (predictions, status) => {
					if (status !== google.maps.places.PlacesServiceStatus.OK || !predictions) {
						this.searchResults = [];
						return;
					}

					this.searchResults = predictions.map(prediction => ({
						id: prediction.place_id,
						main_text: prediction.structured_formatting.main_text,
						secondary_text: prediction.structured_formatting.secondary_text,
						place_id: prediction.place_id
					}));
				});
			},

			// 选择搜索结果
			selectSearchResult(result) {
				this.showSearchResults = false;
				this.searchQuery = result.main_text;

				// 获取地点详情
				this.getPlaceDetails(result.place_id);
			},

			// 获取地点详情并定位
			getPlaceDetails(placeId) {
				const request = {
					placeId: placeId,
					fields: ['geometry', 'name', 'formatted_address']
				};

				this.placesService.getDetails(request, (place, status) => {
					if (status === google.maps.places.PlacesServiceStatus.OK) {
						const location = place.geometry.location;
						this.map.panTo(location);
						this.map.setZoom(16);

						// 可以在这里添加标记
						new google.maps.Marker({
							position: location,
							map: this.map,
							title: place.name
						});
					}
				});
			},

			// 清空搜索
			clearSearch() {
				this.searchQuery = '';
				this.searchResults = [];
				this.showSearchResults = false;
			}




		}
	}
</script>

<style scoped>
	/* 搜索框样式 */
	.search-container {
		position: absolute;
		top: 70px;
		/* 在导航栏下方 */
		left: 15px;
		right: 15px;
		z-index: 100;
	}

	.search-box {
		display: flex;
		align-items: center;
		background: white;
		border-radius: 20px;
		padding: 8px 15px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	}

	.search-box input {
		flex: 1;
		margin: 0 10px;
		font-size: 14px;
	}

	/* 搜索结果列表样式 */
	.search-results {
		max-height: 300px;
		background: white;
		border-radius: 10px;
		margin-top: 5px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		overflow: hidden;
	}

	.result-item {
		display: flex;
		align-items: center;
		padding: 12px 15px;
		border-bottom: 1px solid #f0f0f0;
	}

	.result-item:last-child {
		border-bottom: none;
	}

	.result-text {
		flex: 1;
		margin-left: 10px;
	}

	.main-text {
		display: block;
		font-size: 14px;
		color: #333;
	}

	.secondary-text {
		display: block;
		font-size: 12px;
		color: #999;
		margin-top: 2px;
	}

	.container {
		display: flex;
		flex-direction: column;
		height: 100vh;
		width: 100%;
	}

	.nav-bar {
		height: 60px;
		background-color: #ffffff;
		display: flex;
		align-items: center;
		padding: 0 15px;
		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		position: relative;
		z-index: 10;
	}

	.back-btn {
		display: flex;
		align-items: center;
		margin-right: 15px;
	}

	.back-btn .icon {
		margin-right: 5px;
		font-size: 20px;
	}

	.title {
		font-size: 18px;
		font-weight: bold;
		flex: 1;
		text-align: center;
	}

	.add-btn {
		display: flex;
		align-items: center;
		margin-left: auto;
		padding: 5px 10px;
		background-color: #4285f4;
		color: white;
		border-radius: 5px;
	}

	.add-btn .icon {
		margin-right: 5px;
	}

	.map-container {
		flex: 1;
		position: relative;
		overflow: hidden;
	}

	/* 添加房源面板样式 */
	.add-house-panel {
		background: #fff;
		padding: 20px;
		border-radius: 15px 15px 0 0;
	}

	.panel-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 20px;
	}

	.panel-header .title {
		font-size: 18px;
		font-weight: bold;
		text-align: left;
		flex: none;
	}

	.close-btn {
		font-size: 24px;
		padding: 5px;
	}

	.form-item {
		margin-bottom: 15px;
	}

	.form-item .label {
		display: block;
		margin-bottom: 5px;
		font-weight: bold;
	}

	.form-item input {
		border: 1px solid #ddd;
		padding: 10px;
		border-radius: 5px;
		width: 100%;
	}

	.coord-inputs {
		display: flex;
		gap: 10px;
	}

	.coord-inputs input {
		flex: 1;
	}

	.picker {
		border: 1px solid #ddd;
		padding: 10px;
		border-radius: 5px;
	}

	.confirm-btn {
		background-color: #4285f4;
		color: white;
		border: none;
		border-radius: 5px;
		padding: 12px;
		margin-top: 10px;
		width: 100%;
	}
</style>