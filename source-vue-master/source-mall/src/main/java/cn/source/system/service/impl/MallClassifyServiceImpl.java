package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.MallClassify;
import cn.source.system.mapper.MallClassifyMapper;
import cn.source.system.service.IMallClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类管理Service业务层处理
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@Service
public class MallClassifyServiceImpl implements IMallClassifyService
{
    @Autowired
    private MallClassifyMapper mallClassifyMapper;

    /**
     * 查询分类管理
     *
     * @param id 分类管理主键
     * @return 分类管理
     */
    @Override
    public MallClassify selectMallClassifyById(Long id)
    {
        return mallClassifyMapper.selectMallClassifyById(id);
    }

    /**
     * 查询分类管理列表
     *
     * @param mallClassify 分类管理
     * @return 分类管理
     */
    @Override
    public List<MallClassify> selectMallClassifyList(MallClassify mallClassify)
    {
        return mallClassifyMapper.selectMallClassifyList(mallClassify);
    }

    /**
     * 新增分类管理
     *
     * @param mallClassify 分类管理
     * @return 结果
     */
    @Override
    public int insertMallClassify(MallClassify mallClassify)
    {
        mallClassify.setCreateTime(DateUtils.getNowDate());
        return mallClassifyMapper.insertMallClassify(mallClassify);
    }

    /**
     * 修改分类管理
     *
     * @param mallClassify 分类管理
     * @return 结果
     */
    @Override
    public int updateMallClassify(MallClassify mallClassify)
    {
        mallClassify.setUpdateTime(DateUtils.getNowDate());
        return mallClassifyMapper.updateMallClassify(mallClassify);
    }

    /**
     * 批量删除分类管理
     *
     * @param ids 需要删除的分类管理主键
     * @return 结果
     */
    @Override
    public int deleteMallClassifyByIds(Long[] ids)
    {
        return mallClassifyMapper.deleteMallClassifyByIds(ids);
    }

    /**
     * 删除分类管理信息
     *
     * @param id 分类管理主键
     * @return 结果
     */
    @Override
    public int deleteMallClassifyById(Long id)
    {
        return mallClassifyMapper.deleteMallClassifyById(id);
    }
}
