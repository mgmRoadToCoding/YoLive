<template>
	<view  >
		<view class="text">
			<text style="font-size: 15px;margin-left: 20rpx;">全部评价({{total}})			
			<text style="float: right;margin-right: 20rpx;font-weight: bold;" @click="inputDialogToggle" >添加评价</text>
			</text>
		</view>
		<view class="text" v-for="(item,index) in listData" :key="index" style="padding: 0 20rpx;margin-top: 6rpx;">
			<view><text>{{ item.evaluate }}</text></view>
			<view style="text-align: right;color: #909399;font-size: 13px;"><text>{{ item.createTime }}</text></view>
		</view>	
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="inputDialog" type="dialog">
				<uni-popup-dialog ref="inputClose"  mode="input" title="评价房源" value=""
					 @confirm="submitEval">
					<textarea placeholder="请输入评价" v-model="ownevalu" ></textarea>
				</uni-popup-dialog>
			</uni-popup>			
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				houseId:null,
				listData: [],
				pageNum: 1,
				pageSize: 20,
				scrollTop: 0,
				reload: false,
				loadStatus: 'loadmore',
				status: 'more',
				total:0,
				ownevalu:'',
				userId:null
			};
		},
		onLoad(option) {		
			this.houseId = option.houseId; //上个页面传递的参数。
			this.getList();
		},
		onPageScroll(e) {
		    this.scrollTop = e.scrollTop;
		},
		onReachBottom() {
		    this.loadStatus = 'loading';
		    // 获取数据
			this.getList()
		},
		// 下拉刷新
		onPullDownRefresh() {
			this.pageNum = 1
			this.$refs.uWaterfall.clear();
			// 获取数据
			this.getList();
			// 关闭刷新
			uni.stopPullDownRefresh();
		},
		methods: {			
			getList() {
				let url = "/api/houseApi/selectHouseEvals";
				let defaultData = {					
					houseId:this.houseId,
					pageNum: this.pageNum,
					pageSize: this.pageSize
				}
				this.$u.get(url, {...defaultData}).then(result => {
					console.log(result);
					const data = result.rows;
					this.total= result.total;
					if(this.pageNum>0 && data.length < this.pageSize){
						 this.loadStatus = 'nomore';
					}else{
						++ this.pageNum
						this.loadStatus = 'loadmore';
					}
					this.listData = data;
				});
			},
			inputDialogToggle() {
				// 判断是否有userId
				let lifeData = uni.getStorageSync('lifeData');
				let vuex_user = lifeData.vuex_user
				if(!vuex_user){
					// 没有userId 则跳转到登录
					return uni.reLaunch({
						url:'../login/login'
					})
				}
				this.userId=vuex_user.userId;
				this.$refs.inputDialog.open();
				//判断是否有评价权限
				// let url = "api/houseApi/checkAuthEvals";
				// this.$u.get(url, {
				// 	houseId: this.houseId,
				// 	userId:this.userId
					
				// }).then(result => {	
				// 	if(result.code === 200 && result.data.length>0){
				// 		this.$refs.inputDialog.open();
				// 	}else{
				// 		this.$mytip.toast("只有租客才能进行评论！");
				// 	}				
				// });
			},			
			submitEval(){
				let url = "api/houseApi/saveHouseEvals";
				this.$u.get(url, {
					houseId: this.houseId,
					evalu: this.ownevalu,
					userId:this.userId
					
				}).then(result => {						
					this.$mytip.toast('评价成功')
					this.$refs.inputDialog.close()
					this.getList();
				});
			}
		}
	};
</script>

<style>	
	.text {
		background-color: #fff;
		line-height: 80rpx;
	}
</style>
