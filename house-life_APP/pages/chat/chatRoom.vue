<template>
  <view class="chat-page">
    <view class="chat-container">
      <!-- 自定义头部 -->
      <view class="custom-header">
        <view class="left-controls">
          <view class="back-icon" @click="goBack">
            <u-icon name="arrow-left" size="30" color="#606266"></u-icon>
          </view>
          <view v-if="isAiChat" class="cs-icon" @click="startHumanChat">
            <u-icon name="kefu-ermai" size="30" color="#2979ff"></u-icon>
          </view>
        </view>
        <text class="header-title">ChatRoom</text>
      </view>

      <view class="message-list" ref="messageList" scroll-y>
        <view
          v-for="msg in messages"
          :key="msg.id"
          :id="'msg-' + msg.id"
          :class="['message-item', msg.fromSelf ? 'self' : 'other']"
        >
          <view class="bubble">{{ msg.content }}</view>
          <view class="meta">{{ msg.time }}</view>
        </view>
      </view>

      <view class="input-bar">
        <u-input
          v-model="inputMessage"
          placeholder="请输入消息..."
          :clearable="true"
          :maxlength="500"
          @confirm="sendMessage"
        />
        <u-button type="primary" :disabled="!canSend" @click="sendMessage"
          >发送</u-button
        >
      </view>
    </view>
  </view>
</template>

<script>
import config from '@/common/config.js'

export default {
  data() {
    return {
      socketTask: null,
      wsUrl: '',
      isConnected: false,
      inputMessage: '',
      messages: [],
      lastId: 0,
      bottomAnchor: 'bottom-anchor',
      reconnectAttempts: 0,
      maxReconnectAttempts: 5,
      sessionId: '',
      isAiChat: true,
    }
  },
  computed: {
    canSend() {
      return this.isConnected && this.inputMessage.trim().length > 0
    },
  },
  onLoad(options) {
    const type = options.type ? parseInt(options.type) : 0
    this.isAiChat = type === 0
    const sid = options?.sessionId
    if (sid) {
      this.sessionId = String(sid)
      this.loadHistory(this.sessionId).finally(() => {
        this.connectSocket()
      })
    } else {
      this.startSession().finally(() => {
        this.connectSocket()
      })
    }
  },
  onUnload() {
    this.cleanupSocket()
  },
  methods: {
    goBack() {
      uni.navigateBack({
        delta: 1,
      })
    },
    async startSession() {
      try {
        const postType = this.isAiChat ? 0 : 1
        const res = await this.$u.http.post('/chat/session/start', {
          type: postType,
        })
        const id = res?.data?.sessionId || res?.data?.id || res?.data
        if (id) {
          this.sessionId = String(id)
        } else {
          this.appendSystem('会话启动失败：未返回会话ID')
        }
      } catch (e) {
        this.appendSystem('会话启动失败')
      }
    },
    buildWsUrl() {
      const base = config.baseUrl || ''
      const wsBase = base.replace(/^http/, 'ws')
      const lifeData = uni.getStorageSync('lifeData') || {}
      const token = lifeData.vuex_token || ''
      // 如果需要认证，将token作为查询参数传递（后端若不需要可忽略）
      return token
        ? `${wsBase}/websocket/message?token=${encodeURIComponent(token)}`
        : `${wsBase}/websocket/message`
    },
    connectSocket() {
      if (this.socketTask) {
        try {
          this.socketTask.close({ code: 1000, reason: 'reconnect' })
        } catch (e) {}
        this.socketTask = null
      }
      const url = this.buildWsUrl()
      this.wsUrl = url
      const task = uni.connectSocket({ url, complete: () => {} })
      this.socketTask = task

      task.onOpen(() => {
        this.isConnected = true
        this.reconnectAttempts = 0
        this.appendSystem('连接成功')
      })

      task.onMessage((evt) => {
        let text = ''
        try {
          const obj =
            typeof evt.data === 'string' ? JSON.parse(evt.data) : evt.data
          // 兼容纯文本与JSON格式
          text =
            obj?.content ||
            (typeof evt.data === 'string' ? evt.data : JSON.stringify(evt.data))
        } catch (e) {
          text =
            typeof evt.data === 'string' ? evt.data : JSON.stringify(evt.data)
        }
        if (text === 'pong') return // 心跳回复不入消息列表
        this.appendMessage({ content: text, fromSelf: false })
      })

      task.onClose(() => {
        this.isConnected = false
      })

      task.onError((err) => {
        this.isConnected = false
        this.tryReconnect()
      })
    },
    cleanupSocket() {
      if (this.socketTask) {
        try {
          this.socketTask.close({ code: 1000, reason: 'page_unload' })
        } catch (e) {}
        this.socketTask = null
      }
    },
    async startHumanChat() {
      // 切换到人工客服：创建新会话并重连消息通道
      this.appendSystem('正在切换到人工客服...')
      try {
        const res = await this.$u.http.post('/chat/session/start', { type: 1 })
        const id = res?.data?.sessionId || res?.data?.id || res?.data
        if (!id) {
          return
        }
        this.sessionId = String(id)
        // 断开旧连接并建立新连接
        this.cleanupSocket()
        this.connectSocket()
        this.isAiChat = false
      } catch (e) {}
    },
    tryReconnect() {
      if (this.reconnectAttempts >= this.maxReconnectAttempts) {
        this.$mytip.toast('连接失败，请稍后重试')
        return
      }
      const delay = Math.min(1000 * Math.pow(2, this.reconnectAttempts), 10000)
      this.reconnectAttempts += 1
      setTimeout(() => this.connectSocket(), delay)
    },
    sendRaw(data) {
      if (!this.socketTask) return
      const payload = typeof data === 'string' ? data : JSON.stringify(data)
      try {
        this.socketTask.send({ data: payload })
      } catch (e) {
        this.appendSystem('发送失败，请重试')
      }
    },
    async sendMessage() {
      const text = this.inputMessage.trim()
      if (!text) return
      // 确保有客服会话
      if (!this.sessionId) {
        await this.startSession()
        if (!this.sessionId) return
      }
      // 先本地渲染，提高响应感
      this.appendMessage({ content: text, fromSelf: true })
      // 通过WebSocket发送 JSON：{ sessionId, content }
      this.sendRaw({ sessionId: this.sessionId, content: text })
      this.inputMessage = ''
    },
    appendMessage({ content, fromSelf }) {
      const id = ++this.lastId
      this.messages.push({
        id,
        content,
        fromSelf,
        time: this.formatTime(new Date()),
      })
      this.$nextTick(() => {
        // 触发滚动到新消息
        this.scrollToBottom()
      })
    },
    appendSystem(text) {
      const id = ++this.lastId
      this.messages.push({
        id,
        content: text,
        fromSelf: false,
        time: this.formatTime(new Date()),
        system: true,
      })
      this.$nextTick(() => this.scrollToBottom())
    },
    scrollToBottom() {
      uni.pageScrollTo({
        scrollTop: this.$refs.messageList.$el.offsetHeight,
      })
    },
    formatTime(d) {
      const hh = String(d.getHours()).padStart(2, '0')
      const mm = String(d.getMinutes()).padStart(2, '0')
      return `${hh}:${mm}`
    },
    async loadHistory(id) {
      try {
        const res = await this.$u.http.get(`/chat/messages/${id}`)
        const mapped = res.data.map((m) => {
          const date = new Date(m.createTime.replace(/-/g, '/'))
          return {
            id: typeof m.id === 'number' ? m.id : this.lastId + 1,
            content: m.content || '',
            fromSelf: String(m.role || '').toLowerCase() === 'user',
            time: this.formatTime(date),
          }
        })
        this.messages = mapped
        this.lastId = mapped.reduce((max, msg) => {
          const n = typeof msg.id === 'number' ? msg.id : 0
          return n > max ? n : max
        }, this.lastId)
        this.$nextTick(() => this.scrollToBottom())
      } catch (e) {
        this.appendSystem('历史消息加载失败')
      }
    },
  },
}
</script>

<style scoped>
.chat-page {
  display: flex;
  flex-direction: column;
  background-color: #f5f6f8;
}
.chat-container {
  display: flex;
  flex-direction: column;
  padding-top: 44px; /* 固定头部占位，避免内容被遮挡 */
}
.custom-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 12px;
  background-color: #ffffff;
  border-bottom: 1px solid #f0f0f0;
  z-index: 1000;
}
.header-title {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}
.left-controls {
  position: absolute;
  left: 12px;
  height: 44px;
  display: flex;
  align-items: center;
  gap: 8px;
}
.back-icon {
  display: flex;
  align-items: center;
}
.cs-icon {
  display: flex;
  align-items: center;
}
.message-list {
  flex: 1;
  padding: 12px;
  padding-bottom: 70px;
  height: calc(100vh - 44px - 60px);
  overflow: hidden;
  box-sizing: border-box;
}
.msgs {
  display: flex;
  flex-direction: column;
}
.message-item {
  display: flex;
  flex-direction: column;
  margin: 6px 0;
}
.message-item.self {
  align-self: flex-end;
}
.message-item.other {
  align-self: flex-start;
}
.bubble {
  width: 80%;
  padding: 10px 12px;
  border-radius: 12px;
  background-color: #fff;
  line-height: 1.4;
  word-break: break-word;
}
.message-item.self .bubble {
  background-color: #2979ff;
  color: #fff;
  margin-left: auto;
}
.meta {
  display: flex;
  margin: 0 auto;
  font-size: 12px;
  color: #999;
  margin-top: 4px;
  align-self: flex-end;
}
.input-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  gap: 8px;
  padding: 10px;
  background-color: #fff;
  border-top: 1px solid #eee;
}
.input-bar .u-input {
  flex: 1;
}
</style>
