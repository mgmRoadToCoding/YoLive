// 配置后端路径请修改config.js
module.exports = {
	publicPath: './',
	devServer: {
		host: '0.0.0.0', // ← 让手机可以访问
		port: 8088, // ← 开发服务器运行在 8089 端口
		proxy: {
			'/api': {
				target: 'http://localhost:8088',
				// target: 'http://192.168.0.5:8088', //homeip
				// target: 'http://165.229.125.253:8088', //IT馆
				// target: 'http://165.229.143.169:8088', //图书馆
				changeOrigin: true,
				pathRewrite: {
					'^/api': '/api'
				}
			}
		}
	}
}



// module.exports = {
// 	publicPath: './',
// 	devServer: {
// 		host: '0.0.0.0', // 允许局域网访问
// 		proxy: {
// 			'/api': {
// 				target: 'http://10.0.2.2:8088', // 与config.js保持一致
// 				changeOrigin: true,
// 				pathRewrite: {
// 					'^/api': '/api'
// 				}
// 			}
// 		}
// 	}
// }