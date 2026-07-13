package cn.source.system.domain;

import java.util.Date;

/**
 * 会话实体，用于记录聊天会话信息
 */
public class ImChatSession {
    private Long id;
    private Long userId;
    private String title;
    private Integer type; // 会话类型枚举：0=ai，1=human(人工)
    private Long responderId; // 回复者ID（人工时可记录客服/回复者）
    /** 是否已处理：0=未处理，1=已处理，仅对人工会话有效 */
    private Integer handled;
    private Date createTime;
    private Date updateTime;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getResponderId() {
        return responderId;
    }

    public void setResponderId(Long responderId) {
        this.responderId = responderId;
    }

    public Integer getHandled() {
        return handled;
    }

    public void setHandled(Integer handled) {
        this.handled = handled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}