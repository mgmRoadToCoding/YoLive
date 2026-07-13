package cn.source.system.domain;

import cn.source.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 常见问题对象 cms_faq
 */
public class CmsFaq extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 图标 */
    private String faqIcon;

    /** 名称 */
    private String faqName;

    /** 链接 */
    private String faqLink;

    /** 排序 */
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaqIcon() {
        return faqIcon;
    }

    public void setFaqIcon(String faqIcon) {
        this.faqIcon = faqIcon;
    }

    public String getFaqName() {
        return faqName;
    }

    public void setFaqName(String faqName) {
        this.faqName = faqName;
    }

    public String getFaqLink() {
        return faqLink;
    }

    public void setFaqLink(String faqLink) {
        this.faqLink = faqLink;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("faqIcon", getFaqIcon())
            .append("faqName", getFaqName())
            .append("faqLink", getFaqLink())
            .append("sort", getSort())
            .append("createTime", getCreateTime())
            .toString();
    }
}