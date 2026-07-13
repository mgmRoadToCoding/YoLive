<template>
	<view class="message-list-page">
		<view class="header">
			<text class="title">List</text>
		</view>

		<view class="list">
			<view v-if="loading" class="loading">加载中...</view>
			<view v-else-if="sessions.length === 0" class="empty">暂无历史消息</view>

			<!-- 循环 session 列表 -->
			<view v-else v-for="(item, idx) in sessions" :key="idx">

				<!-- 左滑删除 -->
				<u-swipe-action :options="[
            { text: '删除', style: { backgroundColor: '#ff3b30', color: '#fff' } }
          ]" @click="deleteSession(item, idx)">
					<view class="list-item" @click="openChat(item)">
						<view class="avatar" :class="item.type === 0 ? 'ai' : 'human'">
							<text>{{ item.type === 0 ? 'AI' : '人工' }}</text>
						</view>
						<view class="content">
							<view class="line1">
								<text class="last-content">{{ item.lastContent }}</text>
							</view>
							<view class="line2">
								<text class="last-time">{{ item.lastTime }}</text>
							</view>
						</view>
					</view>
				</u-swipe-action>

			</view>
		</view>

		<view class="fab" @click="goToChat">
			<u-icon name="plus" color="#fff" size="28"></u-icon>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loading: true,
				sessions: [],
			}
		},

		onLoad() {
			this.fetchSessions()
		},

		onPullDownRefresh() {
			this.fetchSessions().finally(() => {
				uni.stopPullDownRefresh()
			})
		},

		methods: {
			// 获取会话列表
			async fetchSessions() {
				this.loading = true
				try {
					const res = await this.$u.http.get('/chat/sessions')
					const raw = Array.isArray(res?.data) ? res.data : []
					const filtered = raw.filter(
						(s) => Array.isArray(s?.messages) && s.messages.length > 0
					)

					this.sessions = filtered.map((item) => {
						const messages = item.messages
						const last = messages[messages.length - 1]
						const lastContent = last.content.replace(/\n/g, '')

						return {
							id: item.id,
							type: item.type,
							lastContent,
							lastTime: this.formatTime(last.createTime),
						}
					})
				} finally {
					this.loading = false
				}
			},

			// 左滑删除
			deleteSession(item, idx) {
				uni.showModal({
					title: '删除确认',
					content: '确定删除该聊天记录吗？',
					success: async (res) => {
						if (res.confirm) {
							try {
								// 调用后端删除接口
								await this.$u.http.delete(`/chat/session/${item.id}`)

								// 前端删除
								this.sessions.splice(idx, 1)
								uni.showToast({
									title: '删除成功',
									icon: 'success'
								})
							} catch (e) {
								console.error('删除失败', e)
								uni.showToast({
									title: '删除失败',
									icon: 'error'
								})
							}
						}
					}
				})
			},

			goToChat() {
				uni.navigateTo({
					url: '/pages/chat/chatRoom',
				})
			},

			formatTime(ts) {
				const d = typeof ts === 'string' ? new Date(ts) : ts
				if (Object.prototype.toString.call(d) !== '[object Date]' || isNaN(d)) {
					return ''
				}
				const yyyy = d.getFullYear()
				const mm = String(d.getMonth() + 1).padStart(2, '0')
				const dd = String(d.getDate()).padStart(2, '0')
				const hh = String(d.getHours()).padStart(2, '0')
				const mi = String(d.getMinutes()).padStart(2, '0')
				return `${yyyy}-${mm}-${dd} ${hh}:${mi}`
			},

			openChat(item) {
				uni.navigateTo({
					url: `/pages/chat/chatRoom?sessionId=${item.id}&type=${item.type}`,
				})
			},
		},
	}
</script>


<style scoped>
	.message-list-page {
		display: flex;
		flex-direction: column;
		height: 100vh;
		background-color: #f7f8fa;
	}

	.header {
		height: 44px;
		display: flex;
		align-items: center;
		padding: 0 16px;
		background-color: #ffffff;
		border-bottom: 1px solid #f0f0f0;
	}

	.title {
		font-size: 16px;
		font-weight: 600;
	}

	.list {
		flex: 1;
		overflow-y: auto;
	}

	.loading,
	.empty {
		padding: 16px;
		color: #909399;
		font-size: 14px;
	}

	.list-item {
		display: flex;
		align-items: center;
		padding: 12px 16px;
		background-color: #fff;
		border-bottom: 1px solid #f5f5f5;
		overflow: hidden;
		/* 防止文字撑开阻断滑动 */
	}

	.avatar {
		width: 40px;
		height: 40px;
		flex-shrink: 0;
		border-radius: 20px;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		font-size: 12px;
		margin-right: 12px;
	}

	.avatar.ai {
		background-color: #2979ff;
	}

	.avatar.human {
		background-color: #ff9900;
	}

	.content {
		flex: 1;
		display: flex;
		flex-direction: column;
		min-width: 0;
		/* 允许收缩 */
	}

	.line1 {
		display: flex;
		align-items: center;
		min-width: 0;
		/* 允许收缩 */
		flex: 1;
	}

	.last-content {
		font-size: 14px;
		color: #303133;
		display: -webkit-box;
		/* 必须，创建伸缩容器 */
		-webkit-box-orient: vertical;
		/* 必须，设置伸缩方向为垂直 */
		-webkit-line-clamp: 3;
		/* 显示的行数 */
		overflow: hidden;
		/* 超出隐藏 */
		text-overflow: ellipsis;
		/* 超出显示省略号 */
		flex: 1;
		min-width: 0;
	}

	.line2 {
		margin-top: 6px;
	}

	.last-time {
		font-size: 12px;
		color: #909399;
	}

	.fab {
		position: fixed;
		right: 16px;
		bottom: 24px;
		/* 略高一点，避免被底部栏遮挡 */
		width: 56px;
		height: 56px;
		border-radius: 28px;
		background-color: #2979ff;
		box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
		display: flex;
		align-items: center;
		justify-content: center;
		z-index: 999;
	}
</style>