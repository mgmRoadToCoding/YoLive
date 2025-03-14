package cn.source.system.domain;

import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 导航管理对象 mall-navigate
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public class MallNavigate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 导航名称 */
    @Excel(name = "导航名称")
    private String navigateName;

    /** 链接地址 */
    @Excel(name = "链接地址")
    private String navigateAddress;

    /** 导航图标 */
    @Excel(name = "导航图标")
    private String navigateUrl;

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
    public void setNavigateName(String navigateName)
    {
        this.navigateName = navigateName;
    }

    public String getNavigateName()
    {
        return navigateName;
    }
    public void setNavigateAddress(String navigateAddress)
    {
        this.navigateAddress = navigateAddress;
    }

    public String getNavigateAddress()
    {
        return navigateAddress;
    }
    public void setNavigateUrl(String navigateUrl)
    {
        this.navigateUrl = navigateUrl;
    }

    public String getNavigateUrl()
    {
        return navigateUrl;
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
            .append("navigateName", getNavigateName())
            .append("navigateAddress", getNavigateAddress())
            .append("navigateUrl", getNavigateUrl())
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
