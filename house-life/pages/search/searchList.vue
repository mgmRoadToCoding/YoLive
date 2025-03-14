<template>
    <view>
		<!-- <u-navbar :is-back="true" title="房源列表" :border-bottom="false"></u-navbar> -->
        <u-sticky offset-top="0">
            <view class="sticky">
                <filterDropdown :menuTop="0" :filterData="filterData" :defaultSelected="defaultSelected" :updateMenuName="true" @confirm="confirm" dataFormat="Object"></filterDropdown>
            </view>
        </u-sticky>
       <view class="u-p-l-10 u-p-r-10 waterfall">
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
		   <u-loadmore bg-color="rgb(240, 240, 240)" :status="loadStatus" @loadmore="findHouseList" style="height: 80rpx;line-height: 80rpx;"></u-loadmore>
		   <u-back-top :scroll-top="scrollTop" top="1000"></u-back-top>
		   <u-no-network></u-no-network>
	   </view>
    </view>
</template>

<script>
	import config from "@/common/config.js" // 全局配置文件
	import searchData from '@/common/utils/searchData.js';//筛选菜单数据
    import filterDropdown from '@/components/zy/filterDropdown.vue';
    export default {
        components: {
            filterDropdown
        },
        data() {
            return {
				indexArr: [],
				valueArr: [],
                defaultSelected: [],
                filterData: [],
				searchData:{},
				pageNum: 1,
				pageSize: 20,
				scrollTop: 0,
				houseList: [],
                loadStatus: 'loadmore',
                flowList: [],
            }
        },
        onLoad(option) {
			let type = option.type
			let villageName = option.villageName
			this.searchData = {}
			if(type){
				this.searchData.type = type
			}
			if(villageName){
				this.searchData.villageName = villageName
			}
            // 获取房源数据
            this.findHouseList();
			// 获取小区数据
			this.findVillageList()
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
        	this.pageNum = 1
        	this.flowList = []
        	this.$refs.uWaterfall.clear();
        	// 获取数据
        	this.findHouseList();
        	// 关闭刷新
        	uni.stopPullDownRefresh();
        },
        methods: {
            findHouseList() {
            	let url = "/api/houseApi/findHouseRoomList";
				let defaultData = {
					state:1,
					villageCity:uni.getStorageSync('lifeData').vuex_city,
            		pageNum: this.pageNum,
            		pageSize: this.pageSize,
            		orderByColumn: 'update_time,create_time',
            		isAsc: 'desc'
            	}
            	this.$u.get(url, {...defaultData,...this.searchData}).then(result => {
					console.log(this.searchData);
					console.log(result);
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
			findVillageList() {
				let url = "/api/houseApi/findVillageList";
				this.$u.get(url,{
					city:uni.getStorageSync('lifeData').vuex_city,
            		orderByColumn: 'name',
            		isAsc: 'desc'
            	}).then(result => {
					const data = result.rows
					for (let i = 0; i < data.length; i++) {
					    // 先转成字符串再转成对象，避免数组对象引用导致数据混乱
					    let item = data[i]
						searchData[0].submenu.push({
							name: item.name,
							value: item.name
						})
					}
					this.filterData = searchData;
				});
			},
			clickImage(houseId) {
			    this.$u.route({
			    	url: '/pages/detail/detail',
			    	params: {
			    		houseId: houseId
			    	}
			    })
			},
			//接收菜单结果
			confirm(e) {
				let type = e.value[1][0]
				let villageName = e.value[0][0]
				let price = e.value[2][0]
				let combo = e.value[3]
				let houseNum = combo[0]
				let decoration = combo[1]
				let feature = combo[2]
				this.searchData = {}
				if(type){
					this.searchData.type = type
				}
				if(villageName){
					this.searchData.villageName = villageName
				}
				if(price){
					this.searchData.price = price
				}
				if(houseNum && houseNum.length>0){
					this.searchData.houseNum = houseNum.toString()
				}
				if(decoration && decoration.length>0){
					this.searchData.decoration = decoration.toString()
				}
				if(feature && feature.length>0){
					this.searchData.feature = feature.toString()
				}
				this.pageNum = 1
				this.houseList = []
				this.flowList = []
				this.$refs.uWaterfall.clear();
				this.findHouseList()
			},
			code(){
				this.$mytip.toast('请咨询技术支持')
			}
        }
    }
</script>

<style lang="scss" scoped>
	.waterfall{
		// padding-top: 85rpx;
	}
	
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
</style>
