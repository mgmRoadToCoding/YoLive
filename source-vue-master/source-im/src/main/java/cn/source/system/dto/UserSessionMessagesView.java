package cn.source.system.dto;

import java.util.List;

/**
 * 扁平化用户会话返回：仅保留所需字段并附带消息列表
 */
public class UserSessionMessagesView {
    private Long id;
    private Long userId;
    private String title;
    /** 0=ai，1=human */
    private Integer type;
    private List<MessageLiteView> messages;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public List<MessageLiteView> getMessages() { return messages; }
    public void setMessages(List<MessageLiteView> messages) { this.messages = messages; }
}