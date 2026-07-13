export default {
	language: 'Language',
	selectLanguage: 'Select Language',
	aboutMe: 'About Me',
	nickname: 'Nickname',
	viewHistory: 'View History',
	myFavorites: 'My Favorites',
	myPosts: 'My Posts',
	forum: 'Forum',
	morePosts: 'More Posts',
	myRequests: 'My Requests',
	feedback: 'Feedback',
	publishForum: 'Publish Post',
	myPage: 'My Page',
	technicalSupport: 'Technical Support',
	faq: 'FAQ',
	aboutUs: 'About Us',

	// forum detail page i18n
	postDetail: 'Post Detail',
	favorite: 'Favorite',
	unfavorite: 'Unfavorite',
	comment: 'Comment',
	submit: 'Submit',
	share: 'Share',
	commentPlaceholder: 'Please enter your comment...',

	// index.vue 新增
	todayRecommend: "Today's Recommendation",
	findRoom: "Find Room",
	studentJobs: "Support",
	studyKorea: "Community",
	swiperTitle1: "Birds of a feather flock together",
	swiperTitle2: "Like-minded people understand each other",

	// 推荐部分的新闻内容
	news: {
		1: {
			title: "Seoul housing prices rose by 0.25% in April...wait-and-see sentiment expands",
			brief: "Last month, the buying trend for apartments in Seoul and the metropolitan area slowed down, with the rate of increase noticeably decreasing. With the expansion of designated land transaction permit zones and increasing uncertainty about interest rates and policy variables, a wait-and-see atmosphere has thickened throughout the market.",
		},
		2: {
			title: "Rental guide for international students: How to avoid illegal brokers",
			brief: "Scam risks for new international students in Korea. Be sure to verify broker qualifications and property details before signing a contract."
		},
		3: {
			title: "Sharing rental scam cases: Precautions for international students",
			brief: "Warning about rental scams through actual cases. Guidance on precautions when signing contracts."
		},
		4: {
			title: "Rental scam alert: Beware of illegal brokers through SNS",
			brief: "Illegal brokers post fake listings in group chat rooms. Use of authorized platforms is recommended."
		},
		5: {
			title: "Overview of rental types in Korea: Comparison of jeonse, monthly rent, and short-term rentals",
			brief: "Analysis of three rental methods. Suggestions for choosing according to your budget."
		}
	},

	//推荐部分的详细新闻

	//MapSelection部分
	location: {
		selectPrompt: "Click to select your location",
		options: {
			gps: "GPS Location",
			gyeongsan: "All City",
			daegu: "Daegu City",
			ulsan: "Ulsan City"
		}
	},

	//houserTemplate部分
	search: {
		placeholder: 'Enter community or business district name'
	},
	filter: {
		location: 'Location',
		price: 'Price',
		room: 'Type',
		locationOptions: {
			all: 'All Areas',
			chaoyang: 'Gyeongsan',
			haidian: 'Daegu',
			dongcheng: 'Ulsan',
			xicheng: 'Other'
		},
		priceOptions: {
			unlimited: 'Any Price',
			under200000: 'Under ₩200,000',
			'200000to500000': '₩200,000 - ₩500,000',
			'500000to1000000': '₩500,000 - ₩1,000,000',
			over1000000: 'Over ₩1,000,000'
		},
		roomOptions: {
			unlimited: 'Any Type',
			'1room': '1 Bedroom',
			'2rooms': '2 Bedrooms',
			'3rooms': '3 Bedrooms',
			'4roomsUp': '4+ Bedrooms'
		}
	},
	house: {
		whole: 'Whole Rent',
		shared: 'Shared Rent',
		master: 'Master Bedroom',
		secondary: 'Secondary Bedroom',
		// unknown: 'Unknown',
		hardcover: 'Hardcover',
		simple: 'Simple Decoration',
		rough: 'Rough House'
	},
	//mapHouse
	mapHouse: {
		title: "Map Housing",
		searchPlaceholder: "Search location or address",
		houseTypes: ["One Room", "Two Room", "Three Room", "Apartment", "Villa"],
		addHouseTitle: "Add Housing Information",
		houseNameLabel: "House Name:",
		houseTypeLabel: "House Type:",
		rentLabel: "Monthly Rent:",
		locationLabel: "Location:",
		confirmBtn: "Confirm",
		latitudePlaceholder: "Latitude",
		longitudePlaceholder: "Longitude",
		backText: "Back",
		addText: "Add",
		mainText: "Main",
		secondaryText: "Secondary",
		completeInfo: "Please complete all information",
		addSuccess: "Added successfully",
		locationNotSupported: "Location not supported",
		defaultLocation: "Default location"
	},
	//detail部分
	houseDetail: {
		title: "House Details",
		wholeRent: "Whole Rent",
		sharedRent: "Shared Rent",
		masterBedroom: "Master Bedroom",
		secondaryBedroom: "Secondary Bedroom",
		unknown: "Unknown",
		month: "month",
		houseIntro: "House Introduction",
		communityIntro: "Community Introduction",
		buildingYear: "Building Year",
		buildingType: "Building Type",
		greeningRate: "Greening Rate",
		straightDistance: "Straight Distance",
		referenceOnly: "For reference only",
		serviceFee: "Service fee additional",
		onlineConsult: "Online Consultation",
		serviceIntro: "Service Introduction",
		houseEvaluation: "House Evaluation",
		agent: "Agent",
		companyService: "YoLive Serves You",
		navigation: "Navigation",
		favorite: "Favorite",
		share: "Share",
		contactAgent: "Agent",
		moreEvaluation: " More Evaluation"
	},
	common: {
		back: "Back"
	},
	serviceItems: {
		serviceIntro: "Service",
		promise: "Promise - Four rental commitments for peace of mind",
		quality: "Quality - Two types of quality assurance",
		service: "Service - Two daily services for convenience",
		payment: "Payment - Multiple payment methods"
	},

	//houseDetailTemplate
	month: 'month',
	priceInfo: 'Price Information',
	paymentMethod: 'Payment Method',
	price: 'Price',
	deposit: 'Deposit',
	fee: 'Fee',
	recommendHouses: 'Recommended Houses',

	// House specific
	house1Title: 'Hanbit One-room',
	house1Desc: 'Sunny one-room apartment in Chaoyang District, convenient transportation, complete surrounding facilities',
	house1Type1: '1 Bedroom',
	house1Type2: '45㎡',
	house1Type3: 'South Facing',

	house2Title: 'Haidian Cozy Two-bedroom',
	house2Desc: 'Cozy two-bedroom apartment in Haidian District, near Zhongguancun, school district',
	house2Type1: '2 Bedrooms',
	house2Type2: '65㎡',
	house2Type3: 'North-South Through',

	house3Title: 'Dongcheng Luxury Three-bedroom',
	house3Desc: 'Luxury three-bedroom apartment in Dongcheng District, Wangfujing business district, high-end community',
	house3Type1: '3 Bedrooms',
	house3Type2: '120㎡',
	house3Type3: 'Southeast Facing',

	// Payment methods
	wechatPay: 'WeChat Pay',
	alipay: 'Alipay',
	bankTransfer: 'Bank Transfer',

	// Messages
	houseNotFound: 'House information not found',

	//
	"contactAgent": "Contact Agent",
	"agentPhone": "Agent Phone Number",
	"confirm": "Confirm"



}
