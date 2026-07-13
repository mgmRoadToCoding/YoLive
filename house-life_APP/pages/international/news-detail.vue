<template>
  <view class="container">
    <!-- 顶部导航栏 -->
    <u-navbar :is-back="true" title="News Details"></u-navbar>

    <!-- 加载中 -->
    <view v-if="loading" class="loading">loading...</view>

    <!-- 正文内容 -->
    <view v-else-if="news" class="news-detail">
      <!-- 标题 -->
      <text class="title">{{ news.title }}</text>

      <!-- 元信息 -->
      <view class="meta-info">
        <text class="source">{{ news.source }}</text>
        <text class="views">{{ formatViews(news.views) }}조회</text>
        <text class="time">{{ news.time }}</text>
      </view>

      <!-- 图片 -->
      <image
        class="detail-img"
        :src="news.img"
        mode="aspectFill"
      ></image>

      <!-- 正文内容 -->
      <view class="content">
        <text>{{ news.content }}</text>
      </view>
    </view>

    <!-- 加载失败 -->
    <view v-else class="empty">Failed to load or the content was not found.</view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      news: null,
      loading: true,
      newsList: [
       {
         id: 1,
         title: '2025년 한국 유학 지원 일정 발표, 너무 빠르지도 늦지도 않게!',
         source: '한국유학가이드',
         views: 8654,
         time: '2시간 전',
         img: '/static/img/index/international/1.jpg',
         content:
           '2025년 한국 대학교 지원 일정이 공식 발표되었습니다. 한국 유학을 준비하는 학생들에게는 각 단계의 일정 파악이 매우 중요합니다.\n\n' +
           '일반적으로 가을학기 입학은 전년도 12월부터 시작되어 다음 해 3월에 마감됩니다. 봄학기 입학은 대개 6월에 시작되어 9월경에 마감됩니다. 각 대학마다 차이가 있으므로 학교 홈페이지에서 사전에 확인하는 것이 좋습니다.\n\n' +
           '학생들은 미리 TOPIK 성적, 추천서, 자기소개서 등의 지원 서류를 준비하고, 각 대학의 오픈 캠퍼스나 면접 일정을 확인하여 가능한 한 빨리 서류를 제출하는 것이 합격 가능성을 높일 수 있습니다.'
       },
       {
         id: 2,
         title: '유학생을 위한 주택 임대 가이드: 서울 vs 부산 인기 지역 비교',
         source: '한국유학도우미',
         views: 10400,
         time: '4시간 전',
         img: '/static/img/index/international/2.jpg',
         content:
           '한국에서 집을 구할 때 위치 선정은 생활의 질과 예산에 큰 영향을 미칩니다. 서울은 전반적으로 집값이 높으며, 특히 강남, 신촌, 홍대 등 유학생 밀집 지역은 더욱 비쌉니다.\n\n' +
           '반면, 부산은 임대 비용이 더 저렴하고 생활 속도도 느린 편으로, 예산이 적거나 바닷가 도시를 선호하는 학생들에게 적합합니다.\n\n' +
           '선택 시 통학 편의성, 안전성, 생활 인프라, 부동산 중개소의 평판 등을 고려해야 하며, 공신력 있는 플랫폼이나 학교 추천 경로를 통해 집을 구하는 것이 좋습니다.'
       },
       {
         id: 3,
         title: '2025년 한국 대학 TOP10 최신 순위 발표! 당신의 목표 학교는 몇 위?',
         source: '펀한뉴스',
         views: 20987,
         time: '6시간 전',
         img: '/static/img/index/international/3.jpg',
         content:
           '2025년 한국 대학 최신 순위가 발표되어 유학생 및 학부모들의 큰 관심을 끌고 있습니다. 서울대학교는 여전히 1위를 차지했고, 고려대학교와 연세대학교가 TOP3를 유지했습니다.\n\n' +
           '예년과 비교해 이공계 대학인 성균관대, 한양대 등의 순위가 꾸준히 상승하고 있으며, 특히 AI, 반도체 분야에서 두각을 나타내고 있습니다.\n\n' +
           '이번 순위는 교육의 질, 국제적 영향력, 취업률 등 다양한 기준을 참고하였습니다. 명문대 지원을 원하는 학생들은 전공에 따라 선택하고, QS, THE 등의 국제 순위도 함께 참고하는 것이 좋습니다.'
       },
       {
         id: 4,
         title: '2025년 봄학기 모집 정보 총정리: 연세·고려·성균관대 등 대학별 안내',
         source: '유학통',
         views: 3150,
         time: '8시간 전',
         img: '/static/img/index/international/4.jpg',
         content:
           '2025년 봄학기 모집 정보가 공개되었으며, 연세대, 고려대, 성균관대 등 많은 한국 명문대학들이 모집에 참여합니다.\n\n' +
           '봄학기 입학의 장점은 경쟁이 상대적으로 덜하고 합격 확률이 높다는 점이 있지만, 일부 전공은 개설되지 않을 수 있습니다.\n\n' +
           '지원자는 서류 제출 기한, 언어 성적 요건, 장학금 제공 여부 등을 주의 깊게 확인해야 하며, 일부 학교는 이미 온라인 접수를 시작했으니 미리 준비하는 것이 좋습니다.'
       },
       {
         id: 5,
         title: '【꿀팁】한국 유학 장학금 완전 분석! 합격률 높이는 비결은?',
         source: '한국유학전략',
         views: 7850,
         time: '1일 전',
         img: '/static/img/index/international/5.jpg',
         content:
           '한국 대학 및 정부는 유학생들을 위해 GKS, 대학 내 장학금, 기업 후원 등 다양한 장학 제도를 운영하고 있습니다.\n\n' +
           '장학금 합격률을 높이기 위한 핵심은 언어 성적(TOPIK 또는 영어), 학점(GPA), 자기소개서 및 추천서의 완성도입니다.\n\n' +
           '지원 전 각 장학금의 조건과 절차를 충분히 이해하고, 미리 준비하여 기한 내 제출하는 것이 중요합니다. 일부 장학금은 면접이나 추가 서류를 요구하기도 합니다.'
       },
       {
         id: 6,
         title: '한국 유학 비자 D-2 신청 가이드 및 유의사항 정리',
         source: '한국유학전략',
         views: 6210,
         time: '1일 전',
         img: '/static/img/index/international/6.jpg',
         content:
           'D-2 비자는 유학생이 한국 대학 또는 대학원에 진학하기 위해 필수적으로 신청해야 하는 비자입니다. 신청자는 입학허가서, 등록금 납입 증명서, 언어 성적표, 재정 증명서 등의 서류를 제출해야 합니다.\n\n' +
           '일부 대사관은 직접 인터뷰를 요구하며, 서류가 불충분하거나 재정 증명이 미흡할 경우 비자가 거절될 수 있습니다.\n\n' +
           '신청자는 최소 3개월 전부터 준비를 시작하고, 서류의 진실성 및 번역 요건을 반드시 충족시켜야 합니다. 비자를 받은 후에는 입국 후 체류증 신청 절차도 알아두는 것이 중요합니다.'
       },
       {
         id: 7,
         title: '유학생의 언어 장벽 극복법? TOPIK 준비 꿀팁 공유!',
         source: '한국어코너',
         views: 5400,
         time: '2일 전',
         img: '/static/img/index/international/7.jpg',
         content:
           'TOPIK 시험은 한국 대학이 유학생을 평가할 때 중요한 기준 중 하나입니다. 준비는 단계별로 진행하는 것이 좋습니다. 초급자는 어휘와 문법에 집중하고, 중급 이상은 듣기, 쓰기, 읽기 능력을 강화해야 합니다.\n\n' +
           '시험 전에는 기출문제를 활용한 실전 연습이 효과적이며, 쓰기 연습 시 문장의 구조와 어휘 사용에 유의해야 합니다.\n\n' +
           '많은 한국어 교육 기관에서는 시험 전 집중반을 운영하고 있으므로, 자신의 수준과 목표 대학의 요구 조건을 고려한 학습 계획을 세우는 것이 좋습니다.'
       },
       {
         id: 8,
         title: '신입생 입국 후 필수 절차 총정리: 입국, 숙소, 건강검진, 은행계좌까지',
         source: '한국생활통',
         views: 3980,
         time: '2일 전',
         img: '/static/img/index/international/8.jpg',
         content:
           '한국에 입국한 유학생 신입생은 공항 픽업, 기숙사 입주, 건강검진, 체류증 신청, 은행 계좌 개설 등 일련의 절차를 완료해야 합니다.\n\n' +
           '학교마다 절차에 약간의 차이가 있으므로, 국제교류처와 사전에 구체적인 일정을 확인하는 것이 좋습니다.\n\n' +
           '여권 사본, 입학 허가서, 사진 등의 서류를 미리 준비하면 진행 속도가 빨라지며, 개강 전 모든 절차를 완료하는 것이 수업에 차질이 없도록 하는 데 중요합니다.'
       },
       {
         id: 9,
         title: '한국 문화 차이 정리: 반드시 알아야 할 금기사항들',
         source: '한국문화정보',
         views: 7230,
         time: '3일 전',
         img: '/static/img/index/international/9.jpg',
         content:
           '한국에서 생활할 때 문화 차이를 이해하는 것은 잘 적응하는 데 도움이 됩니다. 대표적인 금기사항으로는 한 손으로 물건을 주거나 받는 것, 밥에 젓가락을 꽂는 행동, 공공장소에서 큰 소리로 떠드는 것 등이 있습니다.\n\n' +
           '어른과 대화할 때는 경어를 사용해야 하며, 실내에서는 신발을 벗고 들어가는 것이 일반적입니다. 지나치게 자유로운 행동은 삼가야 합니다.\n\n' +
           '현지 문화를 존중하는 태도는 예의일 뿐만 아니라, 원활한 인간관계를 형성하는 데도 큰 도움이 됩니다.'
       },
       {
         id: 10,
         title: '유학생 아르바이트 꿀팁: 합법적인 업무 유형과 시급 기준은?',
         source: '유학통',
         views: 8120,
         img: '/static/img/index/international/10.jpg',
         content:
           '한국에서 유학생은 합법적으로 아르바이트를 할 수 있으며, 반드시 아르바이트 허가서(시간제 확인서)를 받아야 합니다. 대표적인 일자리로는 카페, 편의점, 과외, 번역 등이 있습니다.\n\n' +
           '2025년부터 한국의 최저 시급은 9860원이며, 일부 직종은 경력과 언어 능력에 따라 더 높은 급여를 받을 수 있습니다.\n\n' +
           '학생들은 학업과 아르바이트의 균형을 잘 맞추어야 하며, 불법 노동에 연루되지 않도록 주의해야 합니다.'
       }
      ]
    };
  },
  onLoad(options) {
    const id = parseInt(options.id);
    const found = this.newsList.find(item => item.id === id);
    if (found) {
      this.news = found;
    }
    this.loading = false;
  },
  methods: {
    formatViews(views) {
      return views >= 10000 ? (views / 10000).toFixed(1) + '만' : views;
    }
  }
};
</script>

<style lang="scss">
.container {
  padding: 20rpx;
  background-color: #f9f9f9;
  min-height: 100vh;
}

.loading,
.empty {
  text-align: center;
  color: #888;
  font-size: 28rpx;
  margin-top: 200rpx;
}

.news-detail {
  background-color: #fff;
  padding: 30rpx;
  border-radius: 16rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.title {
  font-size: 36rpx;
  font-weight: bold;
  color: #222;
  margin-bottom: 20rpx;
  line-height: 1.4;
}

.meta-info {
  font-size: 24rpx;
  color: #888;
  margin-bottom: 30rpx;
  display: flex;
  gap: 24rpx;
}

.detail-img {
  width: 100%;
  height: 320rpx;
  border-radius: 12rpx;
  background-color: #eee;
  margin-bottom: 30rpx;
}

.content {
  font-size: 30rpx;
  color: #444;
  line-height: 1.8;
}
</style>