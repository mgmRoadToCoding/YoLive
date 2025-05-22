<template>
	<view class="container" @click="closeAllDropdowns">
		<!-- 搜索框区域 -->
		<view class="search-container">
			<input class="search-input" placeholder="请输入小区或商圈名称" placeholder-class="placeholder-style"
				:style="{ borderRadius: '10px' }" v-model="searchText" @input="handleSearch" />
			<image class="search-icon" src="https://img.icons8.com/ios/50/000000/search--v1.png" />
		</view>

		<!-- 筛选条件区域 -->
		<view class="filter-container">
			<!-- 位置筛选 -->
			<view class="filter-item" @click.stop="toggleDropdown(0)">
				<text>{{ locationText }}</text>
				<image class="arrow-icon" src="https://img.icons8.com/ios/50/000000/expand-arrow--v1.png" />
				<!-- 位置下拉菜单 -->
				<view v-if="dropdownVisible[0]" class="dropdown-menu">
					<view v-for="(item, index) in locationOptions" :key="index" class="dropdown-item"
						@click.stop="selectItem(0, item)">
						{{ item }}
					</view>
				</view>
			</view>

			<!-- 价格筛选 -->
			<view class="filter-item" @click.stop="toggleDropdown(1)">
				<text>{{ priceText }}</text>
				<image class="arrow-icon" src="https://img.icons8.com/ios/50/000000/expand-arrow--v1.png" />
				<!-- 价格下拉菜单 -->
				<view v-if="dropdownVisible[1]" class="dropdown-menu">
					<view v-for="(item, index) in priceOptions" :key="index" class="dropdown-item"
						@click.stop="selectItem(1, item)">
						{{ item }}
					</view>
				</view>
			</view>

			<!-- 房间个数筛选 -->
			<view class="filter-item" @click.stop="toggleDropdown(2)">
				<text>{{ roomText }}</text>
				<image class="arrow-icon" src="https://img.icons8.com/ios/50/000000/expand-arrow--v1.png" />
				<!-- 房间下拉菜单 -->
				<view v-if="dropdownVisible[2]" class="dropdown-menu">
					<view v-for="(item, index) in roomOptions" :key="index" class="dropdown-item"
						@click.stop="selectItem(2, item)">
						{{ item }}
					</view>
				</view>
			</view>
		</view>

		<!-- 租房列表 -->
		<scroll-view class="list-container" scroll-y>
			<view v-for="(item, index) in filteredHouseList" :key="index" class="house-item"
				@click="navigateToDetail(item.id)">
				<image class="house-image" :src="item.image" mode="aspectFill" />
				<view class="house-info">
					<text class="house-title">{{ item.title }}</text>
					<text class="house-desc">{{ item.desc }}</text>
					<text class="house-price">{{ item.price }}</text>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchText: '', // 搜索文本
				// 下拉菜单显示状态
				dropdownVisible: [false, false, false],
				// 位置筛选选项
				locationOptions: ['全部区域', '朝阳区', '海淀区', '东城区', '西城区'],
				// 价格筛选选项
				priceOptions: ['不限价格', '1000以下', '1000-2000', '2000-3000', '3000以上'],
				// 房间筛选选项
				roomOptions: ['不限户型', '1室', '2室', '3室', '4室及以上'],
				// 当前选中的筛选条件
				locationText: '位置',
				priceText: '价格',
				roomText: '户型',

				//API Part
				// 租房列表数据
				houseList: [{
						id: 1,
						title: '朝阳区精装一居室',
						desc: '朝阳区 | 1室1厅 | 45㎡',
						price: '¥2500/月',
						image: 'https://example.com/house1.jpg'
					},
					{
						id: 2,
						title: '海淀区温馨两居',
						desc: '海淀区 | 2室1厅 | 65㎡',
						price: '¥3800/月',
						image: 'https://example.com/house2.jpg'
					},
					{
						id: 3,
						title: '东城区豪华三居',
						desc: '东城区 | 3室2厅 | 120㎡',
						price: '¥6800/月',
						image: 'https://example.com/house3.jpg'
					}
				],
				// 筛选条件
				filters: {
					location: '',
					price: '',
					room: ''
				}
			}
		},
		computed: {
			// 筛选后的房源列表
			filteredHouseList() {
				return this.houseList.filter(house => {
					// 搜索条件匹配
					const searchMatch = this.searchText === '' ||
						house.title.includes(this.searchText) ||
						house.desc.includes(this.searchText)

					// 位置筛选
					const locationMatch = this.filters.location === '' ||
						this.filters.location === '全部区域' ||
						house.desc.includes(this.filters.location)

					// 价格筛选
					const priceMatch = this.filters.price === '' ||
						this.filters.price === '不限价格' ||
						this.checkPriceMatch(house.price, this.filters.price)

					// 户型筛选
					const roomMatch = this.filters.room === '' ||
						this.filters.room === '不限户型' ||
						house.desc.includes(this.filters.room)

					return searchMatch && locationMatch && priceMatch && roomMatch
				})
			}
		},
		methods: {
			/**
			 * 检查价格是否匹配筛选条件
			 */
			checkPriceMatch(price, filter) {
				const priceNum = parseInt(price.replace(/[^0-9]/g, ''))

				switch (filter) {
					case '1000以下':
						return priceNum < 1000
					case '1000-2000':
						return priceNum >= 1000 && priceNum <= 2000
					case '2000-3000':
						return priceNum > 2000 && priceNum <= 3000
					case '3000以上':
						return priceNum > 3000
					default:
						return true
				}
			},

			/**
			 * 处理搜索输入
			 */
			handleSearch() {
				// 搜索逻辑已通过计算属性filteredHouseList自动处理
				console.log('搜索:', this.searchText)
			},

			/**
			 * 关闭所有下拉菜单
			 */
			closeAllDropdowns() {
				this.dropdownVisible = [false, false, false]
			},

			/**
			 * 切换下拉菜单显示状态
			 * @param {Number} index - 下拉菜单索引(0:位置,1:价格,2:户型)
			 */
			toggleDropdown(index) {
				// 先关闭所有下拉菜单
				this.closeAllDropdowns()
				// 再切换当前点击的下拉菜单
				this.$set(this.dropdownVisible, index, !this.dropdownVisible[index])
			},

			/**
			 * 选择下拉菜单选项
			 * @param {Number} type - 筛选类型(0:位置,1:价格,2:户型)
			 * @param {String} value - 选中的值
			 */
			selectItem(type, value) {
				// 根据类型更新显示的文本
				switch (type) {
					case 0:
						this.locationText = value
						this.filters.location = value === '全部区域' ? '' : value
						break
					case 1:
						this.priceText = value
						this.filters.price = value === '不限价格' ? '' : value
						break
					case 2:
						this.roomText = value
						this.filters.room = value === '不限户型' ? '' : value
						break
				}

				// 关闭下拉菜单
				this.dropdownVisible[type] = false

				console.log('筛选条件:', this.filters)
			},
			/**
			 * 跳转到详情页
			 * @param {Number} id - 房屋ID
			 */
			navigateToDetail(id) {
				uni.navigateTo({
					url: '/pages/map/houseDetailTemplate/houseDetail?id=' + id
				})
			}

		}
	}
</script>

<style lang="scss">
	.container {
		padding: 20rpx;
		background-color: #f5f5f5;
		height: 100vh;
		box-sizing: border-box;
	}

	/* 搜索框样式 */
	.search-container {
		position: relative;
		margin-bottom: 20rpx;
		background-color: #fff;
		border-radius: 10px;
		display: flex;
		align-items: center;
		padding: 0 20rpx;
		height: 80rpx;
		border: 1px solid #2979ff;
	}

	.search-input {
		flex: 1;
		height: 100%;
		padding-right: 80rpx;
		font-size: 28rpx;
	}

	.placeholder-style {
		color: #999;
	}

	.search-icon {
		position: absolute;
		right: 20rpx;
		width: 40rpx;
		height: 40rpx;
	}

	/* 筛选条件样式 */
	.filter-container {
		display: flex;
		justify-content: space-between;
		background-color: #fff;
		border-radius: 10px;
		padding: 20rpx;
		margin-bottom: 20rpx;
	}

	.filter-item {
		flex: 1;
		display: flex;
		align-items: center;
		justify-content: center;
		position: relative;
		font-size: 28rpx;
		color: #333;
	}

	.arrow-icon {
		width: 30rpx;
		height: 30rpx;
		margin-left: 10rpx;
	}

	/* 下拉菜单样式 */
	.dropdown-menu {
		position: absolute;
		top: 100%;
		left: 0;
		right: 0;
		background-color: #fff;
		border-radius: 10px;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
		z-index: 10;
		margin-top: 10rpx;
	}

	.dropdown-item {
		padding: 20rpx;
		text-align: center;
		border-bottom: 1rpx solid #eee;
	}

	.dropdown-item:last-child {
		border-bottom: none;
	}

	.dropdown-item:active {
		background-color: #f5f5f5;
	}

	/* 租房列表样式 */
	.list-container {
		height: calc(100vh - 240rpx);
	}

	.house-item {
		display: flex;
		background-color: #fff;
		border-radius: 10px;
		margin-bottom: 20rpx;
		padding: 20rpx;
	}

	.house-image {
		width: 200rpx;
		height: 150rpx;
		border-radius: 8rpx;
		margin-right: 20rpx;
	}

	.house-info {
		flex: 1;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.house-title {
		font-size: 30rpx;
		color: #333;
		font-weight: bold;
	}

	.house-desc {
		font-size: 26rpx;
		color: #666;
	}

	.house-price {
		font-size: 32rpx;
		color: #f40;
		font-weight: bold;
	}
</style>