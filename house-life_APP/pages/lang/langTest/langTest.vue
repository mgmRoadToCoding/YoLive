<template>
	<view>
		<text>{{ $t('hello') }}</text>
		<text>{{ $t('welcome') }}</text>
		<text>{{ $t('Test') }}</text>

		<button @click="changeLang('zh')">中文</button>
		<button @click="changeLang('en')">English</button>
		<button @click="changeLang('kor')">한국어</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			changeLang(lang) {
				this.$i18n.locale = lang
				uni.setStorageSync('lang', lang)
			}
		}
	}
</script>

<style>

</style>