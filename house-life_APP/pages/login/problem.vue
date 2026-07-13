<template>
	<view style="margin: 20rpx;">
		<u-navbar :is-back="true" title="常见问题" :border-bottom="false"></u-navbar>
		<u-collapse class="u-p-30">
			<u-collapse-item class="content" :title="item.head" v-for="(item, index) in itemList" :key="index" :open="item.open">
				{{item.body}}
				<hr />
			</u-collapse-item>
		</u-collapse>
	</view>
</template>

<script>
export default {
	data() {
		return {
			appUrl: false,
			url: "https://sourcebyte.cn",
			itemList: [
				{
					head: "初始密码是什么？",
					body: "初始密码默认为手机号码,为了您的账户安全,请进入个人中心进行修改。",
					open: false,
				},
				{
					head: "技术支持电话是多少？",
					body: "联系电话：01079218222",
					open: false,
				},
				{
					head: "关于我们",
					body: "我们专注提供专业的软件产品和技术。为合作伙伴提供安全可靠的软件产品与解决方案，共同帮助终端用户实现业务创新、快速发展。" ,
					open: true,
				}
			],
		}
	},
	methods: {
		server(){
			// #ifdef  H5
				window.open(url, '_blank');
			// #endif
			// #ifndef  H5
				this.$u.route("/pages/webview/webview", {
				  title: '关于我们',
				  src:url
				});
			// #endif
		},
		download(){
			this.DownLoadAndroid();
		},
		//安卓下载
		DownLoadAndroid() {
			if (this.isWeiXinLogin()) {
				uni.showToast({
					icon:'none',
					title:'请复制链接，在浏览器中打开下载',
					duration:3000
				})
				this.appUrl = true;
				return;
			}
			var downloadUrl = 'http://8.136.213.245/profile/apk/yixin.apk'; // 下载地址
			var urlStr = encodeURI(downloadUrl)
			// #ifdef APP-PLUS
			plus.runtime.openURL(urlStr);
			return;
			// #endif
			// #ifdef H5 
			window.open(urlStr);
			// #endif
		},
	}
};
</script>

<style>
	.content{
		width: 100%;
		/* background-color: red; */
		margin-top: 10px;
	}
	page {
		background: #fff;
	}
	
	.appUrl{
		padding: 20rpx;
	}
</style>
