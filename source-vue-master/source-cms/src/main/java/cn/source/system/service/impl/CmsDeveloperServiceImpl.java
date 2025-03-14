package cn.source.system.service.impl;

import java.util.List;
import cn.source.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.source.system.mapper.CmsDeveloperMapper;
import cn.source.system.domain.CmsDeveloper;
import cn.source.system.service.ICmsDeveloperService;

/**
 * 成员管理Service业务层处理
 * 
 * @author sourcebyte.vip
 * @date 2024-04-27
 */
@Service
public class CmsDeveloperServiceImpl implements ICmsDeveloperService 
{
    @Autowired
    private CmsDeveloperMapper cmsDeveloperMapper;

    /**
     * 查询成员管理
     * 
     * @param id 成员管理主键
     * @return 成员管理
     */
    @Override
    public CmsDeveloper selectCmsDeveloperById(Long id)
    {
        return cmsDeveloperMapper.selectCmsDeveloperById(id);
    }

    /**
     * 查询成员管理列表
     * 
     * @param cmsDeveloper 成员管理
     * @return 成员管理
     */
    @Override
    public List<CmsDeveloper> selectCmsDeveloperList(CmsDeveloper cmsDeveloper)
    {
        return cmsDeveloperMapper.selectCmsDeveloperList(cmsDeveloper);
    }

    /**
     * 新增成员管理
     * 
     * @param cmsDeveloper 成员管理
     * @return 结果
     */
    @Override
    public int insertCmsDeveloper(CmsDeveloper cmsDeveloper)
    {
        cmsDeveloper.setCreateTime(DateUtils.getNowDate());
        return cmsDeveloperMapper.insertCmsDeveloper(cmsDeveloper);
    }

    /**
     * 修改成员管理
     * 
     * @param cmsDeveloper 成员管理
     * @return 结果
     */
    @Override
    public int updateCmsDeveloper(CmsDeveloper cmsDeveloper)
    {
        cmsDeveloper.setUpdateTime(DateUtils.getNowDate());
        return cmsDeveloperMapper.updateCmsDeveloper(cmsDeveloper);
    }

    /**
     * 批量删除成员管理
     * 
     * @param ids 需要删除的成员管理主键
     * @return 结果
     */
    @Override
    public int deleteCmsDeveloperByIds(Long[] ids)
    {
        return cmsDeveloperMapper.deleteCmsDeveloperByIds(ids);
    }

    /**
     * 删除成员管理信息
     * 
     * @param id 成员管理主键
     * @return 结果
     */
    @Override
    public int deleteCmsDeveloperById(Long id)
    {
        return cmsDeveloperMapper.deleteCmsDeveloperById(id);
    }
}
