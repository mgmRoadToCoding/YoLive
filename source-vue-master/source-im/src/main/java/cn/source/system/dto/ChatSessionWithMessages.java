package cn.source.system.dto;

import cn.source.system.domain.ImChatSession;
import cn.source.system.domain.ImChatMessage;
import java.util.List;

/**
 * 用户侧：会话及其消息的组合返回对象
 */
public class ChatSessionWithMessages {
    private ImChatSession session;
    private List<ImChatMessage> messages;

    public ImChatSession getSession() {
        return session;
    }

    public void setSession(ImChatSession session) {
        this.session = session;
    }

    public List<ImChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ImChatMessage> messages) {
        this.messages = messages;
    }
}