<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
			// 初始化语言设置
			const savedLang = uni.getStorageSync('selectedLanguage') || 'ch';
			this.$i18n.locale = savedLang;

			// 监听语言变化事件
			uni.$on('languageChanged', (lang) => {
				this.$i18n.locale = lang;
			});
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}		
	}
</script>

<style lang="scss">
	@import "uview-ui/index.scss";
	/*每个页面公共css */
</style>