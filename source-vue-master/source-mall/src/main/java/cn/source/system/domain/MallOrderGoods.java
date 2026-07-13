package cn.source.system.domain;

import cn.source.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 商品结算对象 mall_order_goods
 *
 * @author Mr Sir
 * @date 2022-08-02
 */
public class MallOrderGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单id */
    private Long orderId;

    /** 商品id */
    private Long goodsId;

    /** 商品名称 */
    private String title;

    /** 卖点 */
    private String desc;

    /** 价格 */
    private BigDecimal price;

    /** 数量 */
    private Integer value;

    /** 主图 */
    private String image;

    /** 是否选中商品 */
    private Boolean check;

    /** 是否显示删除 */
    private Boolean show;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}
