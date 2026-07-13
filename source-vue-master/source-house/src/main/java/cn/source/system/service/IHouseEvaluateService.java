package cn.source.system.service;

import java.util.List;
import cn.source.system.domain.HouseEvaluate;

/**
 * 房源评价Service接口
 * 
 * @author wind
 * @date 2023-04-14
 */
public interface IHouseEvaluateService 
{
    /**
     * 查询房源评价
     * 
     * @param id 房源评价主键
     * @return 房源评价
     */
    public HouseEvaluate selectHouseEvaluateById(Long id);

    /**
     * 查询房源评价列表
     * 
     * @param houseEvaluate 房源评价
     * @return 房源评价集合
     */
    public List<HouseEvaluate> selectHouseEvaluateList(HouseEvaluate houseEvaluate);

    /**
     * 新增房源评价
     * 
     * @param houseEvaluate 房源评价
     * @return 结果
     */
    public int insertHouseEvaluate(HouseEvaluate houseEvaluate);

    /**
     * 修改房源评价
     * 
     * @param houseEvaluate 房源评价
     * @return 结果
     */
    public int updateHouseEvaluate(HouseEvaluate houseEvaluate);

    /**
     * 批量删除房源评价
     * 
     * @param ids 需要删除的房源评价主键集合
     * @return 结果
     */
    public int deleteHouseEvaluateByIds(Long[] ids);

    /**
     * 删除房源评价信息
     * 
     * @param id 房源评价主键
     * @return 结果
     */
    public int deleteHouseEvaluateById(Long id);
}
