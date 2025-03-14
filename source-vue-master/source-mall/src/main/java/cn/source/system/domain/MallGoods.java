package cn.source.system.domain;

import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品管理对象 mall_goods
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public class MallGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 商品分类 */
    @Excel(name = "商品分类")
    private String goodsClassify;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;

    /** 卖点 */
    @Excel(name = "卖点")
    private String goodsFeature;

    /** 销售价 */
    @Excel(name = "销售价")
    private BigDecimal newPrice;

    /** 划线价 */
    @Excel(name = "划线价")
    private BigDecimal oldPrice;

    /** 库存 */
    @Excel(name = "库存")
    private Integer goodsStock;

    /** 主图 */
    @Excel(name = "主图")
    private String goodsFaceUrl;

    /** 轮播图 */
    private String goodsItemUrl;

    /** 图文详情 */
    private String articleContent;

    /** 排序 */
    private Integer sortNo;

    /** 是否上架 */
    @Excel(name = "是否上架")
    private String status;

    /** 是否上架 */
    @Excel(name = "是否热点")
    private String hotStatus;

    /** 浏览量 */
    private Integer goodsView;

    /** 商品规格信息 */
    private List<MallGoodsSpec> mallGoodsSpecList;

    public String getHotStatus() {
        return hotStatus;
    }

    public void setHotStatus(String hotStatus) {
        this.hotStatus = hotStatus;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setGoodsClassify(String goodsClassify)
    {
        this.goodsClassify = goodsClassify;
    }

    public String getGoodsClassify()
    {
        return goodsClassify;
    }
    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName()
    {
        return goodsName;
    }
    public void setGoodsFeature(String goodsFeature)
    {
        this.goodsFeature = goodsFeature;
    }

    public String getGoodsFeature()
    {
        return goodsFeature;
    }
    public void setNewPrice(BigDecimal newPrice)
    {
        this.newPrice = newPrice;
    }

    public BigDecimal getNewPrice()
    {
        return newPrice;
    }
    public void setOldPrice(BigDecimal oldPrice)
    {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getOldPrice()
    {
        return oldPrice;
    }
    public void setGoodsStock(Integer goodsStock)
    {
        this.goodsStock = goodsStock;
    }

    public Integer getGoodsStock()
    {
        return goodsStock;
    }
    public void setGoodsFaceUrl(String goodsFaceUrl)
    {
        this.goodsFaceUrl = goodsFaceUrl;
    }

    public String getGoodsFaceUrl()
    {
        return goodsFaceUrl;
    }
    public void setGoodsItemUrl(String goodsItemUrl)
    {
        this.goodsItemUrl = goodsItemUrl;
    }

    public String getGoodsItemUrl()
    {
        return goodsItemUrl;
    }
    public void setArticleContent(String articleContent)
    {
        this.articleContent = articleContent;
    }

    public String getArticleContent()
    {
        return articleContent;
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

    public List<MallGoodsSpec> getMallGoodsSpecList()
    {
        return mallGoodsSpecList;
    }

    public void setMallGoodsSpecList(List<MallGoodsSpec> mallGoodsSpecList)
    {
        this.mallGoodsSpecList = mallGoodsSpecList;
    }

    public Integer getGoodsView() {
        return goodsView;
    }

    public void setGoodsView(Integer goodsView) {
        this.goodsView = goodsView;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("goodsClassify", getGoodsClassify())
            .append("goodsName", getGoodsName())
            .append("goodsFeature", getGoodsFeature())
            .append("newPrice", getNewPrice())
            .append("oldPrice", getOldPrice())
            .append("goodsStock", getGoodsStock())
            .append("goodsFaceUrl", getGoodsFaceUrl())
            .append("goodsItemUrl", getGoodsItemUrl())
            .append("articleContent", getArticleContent())
            .append("sortNo", getSortNo())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("mallGoodsSpecList", getMallGoodsSpecList())
            .toString();
    }
}
