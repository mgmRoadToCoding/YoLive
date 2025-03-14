package cn.source.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.source.common.annotation.Excel;
import cn.source.common.core.domain.BaseEntity;

/**
 * 工具管理对象 cms_tool
 * 
 * @author sourcebyte.vip
 * @date 2024-12-29
 */
public class CmsTool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 分类id */
    @Excel(name = "分类id")
    private Long catId;

    /** 工具图标 */
    @Excel(name = "工具图标")
    private String icon;

    /** 工具名称 */
    @Excel(name = "工具名称")
    private String name;

    /** 工具排序 */
    @Excel(name = "工具排序")
    private Integer sort;

    /** 工具简介 */
    @Excel(name = "工具简介")
    private String intro;

    /** 工具版本 */
    @Excel(name = "工具版本")
    private String version;

    /** 工具类型 */
    @Excel(name = "工具类型")
    private Integer type;

    /** 链接/网盘地址 */
    @Excel(name = "链接/网盘地址")
    private String url;

    /** 网盘类型 */
    private Integer panType;

    /** 提取码 */
    @Excel(name = "提取码")
    private String extractedCode;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCatId(Long catId) 
    {
        this.catId = catId;
    }

    public Long getCatId() 
    {
        return catId;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getIcon()
    {
        return icon;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public String getIntro() 
    {
        return intro;
    }
    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setPanType(Integer panType) 
    {
        this.panType = panType;
    }

    public Integer getPanType() 
    {
        return panType;
    }
    public void setExtractedCode(String extractedCode) 
    {
        this.extractedCode = extractedCode;
    }

    public String getExtractedCode() 
    {
        return extractedCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("catId", getCatId())
            .append("icon", getIcon())
            .append("name", getName())
            .append("sort", getSort())
            .append("intro", getIntro())
            .append("version", getVersion())
            .append("type", getType())
            .append("url", getUrl())
            .append("panType", getPanType())
            .append("extractedCode", getExtractedCode())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
