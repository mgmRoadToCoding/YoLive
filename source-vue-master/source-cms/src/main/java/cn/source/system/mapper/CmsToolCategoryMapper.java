package cn.source.system.mapper;

import java.util.List;

import cn.source.system.domain.CmsTool;
import cn.source.system.domain.CmsToolCategory;
import cn.source.system.domain.vo.CmsCategoryToolVo;
import cn.source.system.domain.vo.CmsToolVo;

/**
 * 工具分类Mapper接口
 * 
 * @author sourcebyte.vip
 * @date 2024-12-29
 */
public interface CmsToolCategoryMapper 
{
    /**
     * 查询工具分类
     * 
     * @param id 工具分类主键
     * @return 工具分类
     */
    public CmsToolCategory selectCmsToolCategoryById(Long id);

    /**
     * 查询工具分类列表
     * 
     * @param cmsToolCategory 工具分类
     * @return 工具分类集合
     */
    public List<CmsToolCategory> selectCmsToolCategoryList(CmsToolCategory cmsToolCategory);

    /**
     * 新增工具分类
     * 
     * @param cmsToolCategory 工具分类
     * @return 结果
     */
    public int insertCmsToolCategory(CmsToolCategory cmsToolCategory);

    /**
     * 修改工具分类
     * 
     * @param cmsToolCategory 工具分类
     * @return 结果
     */
    public int updateCmsToolCategory(CmsToolCategory cmsToolCategory);

    /**
     * 删除工具分类
     * 
     * @param id 工具分类主键
     * @return 结果
     */
    public int deleteCmsToolCategoryById(Long id);

    /**
     * 批量删除工具分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCmsToolCategoryByIds(Long[] ids);

    /**
     * 查询分了下的工具列表
     */
    public List<CmsCategoryToolVo> selectCategoryToolList(CmsTool cmsTool);
}
