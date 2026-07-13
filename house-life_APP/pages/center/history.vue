<template>
	<view>
		<u-navbar :is-back="true" title="Browsing History" :border-bottom="false">
			<view class="u-nav-slot" slot="right" @click="clearHistory">
				<u-icon name="trash"></u-icon>
				Clear
			</view>
		</u-navbar>
		<scroll-view scroll-y style="height: 100%;width: 100%;">
			<view class="page-box">
				<view v-if="houseHistory.length === 0">
					<view class="centre">
						<image :src="empty" mode=""></image>
						<view class="explain">
							You haven't viewed any listings yet
							<view class="tips">Go explore now</view>
						</view>
						<view class="btn" @click="goHome">Home</view>
					</view>
				</view>
				<view v-else class="order" v-for="(item, index) in houseHistory" :key="index"
					@click="clickImage(item.id)">
					<view class="top">
						<view class="left">
							<view class="store">{{ item.code }}</view>
							<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
						</view>
					</view>
					<view class="item">
						<view class="left">
							<image :src="item.faceUrl" mode="aspectFill"></image>
						</view>
						<view class="content">
							<view class="title u-line-2">
								{{item.villageName}}
								{{item.type == '整租' ? item.houseNum + item.houseHall + item.toiletNum : item.roomType}}
							</view>
							<view class="price">¥{{item.price}}</view>
							<view class="type">
								{{item.type}} | {{item.type == '整租' ? item.houseArea : item.roomArea}}㎡ |
								{{item.decoration}}
							</view>
						</view>
					</view>
					<view class="bottom">
						<view class="evaluate btn" @click="clickImage(item.id)">View</view>
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import config from "@/common/config.js"
	export default {
		data() {
			return {
				empty: '/static/empty/default.png',
				houseHistory: uni.getStorageSync('houseHistory').reverse()
			};
		},
		onLoad() {},
		methods: {
			clickImage(houseId) {
				this.$u.route({
					url: '/pages/detail/detail',
					params: {
						houseId: houseId
					}
				})
			},
			goHome() {
				uni.switchTab({
					url: '/pages/index/index'
				})
			},
			clearHistory() {
				// Clear browsing history
				uni.showModal({
					title: 'Notice',
					content: 'Do you want to clear your browsing history?',
					cancelText: 'Cancel',
					confirmText: 'Confirm',
					success: res => {
						if (res.confirm) {
							uni.removeStorageSync('houseHistory');
							this.houseHistory = []
							this.$mytip.toast('Cleared successfully')
						}
					}
				});
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
	.u-nav-slot {
		color: $u-tips-color;
		font-weight: normal;
		font-size: 28rpx;
		margin-right: 30rpx;
	}

	.container {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		text-align: center;

		.empty {
			overflow: hidden;

			.tip {
				color: #909399;
			}

			.btn {
				color: #fff;
				background-color: #909399;
				width: 200rpx;
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
					font-size: 32rpx;
				}
			}

			.right {
				color: #2979ff;
			}
		}

		.item {
			display: flex;
			margin: 20rpx 0 0;

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

				.price {
					margin: 10rpx 0;
					font-size: 30rpx;
				}

				.type {
					margin: 10rpx 0;
					font-size: 24rpx;
					color: $u-tips-color;
				}
			}
		}

		.bottom {
			display: flex;
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
			background-image: linear-gradient(to left, #2979ff, rgba(#2979ff, 0.6));
		}
	}
</style>