package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.common.utils.StringUtils;
import cn.source.system.domain.MallGoods;
import cn.source.system.domain.MallGoodsSpec;
import cn.source.system.mapper.MallGoodsMapper;
import cn.source.system.service.IMallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理Service业务层处理
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@Service
public class MallGoodsServiceImpl implements IMallGoodsService
{
    @Autowired
    private MallGoodsMapper mallGoodsMapper;

    /**
     * 查询商品管理
     *
     * @param id 商品管理主键
     * @return 商品管理
     */
    @Override
    public MallGoods selectMallGoodsById(Long id)
    {
        return mallGoodsMapper.selectMallGoodsById(id);
    }

    /**
     * 查询商品管理列表
     *
     * @param mallGoods 商品管理
     * @return 商品管理
     */
    @Override
    public List<MallGoods> selectMallGoodsList(MallGoods mallGoods)
    {
        return mallGoodsMapper.selectMallGoodsList(mallGoods);
    }

    /**
     * 新增商品管理
     *
     * @param mallGoods 商品管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMallGoods(MallGoods mallGoods)
    {
        mallGoods.setCreateTime(DateUtils.getNowDate());
        int rows = mallGoodsMapper.insertMallGoods(mallGoods);
        insertMallGoodsSpec(mallGoods);
        return rows;
    }

    /**
     * 修改商品管理
     *
     * @param mallGoods 商品管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMallGoods(MallGoods mallGoods)
    {
        mallGoods.setUpdateTime(DateUtils.getNowDate());
        mallGoodsMapper.deleteMallGoodsSpecByParentId(mallGoods.getId());
        insertMallGoodsSpec(mallGoods);
        return mallGoodsMapper.updateMallGoods(mallGoods);
    }

    /**
     * 批量删除商品管理
     *
     * @param ids 需要删除的商品管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMallGoodsByIds(Long[] ids)
    {
        mallGoodsMapper.deleteMallGoodsSpecByParentIds(ids);
        return mallGoodsMapper.deleteMallGoodsByIds(ids);
    }

    /**
     * 删除商品管理信息
     *
     * @param id 商品管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMallGoodsById(Long id)
    {
        mallGoodsMapper.deleteMallGoodsSpecByParentId(id);
        return mallGoodsMapper.deleteMallGoodsById(id);
    }

    /**
     * 新增商品规格信息
     *
     * @param mallGoods 商品管理对象
     */
    public void insertMallGoodsSpec(MallGoods mallGoods)
    {
        List<MallGoodsSpec> mallGoodsSpecList = mallGoods.getMallGoodsSpecList();
        Long id = mallGoods.getId();
        if (StringUtils.isNotNull(mallGoodsSpecList))
        {
            List<MallGoodsSpec> list = new ArrayList<MallGoodsSpec>();
            for (MallGoodsSpec mallGoodsSpec : mallGoodsSpecList)
            {
                mallGoodsSpec.setParentId(id);
                list.add(mallGoodsSpec);
            }
            if (list.size() > 0)
            {
                mallGoodsMapper.batchMallGoodsSpec(list);
            }
        }
    }

    /**
     * 修改商品管理
     *
     * @param mallGoods 商品管理
     * @return 结果
     */
    @Transactional
    @Override
    public int changeStatus(MallGoods mallGoods)
    {
        mallGoods.setUpdateTime(DateUtils.getNowDate());
        return mallGoodsMapper.changeStatus(mallGoods);
    }
}
