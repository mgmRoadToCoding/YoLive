package cn.source.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;

/**
 * 工单管理对象 csm_work
 *
 * @author sourcebyte.vip
 * @date 2024-05-07
 */
public class CsmWork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 工单编号 */
    @Excel(name = "工单编号")
    private String workNo;

    /** 类型 */
    @Excel(name = "类型")
    private String wrokType;

    /** 描述 */
    @Excel(name = "描述")
    private String workDesc;

    /** 附件 */
    @Excel(name = "附件")
    private String workAttach;

    /** 优先级 */
    @Excel(name = "优先级")
    private String workPrioity;

    /** 计划完成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划完成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planDate;

    /** 负责人 */
    @Excel(name = "负责人")
    private String principal;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;

    /** 处理日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleDate;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handler;

    /** 流水号 */
    private Integer serialNo;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setWorkNo(String workNo)
    {
        this.workNo = workNo;
    }

    public String getWorkNo()
    {
        return workNo;
    }
    public void setWrokType(String wrokType)
    {
        this.wrokType = wrokType;
    }

    public String getWrokType()
    {
        return wrokType;
    }
    public void setWorkDesc(String workDesc)
    {
        this.workDesc = workDesc;
    }

    public String getWorkDesc()
    {
        return workDesc;
    }
    public void setWorkAttach(String workAttach)
    {
        this.workAttach = workAttach;
    }

    public String getWorkAttach()
    {
        return workAttach;
    }
    public void setWorkPrioity(String workPrioity)
    {
        this.workPrioity = workPrioity;
    }

    public String getWorkPrioity()
    {
        return workPrioity;
    }
    public void setPlanDate(Date planDate)
    {
        this.planDate = planDate;
    }

    public Date getPlanDate()
    {
        return planDate;
    }
    public void setPrincipal(String principal)
    {
        this.principal = principal;
    }

    public String getPrincipal()
    {
        return principal;
    }
    public void setHandleResult(String handleResult)
    {
        this.handleResult = handleResult;
    }

    public String getHandleResult()
    {
        return handleResult;
    }
    public void setHandleDate(Date handleDate)
    {
        this.handleDate = handleDate;
    }

    public Date getHandleDate()
    {
        return handleDate;
    }
    public void setHandler(String handler)
    {
        this.handler = handler;
    }

    public String getHandler()
    {
        return handler;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productName", getProductName())
            .append("workNo", getWorkNo())
            .append("wrokType", getWrokType())
            .append("workDesc", getWorkDesc())
            .append("workAttach", getWorkAttach())
            .append("workPrioity", getWorkPrioity())
            .append("planDate", getPlanDate())
            .append("principal", getPrincipal())
            .append("handleResult", getHandleResult())
            .append("handleDate", getHandleDate())
            .append("handler", getHandler())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
