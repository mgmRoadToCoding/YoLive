<template>
	<view>
		<u-navbar :is-back="true" :title="title" :border-bottom="false"></u-navbar>
		<view class="u-content">
			<u-parse :html="content"
			:autosetTitle="true"
			:show-with-animation="true"
			:selectable="true"
			></u-parse>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title:'避坑指南',
				content: ``
			}
		},
		onLoad() {
			let url = "/api/cmsApi/getServiceItemByAjax?type=租房避坑指南"
			this.$u.get(url).then(res => {
				// console.log(res.data.content);
				this.content = res.data.content
			});
		},
	}
</script>

<style>
	page{
		background-color: #FFFFFF;
	}
</style>
<style lang="scss" scoped>
	.u-content{
		// margin:0 10rpx;
		// padding: 24rpx;
		font-size: 34rpx;
		color: $u-main-color;
		line-height: 1.8;
		white-space: pre-wrap !important;
	}
</style>

