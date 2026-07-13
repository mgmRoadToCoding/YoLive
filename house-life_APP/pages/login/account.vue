<template>
	<view>
		<u-toast ref="uToast" />

		<!-- 顶部欢迎 -->
		<view class="img-a">
			<view class="t-b">
				Welcome,
				<br />
				YoLive사용해 주셔서 감사합니다.
			</view>
		</view>

		<!-- 注册/登录表单 -->
		<view class="login-view">
			<view class="t-login">
				<form class="cl">
					<view class="t-a">
						<text class="txt">휴대폰</text>
						<input type="number" name="phone" placeholder="휴대폰 번호를 입력해주세요" maxlength="11"
							v-model="username" />
					</view>

					<view class="t-a">
						<text class="txt">비밀번호</text>
						<input type="password" name="password" maxlength="18" placeholder="비밀번호를 입력해주세요"
							v-model="password" />
					</view>
					<!-- 注册 -->
					<button @tap.prevent="registerOrLogin()">로그인/注册</button>
				</form>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				username: '', // 用户手机号
				password: '', // 密码
			};
		},
		methods: {
			async registerOrLogin() {
				// 韩国手机号验证：010开头 + 8位数字
				const phonePattern = /^010\d{8}$/;
				if (!phonePattern.test(this.username)) {
					return this.$refs.uToast.show({
						title: '유효한 휴대폰 번호를 입력하세요',
						type: 'warning',
					});
				}
				if (!this.password) {
					return this.$refs.uToast.show({
						title: '비밀번호를 입력하세요',
						type: 'warning',
					});
				}

				try {
					// 调用后端注册/登录接口
					const [err, res] = await uni.request({
						url: `${this.$u.api.baseUrl}/api/thirdRegister`,
						method: 'POST',
						header: {
							'Content-Type': 'application/json'
						},
						data: {
							username: this.username,
							password: this.password,
							code: '1234', // 这里如果后端需要验证码，可以在页面添加验证码输入
						},
					});

					if (err || res.statusCode !== 200) {
						const errorMsg = res?.data?.msg || err?.errMsg || '注册失败';
						throw new Error(errorMsg);
					}

					const token = res.data?.token;
					if (!token) {
						throw new Error('未获取到Token');
					}

					// 存储 token 和用户信息
					this.$u.vuex('vuex_token', token);
					this.$u.vuex('vuex_user', res.data?.loginUser || {});
					uni.setStorageSync('token', token);

					// 跳转首页
					uni.switchTab({
						url: '/pages/index/index'
					});
				} catch (error) {
					console.error('注册/登录异常', error);
					this.$refs.uToast.show({
						title: error.message || '注册/登录失败',
						type: 'error',
					});
				}
			},
		},
	};
</script>

<style scoped>
	.img-a {
		width: 100%;
		height: 450rpx;
		background-image: url(https://zhoukaiwen.com/img/loginImg/head.png);
		background-size: 100%;
	}

	.t-b {
		text-align: left;
		font-size: 42rpx;
		color: #ffffff;
		padding: 130rpx 0 0 70rpx;
		font-weight: bold;
		line-height: 70rpx;
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

	.cl:after {
		clear: both;
		display: block;
		visibility: hidden;
		height: 0;
		content: '\20';
	}
</style>