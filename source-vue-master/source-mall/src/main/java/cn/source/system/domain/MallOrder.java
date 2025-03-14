package cn.source.system.domain;

import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单信息对象 mall_order
 *
 * @author 詹Sir
 * @date 2022-06-19
 */
public class MallOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderCode;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderCreateTime;

    /** 收货人 */
    @Excel(name = "收货人")
    private String receiver;

    /** 收货人电话 */
    @Excel(name = "联系方式")
    private String receiverPhone;

    /** 收货地址 */
    @Excel(name = "收货地址")
    private String receiverAddress;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    /** 支付方式 */
    @Excel(name = "支付方式",dictType = "mall_pay_method")
    private String payMethod;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /** 支持状态 */
    @Excel(name = "支持状态",dictType = "mall_pay_status")
    private String payStatus;

    /** 优惠金额 */
    private Long disAmount;

    /** 订单状态 */
    @Excel(name = "订单状态",dictType = "mall_order_status")
    private String orderStatus;

    /** 排序 */
    private Integer sortNo;

    /** 用户id */
    private Long userId;

    /** 订单商品信息 */
    private List<MallOrderGoods> mallOrderGoodsList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode()
    {
        return orderCode;
    }
    public void setOrderCreateTime(Date orderCreateTime)
    {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getOrderCreateTime()
    {
        return orderCreateTime;
    }
    public void setPayAmount(BigDecimal payAmount)
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount()
    {
        return payAmount;
    }
    public void setPayMethod(String payMethod)
    {
        this.payMethod = payMethod;
    }

    public String getPayMethod()
    {
        return payMethod;
    }
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
    }
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }

    public String getPayStatus()
    {
        return payStatus;
    }
    public void setDisAmount(Long disAmount)
    {
        this.disAmount = disAmount;
    }

    public Long getDisAmount()
    {
        return disAmount;
    }
    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }
    public void setSortNo(Integer sortNo)
    {
        this.sortNo = sortNo;
    }

    public Integer getSortNo()
    {
        return sortNo;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public List<MallOrderGoods> getMallOrderGoodsList() {
        return mallOrderGoodsList;
    }

    public void setMallOrderGoodsList(List<MallOrderGoods> mallOrderGoodsList) {
        this.mallOrderGoodsList = mallOrderGoodsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderCode", getOrderCode())
            .append("orderCreateTime", getOrderCreateTime())
            .append("payAmount", getPayAmount())
            .append("payMethod", getPayMethod())
            .append("payTime", getPayTime())
            .append("payStatus", getPayStatus())
            .append("disAmount", getDisAmount())
            .append("orderStatus", getOrderStatus())
            .append("sortNo", getSortNo())
            .append("userId", getUserId())
            .append("receiver", getReceiver())
            .append("receiverPhone", getReceiverPhone())
            .append("receiverAddress", getReceiverAddress())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
