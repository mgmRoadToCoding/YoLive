package cn.source.system.service;

import cn.source.system.domain.MallGoods;

import java.util.List;

/**
 * 商品管理Service接口
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public interface IMallGoodsService
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
     * 批量删除商品管理
     *
     * @param ids 需要删除的商品管理主键集合
     * @return 结果
     */
    public int deleteMallGoodsByIds(Long[] ids);

    /**
     * 删除商品管理信息
     *
     * @param id 商品管理主键
     * @return 结果
     */
    public int deleteMallGoodsById(Long id);

    /**
     * 改变商品状态
     *
     * @param mallGoods 商品管理
     * @return 结果
     */
    public int changeStatus(MallGoods mallGoods);
}
