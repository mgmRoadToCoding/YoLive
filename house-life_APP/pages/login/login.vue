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
						<text class="txt">Phone</text>
						<input type="number" name="phone" placeholder="Please enter your phone number" maxlength="11"
							v-model="username" />
					</view>
					<view class="t-a">
						<text class="txt">Password</text>
						<input type="password" name="code" maxlength="18" placeholder="Please enter your password"
							v-model="password" />
					</view>
					<button @tap="login()">Log In</button>

					<!-- <view class="reg" @tap="reg()">문자 로그인</view> -->
					<view class="reg" @tap="openRegister">Register</view>

					<!-- 登录按钮下方 -->
					<u-popup v-model="showRegister" mode="bottom" length="60%" :mask-closable="true">
						<view class="register-popup">
							<view class="popup-header">
								<text class="popup-title">Register Account</text>
								<u-icon name="close" @tap="showRegister = false" size="40" color="#999" />
							</view>

							<view class="popup-body">
								<view class="t-a">
									<text>Phone number</text>
									<input type="text" v-model="regPhone" placeholder="Please enter your phone number" maxlength="15" />
								</view>
								<view class="t-a" style="position:relative;"> <text>Verification Code</text> <input type="text"
										v-model="regCode" placeholder="Please enter the verification code" maxlength="6" /> <button
										class="send-code" @tap="sendCode" :disabled="countdown>0">
										{{ countdown > 0 ? countdown + 's' : 'Send Verification Code' }} </button> </view>
								<view class="t-a">
									<text>Password</text>
									<input type="password" v-model="regPassword" placeholder="Please enter your password" maxlength="18" />
								</view>
								<view class="t-a">
									<text>Confirm Password</text>
									<input type="password" v-model="regPassword2" placeholder="Please confirm your password" maxlength="18" />
								</view>
								<button class="btn-register" @tap="register">Register</button>
							</view>
						</view>
					</u-popup>

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
		</view>
		<!-- #endif -->
	</view>
</template>
<script>
	export default {
		data() {
			return {
				// username: '18720989281',
				// password: '123456',
				// username: '01079218222',
				// password: '123456',

				username: '',
				password: '',
				showRegister: false, // 控制注册弹窗显示
				regPhone: '',
				regPassword: '',
				regPassword2: ''
			}
		},

		methods: {
			async login() {
				const phonePattern = /^(1[3-9]\d{9}|010\d{8}|010-\d{4}-\d{4})$/;

				if (!phonePattern.test(this.username)) {
					return this.$refs.uToast.show({
						title: '请输入正确的手机号',
						type: 'warning',
					});
				}

				if (!this.password) {
					return this.$refs.uToast.show({
						title: '密码不能为空',
						type: 'warning',
					});
				}

				try {
					const [err, res] = await uni.request({
						url: `${this.$u.api.baseUrl}/api/thirdLogin`,
						method: 'POST',
						header: {
							'Content-Type': 'application/json'
						},
						data: {
							username: this.username,
							password: this.password
						}
					});

					if (err || res.statusCode !== 200) {
						const errorMsg = res?.data?.msg || err?.errMsg || '请求失败';
						throw new Error(errorMsg);
					}

					console.log('后端返回的 res.data:', res.data);

					// 判断后端返回的 code
					if (res.data.code !== 200) {
						// 登录失败
						throw new Error(res.data.msg || '登录失败');
					}

					// 登录成功，获取 token
					const token = res.data.token;
					if (!token) {
						throw new Error('未获取到Token');
					}

					this.$u.vuex('vuex_token', token);
					this.$u.vuex('vuex_user', res.data.loginUser || {});
					uni.setStorageSync('token', token);

					uni.switchTab({
						url: '/pages/index/index'
					});

				} catch (error) {
					console.error('登录异常', error);
					this.$refs.uToast.show({
						title: error.message || '登录失败，请检查网络或稍后重试',
						type: 'error',
					});
				}
			},

			weChatLogin(e) {
				let code = e.detail.code;
				if (code) {
					uni.showLoading({
						title: "登录中....",
						mask: true
					});
					let url = "/api/miniWxApi/getPhoneNum?code=" + code;
					this.$u.get(url).then(res => {
						let phoneNum = res.phoneNum;
						let weChatUrl = "/api/weChatLogin";
						this.$u.post(weChatUrl, {
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
				} else {
					this.$mytip.toast('登录失败');
					uni.hideLoading(); // 失败时也隐藏
				}
			},

			openRegister() {
				this.showRegister = true;
			},
			register() {
				if (!this.regPhone || !this.regPassword || !this.regPassword2) {
					return this.$refs.uToast.show({
						title: '请完整填写注册信息',
						type: 'warning'
					});
				}
				if (this.regPassword !== this.regPassword2) {
					return this.$refs.uToast.show({
						title: '两次密码不一致',
						type: 'warning'
					});
				}

				this.$u.post('/api/thirdRegister', {
						username: this.regPhone,
						password: this.regPassword,
						code: this.regCode
					})
					.then(res => {
						console.log('注册返回:', res);

						if (res.code !== 200) {
							throw new Error(res.msg || "注册失败");
						}

						// ⭐⭐⭐ 自动登录处理（最关键部分）
						const token = res.token || res.data?.token;
						const loginUser = res.loginUser || res.data?.loginUser;

						if (!token) {
							throw new Error("注册成功但未返回 token");
						}

						// 保存 token 和用户信息
						this.$u.vuex('vuex_token', token);
						this.$u.vuex('vuex_user', loginUser);
						uni.setStorageSync('token', token);

						this.$refs.uToast.show({
							title: '注册成功，正在进入...',
							type: 'success'
						});

						// 关闭弹窗
						this.showRegister = false;

						// ⭐⭐⭐ 直接跳转主页
						uni.switchTab({
							url: '/pages/index/index'
						});

					})
					.catch(err => {
						console.error('注册失败:', err);
						this.$refs.uToast.show({
							title: err.message || '注册失败',
							type: 'error'
						});
					});
			},
			sendCode() {
				if (!this.regPhone) {
					return this.$refs.uToast.show({
						title: '请输入手机号',
						type: 'warning'
					});
				}
				// 调用发送验证码接口
				this.$u.post('/api/sendCode', {
						username: this.regPhone
					})
					.then(res => {
						this.$refs.uToast.show({
							title: '发送成功',
							type: 'success'
						});
					})
					.catch(err => {
						this.$refs.uToast.show({
							title: err.message || '发送失败',
							type: 'error'
						});
					});
			},
			reg() {
				this.$u.route({
					url: 'pages/login/account'
				});
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
	.register-popup {
		padding: 30rpx;
		background-color: #fff;
		border-top-left-radius: 20rpx;
		border-top-right-radius: 20rpx;
	}

	.popup-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 20rpx;
	}

	.popup-title {
		font-size: 32rpx;
		font-weight: bold;
	}

	.popup-body .t-a {
		margin-bottom: 20rpx;
	}

	.btn-register {
		width: 100%;
		height: 90rpx;
		background-color: #2796f2;
		color: #fff;
		font-size: 28rpx;
		border-radius: 50rpx;
		text-align: center;
		line-height: 90rpx;
	}




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
			justify-content: center;

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

	.clearBtn {
		margin: 0;
		padding: 0;
		line-height: 1;
		background-color: #FFFFFF;
	}

	.clearBtn::after {
		position: unset !important;
		border: unset;
	}
</style>