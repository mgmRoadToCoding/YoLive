package cn.source.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;

/**
 * 房源评价对象 house_evaluate
 * 
 * @author wind
 * @date 2023-04-14
 */
public class HouseEvaluate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 房屋id */
    @Excel(name = "房屋id")
    private Long houseId;

    /** 评价人id */
    @Excel(name = "评价人id")
    private Long evalUserId;

    /** 评价人 */
    @Excel(name = "评价人")
    private String evalUser;

    /** 评价 */
    @Excel(name = "评价")
    private String evaluate;

    /** 状态 1显示 2删除 */
    @Excel(name = "状态 1显示 2删除")
    private Integer state;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHouseId(Long houseId) 
    {
        this.houseId = houseId;
    }

    public Long getHouseId() 
    {
        return houseId;
    }
    public void setEvalUserId(Long evalUserId) 
    {
        this.evalUserId = evalUserId;
    }

    public Long getEvalUserId() 
    {
        return evalUserId;
    }
    public void setEvalUser(String evalUser) 
    {
        this.evalUser = evalUser;
    }

    public String getEvalUser() 
    {
        return evalUser;
    }
    public void setEvaluate(String evaluate) 
    {
        this.evaluate = evaluate;
    }

    public String getEvaluate() 
    {
        return evaluate;
    }
    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getState()
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("houseId", getHouseId())
            .append("evalUserId", getEvalUserId())
            .append("evalUser", getEvalUser())
            .append("evaluate", getEvaluate())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
