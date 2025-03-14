package cn.source.system.mapper;

import cn.source.system.domain.MallGoodsSpec;

import java.util.List;

/**
 * 商品规格Mapper接口
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public interface MallGoodsSpecMapper
{
    /**
     * 查询商品规格
     *
     * @param id 商品规格主键
     * @return 商品规格
     */
    public MallGoodsSpec selectMallGoodsSpecById(Long id);

    /**
     * 查询商品规格列表
     *
     * @param mallGoodsSpec 商品规格
     * @return 商品规格集合
     */
    public List<MallGoodsSpec> selectMallGoodsSpecList(MallGoodsSpec mallGoodsSpec);

    /**
     * 新增商品规格
     *
     * @param mallGoodsSpec 商品规格
     * @return 结果
     */
    public int insertMallGoodsSpec(MallGoodsSpec mallGoodsSpec);

    /**
     * 修改商品规格
     *
     * @param mallGoodsSpec 商品规格
     * @return 结果
     */
    public int updateMallGoodsSpec(MallGoodsSpec mallGoodsSpec);

    /**
     * 删除商品规格
     *
     * @param id 商品规格主键
     * @return 结果
     */
    public int deleteMallGoodsSpecById(Long id);

    /**
     * 批量删除商品规格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallGoodsSpecByIds(Long[] ids);
}
