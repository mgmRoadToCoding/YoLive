<template>
	<view class="translation-btn-container">
		<view class="translation-btn" @click="showLanguagePicker = true">
			<u-icon name="translate" size="24"></u-icon>
			<text class="btn-text">{{ $t('language') }}</text>
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
</template>

<script>
	export default {
		data() {
			return {
				showLanguagePicker: false,
				currentLanguage: uni.getStorageSync('selectedLanguage') || 'kor',
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
				]
			}
		},
		computed: {
			currentLanguageText() {
				const lang = this.languages.find(item => item.code === this.currentLanguage);
				return lang ? lang.name : '한국어';
			}
		},
		methods: {
			changeLanguage(lang) {
				this.currentLanguage = lang;
				this.$i18n.locale = lang;
				uni.setStorageSync('selectedLanguage', lang);
				this.showLanguagePicker = false;
				this.$emit('languageChanged', lang); // 如果你想监听
			}
		},
		mounted() {
			this.$i18n.locale = this.currentLanguage;
		}
	}
</script>

<style scoped lang="scss">
	.translation-btn-container {
		position: absolute;
		top: 20rpx;
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
			}

			.language-item:last-child {
				border-bottom: none;
			}
		}
	}
</style>