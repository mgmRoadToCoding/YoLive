// 配置后端路径请修改config.js
module.exports = {
  publicPath: './',
  devServer: {
    proxy: {
      '/api': {
		target: 'http://192.168.0.5:8088',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/api'
        }
      }
    }
  }
}