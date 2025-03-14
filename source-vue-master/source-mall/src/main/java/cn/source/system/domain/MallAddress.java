package cn.source.system.domain;

import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收货地址对象 mall_address
 *
 * @author 詹Sir
 * @date 2022-06-19
 */
public class MallAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 收货人 */
    @Excel(name = "收货人")
    private String userName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 默认地址 */
    @Excel(name = "默认地址")
    private String isDefault;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String normalDisable;

    /** 排序 */
    private Integer sortNo;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getIsDefault()
    {
        return isDefault;
    }
    public void setNormalDisable(String normalDisable)
    {
        this.normalDisable = normalDisable;
    }

    public String getNormalDisable()
    {
        return normalDisable;
    }
    public void setSortNo(Integer sortNo)
    {
        this.sortNo = sortNo;
    }

    public Integer getSortNo()
    {
        return sortNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("isDefault", getIsDefault())
            .append("normalDisable", getNormalDisable())
            .append("sortNo", getSortNo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
