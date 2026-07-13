<template>
  <view class="container">
    <!-- 顶部导航栏 -->
    <view class="navbar">
      <text class="navbar-title">News List</text>
    </view>
    
    <!-- 内容区域 -->
    <scroll-view scroll-y class="news-list">
      <!-- 新闻列表 -->
      <view 
        v-for="(item, index) in newsList" 
        :key="index" 
        class="news-item"
        @click="goToDetail(item)"
      >
        <!-- 左侧文字信息 -->
        <view class="info-container">
          <text class="title">{{ item.title }}</text>
          <view class="meta-info">
            <text class="source">{{ item.source }}</text>
            <text class="views">{{ formatViews(item.views) }}조회</text>
            <text class="time">{{ item.time }}</text>
          </view>
        </view>
        
        <!-- 右侧缩略图 -->
        <image 
          class="thumbnail" 
          :src="item.img" 
          mode="aspectFill"
        ></image>
      </view>

      <!-- 评论区 -->
      <!-- <view class="comment-section">
        <text class="comment-title">댓글</text>
        <view 
          v-for="(comment, index) in comments" 
          :key="'comment-' + index" 
          class="comment-item"
        >
          <text class="comment-name">{{ comment.name }}</text>
          <text class="comment-text">{{ comment.text }}</text>
          <text class="comment-time">{{ comment.time }}</text>
        </view>
      </view> -->
    </scroll-view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      newsList: [
        {
          id: 1,
          title: '2025년 한국 유학 신청 일정 공개, 서두르세요!',
          source: '한국유학가이드',
          views: 8654,
          time: '2시간 전',
          img: '/static/img/index/international/1.jpg'
        },
        {
          id: 2,
          title: '유학생을 위한 주거 가이드: 서울 vs 부산 인기 지역 비교',
          source: '한국유학도우미',
          views: 10400,
          time: '4시간 전',
          img: '/static/img/index/international/2.jpg'
        },
        {
          id: 3,
          title: '한국 대학 TOP10 최신 순위 발표, 당신의 목표 대학은 몇 위?',
          source: '펀한정보',
          views: 20987,
          time: '6시간 전',
          img: '/static/img/index/international/3.jpg'
        },
        {
          id: 4,
          title: '2025년 봄학기 모집 정보 총정리: 연세대, 고려대, 성균관대 등',
          source: '유학통',
          views: 3150,
          time: '8시간 전',
          img: '/static/img/index/international/4.jpg'
        },
        {
          id: 5,
          title: '【꿀팁】한국 유학 장학금 완전 분석, 합격률 높이는 방법은?',
          source: '한국유학전략',
          views: 7850,
          time: '1일 전',
          img: '/static/img/index/international/5.jpg'
        },
        {
          id: 6,
          title: '한국 유학 비자 D-2 최신 발급 가이드 및 유의사항',
          source: '한국유학전략',
          views: 6210,
          time: '1일 전',
          img: '/static/img/index/international/6.jpg'
        },
        {
          id: 7,
          title: '유학생의 언어 장벽 극복법? TOPIK 시험 준비 노하우 공유',
          source: '한국어코너',
          views: 5400,
          time: '2일 전',
          img: '/static/img/index/international/7.jpg'
        },
        {
          id: 8,
          title: '신입생 입국부터 은행계좌까지 전과정 가이드',
          source: '한국생활정보',
          views: 3980,
          time: '2일 전',
          img: '/static/img/index/international/8.jpg'
        },
        {
          id: 9,
          title: '한국 문화 차이 정리: 반드시 알아야 할 금기사항',
          source: '한국문화소식',
          views: 7230,
          time: '3일 전',
          img: '/static/img/index/international/9.jpg'
        },
        {
          id: 10,
          title: '유학생 아르바이트 필수 정보: 합법적인 업종과 시급 기준',
          source: '유학통',
          views: 8120,
          time: '3일 전',
          img: '/static/img/index/international/10.jpg'
        }
      ],
      comments: [
        { name: '학생1', text: '좋은 기사 감사합니다!', time: '1시간 전' },
        { name: '학생2', text: '도움이 되었어요!', time: '2시간 전' },
        { name: '학생3', text: '더 많은 정보도 기대할게요!', time: '3시간 전' }
      ]
    };
  },
  methods: {
    formatViews(views) {
      return views >= 10000 ? (views / 10000).toFixed(1) + '만 ' : views;
    },
    goToDetail(item) {
      uni.navigateTo({
        url: `/pages/international/news-detail?id=${item.id}&title=${encodeURIComponent(item.title)}`
      });
    }
  }
};
</script>

<style lang="scss">
.container {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.navbar {
  height: 88rpx;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1rpx solid #eee;

  &-title {
    font-size: 36rpx;
    font-weight: 600;
    color: #333;
  }
}

.news-list {
  padding: 20rpx;
  height: calc(100vh - 88rpx);
}

.news-item {
  display: flex;
  background-color: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 24rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);

  .info-container {
    flex: 1;
    padding-right: 24rpx;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .title {
    font-size: 32rpx;
    font-weight: 500;
    color: #333;
    line-height: 1.5;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .meta-info {
    margin-top: 20rpx;
    display: flex;
    align-items: center;
    font-size: 24rpx;
    color: #999;

    .source {
      max-width: 180rpx;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .views, .time {
      margin-left: 24rpx;
    }
  }

  .thumbnail {
    width: 200rpx;
    height: 140rpx;
    border-radius: 8rpx;
    background-color: #eee;
  }
}

.comment-section {
  background-color: #fff;
  margin-top: 20rpx;
  padding: 24rpx;
  border-radius: 16rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);

  .comment-title {
    font-size: 32rpx;
    font-weight: 600;
    margin-bottom: 20rpx;
    color: #333;
  }

  .comment-item {
    margin-bottom: 20rpx;

    .comment-name {
      font-weight: bold;
      margin-bottom: 6rpx;
      color: #444;
    }

    .comment-text {
      font-size: 28rpx;
      color: #555;
      margin-bottom: 6rpx;
    }

    .comment-time {
      font-size: 24rpx;
      color: #999;
    }
  }
}
</style>