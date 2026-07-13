// 全局配置文件
// let baseUrl = 'http://192.168.0.5:8088'; //所有接口请求的基础路径
// let baseUrl = 'http://192.168.0.40';
// // let staticUrl = 'http://192.168.0.5:8088'; //图片静态访问路径
// let staticUrl = 'http://192.168.0.40';

// const isDev = true; // 强制开启开发模式

// let baseUrl = 'http://localhost:8088';  // 本地后端地址（取消注释并修改IP）
// let staticUrl = 'http://localhost:8088'; // 静态资源地址（同步修改）

// // 版本号 用于更新
// let version = 1;
// // vuex_version版本号 用于显示
// let vuex_version = '1.0.' + version;
// // 是否需要热更新（后台自动更新）
// let flag_hot = false;

// export default {
// 	baseUrl: baseUrl,
// 	staticUrl:staticUrl,
// 	version: version,
// 	vuex_version: vuex_version,
// }

//真机情况
//是否为开发者模式
const isDev = true;

// ✅ 你的 Mac 的局域网 IP
const localIP = 'http://localhost:8088'; //本机
// const localIP = 'http://192.168.0.5:8088'; //Homeip
// const localIP = 'http://165.229.125.253:8088'; //IT馆
// const localIP = 'http://165.229.143.169:8088'; //图书馆
// const LAN_IP = 'http://192.168.0.5:8088';

const isRealDevice = true; // ← 设置为 true 表示真机开发

const devConfig = {
	baseUrl: isRealDevice ? localIP : 'http://165.229.229.135:8088',// 安卓模拟器访问本机的特殊地址
	staticUrl: isRealDevice ? localIP : 'http://165.229.229.135:8088',
	version: 1,
	vuex_version: '1.0.1',
	flag_hot: false
};

export default isDev ? devConfig : prodConfig;
