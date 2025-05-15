<template>
	<view class="u-p-l-10 u-p-r-10">
		<u-navbar :is-back="false">
			<view style="display: flex;justify-content: center;align-items: center;">
				
				<!-- <view class="u-p-20"  @click="location">
					{{vuex_city==''?'选择':vuex_city}}
					<u-icon name="arrow-down-fill" class="u-p-l-10" color="#515356"></u-icon>
				</view> -->
				
				<!-- #ifdef MP-WEIXIN -->  
				<!-- <u-search placeholder="你想住在哪儿" v-model="keyword" input-align="center"  :show-action="false" :clearabled="true"
:disabled="true" style="width: 300rpx;" @click="search"></u-search> -->
				<!-- #endif -->
				<!-- #ifndef MP-WEIXIN --> 
				<u-search placeholder="검색" v-model="keyword" input-align="center"  :show-action="false" :clearabled="true"
				:disabled="true" style="width: 580rpx;" @click="search"></u-search>
				<!-- #endif -->
			</view>
		</u-navbar>
		<view>
			
			<u-swiper :list="swiperList" height="300" @click="moreInfo"></u-swiper>
		</view>
		<view>
			<view class="rowClass">
				<u-row>
					<u-col span="3" text-align="center" v-for="(item,index) in navList" :key="index">
						<view class="u-padding-20" @tap="clickNav(item)" hover-class="hoverClass">
							<image :src="item.src" style="width: 90rpx;height: 90rpx;" mode="widthFix"></image>
							<view class="tabName">{{item.name}}</view>
						</view>
					</u-col>
				</u-row>
			</view>
			<u-gap height="10"></u-gap>
			<view @click="notice">
				<u-notice-bar mode="vertical" :list="noticeList" type="primary" more-icon
				bg-color="#fff" :duration="5000" border-radius="15"></u-notice-bar>
			</view>
			<u-gap height="5"></u-gap>
			<u-waterfall v-model="flowList" ref="uWaterfall">
			    <template v-slot:left="{leftList}">
			        <view class="demo-warter" v-for="(item, index) in leftList" :key="index">
			            <u-lazy-load threshold="750" border-radius="12" :image="item.image" :index="index"
			                         @click="clickImage(item.id)"></u-lazy-load>
			            <view class="item-title">{{item.villageName}} {{item.type == '整租' ? item.houseNum + item.houseHall + item.toiletNum : item.roomType}}</view>
						<view class="item-price">¥{{item.price}}</view>
			             <view class="item-desc">{{item.type}} | {{item.type == '整租' ? item.houseArea : item.roomArea}}㎡ | {{item.decoration}} </view>
			        </view>
			    </template>
			    <template v-slot:right="{rightList}">
			        <view class="demo-warter" v-for="(item, index) in rightList" :key="index">
			            <u-lazy-load threshold="750" border-radius="10" :image="item.image" :index="index"
			                         @click="clickImage(item.id)"></u-lazy-load>
			            <view class="item-title">{{item.villageName}} {{item.type == '整租' ? item.houseNum + item.houseHall + item.toiletNum : item.roomType}}</view>
			            <view class="item-price">¥{{item.price}}</view>
						 <view class="item-desc">{{item.type}} | {{item.type == '整租' ? item.houseArea : item.roomArea}}㎡ | {{item.decoration}} </view>
			        </view>
			    </template>
			</u-waterfall>
		</view>
		<u-loadmore bg-color="rgb(240, 240, 240)" :status="loadStatus" @loadmore="findHouseList" style="height: 80rpx;line-height: 80rpx;"></u-loadmore>
		<u-back-top :scroll-top="scrollTop" top="1000"></u-back-top>
		<u-no-network></u-no-network>
		<view class="buttom">
			<view class="loginType">
				<view class="wechat item">
					<view class="icon"><u-icon size="60" name="server-man" color="#999" @click="server"></u-icon></view>
				</view>
			</view>
		</view>
		<!-- 今日推荐title -->
		<view class="recommend-title">
		    <text>오늘 추천</text>
		    <!-- 如果需要中文和韩文双语显示 -->
		    <!-- <text>오늘 추천 / 今日推荐</text> -->
		</view>
		
		<!-- 今日推荐本体 -->
		<view class="news-section">
		  <view 
		      v-for="(item, index) in newsList" 
		      :key="index" 
		      class="news-item" 
		      @click="goToNewsDetail(item.id)"
		    >
		      <view class="news-content">
		        <!-- 添加图片 -->
		        <image 
		          :src="item.image" 
		          class="news-image"
		          mode="aspectFill"
		          v-if="item.image"
		        ></image>
		        <view class="news-text">
		          <view class="news-title">{{ item.title }}</view>
		          <view class="news-brief">{{ item.brief }}</view>
		          <view class="news-time">{{ item.time }}</view>
		        </view>
		      </view>
		      <u-gap height="10" bg-color="#f5f5f5"></u-gap>
		    </view>
		</view>
		
		
	</view>
</template>

<script>
	import config from "@/common/config.js" // 全局配置文件
	export default {
		data() {
			return {
				keyword: '',
				pageNum: 1,
				pageSize: 20,
				scrollTop: 0,
				houseList: [],
				swiperList: [
					{
						image: '/static/img/index/swiper/swiper2.png',
					    title: '身无彩凤双飞翼，心有灵犀一点通'
					},
					{
						image: '/static/img/index/swiper/swiper.jpg',
					    title: '身无彩凤双飞翼，心有灵犀一点通'
					},
                ],
				// noticeList: [],
				navList:[
				   {name:"방 구하기",src:"/static/img/index/cover_2022/index_cover1.png",url:"/pages/map/map/map"},
				   {name:"留学生招聘",src:"/static/img/index/cover_2022/index_cover2.png",url:"/pages/International Student Recruitment/index/index"}, 
				   {name:"留学韩国",src:"/static/img/index/cover_2022/index_cover3.png",url:"/pages/study/index"}
				],
				//今日推荐短报
				newsList: [
				      {
				        id: 1,
				        title: "韩国租房市场最新动态：首尔房价小幅上涨",
				        brief: "近期首尔地区租房需求增加，部分区域房价环比上涨2%...",
				        time: "2025-4-15",
				      },
				      {
				        id: 2,
				        title: "留学生租房指南：如何避开黑中介",
				        brief: "本文总结了留学生常见的租房陷阱及应对方法...",
				        time: "2025-4-16"
				      },
					  {
					    id: 3,
					    title: "留学生租房指南：如何避开黑中介",
					    brief: "本文总结了留学生常见的租房陷阱及应对方法...",
					    time: "2025-4-17"
					  },
					  {
					    id: 4,
					    title: "留学生租房指南：如何避开黑中介",
					    brief: "本文总结了留学生常见的租房陷阱及应对方法...",
						image: "/static/index.png",
					    time: "2025-4-18"
					  }
				],
				loadStatus: 'loadmore',
				flowList: [],
				uvCode: uni.getStorageSync('uvCode')
			}
		},
		onLoad() {
			// 检查是否已选择城市，如果未选择，跳转到选择城市页面
			this.checkCity();
			// 获取数据
			this.findHouseList();
			this.getNoticecList();
			// 流量统计
			this.appSysFlowInfo();
			uni.$on('findIndexHouseList', (obj) => {
				// 获取数据
				this.findHouseList(1);
			})
		},
		onUnload() {
			// 移除监听事件  
			uni.$off('findIndexHouseList');
		},
		onPageScroll(e) {
		    this.scrollTop = e.scrollTop;
		},
		onReachBottom() {
		    this.loadStatus = 'loading';
		    // 获取数据
			this.findHouseList()
		},
		// 下拉刷新
		onPullDownRefresh() {
			// 获取数据
			this.findHouseList(1);
			// 关闭刷新
			uni.stopPullDownRefresh();
		},
		methods: {
			//今日推荐part
			goToNewsDetail(newsId) {
			    this.$u.route({
			      url: '/pages/news/index/index', // 详情页路径
			      params: {
			        id: newsId // 传递新闻ID
			      }
			    });
			  },
			checkCity(){
				// 检查是否已选择城市，如果未选择，跳转到选择城市页面
				let lifeData = uni.getStorageSync('lifeData');
				let vuex_city = lifeData.vuex_city
				// console.log(vuex_city.length);
				if(!vuex_city || vuex_city.length == 0){
					// 没有token 则跳转到登录
					return this.$u.route('/pages/location/location');
				}
			},
			location(){
				  this.$u.route({
					url: 'pages/location/location',
				  })
			},
			search(){
				this.$u.route({
					url: 'pages/search/search',
				})
			},
			// notice(){
			// 	this.$u.route({
			// 		url: 'pages/notice/notice'
			// 	})
			// },
			findHouseList(type = 0) {
				if(type == 1){
					this.pageNum = 1
					this.flowList = []
					this.$refs.uWaterfall.clear();
				}
				
				let url = "/api/houseApi/findHouseRoomList";
				this.$u.get(url, {
					state:1,
					villageCity:uni.getStorageSync('lifeData').vuex_city,
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					orderByColumn: 'update_time,create_time',
					isAsc: 'desc'
				}).then(result => {
					const data = result.rows;
					if(this.pageNum>1 && data.length < this.pageSize){
						return this.loadStatus = 'nomore';
					}
					this.houseList = data;
					for (let i = 0; i < this.houseList.length; i++) {
					    // 先转成字符串再转成对象，避免数组对象引用导致数据混乱
					    let item = this.houseList[i]
						if(!item.faceUrl.includes(config.staticUrl)){
							item.image = config.staticUrl+item.faceUrl
						}else{
							item.image = item.faceUrl
						}
						if(item.type == 0){
							item.type = '整租'
						}else if(item.type == 1){
							item.type = '合租'
						}
						if(item.roomType == 1){
							item.roomType = '主卧'
						}else if(item.roomType == 2){
							item.roomType = '次卧'
						}else{
							item.roomType = '未知'
						}
						if(this.$u.test.isEmpty(item.houseNum)){
							item.houseNum = ''
						}
						if(this.$u.test.isEmpty(item.houseHall)){
							item.houseHall = ''
						}
						if(this.$u.test.isEmpty(item.toiletNum)){
							item.toiletNum = ''
						}
					    this.flowList.push(item);
					}
					++ this.pageNum 
					this.loadStatus = 'loadmore';
				});
			},
			clickSearch() {
			    this.$u.route('/pages/search/search');
			},
			clickImage(houseId) {
				this.$u.route({
					url: '/pages/detail/detail',
					params: {
						houseId: houseId
					}
				})
			},
			clickNav(item){
				if(item.url){
					return this.$u.route(item.url);
				}
				if(item.type === "2"){
					// 判断是否有token
					let lifeData = uni.getStorageSync('lifeData');
					let token = lifeData.vuex_token
					if(!token){
						// 没有token 则跳转到登录
						return uni.reLaunch({
							url:'../login/login'
						})
					}else{
						// 判断Token是否有效
						let url = "/api/profile/isExpiration";
						return this.$u.get(url,{
							token:token
						}).then(obj => {
							if(obj.data){
								// 没有token过期则跳转到登录
								return uni.reLaunch({
									url:'../login/login'
								})
							}else{
								return this.$u.route('/pages/detail/preHouse');
							}
						});
					}
				}else if(item.type){
					// return this.$u.route('/pages/search/searchList');
					return this.$u.route({
						url: '/pages/search/searchList',
						params: {
							type: item.type
						}
					})
				}
			},
			code(){
				this.$mytip.toast('请咨询作者')
			},
			appSysFlowInfo(){
				// 流量统计
				let uvCode = uni.getStorageSync("uvCode");
				let url = "https://sourcebyte.cn/api/flow/upFlow?type=MINI&uvCode=" + uvCode;
				uni.request({
				  url: url,
				  method: "POST",
				  success: (res) => {
					uni.setStorageSync("uvCode", res.data.data);
				  },
				});
			},
			server(){
				// window.open ('https://sourcebyte.cn')
				uni.makePhoneCall({
				  phoneNumber: "18720989281",
				});
			},
			getNoticecList(){
				let url = "/api/notice/findNoticeList";
				this.$u.get(url,{
					pageNum:1,
					pageSize:50,
					orderByColumn:'create_time',
					isAsc:'desc'
				}).then(obj => {
					this.noticeList = []; // 先清空数组
					let data = obj.rows
					data.filter(item=>{
						this.noticeList.push(item.noticeTitle)
					})
				});
			},
			moreInfo(){
				uni.navigateToMiniProgram({
					appId:'wxbca64173e772915e',// 此为开源字节appid
					path:'/pages/index/index',// 此为开源字节首页路径
					envVersion:"release",
					success: res => {
						 // 打开成功
						 console.log("打开成功", res);
					},
					fail: err => {
						 console.log(err);
					}
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	//title的样式情况
	.recommend-title {
	    font-size: 30rpx;
	    font-weight: bold;
	    color: #333;
	    padding: 20rpx 30rpx;
	    // background-color: #fff;
	    // border-left: 8rpx solid #2979ff;
		text-align:center;
		border-bottom: 1px solid black;
	    // box-shadow: 0 2rpx 10rpx rgba(0,0,0,0.05);
	}
	//今日推荐内容样式
	.news-item {
	  background-color: #f8f9fa;
	  // border-left: 6rpx solid #2979ff;
	  // border-radius: 0 12rpx 12rpx 0;
	  border-radius:12rpx;
	  margin-bottom: 20rpx;
	  padding: 24rpx;
	  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
	  color:#6F6F6F;
	  
	  &:active {
	    background-color: #f1f5ff;
	  }
	}
	
	.news-title {
	  font-size: 34rpx;
	  font-weight: bold;
	  color: #1a1a1a;
	  margin-bottom: 12rpx;
	  position: relative;
	  padding-left: 20rpx;
	  
	  &::before {
	    content: "";
	    position: absolute;
	    left: 0;
	    top: 8rpx;
	    height: 60%;
	    width: 6rpx;
	    background-color: #2979ff;
	    border-radius: 3rpx;
	  }
	}
	//-----------
	.nomore {
		background-color: $u-bg-color;
	}
	
	.search{
		width: 54px;
		height: 44px;
		
		&:active{
			background-color: $u-bg-color;
		}
	}
	
	.rowClass{
		border-radius: 8px;
		background-color: rgb(255, 255, 255);
		margin-top: 10rpx;
	}
	
	.hoverClass{
		background-color: #E4E7ED;
	}
	
	.tabName{
		font-size: 28rpx;
		color: $u-main-color;
	}
	
	.demo-warter {
	    border-radius: 8px;
	    margin-top: 3px;
	    background-color: #ffffff;
	    padding: 3px;
	    position: relative;
	}
	
	.u-close {
	    position: absolute;
	    top: 20rpx;
	    right: 20rpx;
	}
	
	.item-cover {
	    font-size: 55rpx;
	    color: $u-type-warning;
	}
	
	.item-title {
	    font-size: 28rpx;
	    color: $u-main-color;
	    font-weight: bold;
		padding-top: 5rpx;
		padding-left: 10rpx;
	}
	
	.item-price {
	    font-weight: normal;
	    font-size: 32rpx;
	    color: $u-type-warning;
	}
	
	.item-desc {
	    font-weight: normal;
	    font-size: 26rpx;
	    color: $u-tips-color;
		padding-bottom: 5rpx;
		padding-left: 10rpx;
	}
	
	.item-tag {
	    font-size: 24rpx;
	    color: $u-tips-color;
	    margin-top: 3px;
	}
	
	.buttom {
		.loginType {
			font-size: 14px;
			position: fixed;
			right: 30rpx;
			bottom: 300rpx;
			width: 60px;
			height: 60px;
			padding: 4px;
			cursor: pointer;
			background: #FFF;
			text-align: center;
			line-height: 60px;
			border-radius: 100%;
			-webkit-box-shadow: 0px 1px 20px 0px rgba(0,0,0,0.1),inset 0px -1px 0px 0px rgba(0,0,0,0.1);
			box-shadow: 0px 1px 20px 0px rgba(0,0,0,0.1),inset 0px -1px 0px 0px rgba(0,0,0,0.1);
		}
	}
</style>
<style scoped>
/* 强制覆盖 flex 宽度 */
.u-col.u-col-3 {
  flex: 0 0 33% !important;
}
</style>

