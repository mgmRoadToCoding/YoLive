<template>
    <view class="u-margin-left-20 u-margin-right-20">
		<u-navbar :is-back="true" title="房源" :border-bottom="false"
		back-text="返回" :custom-back="goHome" ></u-navbar>
        <view>
            <u-swiper :list="swiperlist" :height="750" mode="number" effect3d effect3d-previous-margin="20" :interval="5000" @click="clickImg"></u-swiper>
        </view>
		<view class="item u-margin-top-5">
			<view class="item-title">{{room.type == '整租' ? room.houseNum + room.houseHall + room.toiletNum : room.roomType}}</view>
			<view class="item-price">¥{{room.price}}/月</view>
		</view>
		<view class="item" style="display: flex;align-items: center;flex-wrap: wrap;">
			 <view v-for="(item,index) in tagList" :key="index"
			  style="background-color: #f5f5f5;color: #606266;margin-right: 15rpx;
			  margin-top: 15rpx;padding: 0 10rpx;height: 60rpx;line-height: 60rpx;">
				{{item.title}}
			</view>
		</view>
		<!-- 小程序通讯 -->
		<button type="default" open-type="contact"  class="clearBtn">
			<u-cell-group>
				<u-cell-item title="服务费另计" value="在线咨询"></u-cell-item>
			</u-cell-group>		
		</button>
		<view v-if="room.introduce">
			<u-gap height="10" bg-color="#f8f8f8"></u-gap>
			<u-card title="房源介绍" :border="false" :head-border-bottom="false" padding="0" title-size="38">
				<view class="u-padding-top-45 item" slot="body">
					{{room.introduce}}
				</view>
			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card title="小区介绍" :border="false" :head-border-bottom="false" padding="0" title-size="38">
				<view class="u-padding-top-45" slot="body">
					<view class="u-flex" style="justify-content: space-between;">
						<view class="item">
							<view>建筑年份：{{village.year}}</view>
							<view>建筑类型：{{village.type}}</view>
							<view>绿化率：{{village.green}}%</view>
						</view>
						<image src="https://img11.360buyimg.com/n7/jfs/t1/94448/29/2734/524808/5dd4cc16E990dfb6b/59c256f85a8c3757.jpg" mode="widthFix" 
						style="width: 250rpx;height: 200rpx;"></image>
					</view>
				</view>
			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card :title="room.villageName" :border="false" :head-border-bottom="false" padding="0" title-size="38">
				<view class="u-padding-top-45" slot="body">
					<map :latitude="latitude" :longitude="longitude">
						<cover-view style='width:100%;height:100%;' @click="clickMap"></cover-view>
					</map>
					<view 
					 @click="clickMap"
					style="margin-top: 30rpx;padding-left: 10rpx;height:80rpx;
					line-height: 80rpx;background-color: #fdfdfd;border-radius: 6px;
					display: flex;justify-content: space-between;
					padding-right: 10rpx;color: #909399;
					">
						<view>
							<u-icon name="map" color="#909399" size="30"></u-icon>
							<text style="margin-left: 8rpx;">
								房源直线距离约 {{distance}} km
							</text>
						</view>
						<view>
							<text style="margin-right: 3rpx;">
								导航
							</text>
							<u-icon name="arrow-right" color="#909399" size="30"></u-icon>
						</view>
					</view>
					<view style="margin-top: 20rpx;margin-left: 10rpx;font-size: 10px;
							color: #909399">
						*数据仅供参考，请以实际看房为准
					</view>
				</view>
			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card title="服务介绍" :border="false" :head-border-bottom="false" padding="0" title-size="38">
				<view class="u-padding-top-35" slot="body">
					<u-cell-group :border="false">
						<u-cell-item icon="heart" title="承诺-四大租住承诺，安心有保障" :arrow="false" hover-class="none" :border-bottom="false"></u-cell-item>
						<u-cell-item icon="level" title="品质-两类品质保障，入住更无忧" :arrow="false" hover-class="none" :border-bottom="false"></u-cell-item>
						<u-cell-item icon="star" title="服务-两项日常服务，生活超便捷" :arrow="false" hover-class="none" :border-bottom="false"></u-cell-item>
						<u-cell-item icon="rmb" title="付款-多种付款方式，支付更灵活" :arrow="false" hover-class="none" :border-bottom="false"></u-cell-item>
					</u-cell-group>
				</view>
			</u-card>
		</view>
		<u-gap height="1" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;">
			<u-card title="房源评价" :border="false" :head-border-bottom="false" padding="0" title-size="38">				
				<view class="u-padding-top-50 u-padding-bottom-50" slot="body">					
					<u-cell-group :border="false">
						{{evaluate}}
						<text style="float: right;color: #909399;font-size: 13px;">{{credt}}</text>
					</u-cell-group>
				</view>		
				<view slot="foot" style="padding-top: 30rpx;float: right;">
					<u-icon name="chat-fill"></u-icon>
					<text @click="moreEval" style="text-align: center;font-size: 12px;margin-left: 5rpx;">更多评价（{{evalsize}}）</text>
				</view>
			</u-card>
		</view>		
		<u-gap height="10" bg-color="#f8f8f8"></u-gap>
		<view style="position: relative;padding-bottom: 50rpx;">
			<u-card title="经纪人" :border="false" :head-border-bottom="false" padding="0" title-size="38">
				<view class="u-padding-top-45" slot="body">
					<view class="u-flex">
					    <view class="u-m-r-20">
					        <u-avatar :src="room.agentAvatar" size="80"></u-avatar>
					    </view>
					    <view class="u-flex-1">
							<view class="u-font-16 u-p-b-40">{{room.agentName}}</view>
					        <!-- <view class="u-font-12 u-p-b-10">{{user.agentPhone}}</view> -->
					    </view>
					</view>
					<view class="arrow-right" @click="clickItem">
						<view class="phone">
							<u-icon name="phone" class="u-p-l-10" color="#d1d1d1"
							size="40"
							></u-icon>
						</view>
					</view>
				</view>
			</u-card>
			<view class="item">
				<u-divider>开源字节为您服务</u-divider>
			</view>
		</view>		
		<view class="navigation">
			<view class="left">
				<button type="default" open-type="contact"  class="clearBtn"
				style="font-size: 14px;color: #6a6a6a;">
					<view class="item">
						<u-icon name="server-fill" :size="40"></u-icon>
						<view class="text">客服</view>	
					</view>
				</button>
				<view class="item" @click="heartHouse">
					<u-icon name="heart" :size="40" v-if="!room.heart"></u-icon>
					<u-icon name="heart-fill" color="#ff9900" :size="40" v-if="room.heart"></u-icon>
					<view class="text">收藏</view>
				</view>
				<button type="default" open-type="share"  class="clearBtn"
				style="font-size: 14px;color: #6a6a6a;">
					<view class="item">
						<u-icon name="zhuanfa" :size="40"></u-icon>
						<view class="text">分享</view>
					</view> 
				</button>
			</view>
			<view class="right" @click="clickItem">
				<view class="btn">联系经纪人</view>
			</view>
		</view>
    </view>
</template>

<script>
	import config from "@/common/config.js" // 全局配置文件
    export default {
        data() {
            return {
				room: {
					villageName:'',
					houseNum:'',
					houseHall:'',
					toiletNum:'',
					roomType:'',
					direction:'',
					price:'',
					introduce:'',
					heart:false,
				},
				village: {
					year:'',
					type:'',
					green:'',
				},
                swiperlist: [],
                tagList: [],
				//评价列表
				evaluList:[],
				user: {},
				longitude: 120.14,
				latitude: 30.35, 
				distance:0,
				//自己的评论
				ownevalu:'',
				houseId:null,
				evalsize:0,
				havem:false,
				evaluate:'',
				credt:''
            }
        },
		onLoad: function (option) {
			let houseId = option.houseId //上个页面传递的参数。
			this.houseId =houseId
			// 渲染当前房源信息
			this.findHouseById(houseId);
			
			// // 在页面中定义激励视频广告
			// let videoAd = null
			// // 在页面onLoad回调事件中创建激励视频广告实例
			// if (wx.createRewardedVideoAd) {
			//   videoAd = wx.createRewardedVideoAd({
			// 	adUnitId: 'adunit-8cd5789a01a51891'
			//   })
			//   videoAd.onLoad(() => {
			// 	  console.log('激励视频 成功加载广告')
			//   })
			//   videoAd.onError((err) => {})
			//   videoAd.onClose((res) => {
			// 	  console.log('激励视频 成功关闭广告')
			//   })
			// }
			// // 用户触发广告后，显示激励视频广告
			// if (videoAd) {
			//   videoAd.show().catch(() => {
			// 	// 失败重试
			// 	videoAd.load()
			// 	  .then(() => videoAd.show())
			// 	  .catch(err => {
			// 		console.log('激励视频 广告显示失败')
			// 	  })
			//   })
			// }
		},
        methods: {
			heartHouse(){
				// 判断是否有userId
				let lifeData = uni.getStorageSync('lifeData');
				let vuex_user = lifeData.vuex_user
				if(!vuex_user){
					// 没有userId 则跳转到登录
					return uni.reLaunch({
						url:'../login/login'
					})
				}
				// 收藏
				let url = "api/houseApi/saveHeart";
				this.$u.post(url, {
					heart: this.room.heart,
					houseId: this.room.id,
					userId: vuex_user.user.userId,
				}).then(result => {
					this.room.heart = !this.room.heart
					this.$mytip.toast(result.msg)
				});
			},
			goHome(){
				uni.reLaunch({
					url:'../index/index'
				})
			},
            clickItem() {
                //拨打固定电话
                uni.makePhoneCall({
                	phoneNumber: this.room.agentPhone,
                });
            },
			findHouseById(houseId){
				let url = "api/houseApi/findHouseById";
				this.$u.get(url, {
					id: houseId
				}).then(result => {
					let room = result.data
					if(room.type == 0){
						room.type = '整租'
					}else if(room.type == 1){
						room.type = '合租'
					}
					if(room.roomType == 1){
						room.roomType = '主卧'
					}else if(room.roomType == 2){
						room.roomType = '次卧'
					}else{
						room.roomType = '未知'
					}
					
					if(this.$u.test.isEmpty(room.houseNum)){
						room.houseNum = ''
					}
					if(this.$u.test.isEmpty(room.houseHall)){
						room.houseHall = ''
					}
					if(this.$u.test.isEmpty(room.toiletNum)){
						room.toiletNum = ''
					}
					if(this.$u.test.isEmpty(room.floor)){
						room.floor = ''
					}else{
						room.floor = room.floor + '层'
					}
					this.swiperlist = room.imageList.map(val=>{
						let imgUrl = val.imgUrl
						if(!imgUrl.includes(config.staticUrl)){
							imgUrl = config.staticUrl+val.imgUrl
						}else{
							imgUrl = val.imgUrl
						}
						return {
							title: val.imageName,
							image: imgUrl
						}
					})
					this.tagList = room.featureList.map(val=>{
						return {
							title: val.feature,
						}
					})
					if(!room.agentAvatar.includes(config.staticUrl)){
						room.agentAvatar = config.staticUrl+room.agentAvatar
					}
					this.user = room.user
					this.village = room.village
					this.room = room
					// 判断是否收藏
					this.selectHouseHeart(houseId);
					//查询房源评价
					this.selectHouseEvals(houseId);
					
					// 分享自定义标题与图片
					let shareTitle = ''
					if(room.type == '整租'){
						shareTitle = this.village.name + " " + this.room.houseNum + this.room.houseHall + this.room.toiletNum + " " + this.room.decoration+ " ¥" + this.room.price+"/月"
					}else{
						shareTitle = this.village.name + " " + room.roomType + " " + this.room.decoration+  " ¥" + this.room.price+"/月"
					}
					this.$u.mpShare = {
						title:  shareTitle, // 默认为小程序名称，可自定义
						// 支持PNG及JPG，默认为当前页面的截图
						imageUrl: this.room.faceUrl, 
					}
					
					// 添加到浏览历史
					let houseHistory = uni.getStorageSync('houseHistory')
					if(!houseHistory){
						houseHistory = []
					}
					// 如果超过20个了，则删除最后一个
					if(houseHistory.length >= 20){
						houseHistory.pop()
					}
					houseHistory.push(this.room)
					// 数据去重
					const keyArr = [];
					houseHistory.forEach((element,index) => {
						keyArr.push(element.code);  // 通过code来判断
					});
					const newArr = [];
					const newKey = new Set(keyArr);  // key去重
					newKey.forEach(item =>{
						const index = keyArr.findIndex(item2 => item2 === item);
						newArr.push(houseHistory[index]);
					})
					uni.setStorageSync('houseHistory',newArr)
					
					// 经纬度
					this.longitude = room.longitude
					this.latitude = room.latitude
					// 计算距离
					let lat1 = 39.909
					let lng1 = 116.39742
					let lat2 = this.latitude
					let lng2 = this.longitude
					uni.getLocation({
						type: 'gcj02',
						success: res=> {
							lat1 = res.latitude
							lng1 = res.longitude
							this.distance = this.getDistance(lat1,lng1 ,lat2 , lng2);
						}
					})
				});
			},
			selectHouseHeart(houseId){
				// 判断是否有userId
				let lifeData = uni.getStorageSync('lifeData');
				let vuex_user = lifeData.vuex_user
				if(vuex_user){
					let url = "api/houseApi/selectHouseHeart";
					this.$u.get(url, {
						houseId: houseId,
						userId: vuex_user.user.userId,
					}).then(result => {
						this.room.heart = result.data
					});
				}
			},			
			selectHouseEvals(houseId){
				let url = "api/houseApi/selectHouseEvals";
				this.$u.get(url, {
					houseId: houseId,
					pageNum: 1,
					pageSize: 1
				}).then(result => {	
					if(result.total >0){
						this.evaluate = result.rows[0].evaluate;
						if(this.evaluate.length>30){
							this.evaluate = this.evaluate .substring(0, 30)+"......";
						}
						this.credt=result.rows[0].createTime;
						this.havem=true;
						this.evalsize = result.total;
					}					
				});
			},
			moreEval() {
				this.$u.route({
					url: '/pages/detail/evalList',
					params: {
						houseId: this.houseId
					}
				})
			},
			clickImg(index){
				let imgArr = this.swiperlist.map(val=>{
					return val.image
				})
				console.log(imgArr);
				 // 预览图片
				uni.previewImage({
					current: index,
					urls: imgArr
				});
			},
			clickMap(e){
				uni.openLocation({
					longitude: Number(this.longitude),
					latitude: Number(this.latitude),
					name: this.room.villageName,
					address: this.room.address
				})
			},
			getDistance(lat1, lng1, lat2, lng2) {
			    lat1 = lat1 || 0;
				lng1 = lng1 || 0;
				lat2 = lat2 || 0;
				lng2 = lng2 || 0;
				var rad1 = lat1 * Math.PI / 180.0;
				var rad2 = lat2 * Math.PI / 180.0;
				var a = rad1 - rad2;
				var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
				var r = 6378137;
				var distance = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) * Math.pow(Math.sin(b / 2), 2)));
				return (distance/1000).toFixed(2);
			}
        }
    }
</script>

<style>
    /* page不能写带scope的style标签中，否则无效 */
    page {
        background-color: rgb(255, 255, 255);
    }
</style>


<style lang="scss" scoped>
	map {
		width: 100%;
		height: 350rpx;
	}
	
	.item{
		padding: 25rpx;
		line-height: 80rpx;
		margin-top: -30rpx;
	}
	
    .item-title {
        font-size: 42rpx;
        color: $u-main-color;
        font-weight: bold;
    }

    .item-price {
        font-weight: normal;
        font-size: 45rpx;
        color: $u-type-warning;
    }

    .item-desc {
        font-weight: normal;
        font-size: 36rpx;
        color: $u-tips-color;
    }
	
	.arrow-right{
		position: absolute;
		top: 80rpx;
		right: 28rpx;
		font-weight: normal;
		font-size: 28rpx;
		color: $u-tips-color;
		.phone{
			width: 90rpx;height:90rpx;
			border-radius: 100%;
			box-shadow: 0px 1px 20px 0px rgba(0,0,0,0.1);
			display: flex;
			justify-content: center;
			align-items: center;
		}
	}
	
	.wayClass{
		color: #606266;
		padding: 30rpx;
		line-height: 50rpx;
	}

	.clearBtn{
		margin:0;
		padding: 0;
		line-height: 1;
		background-color: #FFFFFF;
	}
	
	.clearBtn::after{
	    position: unset !important;
	    border: unset;
	}
	
	.navigation {
		display: flex;
		justify-content: center;
		align-items: center;
		position: fixed;
		left: 0;
		right: 0;
		bottom: 0;
		padding: 0rpx 15rpx;
		background-color: #ffffff;
		box-shadow:0 -1px 1px 0 rgba(0, 0, 0, 0.05);
		z-index: 9999;
		.left {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-top: 38rpx;
			font-size: 14px;
			color: #6a6a6a;
			.item {
				display: flex;
				.text{
					margin-left: 4rpx;
				}
			}
		}
		.right {
			display: flex;
			font-size: 28rpx;
			align-items: center;
			.btn {
				line-height: 66rpx;
				margin-left: 30rpx;
				padding: 5rpx 50rpx;
				color: #ffffff;
				border-radius:  36rpx;
				background-color: #2979ff;
			}
		}
	}
</style>
