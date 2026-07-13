package cn.source.system.service;

import cn.source.system.domain.MallAdvert;

import java.util.List;

/**
 * 广告管理Service接口
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public interface IMallAdvertService
{
    /**
     * 查询广告管理
     *
     * @param id 广告管理主键
     * @return 广告管理
     */
    public MallAdvert selectMallAdvertById(Long id);

    /**
     * 查询广告管理列表
     *
     * @param mallAdvert 广告管理
     * @return 广告管理集合
     */
    public List<MallAdvert> selectMallAdvertList(MallAdvert mallAdvert);

    /**
     * 新增广告管理
     *
     * @param mallAdvert 广告管理
     * @return 结果
     */
    public int insertMallAdvert(MallAdvert mallAdvert);

    /**
     * 修改广告管理
     *
     * @param mallAdvert 广告管理
     * @return 结果
     */
    public int updateMallAdvert(MallAdvert mallAdvert);

    /**
     * 批量删除广告管理
     *
     * @param ids 需要删除的广告管理主键集合
     * @return 结果
     */
    public int deleteMallAdvertByIds(Long[] ids);

    /**
     * 删除广告管理信息
     *
     * @param id 广告管理主键
     * @return 结果
     */
    public int deleteMallAdvertById(Long id);
}
