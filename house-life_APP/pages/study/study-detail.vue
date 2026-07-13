<template>
	<view class="container">
		<!-- 顶部导航栏 -->
		<u-navbar :is-back="true" title="Information Details"></u-navbar>

		<!-- 加载中 -->
		<view v-if="loading" class="loading">
			loading...
		</view>

		<!-- 详情内容 -->
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
			<image class="detail-img" :src="news.img" mode="aspectFill"></image>

			<!-- 正文内容 -->
			<view class="content">
				<text>{{ news.content }}</text>
			</view>

			<!-- ✅ 底部小动物图标装饰：移进来 -->
			<view class="animal-icons">
				<view class="animal-icon">🦊</view>
				<view class="animal-icon">🐼</view>
				<view class="animal-icon">🐧</view>
				<view class="animal-icon">🐤</view>
				<view class="animal-icon">🐸</view>
				<view class="animal-icon">🐶</view>
			</view>
		</view>

		<!-- 加载失败 -->
		<view v-else class="empty">
			<text>Failed to load or the content was not found.</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				news: null,
				loading: true
			};
		},
		// onLoad(options) {
		//   const id = options.id;
		//   if (!id) {
		//     this.loading = false;
		//     return;
		//   }

		//   uni.request({
		//     url: `https://your-api.com/news/detail`,
		//     method: 'GET',
		//     data: { id },
		//     success: res => {
		//       if (res.statusCode === 200 && res.data) {
		//         this.news = res.data;
		//       } else {
		//         console.error('接口返回异常', res);
		//       }
		//     },
		//     fail: err => {
		//       console.error('请求失败', err);
		//     },
		//     complete: () => {
		//       this.loading = false;
		//     }
		//   });
		// }
		onLoad(options) {
			const id = parseInt(options.id);
			this.loading = true;

			// 本地模拟的数据
			const newsList = [{
					id: 1,
					title: "한국 경제, '고용 호조 속 저성장' 딜레마에 빠지다",
					source: "펀한 정보",
					views: 8175,
					time: "4시간 전",
					img: "/static/img/index/international/10.jpg",
					content: `현재 한국은 고용률은 높은 반면 경제 성장률이 정체되는 새로운 경제 패턴에 직면해 있다. 실업률은 계속 하락하고 있으며, 청년층과 중장년층 모두 고용 참여율이 상승하고 있다.
      
  하지만 기업의 투자 의욕 저하, 소비 심리 위축, 수출 중심 산업의 글로벌 경기 영향 등으로 인해 GDP 성장률이 지속적으로 저조하다. 전문가들은 서비스업 중심의 고용 증가가 부가가치가 낮은 분야에 집중되어 있어 경제 구조 개선에 효과적으로 기여하지 못하고 있다고 지적한다.
  
  정부는 인공지능, 친환경 에너지 등 고부가가치 산업에 자원을 집중 투자하여 성장의 질을 개선해야 할 것으로 보인다.`
				},
				{
					id: 2,
					title: "서울 버스 파업, 최대 3일간 운행 중단 우려… 시장 '총력 대응' 지시",
					source: "펀한 정보",
					views: 11000,
					time: "8시간 전",
					img: "/static/img/index/international/9.jpg",
					content: `6월 초, 서울시 버스노조가 임금 협상 결렬과 근무시간 개편 문제에 항의하며 파업을 시작했다. 이번 파업은 전체 노선의 70% 이상에 영향을 미치며 약 천만 명의 시민들의 출퇴근에 큰 불편을 줄 것으로 예상된다.
  
  서울시는 긴급 대책 회의를 소집하고, 오세훈 시장은 임시 차량 투입, 지하철 운행 연장, 시민들에게 출퇴근 시간 조정 요청 등 대응책을 지시했다. 시민 반응은 엇갈리며, 일부는 노동자 권리를 지지하는 반면, 공공서비스를 협상의 도구로 삼는 것에 비판을 제기하고 있다.
  
  협상이 계속 진행 중이며, 진전이 없을 경우 교통 대란으로 이어질 수 있다.`
				},
				{
					id: 3,
					title: "초등학생이 교사를 폭행… 교육계 '충격'",
					source: "하얀 구름맛 솜사탕...",
					views: 21,
					time: "6분 전",
					img: "/static/img/index/international/8.jpg",
					content: `최근 충청도 한 초등학교에서 학생이 수업 중 교사를 폭행하는 사건이 발생해 사회적 파장을 일으켰다. 교사의 수업 규율 지도에 불만을 품은 학생이 감정적으로 격해져 교사를 밀치고 폭행하여 교사가 경상을 입고 병원에 이송됐다.
  
  학교는 긴급 대응 조치를 시행해 심리 상담을 제공하고 학생 수업을 중단시켰다. 교육부는 본 사건을 중대하게 보고 원인 조사를 실시하며, 각급 학교에 안전관리와 학생 정신건강 교육 강화를 지시했다.
  
  사회 여론은 교사에 대한 존중과 보호를 제도적으로 강화해야 한다는 목소리를 높이고 있다.`
				},
				{
					id: 4,
					title: "[05/27 중국 채용공고] 상하이 구매 담당자(한국어 가능자)",
					source: "펀한 위챗 공식계정",
					views: 2347,
					time: "1시간 전",
					img: "/static/img/index/international/7.jpg",
					content: `상하이 도심에 위치한 한국계 기업에서 한국어 가능한 구매 담당자를 모집 중이다. 주요 업무는 원자재 공급망 관리, 한국 본사와의 커뮤니케이션, 계약 검토 및 구매 진행 상황 관리 등이다.
  
  한국어 능통자 및 관련 업무 경험자 우대, 커뮤니케이션 및 협상 능력이 요구된다. 근무 시간은 오전 9시부터 오후 6시까지이며, 주말 휴무, 4대 보험 및 연말 보너스 제공.
  
  중한 무역 분야에 관심 있는 신입 및 경력직 모두 지원 가능하며, 채용 기간은 약 2주로 예상된다.`
				},
				{
					id: 5,
					title: "[05/27 한국 채용공고] 안산 중앙동 미쉐 직원 모집",
					source: "펀한 정보",
					views: 1500,
					time: "2시간 전",
					img: "/static/img/index/international/6.jpg",
					content: `안산 중앙동의 음료 프랜차이즈 매장에서 파트타임 및 풀타임 직원을 모집 중이다. 업무는 주문 접수, 음료 제조, 계산 및 고객 응대 등이다.
  
  경력 무관, 회사에서 체계적인 교육 제공. 근무 시간은 유동적으로 조정 가능하며, 시급은 한국 최저임금 기준. 성실히 근무 시 점장으로 승진 가능.
  
  매장은 교통이 편리한 위치에 있으며, 근무 환경이 쾌적하고 분위기가 좋다. 유학생이나 단기 취업 희망자에게 적합하며, 신분증 지참 후 매장 방문 면접 가능. 모집 마감은 이번 달 말까지이다.`
				},
				{
					id: 6,
					title: "한국 출산율 또다시 최저치 경신… 정부, 육아지원 확대 검토",
					source: "연합뉴스",
					views: 9542,
					time: "3시간 전",
					img: "/static/img/index/international/5.jpg",
					content: `통계청 최신 자료에 따르면 2024년 출산율이 다시 역대 최저치를 기록하며 여성 1인당 평균 출생아 수가 0.7명 이하로 떨어졌다. 고령화로 인한 사회·경제적 영향을 막기 위해 정부는 육아 지원을 대폭 강화할 계획이다.
  
  신생아 1명당 현금 지원 확대, 국공립 보육시설 확충, 다자녀 가정 세금 감면 등의 방안이 검토되고 있다. 재정 부담은 크지만, 전문가들은 강력한 조치 없이는 노동력 부족과 고령화 부담이라는 이중 위기를 피할 수 없다고 경고한다.
  
  육아 및 근무 환경 전반의 개선이 시급하다고 강조한다.`
				},
				{
					id: 7,
					title: "한중 무역 감소… 전문가들 '기술 협력 강화' 제안",
					source: "펀한 정보",
					views: 6700,
					time: "5시간 전",
					img: "/static/img/index/international/4.jpg",
					content: `한국과 중국 간 무역 규모가 최근 감소세를 보이고 있다. 이에 대해 전문가들은 기술 협력 강화를 통해 양국 경제 협력의 질적 전환이 필요하다고 지적한다.
  
  특히 반도체, 친환경 기술, 인공지능 분야에서 공동 연구 및 인재 교류를 확대해야 한다는 의견이 제기된다. 또한 외교적 안정성과 공급망 재편 속에서 상호 의존을 줄이되, 전략적 협력은 지속 유지해야 한다는 목소리도 높다.`
				},
				{
					id: 8,
					title: "한국 대학 졸업생 취업률 회복… IT 분야 수요 급증",
					source: "한국경제신문",
					views: 8123,
					time: "7시간 전",
					img: "/static/img/index/international/3.jpg",
					content: `교육부가 최근 발표한 보고서에 따르면 2024년 대학 졸업생의 전체 취업률이 최근 5년 내 최고치를 기록했다. 특히 정보기술, 반도체, 핀테크 등 신산업 분야에서 채용 증가세가 두드러졌다.
  
  서울대, 고려대 등 주요 대학 졸업생들은 대기업, 국책 연구소, 다국적 기업에 주로 취업하고 있으며, 재택근무와 유연 근무제 확산으로 중소기업의 매력도도 상승하고 있다.
  
  정부는 청년 취업 지원금 정책을 확대하고, 대학과 산업체 간 실습 프로그램을 강화하여 졸업생의 실무 역량을 높일 계획이다.`
				},
				{
					id: 9,
					title: "[05/27 한국 채용공고] 인천 전자공장 품질검사원 모집",
					source: "펀한 채용",
					views: 1980,
					time: "6시간 전",
					img: "/static/img/index/international/2.jpg",
					content: `인천 송도 산업단지에 위치한 한국계 전자제조업체에서 품질검사원 2명을 모집한다. 업무는 제품 외관 및 기능 검사, 생산 데이터 기록, 이상 발생 시 지원 등이다.
  
  꼼꼼하고 책임감 있는 인재를 선호하며, 제조업 경력자 및 F-4 비자 또는 합법적 취업 자격 보유자 우대. 근무 환경은 쾌적하고 중앙 냉난방 및 숙소 제공.
  
  2교대 근무제 시행, 월급은 250만 원부터 시작. 식사 제공, 교통비 지원, 연말 보너스 등 복지 제공. 장기 안정적 근무 희망자는 즉시 면접 및 입사 가능.`
				},
				{
					id: 10,
					title: "한국 정부, 여름방학 유학생 입국 신규 지침 발표… K-ETA 사전 신청 의무화",
					source: "교육부",
					views: 4120,
					time: "9시간 전",
					img: "/static/img/index/international/1.jpg",
					content: `외국인 입국 관리를 강화하기 위해 법무부는 2025년 여름방학부터 단기 비자를 소지하고 한국에 학습이나 프로그램 참가를 목적으로 입국하는 유학생은 'K-ETA 전자여행허가'를 사전에 온라인으로 신청해야 한다고 발표했다.
  
  이 정책은 30일 이내의 단기 연수, 썸머 캠프, 언어 및 문화 교류 활동 참여자를 대상으로 하며, 신청 시 수업 일정, 숙소 정보 등의 자료 제출이 필요하다. 심사 기간은 약 72시간이다.
  
  교육부는 각 대학에 국제학생들에게 조기 안내를 요청했으며, 서류 미비로 인한 입국 차질을 예방하기 위한 조치라고 밝혔다. 본 지침은 국경 관리 효율성을 높이고, 유학생 입국 서비스 체계의 개선을 도모할 것으로 기대된다.`
				}
			];

			const found = newsList.find(item => item.id === id);
			if (found) {
				this.news = found;
			}
			this.loading = false;
		},
		methods: {
			formatViews(views) {
				return views >= 10000 ? (views / 10000).toFixed(1) + '万' : views;
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
		font-weight: 600;
		color: #333;
		/* 原本是 #222，更温和 */
		margin-bottom: 20rpx;
		line-height: 1.5;
		letter-spacing: 1rpx;
		/* 字间距更扁平自然 */
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
		color: #555;
		/* 从 #444 调整为更柔和的 #555 */
		line-height: 2;
		letter-spacing: 0.5rpx;
	}

	/* 底部小动物图标样式 */
	.animal-icons {
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
		gap: 24rpx;
		margin-top: 60rpx;
	}

	.animal-icon {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		font-size: 44rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.05);
		background-color: #fffbe6;
		transition: transform 0.3s;
		cursor: default;
	}

	.animal-icon:hover {
		transform: scale(1.1);
		background-color: #fce4ec;
	}

	/* 浮动动画（可选） */
	@keyframes float {

		0%,
		100% {
			transform: translateY(0);
		}

		50% {
			transform: translateY(-6rpx);
		}
	}

	.animal-icon {
		animation: float 2s ease-in-out infinite;
	}
</style>