<template>
	<view class="container">
		<view>TEST</view>
	    <!-- 顶部导航栏 -->
	    <view class="nav-bar">
	      <view class="back-btn" @click="goBack">
	        <text class="icon">←</text>
	        <text>返回</text>
	      </view>
	      <text class="title">选择位置</text>
	    </view>
	    
	    <!-- Google Maps iframe容器 -->
	    <view class="map-container">
	      <iframe 
	        v-if="mapUrl"
	        :src="mapUrl"
	        class="map-iframe"
	        frameborder="0"
	        @load="iframeLoaded"
	      ></iframe>
	      <view v-else class="loading">加载地图中...</view>
	    </view>
	    
	    <!-- 底部地址信息和确认按钮 -->
	    <view class="address-container" :class="{active: selectedAddress}">
	      <view class="address-info">
	        <text class="label">选择的位置:</text>
	        <text class="address">{{ selectedAddress || "请在地图上选择位置" }}</text>
	      </view>
	      <button 
	        class="confirm-btn" 
	        :disabled="!selectedAddress"
	        @click="confirmLocation"
	      >
	        确定
	      </button>
	    </view>
	  </view>
</template>

<script>
	export default {
		data() {
			return {
				mapUrl: "", // Google Maps嵌入URL
				selectedAddress: "",
				iframeLoaded: false
			}
		},
		onLoad(options) {
		    // 初始化地图URL，可以根据需要添加参数
		    this.initMapUrl();
		  },
		methods: {
			initMapUrl() {
			      // 这里替换为你的Google Maps嵌入URL
			      // embed?pb=...格式的URL
			      this.mapUrl = "https://www.google.com/maps/embed/v1/view?key=YOUR_API_KEY&center=37.7749,-122.4194&zoom=12";
			      
			      // 或者使用place模式
			      // this.mapUrl = "https://www.google.com/maps/embed/v1/place?key=YOUR_API_KEY&q=Space+Needle,Seattle+WA";
			    },
			
			// 监听iframe加载完成
			    iframeLoaded() {
			      this.iframeLoaded = true;
			      // 这里可以添加与iframe通信的代码
			    },
			    
			    // 返回上一页
			    goBack() {
			      uni.navigateBack();
			    },
			    
			    // 确认选择的位置
			    confirmLocation() {
			      if (this.selectedAddress) {
			        // 返回上一页并带回调数据
			        const eventChannel = this.getOpenerEventChannel();
			        if (eventChannel) {
			          eventChannel.emit('mapLocationSelected', {
			            address: this.selectedAddress,
			            // 可以添加经纬度等其他信息
			          });
			        }
			        uni.navigateBack();
			      }
			    },
				// 模拟从iframe接收地址信息
				// 注意: 由于跨域限制，实际实现可能需要其他方案
				receiveAddressFromIframe(address) {
				    this.selectedAddress = address;
				}
		}
		
	}
</script>

<style scoped>
	.container {
	  display: flex;
	  flex-direction: column;
	  height: 100vh;
	  width: 100%;
	}

	.nav-bar {
	  height: 60px;
	  background-color: #ffffff;
	  display: flex;
	  align-items: center;
	  padding: 0 15px;
	  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
	  position: relative;
	  z-index: 10;
	}

	.back-btn {
	  display: flex;
	  align-items: center;
	  margin-right: 15px;
	}

	.back-btn .icon {
	  margin-right: 5px;
	  font-size: 20px;
	}

	.title {
	  font-size: 18px;
	  font-weight: bold;
	}

	.map-container {
	  flex: 1;
	  position: relative;
	  overflow: hidden;
	}

	.map-iframe {
	  width: 100%;
	  height: 100%;
	}

	.loading {
	  position: absolute;
	  top: 0;
	  left: 0;
	  right: 0;
	  bottom: 0;
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  background-color: #f5f5f5;
	}

	.address-container {
	  padding: 15px;
	  background-color: #ffffff;
	  border-top: 1px solid #e0e0e0;
	  transform: translateY(100%);
	  transition: transform 0.3s ease;
	}

	.address-container.active {
	  transform: translateY(0);
	}

	.address-info {
	  margin-bottom: 15px;
	}

	.label {
	  font-weight: bold;
	  margin-right: 10px;
	}

	.address {
	  color: #666;
	}

	.confirm-btn {
	  background-color: #4285f4;
	  color: white;
	  border-radius: 5px;
	  padding: 10px;
	  text-align: center;
	}

	.confirm-btn:disabled {
	  background-color: #cccccc;
	  opacity: 0.7;
	}
</style>
