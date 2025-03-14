package cn.source.system.mapper;

import java.util.List;
import cn.source.system.domain.CmsDeveloper;

/**
 * 成员管理Mapper接口
 * 
 * @author sourcebyte.vip
 * @date 2024-04-27
 */
public interface CmsDeveloperMapper 
{
    /**
     * 查询成员管理
     * 
     * @param id 成员管理主键
     * @return 成员管理
     */
    public CmsDeveloper selectCmsDeveloperById(Long id);

    /**
     * 查询成员管理列表
     * 
     * @param cmsDeveloper 成员管理
     * @return 成员管理集合
     */
    public List<CmsDeveloper> selectCmsDeveloperList(CmsDeveloper cmsDeveloper);

    /**
     * 新增成员管理
     * 
     * @param cmsDeveloper 成员管理
     * @return 结果
     */
    public int insertCmsDeveloper(CmsDeveloper cmsDeveloper);

    /**
     * 修改成员管理
     * 
     * @param cmsDeveloper 成员管理
     * @return 结果
     */
    public int updateCmsDeveloper(CmsDeveloper cmsDeveloper);

    /**
     * 删除成员管理
     * 
     * @param id 成员管理主键
     * @return 结果
     */
    public int deleteCmsDeveloperById(Long id);

    /**
     * 批量删除成员管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCmsDeveloperByIds(Long[] ids);
}
