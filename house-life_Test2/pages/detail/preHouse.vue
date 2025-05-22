<template>
	<view class="">
		<!-- #ifndef MP-WEIXIN --> 
			<u-image width="100%" height="350rpx" :src="src"></u-image>
		<!-- #endif -->
		<view class="wrap">
			<u-form :model="model" :rules="rules" ref="uForm" :errorType="errorType">
				<u-form-item :label-position="labelPosition" label="小区名称" prop="villageName" label-width="180"
				left-icon="map" :leftIconStyle="{color:'#d5d5d5'}">
					<u-input :border="border" type="select" :select-open="selectShow" v-model="model.villageName" placeholder="请选择小区(必选)" @click="selectShow = true"></u-input>
				</u-form-item>
				<u-form-item :label-position="labelPosition" label="详细地址" prop="address" label-width="180"
				left-icon="file-text" :leftIconStyle="{color:'#d5d5d5'}" right-icon="map"
				:rightIconStyle="{color:'#d5d5d5'}">
					<u-input :border="border" placeholder="请在地图选择详细地址" v-model="model.address" type="text" disabled
					@click="chooseAddress"></u-input>
				</u-form-item>
				<u-form-item  :label-position="labelPosition" label="房牌号" prop="houseNo" label-width="180"
				left-icon="home" :leftIconStyle="{color:'#d5d5d5'}">
					<u-input :border="border" placeholder="请输入门牌号(必填)" v-model="model.houseNo" type="text"></u-input>
				</u-form-item>
				<u-form-item  :label-position="labelPosition" label="装修" prop="decoration" label-width="180" left-icon="eye" :leftIconStyle="{color:'#d5d5d5'}">
					<u-input :border="border" type="select" placeholder="请选择装修(必选)":select-open="decorationShow" v-model="model.decoration"  @click="decorationShow = true"></u-input>
				</u-form-item>
				<u-form-item  :label-position="labelPosition" label="房东姓名" prop="ownerName" label-width="180"
				left-icon="account" :leftIconStyle="{color:'#d5d5d5'}">
					<u-input :border="border" placeholder="请输入房东姓名(必填)" v-model="model.ownerName" type="text"></u-input>
				</u-form-item>
				<u-form-item  :label-position="labelPosition" label="房东电话" prop="owerPhone" label-width="180"
				left-icon="phone" :leftIconStyle="{color:'#d5d5d5'}">
					<u-input :border="border" placeholder="请输入房东电话(必填)" v-model="model.owerPhone" type="number" maxlength="11"></u-input>
				</u-form-item>
				<u-form-item label-width="180" :label-position="labelPosition" label="付款方式" prop="payType"
				left-icon="rmb-circle" :leftIconStyle="{color:'#d5d5d5'}">
					<u-input :border="border" type="select" :select-open="actionSheetShow" v-model="model.payType" placeholder="请选择付款方式(必选)" @click="actionSheetShow = true"></u-input>
				</u-form-item>
			</u-form>
			<!-- 流量主-腾讯视频广告 -->
			<ad unit-id="adunit-c6cdd74f48eed506" ad-type="video" ad-theme="white"></ad>
			<u-select mode="single-column" :list="selectList" v-model="selectShow" @confirm="selectConfirm"></u-select>
			<u-select mode="single-column" :list="decorationList" v-model="decorationShow" @confirm="decorationConfirm"></u-select>
			<u-action-sheet :list="actionSheetList" v-model="actionSheetShow" @click="actionSheetCallback"></u-action-sheet>
		</view>
		<view class="bottom-btn">
			<u-button type="primary" @click="submit">下一步</u-button>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		let that = this;
		return {
			src: 'http://img.sccnn.com/bimg/340/02027.jpg',
			actionSheetShow: false,
			model: {
				type: 0,
				houseType: '',
				roomLabel: '',
				villageName: '',
				villageId: '',
				address: '',
				houseNo: '',
				//装修
				decoration:'',
				//房东
				ownerName:'',
				//房东电话
				owerPhone:'',
				payType:'',
				createName: uni.getStorageSync('lifeData').vuex_user.userName,
				publishId: uni.getStorageSync('lifeData').vuex_user.userId,
				state: 0,
				longitude: 0,
				latitude: 0,
			},
			selectList: [],
			decorationList: [
				{
					label: '简装',
					value: '简装'
				},
				{
					label: '中装',
					value: '中装'
				},
				{
					label: '精装修',
					value: '精装修'
				},
				{
					label: '豪装',
					value: '豪装'
				}
			],
			actionSheetList: [
				{
					text: '月付'
				},
				{
					text: '季付'
				},
				{
					text: '半年付'
				},
				{
					text: '年付'
				}
			],
			rules: {
				villageName: [
					{
						required: true,
						message: '请选择小区',
						trigger: ['change','blur'],
					}
				],
				address: [
					{
						required: true,
						message: '请输入详细地址',
						trigger: ['change','blur'],
					}
				],
				houseNo: [
					{
						required: true,
						message: '请输入门牌号',
						trigger: ['change','blur'],
					}
				],
				decoration: [
					{
						required: true,
						message: '请选择装修',
						trigger: ['change','blur'],
					}
				],
				ownerName: [
					{
						required: true,
						message: '请输入房东姓名',
						trigger: ['change','blur'],
					}
				],
				owerPhone: [
					{
						required: true,
						message: '请输入房东电话',
						trigger: ['change','blur'],
					}
				],
				payType: [
					{
						required: true,
						message: '请选择付款方式',
						trigger: ['change','blur'],
					}
				],
			},
			border: false,
			check: false,
			selectShow: false,
			decorationShow: false,
			labelPosition: 'left',
			errorType: ['toast'],
		};
	},
	onLoad(option) {
		let houseId = option.houseId //上个页面传递的参数。
		if(houseId){
			// 渲染当前房源信息
			this.findHouseById(houseId);
		}
		this.findVillageList()
		
		// 定位经纬度
		uni.getLocation({
			 type: 'gcj02',
			 // 当指定 high 时，期望精度值为100m，当指定 best 时期望精度值为20m
			 accuracy:'best',
			 // 开启高精度定位
			 isHighAccuracy:true,
			 success: (res) => {
			 	this.longitude=res.longitude
			 	this.latitude= res.latitude
			 }
		});
		uni.$on('changeAddressConfig', (address,lon,lat) => {
			// 获取数据
			this.changeAddressConfig(address,lon,lat);
		})
	},
	onUnload() {
		// 移除监听事件  
		uni.$off('changeAddressConfig');
	},
	onReady() {
		this.$refs.uForm.setRules(this.rules);
	},
	methods: {
		changeAddressConfig(address,lon,lat){
			this.model.address = address
			this.model.longitude= lon
			this.model.latitude= lat
		},
		submit() {
			this.$refs.uForm.validate(valid => {
				if (valid) {
					if(!this.$u.test.mobile(this.model.owerPhone)){
						return this.$mytip.toast('房东电话请输入手机号码')
					}
					return this.$u.route({
						url: '/pages/detail/addHouse',
						params: {
							room: JSON.stringify(this.model)
						}
					})
				} else {
					console.log('验证失败');
				}
			});
		},
		selectConfirm(e) {
			e.map((val, index) => {
				this.model.villageName = val.label;
				this.model.villageId = val.value
			})
		},
		decorationConfirm(e) {
			e.map((val, index) => {
				this.model.decoration = val.label;
			})
		},
		labelPositionChange(index) {
			this.labelPosition = index == 0 ? 'left' : 'top';
		},
		// 点击actionSheet回调
		actionSheetCallback(index) {
			uni.hideKeyboard();
			this.model.payType = this.actionSheetList[index].text;
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
					this.selectList.push({
						label: item.name,
						value: item.id
					})
				}
				return data
			});
		},
		findHouseById(houseId){
			let url = "api/houseApi/findHouseById";
			this.$u.get(url, {
				id: houseId
			}).then(result => {
				let room = result.data
				this.model = room
				let houseType = this.model.houseNum + this.model.houseHall + this.model.toiletNum
				if(houseType == 0){
					houseType = ''
				}
				this.model.houseType = houseType
				if(this.model.roomType == 1){
					this.model.roomLabel = '主卧'
				}else if(this.model.roomType == 2){
					this.model.roomLabel = '次卧'
				}
			});
		},
		chooseAddress() {
			this.$u.route({
				url: '/pages/chooseAddress/index'
			})
		},
	}
};
</script>

<style>
	page{
		background-color: #FFFFFF;
	}
</style>
<style scoped lang="scss">
.wrap {
	padding: 30rpx;
}

/* #ifdef MP-WEIXIN */  
.bottom-btn {
	position: fixed;
	bottom: 8rpx;
	width: 100%;
	padding: 30rpx;
	border-top: 1rpx solid #eee;
	background-color: #FFFFFF;
}
/* #endif */

/* #ifndef MP-WEIXIN */  
.bottom-btn {
	padding: 0 30rpx 30rpx 30rpx;
	background-color: #FFFFFF;
}
/* #endif */
</style>
