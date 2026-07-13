// 全局引入 PSV CSS
import '@photo-sphere-viewer/core/index.css'
import '@photo-sphere-viewer/markers-plugin/index.css'


// 1. 导入核心库
import Vue from 'vue'
import App from './App'

// 2. 引入 vue-i18n 用于多语言切换
import VueI18n from 'vue-i18n'
Vue.use(VueI18n)

// 3. 导入语言包
import en from '@/pages/lang/en.js'
import zh from '@/pages/lang/zh.js'
import kor from '@/pages/lang/kor.js'

// 4. 设置默认语言（读取缓存或默认为韩语）
const locale = uni.getStorageSync('selectedLanguage') || 'zh'

// 5. 创建 i18n 实例
const i18n = new VueI18n({
	locale,
	messages: {
		en,
		zh,
		kor
	},
	silentTranslationWarn: true
})

// 6. 挂载到Vue实例
Vue.prototype._i18n = i18n

// 7. 创建全局混入 - 语言切换功能
Vue.mixin({
	computed: {
		currentLanguage() {
			return this.$i18n.locale
		}
	},
	methods: {
		changeLanguage(lang) {
			this.$i18n.locale = lang
			uni.setStorageSync('selectedLanguage', lang)
			this.$forceUpdate()
			// 通知所有页面更新
			uni.$emit('languageChanged', lang)
		}
	}
})

// 8. 全局配置
Vue.config.productionTip = false
App.mpType = 'app'

// 9. 注册 uView UI
import uView from 'uview-ui'
Vue.use(uView)


// 10. 注册 Vuex store
import store from '@/store'

// 11. 设置全局 API 基础地址
import config from '@/common/config.js' // 之前的config文件
Vue.prototype.$u.api = {
	// baseUrl: 'http://localhost:8088' // 后端地址
	// baseUrl: 'http://10.0.2.2:8088' //模拟器地址
	baseUrl: config.baseUrl //从config中读取地址而不是写死
}

// 12. 混入 vuex 快捷写法
let vuexStore = require('@/store/$u.mixin.js')
Vue.mixin(vuexStore)

// 13. 混入 uView 小程序分享封装
let mpShare = require('uview-ui/libs/mixin/mpShare.js')
Vue.mixin(mpShare)

// 14. 封装自定义提示框
import $mytip from 'common/utils/tip.js'
Vue.prototype.$mytip = $mytip

// 15. 注册全局 token 检查方法
Vue.prototype.$checkAuth = async function() {
	const lifeData = uni.getStorageSync('lifeData') || {}
	const token = lifeData.vuex_token

	if (!token) {
		this.$mytip.toast('请先登录')
		setTimeout(() => {
			uni.reLaunch({
				url: '/pages/login/login'
			})
		}, 1500)
		return false
	}

	try {
		const res = await this.$u.http.get('/api/profile/isExpiration', {
			header: {
				Authorization: 'Bearer ' + token
			}
		})
		if (res.code !== 200 || res.data !== true) {
			this.$mytip.toast('登录已过期')
			this.$u.vuex('vuex_token', '') // 清除 token
			setTimeout(() => {
				uni.reLaunch({
					url: '/pages/login/login'
				})
			}, 1500)
			return false
		}
		return true
	} catch (err) {
		this.$mytip.toast('验证失败')
		return false
	}
}

// 16. 注册全局头像处理方法
Vue.prototype.$updateAvatar = function() {
	const lifeData = uni.getStorageSync('lifeData') || {}
	const user = lifeData.vuex_user?.user || {}
	const avatar = user.avatar || ''
	return avatar.includes('http') ? avatar : this.$u.api.baseUrl + avatar
}

// 17. 创建 Vue 实例，注入 i18n 和 store
const app = new Vue({
	i18n, // 多语言支持
	store, // 状态管理
	...App
})

// 18. 注册 HTTP 拦截器（必须放在实例化之后）
import httpInterceptor from '@/common/http.interceptor.js'
Vue.use(httpInterceptor, app)

// main.js
// import config from '@/common/config'
// Vue.prototype.$config = config
// uni.$config = config


// 19. 挂载应用
app.$mount()