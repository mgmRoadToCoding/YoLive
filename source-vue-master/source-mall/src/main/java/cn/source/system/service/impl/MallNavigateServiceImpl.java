package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.MallNavigate;
import cn.source.system.mapper.MallNavigateMapper;
import cn.source.system.service.IMallNavigateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导航管理Service业务层处理
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@Service
public class MallNavigateServiceImpl implements IMallNavigateService
{
    @Autowired
    private MallNavigateMapper mallNavigateMapper;

    /**
     * 查询导航管理
     *
     * @param id 导航管理主键
     * @return 导航管理
     */
    @Override
    public MallNavigate selectMallNavigateById(Long id)
    {
        return mallNavigateMapper.selectMallNavigateById(id);
    }

    /**
     * 查询导航管理列表
     *
     * @param mallNavigate 导航管理
     * @return 导航管理
     */
    @Override
    public List<MallNavigate> selectMallNavigateList(MallNavigate mallNavigate)
    {
        return mallNavigateMapper.selectMallNavigateList(mallNavigate);
    }

    /**
     * 新增导航管理
     *
     * @param mallNavigate 导航管理
     * @return 结果
     */
    @Override
    public int insertMallNavigate(MallNavigate mallNavigate)
    {
        mallNavigate.setCreateTime(DateUtils.getNowDate());
        return mallNavigateMapper.insertMallNavigate(mallNavigate);
    }

    /**
     * 修改导航管理
     *
     * @param mallNavigate 导航管理
     * @return 结果
     */
    @Override
    public int updateMallNavigate(MallNavigate mallNavigate)
    {
        mallNavigate.setUpdateTime(DateUtils.getNowDate());
        return mallNavigateMapper.updateMallNavigate(mallNavigate);
    }

    /**
     * 批量删除导航管理
     *
     * @param ids 需要删除的导航管理主键
     * @return 结果
     */
    @Override
    public int deleteMallNavigateByIds(Long[] ids)
    {
        return mallNavigateMapper.deleteMallNavigateByIds(ids);
    }

    /**
     * 删除导航管理信息
     *
     * @param id 导航管理主键
     * @return 结果
     */
    @Override
    public int deleteMallNavigateById(Long id)
    {
        return mallNavigateMapper.deleteMallNavigateById(id);
    }
}
