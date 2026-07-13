export default {
	language: '언어',
	selectLanguage: '언어 선택',
	aboutMe: 'AboutMe',
	nickname: '닉네임',
	viewHistory: '열람 기록',
	myFavorites: '내 즐겨찾기',
	myPosts: '내 게시물',
	forum: '포럼',
	morePosts: '더 많은 게시물',
	myRequests: '내 의뢰',
	feedback: '문제 피드백',
	publishForum: '게시글 작성',
	myPage: '마이페이지',
	technicalSupport: 'Technical Support',
	faq: 'FAQ',
	aboutUs: 'About Us',

	// forum detail page i18n
	postDetail: '게시글 상세',
	favorite: '찜하기',
	unfavorite: '찜 취소',
	comment: '댓글',
	submit: '제출',
	share: '공유',
	commentPlaceholder: '댓글을 입력하세요...',
	// index.vue 新增
	todayRecommend: "오늘 추천",
	findRoom: "방 구하기",
	studentJobs: "고객센터",
	studyKorea: "게시판",
	swiperTitle1: "짝이 없는 봉황의 날개",
	swiperTitle2: "마음이 통하는 사람들",

	// 新闻内容
	news: {
		1: {
			title: "4월 서울 집값 상승폭 0.25%로 줄어…관망 심리 확대",
			brief: "지난달 서울을 비롯한 수도권 아파트 매수세가 둔화되며 상승폭이 눈에 띄게 줄었다. 토지거래허가구역의 확대 지정과 함께 금리 및 정책 변수에 대한 불확실성이 커지면서 시장 전반에 관망 분위기가 짙어진 모습이다."
		},
		2: {
			title: "유학생 임대 가이드: 불법 중개업체 피하는 법",
			brief: "한국에 처음 온 유학생 사기 위험, 계약 전 중개 자격과 매물 확인 필수."
		},
		3: {
			title: "임대 사기 사례 공유: 유학생 주의사항",
			brief: "실제 사례를 통해 임대 사기 경고, 계약 시 주의사항 안내."
		},
		4: {
			title: "임대 사기 경고: SNS 통한 불법 중개 주의",
			brief: "불법 중개인들이 단체 채팅방에 가짜 매물 게시, 공인 플랫폼 이용 권장."
		},
		5: {
			title: "한국 임대 유형 정리: 전세, 월세, 단기임대 비교",
			brief: "세 가지 임대 방식 분석, 예산에 맞는 선택법 제시."
		}
	},

	//MapSelection部分
	location: {
		selectPrompt: "현 위치 클릭하세요",
		options: {
			gps: "GPS 위치",
			gyeongsan: "전체 시",
			daegu: "대구시",
			ulsan: "울산시"
		}
	},

	//houseTemplate
	search: {
		placeholder: '지역 또는 상업 지구 이름을 입력하세요'
	},
	filter: {
		location: '위치',
		price: '가격',
		room: '유형',
		locationOptions: {
			all: '모든 지역',
			chaoyang: '경산',
			haidian: '대구',
			dongcheng: '울산',
			xicheng: '다른'
		},
		priceOptions: {
			unlimited: '가격 제한 없음',
			under200000: '₩200,000 위안 미만',
			'200000to500000': '₩200,000 - ₩500,000 위안',
			'500000to1000000': '₩500,000 - ₩1,000,000 위안',
			over1000000: '₩1,000,000 위안 이상'
		},
		roomOptions: {
			unlimited: '유형 제한 없음',
			'1room': '1 침실',
			'2rooms': '2 침실',
			'3rooms': '3 침실',
			'4roomsUp': '4+ 침실'
		}
	},
	house: {
		whole: '전체 임대',
		shared: '공유 임대',
		master: '마스터 침실',
		secondary: '보조 침실',
		unknown: '알 수 없음',
		hardcover: '고급 인테리어',
		simple: '간단한 인테리어',
		rough: '미장 상태',
		简装: "표준"
	},

	//MapHouse
	mapHouse: {
		title: "지도에서 집 찾기",
		searchPlaceholder: "위치 또는 주소 검색",
		houseTypes: ["원룸", "투룸", "쓰리룸", "아파트", "빌라"],
		addHouseTitle: "주택 정보 추가",
		houseNameLabel: "주택 이름:",
		houseTypeLabel: "주택 유형:",
		rentLabel: "월세:",
		locationLabel: "위치:",
		confirmBtn: "확인",
		latitudePlaceholder: "위도",
		longitudePlaceholder: "경도",
		backText: "뒤로",
		addText: "추가",
		mainText: "메인",
		secondaryText: "보조",
		completeInfo: "모든 정보를 입력해 주세요",
		addSuccess: "성공적으로 추가되었습니다",
		locationNotSupported: "위치 지원 안 됨",
		defaultLocation: "기본 위치"
	},

	//detail部分
	houseDetail: {
		title: "주택 상세 정보",
		wholeRent: "전체 임대",
		sharedRent: "합숙",
		masterBedroom: "마스터 침실",
		secondaryBedroom: "보조 침실",
		// unknown: "알 수 없음",
		month: "월",
		houseIntro: "주택 소개",
		communityIntro: "커뮤니티 소개",
		buildingYear: "건축 연도",
		buildingType: "건물 유형",
		greeningRate: "녹화율",
		straightDistance: "직선 거리",
		referenceOnly: "참고용",
		serviceFee: "서비스 요금 별도",
		onlineConsult: "온라인 상담",
		serviceIntro: "서비스 소개",
		houseEvaluation: "주택 평가",
		agent: "중개인",
		companyService: "YoLive가 서비스합니다",
		navigation: "네비게이션",
		favorite: "찜하기",
		share: "공유",
		contactAgent: "중개자",
		moreEvaluation: " 더 많은 평가 보기"
	},
	common: {
		back: "뒤로"
	},
	serviceItems: {
		serviceIntro: "서비스",
		promise: "약속 - 안심을 위한 4가지 임대 약속",
		quality: "품질 - 두 가지 유형의 품질 보증",
		service: "서비스 - 편의를 위한 두 가지 일상 서비스",
		payment: "결제 - 다양한 결제 방법"
	},

	//houseDetailTemplate
	month: '월',
	priceInfo: '가격 정보',
	paymentMethod: '결제 방식',
	price: '가격',
	deposit: '보증금',
	fee: '수수료',
	recommendHouses: '추천 주택',

	// House specific
	house1Title: '한빛원룸',
	house1Desc: '조양구의 깔끔한 원룸, 교통이 편리하고 주변 시설이 완비됨',
	house1Type1: '1룸 1거실',
	house1Type2: '45㎡',
	house1Type3: '남향',

	house2Title: '힐스테이트',
	house2Desc: '영남대의 아늑한 투룸, 빅마트 근처, 학군 주택',
	house2Type1: '2룸 1거실',
	house2Type2: '65㎡',
	house2Type3: '남북 통풍',

	house3Title: '파스텔',
	house3Desc: '공원 근처, 산책하기 좋은 곳, 고급 아파트',
	house3Type1: '3룸 2거실',
	house3Type2: '120㎡',
	house3Type3: '동남향',

	// Payment methods
	wechatPay: '위챗 페이',
	alipay: '알리페이',
	bankTransfer: '계좌이체',

	// Messages
	houseNotFound: '주택 정보를 찾을 수 없습니다',

	//

	"contactAgent": "중개인에게 문의하기",
	"agentPhone": "중개인 전화번호",
	"confirm": "확인"


}
