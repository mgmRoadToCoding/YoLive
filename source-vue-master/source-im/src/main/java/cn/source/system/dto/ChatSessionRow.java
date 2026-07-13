package cn.source.system.dto;

import java.util.Date;

/**
 * 后台会话列表/详情的精简视图 DTO。
 * 包含用户名、（人工客服时）回复者名，以及最新消息时间。
 */
public class ChatSessionRow {
    private Long id;
    private String title;
    /** 0=机器人，1=人工客服 */
    private Integer type;
    /** 是否已处理：0=未处理，1=已处理，仅对人工会话有效 */
    private Integer handled;
    private String nickname;
    /** sys_user.user_name 原始用户名 */
    private String username;
    /** sys_user.phonenumber 手机号 */
    private String phoneNumber;
    /** 仅当 type=1 时可能有值 */
    private String responderName;
    private Date lastMessageTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public Integer getHandled() { return handled; }
    public void setHandled(Integer handled) { this.handled = handled; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getResponderName() { return responderName; }
    public void setResponderName(String responderName) { this.responderName = responderName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Date getLastMessageTime() { return lastMessageTime; }
    public void setLastMessageTime(Date lastMessageTime) { this.lastMessageTime = lastMessageTime; }
}


