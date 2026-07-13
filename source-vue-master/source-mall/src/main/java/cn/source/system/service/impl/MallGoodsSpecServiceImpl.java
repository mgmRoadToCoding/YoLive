package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.MallGoodsSpec;
import cn.source.system.mapper.MallGoodsSpecMapper;
import cn.source.system.service.IMallGoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品规格Service业务层处理
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@Service
public class MallGoodsSpecServiceImpl implements IMallGoodsSpecService
{
    @Autowired
    private MallGoodsSpecMapper mallGoodsSpecMapper;

    /**
     * 查询商品规格
     *
     * @param id 商品规格主键
     * @return 商品规格
     */
    @Override
    public MallGoodsSpec selectMallGoodsSpecById(Long id)
    {
        return mallGoodsSpecMapper.selectMallGoodsSpecById(id);
    }

    /**
     * 查询商品规格列表
     *
     * @param mallGoodsSpec 商品规格
     * @return 商品规格
     */
    @Override
    public List<MallGoodsSpec> selectMallGoodsSpecList(MallGoodsSpec mallGoodsSpec)
    {
        return mallGoodsSpecMapper.selectMallGoodsSpecList(mallGoodsSpec);
    }

    /**
     * 新增商品规格
     *
     * @param mallGoodsSpec 商品规格
     * @return 结果
     */
    @Override
    public int insertMallGoodsSpec(MallGoodsSpec mallGoodsSpec)
    {
        mallGoodsSpec.setCreateTime(DateUtils.getNowDate());
        return mallGoodsSpecMapper.insertMallGoodsSpec(mallGoodsSpec);
    }

    /**
     * 修改商品规格
     *
     * @param mallGoodsSpec 商品规格
     * @return 结果
     */
    @Override
    public int updateMallGoodsSpec(MallGoodsSpec mallGoodsSpec)
    {
        mallGoodsSpec.setUpdateTime(DateUtils.getNowDate());
        return mallGoodsSpecMapper.updateMallGoodsSpec(mallGoodsSpec);
    }

    /**
     * 批量删除商品规格
     *
     * @param ids 需要删除的商品规格主键
     * @return 结果
     */
    @Override
    public int deleteMallGoodsSpecByIds(Long[] ids)
    {
        return mallGoodsSpecMapper.deleteMallGoodsSpecByIds(ids);
    }

    /**
     * 删除商品规格信息
     *
     * @param id 商品规格主键
     * @return 结果
     */
    @Override
    public int deleteMallGoodsSpecById(Long id)
    {
        return mallGoodsSpecMapper.deleteMallGoodsSpecById(id);
    }
}
