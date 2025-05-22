<template>
	<view class="container">
		<!-- 背景图片 -->
		<image class="background-image" src="/static/pexels-jvdm-1457841.jpg" mode="aspectFill"></image>

		<!-- 选择框区域 -->
		<view class="selection-container">
			<view class="select-box" @click="toggleDropdown">
				<text>{{ selectedOption || '현 위치 클릭하세요' }}</text>
				<uni-icons :type="showDropdown ? 'arrowup' : 'arrowdown'" size="16" color="#666"></uni-icons>
			</view>

			<!-- 下拉列表 -->
			<view class="dropdown" v-if="showDropdown">
				<view class="dropdown-item" v-for="(item, index) in options" :key="index" @click="selectOption(item)">
					{{ item.label }}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				showDropdown: false,
				selectedOption: '',
				options: [{
						label: 'GPS',
						path: '/pages/map/gps_map/index'
					},
					{
						label: '경산시',
						path: '/pages/map/houserTemplate/house'
					},
					{
						label: '대구시',
						path: '/pages/map/houserTemplate/house'
					},
					{
						label: '울산시',
						path: '/pages/map/houserTemplate/house'
					}
				]
			}
		},
		methods: {
			toggleDropdown() {
				this.showDropdown = !this.showDropdown
			},
			selectOption(item) {
				this.selectedOption = item.label
				this.showDropdown = false
				// 跳转到对应页面
				uni.navigateTo({
					url: item.path
				})
			}
		}
	}
</script>

<style>
	.container {
		position: relative;
		width: 100vw;
		height: 50vh;
		display: flex;
		justify-content: center;
		align-items: flex-start;
		padding-top: 100px;
	}

	.background-image {
		width: 95%;
		position: absolute;
		border-radius: 10px;
	}

	.selection-container {
		width: 80%;
		max-width: 300px;
		position: relative;
		margin: auto;
	}

	.select-box {
		background-color: rgba(255, 255, 255, 0.9);
		padding: 12px 15px;
		border-radius: 8px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
		justify-content: center;
	}

	.select-box:hover {
		background-color: #2979ff;
		color: white;
	}

	.dropdown {
		background-color: rgba(255, 255, 255, 0.95);
		border-radius: 8px;
		margin-top: 5px;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
		max-height: 200px;
		overflow-y: auto;
		text-align: center;
	}


	.dropdown-item {
		padding: 12px 15px;
		border-bottom: 1px solid #eee;
	}

	.dropdown-item:last-child {
		border-bottom: none;
	}

	.dropdown-item:active {
		background-color: #f5f5f5;
	}
</style>