<template>
	<view>
		<u-navbar :is-back="true" :title="title" :border-bottom="false"></u-navbar>
		<view class="u-content">
			<u-parse
				:html="content"
				:autosetTitle="true"
				:show-with-animation="true"
				:selectable="true"
			></u-parse>
		</view>
		
		<view>test</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: '학습경험 공유',
				content: ``
			};
		},
		onLoad() {
			let url = "/api/cmsApi/getServiceItemByAjax?type=学习经验分享";
			this.$u.get(url).then(res => {
				this.content = res.data.content;
			});
		}
	};
</script>

<style>
	page {
		background-color: #FFFFFF;
	}
</style>

<style lang="scss" scoped>
	.u-content {
		font-size: 34rpx;
		color: $u-main-color;
		line-height: 1.8;
		white-space: pre-wrap !important;
	}
</style>
