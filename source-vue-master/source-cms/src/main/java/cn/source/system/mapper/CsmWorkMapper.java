package cn.source.system.mapper;

import java.util.Date;
import java.util.List;
import cn.source.system.domain.CsmWork;
import org.apache.ibatis.annotations.Param;

/**
 * 工单管理Mapper接口
 *
 * @author sourcebyte.vip
 * @date 2024-05-07
 */
public interface CsmWorkMapper
{
    /**
     * 查询工单管理
     *
     * @param id 工单管理主键
     * @return 工单管理
     */
    public CsmWork selectCsmWorkById(Long id);

    /**
     * 查询工单管理列表
     *
     * @param csmWork 工单管理
     * @return 工单管理集合
     */
    public List<CsmWork> selectCsmWorkList(CsmWork csmWork);

    /**
     * 新增工单管理
     *
     * @param csmWork 工单管理
     * @return 结果
     */
    public int insertCsmWork(CsmWork csmWork);

    /**
     * 修改工单管理
     *
     * @param csmWork 工单管理
     * @return 结果
     */
    public int updateCsmWork(CsmWork csmWork);

    /**
     * 删除工单管理
     *
     * @param id 工单管理主键
     * @return 结果
     */
    public int deleteCsmWorkById(Long id);

    /**
     * 批量删除工单管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCsmWorkByIds(Long[] ids);

    /**
     * 查询最大流水号
     */
    public Integer selectSerialNo();

    /**
     * 处理工单
     */
    public int feedWork(@Param("ids") Long[] ids,@Param("handleResult") String handleResult,
                        @Param("handleDate") Date handleDate,@Param("handler") String handler);
}
