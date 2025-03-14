package cn.source.system.service;

import cn.source.system.domain.MallNavigate;

import java.util.List;

/**
 * 导航管理Service接口
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
public interface IMallNavigateService
{
    /**
     * 查询导航管理
     *
     * @param id 导航管理主键
     * @return 导航管理
     */
    public MallNavigate selectMallNavigateById(Long id);

    /**
     * 查询导航管理列表
     *
     * @param mallNavigate 导航管理
     * @return 导航管理集合
     */
    public List<MallNavigate> selectMallNavigateList(MallNavigate mallNavigate);

    /**
     * 新增导航管理
     *
     * @param mallNavigate 导航管理
     * @return 结果
     */
    public int insertMallNavigate(MallNavigate mallNavigate);

    /**
     * 修改导航管理
     *
     * @param mallNavigate 导航管理
     * @return 结果
     */
    public int updateMallNavigate(MallNavigate mallNavigate);

    /**
     * 批量删除导航管理
     *
     * @param ids 需要删除的导航管理主键集合
     * @return 结果
     */
    public int deleteMallNavigateByIds(Long[] ids);

    /**
     * 删除导航管理信息
     *
     * @param id 导航管理主键
     * @return 结果
     */
    public int deleteMallNavigateById(Long id);
}
