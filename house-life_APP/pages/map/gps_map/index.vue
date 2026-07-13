<template>
	<view class="container">
		<web-view src="/static/googleMap/googleMap.html" :fullscreen="true"></web-view>
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
					count: 1
				},
				map: null,
				// 当前地图中心坐标
				currentLat: 35.83656,
				currentLng: 128.7536,
				mapZoom: 15,

				// 模拟房源数据
				housesData: [{
						id: 1,
						name: '한빛원룸',
						type: 'oneRoom',
						price: 240000,
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
				// 房屋标记点
				houseMakers: [],
				// 搜索相关
				searchQuery: '',
				searchResults: [],
				showSearchResults: false,
				debounceTimer: null
			}
		},
		computed: {
			// 动态生成 Google Maps Embed URL
			iframeMapUrl() {
				return `https://www.google.com/maps/embed/v1/view?key=AIzaSyA8nCCFuQvMJHagDBqjlxxWJtRqQETZ-nc&center=${this.currentLat},${this.currentLng}&zoom=${this.mapZoom}`;
			}
		},

		watch: {
			housesData() {
				// this.createHouseMaker()
			}
		},
		methods: {
			// googole地图初始化
			loadGoogleMap() {
				(g => {
					var h, a, k, p = "The Google Maps JavaScript API",
						c = "google",
						l = "importLibrary",
						q = "__ib__",
						m = document,
						b = window;
					b = b[c] || (b[c] = {});
					var d = b.maps || (b.maps = {}),
						r = new Set,
						e = new URLSearchParams,
						u = () => h || (h = new Promise(async (f, n) => {
							await (a = m.createElement("script"));
							e.set("libraries", [...r] + "");
							for (k in g) e.set(k.replace(/[A-Z]/g, t => "_" + t[0].toLowerCase()), g[k]);
							e.set("callback", c + ".maps." + q);
							a.src = `https://maps.${c}apis.com/maps/api/js?` + e;
							d[q] = f;
							a.onerror = () => h = n(Error(p + " could not load."));
							a.nonce = m.querySelector("script[nonce]")?.nonce || "";
							a.onload = () => {
								console.log(a, 'asdasd')
							}
							m.head.append(a)
						}));
					d[l] ? console.warn(p + " only loads once. Ignoring:", g) : d[l] = (f, ...n) => r.add(f) && u()
						.then(
							() => d[l](f, ...n))
				})({
					key: "AIzaSyA8nCCFuQvMJHagDBqjlxxWJtRqQETZ-nc",
					v: "weekly",
					// Use the 'v' parameter to indicate the version to use (weekly, beta, alpha, etc.).
					// Add other bootstrap parameters as needed, using camel case.
				});
			},
			async initMap() {
				const {
					Map
				} = await google.maps.importLibrary("maps");

				this.map = new Map(document.querySelector(".map-container"), {
					center: {
						lat: this.currentLat,
						lng: this.currentLng
					},
					zoom: this.mapZoom,
					mapId: "gps_map",
				});
			},
			// 获取当前位置
			getCurrentPosition() {
				uni.getLocation({
					type: 'wgs84',
					success: (res) => {
						this.currentLat = res.latitude;
						this.currentLng = res.longitude;
					},
					fail: () => {
						uni.showToast({
							title: '定位失败，使用默认位置',
							icon: 'none'
						});
						// 使用默认坐标
						this.currentLat = 35.83656;
						this.currentLng = 128.7536;
					}
				});
			},

			// 计算标记位置样式
			getMarkerStyle(house) {
				// 简化版坐标转换（实际项目需要更精确的算法）
				const leftPercent = ((house.lng - this.currentLng + 0.02) / 0.04) * 100;
				const topPercent = ((this.currentLat - house.lat + 0.02) / 0.04) * 100;

				return {
					left: `${Math.max(0, Math.min(100, leftPercent))}%`,
					top: `${Math.max(0, Math.min(100, topPercent))}%`
				};
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

			// 显示房源详情
			showHouseDetail(house) {
				uni.navigateTo({
					url: `/pages/map/houseDetailTemplate/houseDetail?id=${house.id}`
				});
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

			// 搜索地点 (使用Google Maps API)
			searchPlaces() {
				if (!this.searchQuery.trim()) return;

				const apiUrl =
					`https://maps.googleapis.com/maps/api/place/autocomplete/json?input=${encodeURIComponent(this.searchQuery)}&key=AIzaSyBAF0EP73NCtVm8ZmyyIzFXR1cnDZQ_TpI&language=ko&region=KR`;

				uni.request({
					url: apiUrl,
					success: (res) => {
						if (res.data.status === 'OK' && res.data.predictions) {
							this.searchResults = res.data.predictions.map(prediction => ({
								id: prediction.place_id,
								main_text: prediction.structured_formatting.main_text,
								secondary_text: prediction.structured_formatting.secondary_text,
								place_id: prediction.place_id
							}));
						} else {
							this.searchResults = [];
						}
					},
					fail: () => {
						this.searchResults = [];
					}
				});
			},

			// 选择搜索结果
			selectSearchResult(result) {
				this.showSearchResults = false;
				this.searchQuery = result.main_text;

				// 获取地点详情并定位
				this.getPlaceDetails(result.place_id);
			},

			// 获取地点详情并定位
			getPlaceDetails(placeId) {
				const apiUrl =
					`https://maps.googleapis.com/maps/api/place/details/json?placeid=${placeId}&key=AIzaSyA8nCCFuQvMJHagDBqjlxxWJtRqQETZ-nc`;

				uni.request({
					url: apiUrl,
					success: (res) => {
						if (res.data.status === 'OK' && res.data.result) {
							const place = res.data.result;
							const location = place.geometry.location;

							// 更新地图位置
							this.currentLat = location.lat;
							this.currentLng = location.lng;
							this.mapZoom = 16;
						}
					}
				});
			},

			// 清空搜索
			clearSearch() {
				this.searchQuery = '';
				this.searchResults = [];
				this.showSearchResults = false;
			},

			goBack() {
				uni.navigateBack();
			}
		}
	}
</script>

<style scoped lang="scss">
	.container {
		display: flex;
		flex-direction: column;
		width: 100%;
	}

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