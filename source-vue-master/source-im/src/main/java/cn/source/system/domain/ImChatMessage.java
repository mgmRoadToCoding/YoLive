package cn.source.system.domain;

import cn.source.common.core.domain.BaseEntity;

/**
 * 即时聊天消息实体，用于客服聊天记录持久化。
 */
public class ImChatMessage extends BaseEntity {
    private Long id;
    /** WebSocket会话ID */
    private String sessionId;
    /** 发送方用户ID（来自登录用户） */
    private Long userId;
    /** 角色：user 或 agent */
    private String role;
    /** 文本内容 */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}