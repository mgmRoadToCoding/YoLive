package cn.source.system.mapper;

import java.util.List;
import cn.source.system.domain.CmsTool;
import cn.source.system.domain.vo.CmsToolVo;

/**
 * 工具管理Mapper接口
 * 
 * @author sourcebyte.vip
 * @date 2024-12-29
 */
public interface CmsToolMapper 
{
    /**
     * 查询工具管理
     * 
     * @param id 工具管理主键
     * @return 工具管理
     */
    public CmsTool selectCmsToolById(Long id);

    /**
     * 查询工具管理列表
     * 
     * @param cmsTool 工具管理
     * @return 工具管理集合
     */
    public List<CmsToolVo> selectCmsToolList(CmsTool cmsTool);

    /**
     * 新增工具管理
     * 
     * @param cmsTool 工具管理
     * @return 结果
     */
    public int insertCmsTool(CmsTool cmsTool);

    /**
     * 修改工具管理
     * 
     * @param cmsTool 工具管理
     * @return 结果
     */
    public int updateCmsTool(CmsTool cmsTool);

    /**
     * 删除工具管理
     * 
     * @param id 工具管理主键
     * @return 结果
     */
    public int deleteCmsToolById(Long id);

    /**
     * 批量删除工具管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCmsToolByIds(Long[] ids);
}
