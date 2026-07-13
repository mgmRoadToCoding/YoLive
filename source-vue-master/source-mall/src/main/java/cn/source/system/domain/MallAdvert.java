package cn.source.system.domain;

import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 广告管理对象 mall-advert
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public class MallAdvert extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 广告类别 */
    @Excel(name = "广告类别")
    private Integer advertType;

    /** 广告名称 */
    @Excel(name = "广告名称")
    private String advertName;

    /** 链接地址 */
    @Excel(name = "链接地址")
    private String advertAddress;

    /** 广告图片 */
    @Excel(name = "广告图片")
    private String advertUrl;

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
    public void setAdvertType(Integer advertType)
    {
        this.advertType = advertType;
    }

    public Integer getAdvertType()
    {
        return advertType;
    }
    public void setAdvertName(String advertName)
    {
        this.advertName = advertName;
    }

    public String getAdvertName()
    {
        return advertName;
    }
    public void setAdvertAddress(String advertAddress)
    {
        this.advertAddress = advertAddress;
    }

    public String getAdvertAddress()
    {
        return advertAddress;
    }
    public void setAdvertUrl(String advertUrl)
    {
        this.advertUrl = advertUrl;
    }

    public String getAdvertUrl()
    {
        return advertUrl;
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
            .append("advertType", getAdvertType())
            .append("advertName", getAdvertName())
            .append("advertAddress", getAdvertAddress())
            .append("advertUrl", getAdvertUrl())
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
