package cn.source.system.domain;

import cn.source.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 论坛帖子对象 cms_forum_post
 */
public class CmsForumPost extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 标题 */
    private String title;

    /** 内容（纯文本或富文本存储） */
    private String content;

    /** 图片资源路径（逗号分隔的 /profile 路径） */
    private String images;

    /** 作者用户ID */
    private Long userId;

    /** 审核状态：0-待审核，1-已通过，2-已驳回 */
    private Integer status;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 审核说明（驳回原因等） */
    private String auditReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("images", getImages())
            .append("userId", getUserId())
            .append("status", getStatus())
            .append("isDeleted", getIsDeleted())
            .append("auditReason", getAuditReason())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}