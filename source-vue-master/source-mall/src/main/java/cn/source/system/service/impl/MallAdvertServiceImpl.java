package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.MallAdvert;
import cn.source.system.mapper.MallAdvertMapper;
import cn.source.system.service.IMallAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告管理Service业务层处理
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@Service
public class MallAdvertServiceImpl implements IMallAdvertService
{
    @Autowired
    private MallAdvertMapper mallAdvertMapper;

    /**
     * 查询广告管理
     *
     * @param id 广告管理主键
     * @return 广告管理
     */
    @Override
    public MallAdvert selectMallAdvertById(Long id)
    {
        return mallAdvertMapper.selectMallAdvertById(id);
    }

    /**
     * 查询广告管理列表
     *
     * @param mallAdvert 广告管理
     * @return 广告管理
     */
    @Override
    public List<MallAdvert> selectMallAdvertList(MallAdvert mallAdvert)
    {
        return mallAdvertMapper.selectMallAdvertList(mallAdvert);
    }

    /**
     * 新增广告管理
     *
     * @param mallAdvert 广告管理
     * @return 结果
     */
    @Override
    public int insertMallAdvert(MallAdvert mallAdvert)
    {
        mallAdvert.setCreateTime(DateUtils.getNowDate());
        return mallAdvertMapper.insertMallAdvert(mallAdvert);
    }

    /**
     * 修改广告管理
     *
     * @param mallAdvert 广告管理
     * @return 结果
     */
    @Override
    public int updateMallAdvert(MallAdvert mallAdvert)
    {
        mallAdvert.setUpdateTime(DateUtils.getNowDate());
        return mallAdvertMapper.updateMallAdvert(mallAdvert);
    }

    /**
     * 批量删除广告管理
     *
     * @param ids 需要删除的广告管理主键
     * @return 结果
     */
    @Override
    public int deleteMallAdvertByIds(Long[] ids)
    {
        return mallAdvertMapper.deleteMallAdvertByIds(ids);
    }

    /**
     * 删除广告管理信息
     *
     * @param id 广告管理主键
     * @return 结果
     */
    @Override
    public int deleteMallAdvertById(Long id)
    {
        return mallAdvertMapper.deleteMallAdvertById(id);
    }
}
