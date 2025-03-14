package cn.source.system.domain.vo;

import cn.source.system.domain.CmsTool;
import cn.source.system.domain.CmsToolCategory;

import java.util.List;

/**
 * 分类工具列表
 */
public class CmsCategoryToolVo extends CmsToolCategory {
    private List<CmsTool> children;

    public List<CmsTool> getChildren() {
        return children;
    }

    public void setChildren(List<CmsTool> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "CmsCategoryToolVo{" +
                "children=" + children +
                '}';
    }
}
