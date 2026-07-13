<template>
  <div class="app-container">
    <el-table :height="tableHeight" v-loading="loading" :data="sessionList">
      <el-table-column label="#" type="index" width="60" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名称" align="center" prop="nickname" />
      <el-table-column label="客服类型" align="center" prop="type">
        <template slot-scope="scope">
          <span>{{ scope.row.type === 0 ? "ai客服" : "人工客服" }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center" prop="phoneNumber" />
      <el-table-column
        label="最新消息时间"
        align="center"
        prop="lastMessageTime"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastMessageTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否已回复" align="center" prop="handled">
        <template slot-scope="scope">
          <span>{{ scope.row.handled === 0 ? "未回复" : "已回复" }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-ChatLineRound"
            @click="openMessages(scope.row)"
            v-hasPermi="['house:chat:detail']"
            >查看详情</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog
      title="会话消息"
      :visible.sync="messageDialogVisible"
      width="800px"
    >
      <div class="chat-container" v-loading="loadingMessages">
        <div
          v-for="(m, idx) in activeMessages"
          :key="m.id || idx"
          class="chat-row"
          :class="(m.role || '').toLowerCase() === 'user' ? 'left' : 'right'"
        >
          <div class="bubble">
            <div class="bubble-content">{{ m.content }}</div>
            <div class="bubble-meta">
              <span class="role">{{ roleText(m.role) }}</span>
              <span class="time">{{ parseTime(m.createTime) }}</span>
            </div>
          </div>
        </div>
      </div>
      <!-- 发送区，仅人工客服显示 -->
      <div class="send-bar" v-if="canShowSendBar">
        <el-input
          v-model="sendText"
          placeholder="请输入要发送的内容"
          size="mini"
          maxlength="500"
          show-word-limit
          clearable
          @keyup.enter.native="sendMessage"
        />
        <el-button
          type="primary"
          size="mini"
          :disabled="!canSend"
          @click="sendMessage"
          >发 送</el-button
        >
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="messageDialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { sessions } from "@/api/house/chat";
import { getMessages } from "@/api/house/chat.js";
import { parseTime } from "@/utils/ruoyi";
import { getToken } from "@/utils/auth";

export default {
  name: "HouseChat",
  data() {
    return {
      // 表格高度
      tableHeight: document.documentElement.clientHeight - 220,
      // 遮罩层
      loading: false,
      // 总条数（对应消息总数分页）
      total: 0,
      // 原始消息
      msgList: [],
      // 会话列表
      sessionList: [],
      // 会话消息弹窗
      messageDialogVisible: false,
      loadingMessages: false,
      activeMessages: [],
      activePairKey: null,
      activeSessionType: null,
      // WebSocket 连接
      ws: null,
      wsUrl: null,
      // 发送内容
      sendText: "",
      // 查询参数（沿用系统消息接口）
      queryParams: {
        pageNum: 1,
        pageSize: 20,
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.initWebSocket();
  },
  beforeDestroy() {
    try {
      if (this.ws) {
        this.ws.close();
        this.ws = null;
      }
    } catch (e) {}
  },
  methods: {
    parseTime,
    /** 初始化 WebSocket 连接到 /websocket/admin */
    initWebSocket() {
      try {
        this.wsUrl = `${
          process.env.VUE_APP_BASE_API
        }/websocket/admin?token=${getToken()}`;


        // 建立连接
        this.ws = new WebSocket(this.wsUrl);
        // 连接成功
        this.ws.addEventListener("open", () => {
          // 可选：连接成功提示
          // this.$modal && this.$modal.msgSuccess && this.$modal.msgSuccess('WebSocket 已连接');
        });
        // 收到消息
        this.ws.addEventListener("message", (event) => {
          const raw = event.data;
          const payload = JSON.parse(raw);
          const contentText = payload.content;

          // 若弹窗已打开且会话一致，则直接插入当前消息并跳过通知
          const currentSessionId = parseInt(payload.sessionId, 10);
          console.log(
            currentSessionId,
            this.messageDialogVisible,
            this.activePairKey
          );
          if (
            currentSessionId &&
            this.messageDialogVisible &&
            this.activePairKey === currentSessionId
          ) {
            const newMsg = {
              id: Date.now(),
              content: contentText,
              role: (payload && payload.role) || "user",
              createTime: Date.now(),
            };
            this.activeMessages = Array.isArray(this.activeMessages)
              ? [...this.activeMessages, newMsg]
              : [newMsg];
            this.scrollChatToBottom();
            // 更新会话列表（如最新消息时间、回复状态）
            this.getList();
            return; // 不弹通知
          }
          // 弹出通知显示 content；仅在点击通知时打开会话弹窗
          const openOnClick = () => {
            if (!(payload && payload.sessionId)) return;
            const sessionId = payload.sessionId;
            // 点击后仅打开/刷新对应会话
            this.openMessages({ id: sessionId });
          };
          if (this.$notify) {
            const notif = this.$notify({
              title: "新消息",
              message: `收到消息：${contentText}`,
              type: "info",
              position: "bottom-right",
              duration: 5000,
              onClick: () => {
                openOnClick();
                // 点击后关闭通知
                try {
                  notif && notif.close && notif.close();
                } catch (e) {}
              },
            });
          } else if (this.$modal && this.$modal.notify) {
            // 备用提示（不支持点击事件），仍然不自动打开弹窗
            this.$modal.notify(`收到消息：${contentText}`);
          }
          // 非当前会话的消息也刷新列表，以便最新消息时间变化
          this.getList();
        });
        // 异常与关闭
        this.ws.addEventListener("error", () => {
          this.$modal &&
            this.$modal.notifyError &&
            this.$modal.notifyError("WebSocket 连接异常");
        });
        this.ws.addEventListener("close", () => {
          // 可选：关闭提示
          // this.$modal && this.$modal.notifyWarning && this.$modal.notifyWarning('WebSocket 已关闭');
        });
      } catch (e) {
        this.$modal &&
          this.$modal.notifyError &&
          this.$modal.notifyError("WebSocket 初始化失败");
      }
    },
    /** 查询消息并聚合为会话列表 */
    getList() {
      this.loading = true;
      sessions(this.queryParams)
        .then((response) => {
          this.msgList = response.rows || [];
          this.total = response.total || 0;
          this.sessionList = this.msgList;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    /** 打开会话消息弹窗（按 /messages/{id} 查询） */
    async openMessages(session) {
      const sessionId = session.id;
      if (!sessionId) {
        this.$modal.msgError("无法识别会话ID");
        return;
      }
      this.messageDialogVisible = true;
      this.activePairKey = parseInt(sessionId, 10);
      // 记录会话类型（0: ai客服；非0: 人工客服）
      let t = typeof session.type !== "undefined" ? session.type : null;
      if (t == null && Array.isArray(this.sessionList)) {
        const found = this.sessionList.find(
          (s) =>
            (s.sessionId || s.id || s.fromSession || s.toSession) === sessionId
        );
        t = found ? found.type : null;
      }
      this.activeSessionType = t;
      this.loadingMessages = true;
      try {
        const res = await getMessages(sessionId);
        const rows = Array.isArray(res) ? res : res.rows || res.data || [];
        this.activeMessages = rows || [];
        this.scrollChatToBottom();
      } catch (e) {
        this.activeMessages = [];
        this.$modal &&
          this.$modal.msgError &&
          this.$modal.msgError("消息查询失败");
      } finally {
        this.loadingMessages = false;
      }
    },
    roleText(role) {
      const r = (role || "").toLowerCase();
      if (r === "user") return "访客";
      if (r === "admin") return "管理员";
      if (r === "agent") return "AI客服";
      return role || "";
    },
    /** 通过 WebSocket 发送人工消息 */
    sendMessage() {
      const text = (this.sendText || "").trim();
      if (!text) {
        this.$modal &&
          this.$modal.msgWarning &&
          this.$modal.msgWarning("请输入要发送的内容");
        return;
      }
      // 仅人工客服会话允许发送
      if (this.activeSessionType === 0) {
        this.$modal &&
          this.$modal.msgWarning &&
          this.$modal.msgWarning("当前为 AI 客服会话，无法发送人工消息");
        return;
      }
      if (!this.activePairKey) {
        this.$modal &&
          this.$modal.msgWarning &&
          this.$modal.msgWarning("请先选择会话");
        return;
      }
      if (!this.ws || this.ws.readyState !== 1) {
        this.$modal &&
          this.$modal.msgError &&
          this.$modal.msgError("WebSocket 未连接");
        return;
      }
      const payload = {
        event: "adminMessage",
        sessionId: this.activePairKey,
        content: text,
        role: "admin",
      };
      try {
        this.ws.send(JSON.stringify(payload));
        // 本地追加消息并清空输入
        const localMsg = {
          id: Date.now(),
          content: text,
          role: "admin",
          createTime: Date.now(),
        };
        this.activeMessages = Array.isArray(this.activeMessages)
          ? [...this.activeMessages, localMsg]
          : [localMsg];
        this.sendText = "";
        // 滚动到底部
        this.$nextTick(() => {
          const container = this.$el.querySelector(".chat-container");
          if (container) container.scrollTop = container.scrollHeight;
        });
        // 发送成功后刷新会话列表（例如设置为已回复、更新时间）
        this.getList();
      } catch (e) {
        this.$modal &&
          this.$modal.msgError &&
          this.$modal.msgError("消息发送失败");
      }
    },
    scrollChatToBottom() {
      this.$nextTick(() => {
        try {
          const container = this.$el.querySelector(".chat-container");
          if (container) {
            container.scrollTop = container.scrollHeight;
          }
        } catch (e) {}
      });
    },
  },
  computed: {
    /** 是否允许发送（文本非空、WS已连接、会话存在） */
    canSend() {
      const t = (this.sendText || "").trim();
      return !!(t && this.ws && this.ws.readyState === 1);
    },
    /** 是否显示发送栏：仅人工客服会话显示 */
    canShowSendBar() {
      return this.activeSessionType !== 0;
    },
  },
};
</script>

<style scoped lang="scss">
.el-form-search {
  margin-bottom: 10px;
}
.chat-container {
  max-height: 420px;
  overflow-y: auto;
  padding: 12px;
  background: #f5f7fa;
}
.chat-row {
  display: flex;
  margin: 8px 0;
}
.chat-row.left {
  justify-content: flex-start;
}
.chat-row.right {
  justify-content: flex-end;
}
.bubble {
  max-width: 70%;
  padding: 8px 12px;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}
.chat-row.right .bubble {
  background: #409eff;
  color: #fff;
}
.bubble-content {
  white-space: pre-wrap;
  word-break: break-word;
}
.bubble-meta {
  margin-top: 6px;
  font-size: 12px;
  opacity: 0.7;
  display: flex;
  gap: 8px;
}
/* 发送区样式 */
.send-bar {
  display: flex;
  align-items: flex-end;
  gap: 8px;
  padding: 10px 12px 0;
  border-top: 1px solid #ebeef5;
}
.send-bar .el-input {
  flex: 1;
}
</style>
