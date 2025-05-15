<!-- 蓝色登录页面2 -->
<template>
	<view>
		<u-toast ref="uToast" />
		<view class="img-a">
			<view class="t-b">
				Welcome,
				<br />
				YoLive사용해 주셔서 감사합니다.
			</view>
		</view>
		<view class="login-view">
			<view class="t-login">
				<form class="cl">
					<view class="t-a">
						<text class="txt">휴대폰</text>
						<input type="number" name="phone" placeholder="휴대폰 번호를 입력해주세요" maxlength="11" v-model="username" />
					</view>
					<view class="t-a">
						<text class="txt">비밀번호</text>
						<input type="password" name="code" maxlength="18" placeholder="비밀번호를 입력해주세요" v-model="password" />
					</view>
					<button @tap="login()">로그인</button>
					<view class="reg" @tap="reg()">문자 로그인</view>
				</form>
			</view>
		</view>
		
		<!-- #ifdef MP-WEIXIN -->  
		<view class="buttom">
			<button open-type="getPhoneNumber" @getphonenumber="weChatLogin" class="clearBtn">
				<view class="loginType">
					<view class="item">
						<view class="icon"><u-icon size="60" name="weixin-fill" color="rgb(83,194,64)"></u-icon></view>
						微信
					</view>
				</view>
			</button>
			<!-- <view class="hint">
				登录代表同意
				<text class="link">开源字节用户协议、隐私政策，</text>
				并授权使用您的账号信息（如昵称、头像、收获地址）以便您统一管理
			</view> -->
		</view>
		<!-- #endif -->
	</view>
</template>
<script>
export default {
	data() {
		return {
			// username: '',
			// password: '',
			username: '18720989281', //
			password: '123456',
		}
	},
	
	methods: {
		login() {
			if(!this.$u.test.mobile(this.username)){
				return this.$refs.uToast.show({
					title: '手机号不正确',
					type: 'warning',
				})
			}
			if(!this.password){
				return this.$refs.uToast.show({
					title: '密码不能为空',
					type: 'warning',
				})
			}
			// 登录json参数，不同于表单参数
			let url = "/api/thirdLogin";
			this.$u.post(url,{
				username: this.username,
				password: this.password
			}).then(data => {
				console.log(data); // 检查返回的登录数据格式
				// 登录成功初始化token与用户信息
				this.$u.vuex('vuex_token', data.token);
				this.$u.vuex('vuex_user', data.loginUser);
				uni.switchTab({
					url: '/pages/index/index'
				})
			});
		},
		weChatLogin(e){
			let code= e.detail.code;
			if(code){
				uni.showLoading({title:"登录中....",mask:true})
				let url = "/api/miniWxApi/getPhoneNum?code="+code;
				this.$u.get(url).then(res => {
					let phoneNum = res.phoneNum
					let weChatUrl = "/api/weChatLogin";
					this.$u.post(weChatUrl,{
						username: phoneNum,
						code: code
					}).then(data => {
						uni.hideLoading();
						// 登录成功初始化token与用户信息
						this.$u.vuex('vuex_token', data.token);
						this.$u.vuex('vuex_user', data.loginUser);
						uni.switchTab({
							url: '/pages/index/index'
						});
					});
				});
			}else{
				this.$mytip.toast('登录失败')
				uni.hideLoading(); // 失败时也隐藏
			}
		},
		reg(){
			this.$u.route({
				url: 'pages/login/account'
			})
		}
	}
};
</script>


<style>
page {
  background-color: #ffffff;
}
</style>
<style lang="scss" scoped>
.txt {
	font-size: 32rpx;
	font-weight: bold;
	color: #333333;
}
.img-a {
	width: 100%;
	height: 450rpx;
	background-image: url(https://zhoukaiwen.com/img/loginImg/head.png);
	background-size: 100%;
}
.reg {
	font-size: 28rpx;
	color: #fff;
	height: 90rpx;
	line-height: 90rpx;
	border-radius: 50rpx;
	font-weight: bold;
	background: #f5f6fa;
	color: #000000;
	text-align: center;
	margin-top: 30rpx;
}

.login-view {
	width: 100%;
	position: relative;
	margin-top: -120rpx;
	background-color: #ffffff;
	border-radius: 8% 8% 0% 0;
}

.t-login {
	width: 600rpx;
	margin: 0 auto;
	font-size: 28rpx;
	padding-top: 80rpx;
}

.t-login button {
	font-size: 28rpx;
	background: #2796f2;
	color: #fff;
	height: 90rpx;
	line-height: 90rpx;
	border-radius: 50rpx;
	font-weight: bold;
}

.t-login input {
	height: 90rpx;
	line-height: 90rpx;
	margin-bottom: 50rpx;
	border-bottom: 1px solid #e9e9e9;
	font-size: 28rpx;
}

.t-login .t-a {
	position: relative;
}

.t-b {
	text-align: left;
	font-size: 42rpx;
	color: #ffffff;
	padding: 130rpx 0 0 70rpx;
	font-weight: bold;
	line-height: 70rpx;
}

.t-login .t-c {
	position: absolute;
	right: 22rpx;
	top: 22rpx;
	background: #5677fc;
	color: #fff;
	font-size: 24rpx;
	border-radius: 50rpx;
	height: 50rpx;
	line-height: 50rpx;
	padding: 0 25rpx;
}

.t-login .t-d {
	text-align: center;
	color: #999;
	margin: 80rpx 0;
}

.t-login .t-e {
	text-align: center;
	width: 250rpx;
	margin: 80rpx auto 0;
}

.t-login .t-g {
	float: left;
	width: 50%;
}

.t-login .t-e image {
	width: 50rpx;
	height: 50rpx;
}

.t-login .t-f {
	text-align: center;
	margin: 150rpx 0 0 0;
	color: #666;
}

.t-login .t-f text {
	margin-left: 20rpx;
	color: #aaaaaa;
	font-size: 27rpx;
}

.t-login .uni-input-placeholder {
	color: #aeaeae;
}

.cl {
	zoom: 1;
}

.cl:after {
	clear: both;
	display: block;
	visibility: hidden;
	height: 0;
	content: '\20';
}

.buttom {
	.loginType {
		display: flex;
		padding: 140rpx 0;
		justify-content:center;
		
		.item {
			display: flex;
			flex-direction: column;
			align-items: center;
			color: $u-tips-color;
			font-size: 22rpx;
		}
	}
	
	.hint {
		position: absolute;
		bottom: 0;
		padding: 20rpx 40rpx;
		font-size: 20rpx;
		color: $u-tips-color;
		
		.link {
			color: #2979ff;
		}
	}
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
</style>
