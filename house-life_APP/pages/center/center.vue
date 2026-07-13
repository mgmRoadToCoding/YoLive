<template>
	<view>
		<!-- 语言切换按钮 -->
		<view class="translation-btn-container">
			<view class="translation-btn" @click="showLanguagePicker = true">
				<u-icon name="translate" size="24"></u-icon>
				<text class="btn-text" style="color: red;">{{ $t('language') }} /</text>
				<text class="current-language">{{ currentLanguageText }}</text>
			</view>

			<!-- 语言选择弹窗 -->
			<u-popup v-model="showLanguagePicker" mode="bottom" border-radius="20">
				<view class="language-picker">
					<view class="picker-header">
						<text class="picker-title">{{ $t('selectLanguage') }}</text>
						<u-icon name="close" @click="showLanguagePicker = false"></u-icon>
					</view>
					<view class="language-list">
						<view v-for="lang in languages" :key="lang.code" class="language-item"
							@click="changeLanguage(lang.code)">
							<text>{{ lang.name }}</text>
							<u-icon v-if="currentLanguage === lang.code" name="checkbox-mark" color="#2979ff"></u-icon>
						</view>
					</view>
				</view>
			</u-popup>
		</view>

		<!-- 用户信息区域 -->
		<u-navbar :is-back="false" :title="$t('personalCenter')" :border-bottom="false"></u-navbar>
		<view class="u-flex user-box u-p-l-30 u-p-r-20 u-p-t-30 u-p-b-30">
			<view class="u-flex" @click="profile">
				<view class="u-m-r-20">
					<u-avatar :src="avatar" size="140"></u-avatar>
				</view>
				<view class="u-flex-1">
					<view class="u-font-18 u-p-b-20">{{vuex_user.user.userName || $t('guest')}}</view>
					<view class="u-font-14 u-tips-color">{{ $t('nickname') }}:
						{{vuex_user.user.nickName || $t('unset')}}
					</view>
				</view>
			</view>
		</view>

		<!-- 导航菜单 -->
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item v-for="(item, index) in navList" :key="index" :icon="item.icon" :title="item.name"
					@click="clickNav(item.url)"></u-cell-item>
			</u-cell-group>
		</view>

		<!-- 其他功能区域 -->
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="setting" :title="$t('Settings')" @click="setting" S></u-cell-item>
				<u-cell-item icon="info-circle" :title="$t('About')" @click="aboutMe"></u-cell-item>
			</u-cell-group>
		</view>

		<!-- 退出登录按钮 -->
		<view class="u-m-t-50 u-p-l-40 u-p-r-40">
			<u-button type="error" @click="logout">{{ $t('Logout') }}</u-button>
		</view>
	</view>
</template>

<script>
	import en from '@/pages/lang/en.js'
	import kor from '@/pages/lang/kor.js'
	import zh from '@/pages/lang/zh.js'
	import config from "@/common/config.js" // 全局配置文件

	export default {
		data() {
			return {
				showLanguagePicker: false,
				currentLanguage: uni.getStorageSync('selectedLanguage') || 'kor', // 从本地存储获取或默认韩语
				languages: [{
						code: 'en',
						name: 'English'
					},
					{
						code: 'zh',
						name: '中文'
					},
					{
						code: 'kor',
						name: '한국어'
					}
				],
				avatar: this.getSafeAvatar(),
				show: true,
				// navList移动到computed中实现多语言
			}
		},
		computed: {
			currentLanguageText() {
				const lang = this.languages.find(item => item.code === this.currentLanguage);
				return lang ? lang.name : '한국어';
			},
			navList() {
				return [{
						name: this.$t('viewHistory'),
						icon: "checkmark-circle",
						url: "pages/center/history"
					},
					{
						name: this.$t('myFavorites'),
						icon: "heart",
						url: "pages/center/heart"
					},
					{
						name: this.$t('myPosts'),
						icon: "list",
						url: "pages/forum/my"
					},
					{
						name: this.$t('publishForum'),
						icon: "edit",
						url: "pages/forum/publish"
					}
					// {
					// 	name: this.$t('myRequests'),
					// 	icon: "order",
					// 	url: "pages/center/order"
					// },
					// {
					// 	name: this.$t('feedback'),
					// 	icon: "info-circle"
					// }
				]
			}
		},
		onLoad() {
			uni.$on('updateAvatar', this.updateAvatar);
			// 初始化语言设置
			this.initI18n();
		},
		onUnload() {
			uni.$off('updateAvatar', this.updateAvatar);
		},
		onShow() {
			this.checkToken();
			if (!this.vuex_user?.user) {
				this.$u.vuex('vuex_user', {
					user: {}
				});
			}
			// 更新头像路径
			this.avatar = this.getSafeAvatar();
		},
		methods: {
			getSafeAvatar() {
				const lifeData = uni.getStorageSync('lifeData') || {};
				const user = lifeData.vuex_user?.user || {};
				if (!user.avatar) return config.staticUrl + 'default-avatar.jpg';
				return user.avatar.includes(config.staticUrl) ?
					user.avatar :
					config.staticUrl + user.avatar;
			},
			initI18n() {
				// 设置当前语言
				this.$i18n.locale = this.currentLanguage;
			},
			changeLanguage(lang) {
				// 更新全局i18n语言
				this.$i18n.locale = lang;
				this.currentLanguage = lang;

				// 持久化存储
				uni.setStorageSync('selectedLanguage', lang);

				// 关闭语言选择器
				this.showLanguagePicker = false;

				// 强制刷新所有已打开的页面
				const pages = getCurrentPages();
				pages.forEach(page => {
					if (page.$vm) {
						page.$vm.$i18n.locale = lang; // 更新语言
						page.$vm.$forceUpdate(); // 强制更新
					}
				});

				// 发送全局事件通知
				uni.$emit('languageChanged', lang);

				// 显示切换成功提示
				uni.showToast({
					title: this.$t('languageChanged'),
					icon: 'none'
				});
			},
			// 其他原有方法保持不变...
			logout() {
				this.$u.vuex('vuex_token', '');
				this.$u.vuex('vuex_user', {});
				this.$u.route('/pages/login/login');
			},
			profile() {
				this.$u.route('/pages/profile/profile');
			},
			setting() {
				this.$u.route('/pages/profile/setting');
			},
			callPhoneNumber() {
				uni.makePhoneCall({
					phoneNumber: "18720989281"
				});
			},
			problem() {
				this.$u.route({
					url: 'pages/login/problem'
				});
			},
			aboutMe() {
				this.$u.route('/pages/profile/aboutMe');
			},
			checkToken() {
				let lifeData = uni.getStorageSync('lifeData');
				let token = lifeData.vuex_token;
				if (!token) {
					return uni.reLaunch({
						url: '../login/login'
					});
				} else {
					let url = "/api/profile/isExpiration";
					this.$u.get(url, {
						token: token
					}).then(obj => {
						if (obj.data) {
							return uni.reLaunch({
								url: '../login/login'
							});
						}
					});
				}
			},
			code() {
				this.$mytip.toast(this.$t('comingSoon'));
			},
			clickNav(url) {
				if (url) {
					this.$u.route(url);
				} else {
					this.$mytip.toast(this.$t('comingSoon'));
				}
			},
			updateAvatar() {
				this.avatar = this.getSafeAvatar();
			}
		}
	}
</script>

<style lang="scss">
	//语言翻译
	.translation-btn-container {
		position: absolute;
		top: 100rpx;
		right: 20rpx;
		z-index: 999;
	}

	.translation-btn {
		display: flex;
		align-items: center;
		padding: 8rpx 16rpx;
		background-color: #fff;
		border-radius: 40rpx;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);

		.btn-text {
			margin: 0 10rpx;
			font-size: 24rpx;
		}

		.current-language {
			font-size: 24rpx;
			font-weight: bold;
			color: #2979ff;
		}
	}

	.language-picker {
		padding: 30rpx;

		.picker-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding-bottom: 20rpx;
			border-bottom: 1rpx solid #eee;

			.picker-title {
				font-size: 32rpx;
				font-weight: bold;
			}
		}

		.language-list {
			padding-top: 20rpx;

			.language-item {
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding: 25rpx 0;
				border-bottom: 1rpx solid #f5f5f5;

				&:last-child {
					border-bottom: none;
				}
			}
		}
	}

	//语言翻译end

	.user-box {
		background-color: #fff;
		justify-content: space-between;
	}

	.center-nav {
		background-color: #FFFFFF;
		margin-top: 30rpx;
		padding: 30rpx 0;
		border-radius: 8px;

		.tabName {
			color: #606266;
			font-size: 26rpx;
			padding-top: 10rpx;
		}
	}

	.qiandao {
		color: #606266;
		font-size: 24rpx;
		margin-right: 24rpx;
		margin-top: 54rpx;
		justify-content: center;
		align-items: center;
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