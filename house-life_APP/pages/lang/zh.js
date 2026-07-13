export default {
	language: '语言',
	selectLanguage: '选择语言',
	aboutMe: '关于我',
	nickname: '昵称',
	viewHistory: '浏览记录',
	myFavorites: '我的收藏',
	myPosts: '我的发布',
	forum: '论坛',
	morePosts: '更多帖子',
	myRequests: '我的委托',
	feedback: '问题反馈',
	publishForum: '发布帖子',
	myPage: '个人中心',
	technicalSupport: '技术支持',
	faq: '常见问题',
	aboutUs: '关于我们',

	// forum detail page i18n
	postDetail: '帖子详情',
	favorite: '收藏',
	unfavorite: '取消收藏',
	comment: '评论',
	submit: '提交',
	share: '分享',
	commentPlaceholder: '请输入评论...',

	// index.vue 新增
	todayRecommend: "今日推荐",
	findRoom: "找房",
	studentJobs: "客服",
	studyKorea: "论坛",
	swiperTitle1: "身无彩凤双飞翼",
	swiperTitle2: "心有灵犀一点通",

	// 新闻内容
	news: {
		1: {
			title: "4月首尔房价涨幅缩小至0.25%...观望情绪扩大",
			brief: "上个月首尔及首都圈公寓购买趋势放缓，涨幅明显缩小。随着土地交易许可区域的扩大指定以及对利率和政策变量的不确定性增加，整个市场的观望气氛浓厚。"
		},
		2: {
			title: "留学生租房指南：如何避开非法中介",
			brief: "初到韩国的留学生面临诈骗风险。签约前务必核实中介资质和房源信息。"
		},
		3: {
			title: "分享租房诈骗案例：留学生注意事项",
			brief: "通过实际案例警示租房诈骗，提供签约时的注意事项指导。"
		},
		4: {
			title: "租房诈骗警示：警惕通过SNS的非法中介",
			brief: "非法中介在群聊中发布虚假房源，建议使用正规平台。"
		},
		5: {
			title: "韩国租房类型整理：全租、月租、短租比较",
			brief: "分析三种租赁方式，根据预算提供选择建议。"
		}
	},

	//MapSelection部分
	location: {
		selectPrompt: "点击选择您的位置",
		options: {
			gps: "GPS定位",
			gyeongsan: "全部地区",
			daegu: "大邱市",
			ulsan: "蔚山市"
		}
	},

	//houserTemplate
	search: {
		placeholder: '请输入小区或商圈名称'
	},
	filter: {
		location: '位置',
		price: '价格',
		room: '户型',
		locationOptions: {
			all: '全部区域',
			chaoyang: '朝阳区',
			haidian: '海淀区',
			dongcheng: '东城区',
			xicheng: '西城区'
		},
		priceOptions: {
			unlimited: '不限价格',
			under1000: '1000以下',
			'1000to2000': '1000-2000',
			'2000to3000': '2000-3000',
			over3000: '3000以上'
		},
		roomOptions: {
			unlimited: '不限户型',
			'1room': '1室',
			'2rooms': '2室',
			'3rooms': '3室',
			'4roomsUp': '4室及以上'
		}
	},
	house: {
		whole: '整租',
		shared: '合租',
		master: '主卧',
		secondary: '次卧',
		unknown: '未知',
		hardcover: '精装修',
		simple: '简装修',
		rough: '毛坯'
	},

	//MapHouse
	mapHouse: {
		title: "地图找房",
		searchPlaceholder: "搜索地点或地址",
		houseTypes: ["一居室", "两居室", "三居室", "公寓", "别墅"],
		addHouseTitle: "添加房源信息",
		houseNameLabel: "房源名称:",
		houseTypeLabel: "房源类型:",
		rentLabel: "月租金:",
		locationLabel: "坐标位置:",
		confirmBtn: "确认添加",
		latitudePlaceholder: "纬度",
		longitudePlaceholder: "经度",
		backText: "返回",
		addText: "添加",
		mainText: "主",
		secondaryText: "次",
		completeInfo: "请填写完整信息",
		addSuccess: "添加成功",
		locationNotSupported: "浏览器不支持定位",
		defaultLocation: "默认位置"
	},

	//detail部分
	houseDetail: {
		title: "房源详情",
		wholeRent: "整租",
		sharedRent: "合租",
		masterBedroom: "主卧",
		secondaryBedroom: "次卧",
		// unknown: "未知",
		month: "月",
		houseIntro: "房源介绍",
		communityIntro: "小区介绍",
		buildingYear: "建筑年代",
		buildingType: "建筑类型",
		greeningRate: "绿化率",
		straightDistance: "直线距离",
		referenceOnly: "仅供参考",
		serviceFee: "服务费另计",
		onlineConsult: "在线咨询",
		serviceIntro: "服务介绍",
		houseEvaluation: "房源评价",
		agent: "经纪人",
		companyService: "开源字节为您服务",
		navigation: "导航",
		favorite:"收藏",
		share:"分享",
		contactAgent:"中介",
		moreEvaluation:" 查看更多的评价"
	},
	common: {
		back: "返回"
	},
	serviceItems: {
		serviceIntro:"服务",
		promise: "承诺-四大租住承诺，安心有保障",
		quality: "品质-两类品质保障，入住更无忧",
		service: "服务-两项日常服务，生活超便捷",
		payment: "付款-多种付款方式，支付更灵活"
	},

	//houseDetailTemplate
	month: '月',
	priceInfo: '价格信息',
	paymentMethod: '支付方式',
	price: '价格',
	deposit: '保证金',
	fee: '手续费',
	recommendHouses: '推荐房源',

	// House specific
	house1Title: '朝阳区精装一居',
	house1Desc: '朝阳区精装一居室，交通便利，周边设施齐全',
	house1Type1: '1室1厅',
	house1Type2: '45㎡',
	house1Type3: '朝南',

	house2Title: '海淀区温馨两居',
	house2Desc: '海淀区温馨两居室，近中关村，学区房',
	house2Type1: '2室1厅',
	house2Type2: '65㎡',
	house2Type3: '南北通透',

	house3Title: '东城区豪华三居',
	house3Desc: '东城区豪华三居室，王府井商圈，高档小区',
	house3Type1: '3室2厅',
	house3Type2: '120㎡',
	house3Type3: '东南朝向',

	// Payment methods
	wechatPay: '微信支付',
	alipay: '支付宝',
	bankTransfer: '银行转账',

	// Messages
	houseNotFound: '未找到该房源信息',
	
	"contactAgent": "联系中介",
	"agentPhone": "电话",
	"agentPhoneTitle": "联系方式",
	"confirm": "确定"

}