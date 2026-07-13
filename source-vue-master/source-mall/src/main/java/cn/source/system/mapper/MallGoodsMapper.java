package cn.source.system.mapper;

import cn.source.system.domain.MallGoods;
import cn.source.system.domain.MallGoodsSpec;

import java.util.List;

/**
 * 商品管理Mapper接口
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public interface MallGoodsMapper
{
    /**
     * 查询商品管理
     *
     * @param id 商品管理主键
     * @return 商品管理
     */
    public MallGoods selectMallGoodsById(Long id);

    /**
     * 查询商品管理列表
     *
     * @param mallGoods 商品管理
     * @return 商品管理集合
     */
    public List<MallGoods> selectMallGoodsList(MallGoods mallGoods);

    /**
     * 新增商品管理
     *
     * @param mallGoods 商品管理
     * @return 结果
     */
    public int insertMallGoods(MallGoods mallGoods);

    /**
     * 修改商品管理
     *
     * @param mallGoods 商品管理
     * @return 结果
     */
    public int updateMallGoods(MallGoods mallGoods);

    /**
     * 删除商品管理
     *
     * @param id 商品管理主键
     * @return 结果
     */
    public int deleteMallGoodsById(Long id);

    /**
     * 批量删除商品管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallGoodsByIds(Long[] ids);

    /**
     * 批量删除商品规格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallGoodsSpecByParentIds(Long[] ids);

    /**
     * 批量新增商品规格
     *
     * @param mallGoodsSpecList 商品规格列表
     * @return 结果
     */
    public int batchMallGoodsSpec(List<MallGoodsSpec> mallGoodsSpecList);


    /**
     * 通过商品管理主键删除商品规格信息
     *
     * @param id 商品管理ID
     * @return 结果
     */
    public int deleteMallGoodsSpecByParentId(Long id);

    /**
     * 修改商品管理
     *
     * @param mallGoods 商品管理
     * @return 结果
     */
    public int changeStatus(MallGoods mallGoods);
}
