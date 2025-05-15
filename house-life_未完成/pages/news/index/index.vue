<template>
	<view class="news-section">
	    <!-- 新闻详情内容容器 -->
	    <view class="news-item">
	      <view class="news-title">{{ newsDetail.title || '加载中...' }}</view>
	      <view class="news-content">{{ newsDetail.content }}</view>
	      <view class="news-time">{{ newsDetail.publishTime }}</view>
	    </view>
	  </view>
</template>

<script>
	export default {
		data() {
		    return {
		      newsDetail: {} // 存储新闻详情数据
		    }
		  },
		  onLoad(options) {
		    const newsId = options.id;
		    console.log("当前新闻ID:", newsId);
		    this.loadNewsDetail(newsId); // 加载数据
		  },
		  methods: {
		    async loadNewsDetail(id) {
		      try {
		        // 示例：调用API获取数据
		        const res = await uni.request({
		          url: `/api/news/${id}`,
		          method: 'GET'
		        });
		        this.newsDetail = res.data;
		      } catch (e) {
		        uni.showToast({ title: "加载失败", icon: "none" });
		      }
		    }
		  }
	}
</script>

<style>
.news-section {
  padding: 20rpx;
  background-color: #fff;
}

.news-item {
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
}

.news-title {
  font-size: 36rpx;
  font-weight: bold;
  margin-bottom: 20rpx;
}

.news-content {
  font-size: 30rpx;
  line-height: 1.6;
  color: #333;
}

.news-time {
  font-size: 24rpx;
  color: #999;
  margin-top: 30rpx;
  text-align: right;
}
</style>
