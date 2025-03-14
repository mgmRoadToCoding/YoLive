package cn.source.system.domain;

import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 商品规格对象 mall_goods_spec
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public class MallGoodsSpec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 父id */
    private Long parentId;

    /** 规格名称 */
    @Excel(name = "规格名称")
    private String specName;

    /** 规格图片 */
    @Excel(name = "规格图片")
    private String specUrl;

    /** 销售价 */
    @Excel(name = "销售价")
    private BigDecimal specPrice;

    /** 库存 */
    @Excel(name = "库存")
    private Integer specStock;

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
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setSpecName(String specName)
    {
        this.specName = specName;
    }

    public String getSpecName()
    {
        return specName;
    }
    public void setSpecUrl(String specUrl)
    {
        this.specUrl = specUrl;
    }

    public String getSpecUrl()
    {
        return specUrl;
    }
    public void setSpecPrice(BigDecimal specPrice)
    {
        this.specPrice = specPrice;
    }

    public BigDecimal getSpecPrice()
    {
        return specPrice;
    }
    public void setSpecStock(Integer specStock)
    {
        this.specStock = specStock;
    }

    public Integer getSpecStock()
    {
        return specStock;
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
            .append("specName", getSpecName())
            .append("specUrl", getSpecUrl())
            .append("specPrice", getSpecPrice())
            .append("specStock", getSpecStock())
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
