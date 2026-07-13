<template>
	<view>
		<u-navbar :is-back="true" title="비밀번호 변경" :border-bottom="false"></u-navbar>
		<view class="u-m-20">
			<u-toast ref="uToast" />
			<view>
				<u-cell-group>
					<u-field v-model="oldpassword" label="기존 비밀번호" placeholder="기존 비밀번호를 입력하세요" icon="lock"
						iconColor="#d5d5d5" label-width="180" password focus>
					</u-field>
					<u-field v-model="newpassword" label="새 비밀번호" placeholder="새 비밀번호를 입력하세요" icon="lock"
						iconColor="#d5d5d5" label-width="180" password>
					</u-field>
					<u-field v-model="password" label="비밀번호 확인" placeholder="비밀번호를 다시 입력하세요" icon="lock-fill"
						iconColor="#d5d5d5" label-width="180" password>
					</u-field>
				</u-cell-group>
			</view>
		</view>
		<view class="btn">
			<u-button type="primary" @tap="changePasswold">제출</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				oldpassword: '',
				newpassword: '',
				password: '',
			}
		},
		methods: {
			changePasswold() {
				if (!this.oldpassword) {
					return this.$mytip.toast('기존 비밀번호를 입력하세요')
				}
				if (!this.newpassword) {
					return this.$mytip.toast('새 비밀번호를 입력하세요')
				}
				if (!this.password) {
					return this.$mytip.toast('비밀번호를 확인하세요')
				}
				if (this.newpassword != this.password) {
					return this.$mytip.toast('비밀번호가 일치하지 않습니다. 확인해주세요')
				}
				let url = "api/profile/updatePwd";
				let userId = uni.getStorageSync('lifeData').vuex_user.user.userId;
				this.$u.put(url, {
					userId: userId,
					oldPassword: this.oldpassword,
					newPassword: this.newpassword
				}).then(data => {
					//提示后跳转页面
					this.$mytip.toast('비밀번호가 성공적으로 변경되었습니다')
					setTimeout(() => {
						uni.switchTab({
							url: '/pages/center/center'
						})
					}, 1000);
				});
			},
		}
	};
</script>

<style lang="scss" scoped>
	page {
		overflow: hidden;
	}

	.logo {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 550rpx;

		img {
			text-align: center;
		}
	}

	.version {
		position: fixed;
		bottom: 0;
		width: 100%;
		text-align: center;
		padding: 15rpx;
	}

	.btn {
		margin: 20rpx;
	}
</style>