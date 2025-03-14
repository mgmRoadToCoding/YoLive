package cn.source.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;

/**
 * 产品管理对象 cms_product
 * 
 * @author sourcebyte.vip
 * @date 2024-06-08
 */
public class CmsProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 行业 */
    @Excel(name = "行业")
    private String productIndustry;

    /** 标签 */
    @Excel(name = "标签")
    private String productTag;

    /** 大标题 */
    @Excel(name = "大标题")
    private String productBigTitle;

    /** 小标题 */
    @Excel(name = "小标题")
    private String productSmallTitle;

    /** 图标 */
    @Excel(name = "图标")
    private String productIcon;

    /** 主图 */
    @Excel(name = "主图")
    private String productThImg;

    /** 背景图 */
    @Excel(name = "背景图")
    private String productBgImg;

    /** 描述 */
    @Excel(name = "描述")
    private String productDesc;

    /** 详情 */
    @Excel(name = "详情")
    private String productContent;

    /** 链接 */
    @Excel(name = "链接")
    private String prodcutUrl;

    /** 在建 */
    @Excel(name = "在建")
    private String isBuild;

    /** 开源 */
    @Excel(name = "开源")
    private String isOpen;

    /** 展示 */
    @Excel(name = "展示")
    private String isShow;

    /** 热点 */
    @Excel(name = "热点")
    private String isHot;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactCode;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortNo;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductIndustry(String productIndustry) 
    {
        this.productIndustry = productIndustry;
    }

    public String getProductIndustry() 
    {
        return productIndustry;
    }
    public void setProductTag(String productTag) 
    {
        this.productTag = productTag;
    }

    public String getProductTag() 
    {
        return productTag;
    }
    public void setProductBigTitle(String productBigTitle) 
    {
        this.productBigTitle = productBigTitle;
    }

    public String getProductBigTitle() 
    {
        return productBigTitle;
    }
    public void setProductSmallTitle(String productSmallTitle) 
    {
        this.productSmallTitle = productSmallTitle;
    }

    public String getProductSmallTitle() 
    {
        return productSmallTitle;
    }
    public void setProductIcon(String productIcon) 
    {
        this.productIcon = productIcon;
    }

    public String getProductIcon() 
    {
        return productIcon;
    }
    public void setProductThImg(String productThImg) 
    {
        this.productThImg = productThImg;
    }

    public String getProductThImg() 
    {
        return productThImg;
    }
    public void setProductBgImg(String productBgImg) 
    {
        this.productBgImg = productBgImg;
    }

    public String getProductBgImg() 
    {
        return productBgImg;
    }
    public void setProductDesc(String productDesc) 
    {
        this.productDesc = productDesc;
    }

    public String getProductDesc() 
    {
        return productDesc;
    }
    public void setProductContent(String productContent) 
    {
        this.productContent = productContent;
    }

    public String getProductContent() 
    {
        return productContent;
    }
    public void setProdcutUrl(String prodcutUrl) 
    {
        this.prodcutUrl = prodcutUrl;
    }

    public String getProdcutUrl() 
    {
        return prodcutUrl;
    }
    public void setIsBuild(String isBuild) 
    {
        this.isBuild = isBuild;
    }

    public String getIsBuild() 
    {
        return isBuild;
    }
    public void setIsOpen(String isOpen) 
    {
        this.isOpen = isOpen;
    }

    public String getIsOpen() 
    {
        return isOpen;
    }
    public void setIsShow(String isShow) 
    {
        this.isShow = isShow;
    }

    public String getIsShow() 
    {
        return isShow;
    }
    public void setIsHot(String isHot) 
    {
        this.isHot = isHot;
    }

    public String getIsHot() 
    {
        return isHot;
    }
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setContactCode(String contactCode) 
    {
        this.contactCode = contactCode;
    }

    public String getContactCode() 
    {
        return contactCode;
    }
    public void setSortNo(Integer sortNo) 
    {
        this.sortNo = sortNo;
    }

    public Integer getSortNo() 
    {
        return sortNo;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productIndustry", getProductIndustry())
            .append("productTag", getProductTag())
            .append("productBigTitle", getProductBigTitle())
            .append("productSmallTitle", getProductSmallTitle())
            .append("productIcon", getProductIcon())
            .append("productThImg", getProductThImg())
            .append("productBgImg", getProductBgImg())
            .append("productDesc", getProductDesc())
            .append("productContent", getProductContent())
            .append("prodcutUrl", getProdcutUrl())
            .append("isBuild", getIsBuild())
            .append("isOpen", getIsOpen())
            .append("isShow", getIsShow())
            .append("isHot", getIsHot())
            .append("contact", getContact())
            .append("contactCode", getContactCode())
            .append("sortNo", getSortNo())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
