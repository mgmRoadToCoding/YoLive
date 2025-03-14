package cn.source.system.service.impl;

import java.util.List;
import cn.source.common.utils.DateUtils;
import cn.source.system.domain.vo.CmsToolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.source.system.mapper.CmsToolMapper;
import cn.source.system.domain.CmsTool;
import cn.source.system.service.ICmsToolService;

/**
 * 工具管理Service业务层处理
 * 
 * @author sourcebyte.vip
 * @date 2024-12-29
 */
@Service
public class CmsToolServiceImpl implements ICmsToolService 
{
    @Autowired
    private CmsToolMapper cmsToolMapper;

    /**
     * 查询工具管理
     * 
     * @param id 工具管理主键
     * @return 工具管理
     */
    @Override
    public CmsTool selectCmsToolById(Long id)
    {
        return cmsToolMapper.selectCmsToolById(id);
    }

    /**
     * 查询工具管理列表
     * 
     * @param cmsTool 工具管理
     * @return 工具管理
     */
    @Override
    public List<CmsToolVo> selectCmsToolList(CmsTool cmsTool)
    {
        return cmsToolMapper.selectCmsToolList(cmsTool);
    }

    /**
     * 新增工具管理
     * 
     * @param cmsTool 工具管理
     * @return 结果
     */
    @Override
    public int insertCmsTool(CmsTool cmsTool)
    {
        cmsTool.setCreateTime(DateUtils.getNowDate());
        return cmsToolMapper.insertCmsTool(cmsTool);
    }

    /**
     * 修改工具管理
     * 
     * @param cmsTool 工具管理
     * @return 结果
     */
    @Override
    public int updateCmsTool(CmsTool cmsTool)
    {
        cmsTool.setUpdateTime(DateUtils.getNowDate());
        return cmsToolMapper.updateCmsTool(cmsTool);
    }

    /**
     * 批量删除工具管理
     * 
     * @param ids 需要删除的工具管理主键
     * @return 结果
     */
    @Override
    public int deleteCmsToolByIds(Long[] ids)
    {
        return cmsToolMapper.deleteCmsToolByIds(ids);
    }

    /**
     * 删除工具管理信息
     * 
     * @param id 工具管理主键
     * @return 结果
     */
    @Override
    public int deleteCmsToolById(Long id)
    {
        return cmsToolMapper.deleteCmsToolById(id);
    }
}
