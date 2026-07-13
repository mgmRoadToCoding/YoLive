<template>
  <view class="container">
    <!-- 顶部导航栏 -->
    <view class="navbar">
      <text class="navbar-title">Information List</text>
    </view>
    
    <!-- 新闻列表 -->
    <scroll-view scroll-y class="news-list">
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
    </scroll-view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      newsList: [
        {
          "id": 1,
          "title": "한국 경제, '고고용률 저성장' 딜레마에 빠져들다",
          "source": "펀한정보",
          "views": 8175,
          "time": "4시간 전",
          "img": "/static/img/index/international/10.jpg"
        },
        {
          "id": 2,
          "title": "서울 시내버스 파업 3일 연속 운행 중단 우려! 시장 '모든 역량 동원' 지시",
          "source": "펀한정보",
          "views": 11000,
          "time": "8시간 전",
          "img": "/static/img/index/international/9.jpg"
        },
        {
          "id": 3,
          "title": "한국 초등학교에서 학생 교사 폭행 사건 발생, 교육계 경악",
          "source": "백운색의면화...",
          "views": 21,
          "time": "6분 전",
          "img": "/static/img/index/international/8.jpg"
        },
        {
          "id": 4,
          "title": "[05/27 중국 채용] 상하이 구매 전문가(한국어)",
          "source": "펀한위챗공식계정",
          "views": 2347,
          "time": "1시간 전",
          "img": "/static/img/index/international/7.jpg"
        },
        {
          "id": 5,
          "title": "[05/27 한국 채용] 안산 중앙동 스타벅스",
          "source": "펀한정보",
          "views": 1500,
          "time": "2시간 전",
          "img": "/static/img/index/international/6.jpg"
        },
        {
          "id": 6,
          "title": "한국 출생률 사상 최저치 기록... 정부 보육 지원 확대 검토",
          "source": "한겨레",
          "views": 9542,
          "time": "3시간 전",
          "img": "/static/img/index/international/5.jpg"
        },
        {
          "id": 7,
          "title": "한중 무역액 감소, 전문가 '과학기술 협력 강화' 제안",
          "source": "펀한정보",
          "views": 6700,
          "time": "5시간 전",
          "img": "/static/img/index/international/4.jpg"
        },
        {
          "id": 8,
          "title": "한국 대학 졸업생 취업률 회복... IT 분야 수요 급증",
          "source": "한국경제일보",
          "views": 8123,
          "time": "7시간 전",
          "img": "/static/img/index/international/3.jpg"
        },
        {
          "id": 9,
          "title": "[05/27 한국 채용] 인천 전자공장 품질 검사원 모집",
          "source": "펀한채용",
          "views": 1980,
          "time": "6시간 전",
          "img": "/static/img/index/international/2.jpg"
        },
        {
          "id": 10,
          "title": "한국 정부, 여름방학 유학생 입국 규정 변경... 전자여행허가 사전 신청 필수",
          "source": "한국교육부",
          "views": 4120,
          "time": "9시간 전",
          "img": "/static/img/index/international/1.jpg"
        }
]
    };
  },
  methods: {
    // 格式化阅读量显示
    formatViews(views) {
      return views >= 10000 ? (views / 10000).toFixed(1) + '만' : views;
    },
    
    // 跳转到详情页
    goToDetail(item) {
      uni.navigateTo({
        url: `/pages/study/study-detail?id=${item.id}&title=${encodeURIComponent(item.title)}`
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
</style>