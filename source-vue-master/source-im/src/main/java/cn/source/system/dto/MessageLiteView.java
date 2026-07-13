package cn.source.system.dto;

import java.util.Date;

/**
 * 轻量消息视图：仅保留 content、role、createTime
 */
public class MessageLiteView {
    private String content;
    private String role;
    private Date createTime;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}