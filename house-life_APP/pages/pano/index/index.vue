<template>
	<view class="container">
		<div id="viewer"></div>
	</view>
</template>

<script>
	import {
		Viewer
	} from '@photo-sphere-viewer/core'

	export default {
		data() {
			return {
				viewer: null
			}
		},
		mounted() {
			this.$nextTick(() => this.initPano())
		},
		methods: {
			initPano() {
				const container = document.getElementById('viewer')
				if (!container) return

				this.viewer = new Viewer({
					container: container,
					panorama: 'https://yolivepanoimages.oss-ap-northeast-2.aliyuncs.com/quanjing.jpg',
					// 基础配置
					caption: '阿里云全景图',
					defaultZoomLvl: 50,
					mousewheel: true,
					touchmoveTwoFingers: true,
				})

				// 错误监听
				this.viewer.on('error', (error) => {
					console.error('加载错误:', error.message)
					uni.showToast({
						title: '全景图加载失败',
						icon: 'none'
					})
				})

				// 加载完成监听
				this.viewer.on('ready', () => {
					console.log('全景图加载完成')
					uni.showToast({
						title: '加载成功',
						icon: 'success'
					})
				})
			}
		},
		onUnload() {
			if (this.viewer) {
				this.viewer.destroy()
				this.viewer = null
			}
		}
	}
</script>

<style>
	.container {
		width: 100vw;
		height: 100vh;
		overflow: hidden;
	}

	#viewer {
		width: 100%;
		height: 100%;
		background: #000;
	}
</style>