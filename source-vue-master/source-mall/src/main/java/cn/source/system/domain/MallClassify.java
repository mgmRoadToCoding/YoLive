package cn.source.system.domain;

import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分类管理对象 mall_classify
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public class MallClassify extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String classifyName;

    /** 分类图片 */
    @Excel(name = "分类图片")
    private String classifyUrl;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortNo;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String normalDisable;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setClassifyName(String classifyName)
    {
        this.classifyName = classifyName;
    }

    public String getClassifyName()
    {
        return classifyName;
    }
    public void setClassifyUrl(String classifyUrl)
    {
        this.classifyUrl = classifyUrl;
    }

    public String getClassifyUrl()
    {
        return classifyUrl;
    }
    public void setSortNo(Integer sortNo)
    {
        this.sortNo = sortNo;
    }

    public Integer getSortNo()
    {
        return sortNo;
    }
    public void setNormalDisable(String normalDisable)
    {
        this.normalDisable = normalDisable;
    }

    public String getNormalDisable()
    {
        return normalDisable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("classifyName", getClassifyName())
            .append("classifyUrl", getClassifyUrl())
            .append("sortNo", getSortNo())
            .append("normalDisable", getNormalDisable())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
