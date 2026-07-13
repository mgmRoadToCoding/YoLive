package cn.source.system.domain.vo;

import cn.source.common.annotation.Excel;
import cn.source.system.domain.CmsTool;
import lombok.Getter;

/**
 * 附加工具分类的vo
 */
public class CmsToolVo extends CmsTool {
    /**
     * 分类名称
     */
    @Getter
    @Excel(name = "分类名称")
    private String catName;

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public String toString() {
        return "CmsToolVo{" +
                ", catName='" + catName + '\'' +
                '}';
    }
}
