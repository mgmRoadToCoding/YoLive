package cn.source.system.service;

import cn.source.system.domain.MallClassify;

import java.util.List;

/**
 * 分类管理Service接口
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public interface IMallClassifyService
{
    /**
     * 查询分类管理
     *
     * @param id 分类管理主键
     * @return 分类管理
     */
    public MallClassify selectMallClassifyById(Long id);

    /**
     * 查询分类管理列表
     *
     * @param mallClassify 分类管理
     * @return 分类管理集合
     */
    public List<MallClassify> selectMallClassifyList(MallClassify mallClassify);

    /**
     * 新增分类管理
     *
     * @param mallClassify 分类管理
     * @return 结果
     */
    public int insertMallClassify(MallClassify mallClassify);

    /**
     * 修改分类管理
     *
     * @param mallClassify 分类管理
     * @return 结果
     */
    public int updateMallClassify(MallClassify mallClassify);

    /**
     * 批量删除分类管理
     *
     * @param ids 需要删除的分类管理主键集合
     * @return 结果
     */
    public int deleteMallClassifyByIds(Long[] ids);

    /**
     * 删除分类管理信息
     *
     * @param id 分类管理主键
     * @return 结果
     */
    public int deleteMallClassifyById(Long id);
}
