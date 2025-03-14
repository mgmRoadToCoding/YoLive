<template>
	<view>
		<u-navbar :is-back="false" title="选择城市" :border-bottom="false"></u-navbar>
		<view class="selected" >
			<view class="title">
				已选 : 
				<text style="color: #2979ff;margin-left: 12rpx;">
					{{vuex_city==''?'请选择城市':vuex_city}}
				</text>
			</view>
		</view>
		<view class="location" @click="setLocation">
			<view class="title">当前定位</view>
			<view class="body">
				<view class="left">
					<image src="../../static/navigate.png" mode="widthFix" class="img"></image>
					{{locationCity}}
				</view>
				<view class="right">切换城市</view>
			</view>
		</view>
		<view class="hot" >
			<view class="title">热门城市</view>
			<view class="body">
				<view class="tag" v-for="(item,index) in hotList" :key="index" @click="clickCity(item)">{{item}}</view>
			</view>
		</view>
	</view>
</template>
<script>
	// import wxGetAddress from '@/common/utils/wxGetAddress.js'
	export default {
		data() {
			return {
				locationCity:'',
				hotList:['杭州市','郑州市','北京市','上海市','广州市','深圳市']
			}
		},
		onLoad() {
			this.findLocation()
		},
		methods: {
			clickCity(item){
				this.$u.vuex('vuex_city', item);
				return uni.reLaunch({
					url:'../index/index'
				})
			},
			setLocation(){
				this.$u.vuex('vuex_city', this.locationCity);
				return uni.reLaunch({
					url:'../index/index'
				})
			},
			findLocation(){
				let that = this
				uni.showLoading({title:"定位中....",mask:true})
				this.$u.get("/api/profile/getRealCity").then(obj => {
					let cityName = obj.msg
					if(cityName){
						this.locationCity = cityName
						let lifeData = uni.getStorageSync('lifeData');
						let vuex_city = lifeData.vuex_city
						if(!vuex_city){
							this.$u.vuex('vuex_city', cityName);
						}
						uni.hideLoading();
					}else{
						uni.request({
							// url:'https://pv.sohu.com/cityjson?ie=utf-8',
							url:'https://tianqiapi.com/ip?version=v9&appid=23035354&appsecret=8YvlPNrz',
							success(resp) {
								let ip = resp.data.ip
								that.$u.get("/api/profile/getRealCityByIP?ip="+ip).then(obj => {
									let cityName = obj.msg
									if(cityName){
										that.locationCity = cityName
										let lifeData = uni.getStorageSync('lifeData');
										let vuex_city = lifeData.vuex_city
										if(!vuex_city){
											that.$u.vuex('vuex_city', cityName);
										}
										uni.hideLoading();
									}else{
										that.$mytip.toast('定位失败')
									}
									uni.hideLoading();
								});
							}
						})
					}
				});
			}
			// 微信小程序定位审核太严厉，不使用了。扩展后端接口，使用ip定位
			// findLocation(){
			// 	uni.showLoading({title:"定位中....",mask:true})
			// 	uni.getLocation({
			// 		type: 'gcj02',
			// 		success: async res => {
			// 			let { longitude, latitude } = res;
			// 			let result = await wxGetAddress({ longitude, latitude });
			// 			let province = result.regeocodeData.addressComponent.province
			// 			let cityName = result.regeocodeData.addressComponent.city
			// 			this.locationCity = cityName
			// 			let lifeData = uni.getStorageSync('lifeData');
			// 			let vuex_city = lifeData.vuex_city
			// 			if(!vuex_city){
			// 				this.$u.vuex('vuex_city', cityName);
			// 			}
			// 			uni.hideLoading();
			// 		}
			// 	});
			// }
		}
	}
</script>

<style lang="scss" scoped>
	.selected{
		.title {
		    font-size: 30rpx;
		    color: $u-main-color;
			margin: 30rpx 20rpx;
			font-weight: 600;
			margin-left: 30rpx;
		}
	}
	.location{
		background: #FFFFFF;
		border-radius: 18rpx;
		padding: 3rpx 0;
		margin: 20rpx;
		.title {
		    font-size: 22rpx;
		    color: $u-tips-color;
			margin: 30rpx 20rpx;
		}
		
		.body{
			margin: 30rpx 20rpx;
			display: flex;
			justify-content: space-between;
			.left{
				font-size: 32rpx;
				font-weight: 800;
				display: flex;
				justify-content: center;
				align-items: center;
				.img{
					width: 35rpx;
					margin-right: 12rpx;
				}
			}
			.right{
				color: #2979ff;
				font-weight: 600;
				margin-right: 10rpx;
			};
		}
	}
	.hot{
		.title {
		    font-size: 30rpx;
		    color: $u-main-color;
			margin: 30rpx 20rpx;
			font-weight: 600;
			margin-left: 30rpx;
		}
		
		.body{
			background: #FFFFFF;
			padding-bottom: 15rpx;
			border-radius: 18rpx;
			margin: 20rpx;
			.tag {
				display: inline-block;
				width: 137rpx;
				height: 75rpx;
				line-height: 75rpx;
			    font-size: 26rpx;
			    color: $u-content-color;
			    margin: 20rpx 20rpx 5rpx 20rpx;
				padding: 5rpx 10rpx;
				text-align: center;
				background-color: $u-bg-color;
				border-radius: 12rpx;
			}
		}
	}
</style>
