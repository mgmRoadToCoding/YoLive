package cn.source.system.service.impl;

import java.util.List;
import cn.source.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.source.system.mapper.HouseEvaluateMapper;
import cn.source.system.domain.HouseEvaluate;
import cn.source.system.service.IHouseEvaluateService;

/**
 * 房源评价Service业务层处理
 * 
 * @author wind
 * @date 2023-04-14
 */
@Service
public class HouseEvaluateServiceImpl implements IHouseEvaluateService 
{
    @Autowired
    private HouseEvaluateMapper houseEvaluateMapper;

    /**
     * 查询房源评价
     * 
     * @param id 房源评价主键
     * @return 房源评价
     */
    @Override
    public HouseEvaluate selectHouseEvaluateById(Long id)
    {
        return houseEvaluateMapper.selectHouseEvaluateById(id);
    }

    /**
     * 查询房源评价列表
     * 
     * @param houseEvaluate 房源评价
     * @return 房源评价
     */
    @Override
    public List<HouseEvaluate> selectHouseEvaluateList(HouseEvaluate houseEvaluate)
    {
        return houseEvaluateMapper.selectHouseEvaluateList(houseEvaluate);
    }

    /**
     * 新增房源评价
     * 
     * @param houseEvaluate 房源评价
     * @return 结果
     */
    @Override
    public int insertHouseEvaluate(HouseEvaluate houseEvaluate)
    {
        houseEvaluate.setCreateTime(DateUtils.getNowDate());
        houseEvaluate.setState(1);
        return houseEvaluateMapper.insertHouseEvaluate(houseEvaluate);
    }

    /**
     * 修改房源评价
     * 
     * @param houseEvaluate 房源评价
     * @return 结果
     */
    @Override
    public int updateHouseEvaluate(HouseEvaluate houseEvaluate)
    {
        houseEvaluate.setUpdateTime(DateUtils.getNowDate());
        return houseEvaluateMapper.updateHouseEvaluate(houseEvaluate);
    }

    /**
     * 批量删除房源评价
     * 
     * @param ids 需要删除的房源评价主键
     * @return 结果
     */
    @Override
    public int deleteHouseEvaluateByIds(Long[] ids)
    {
        //改写为状态更新
        return houseEvaluateMapper.updateEvalState(ids);
    }

    /**
     * 删除房源评价信息
     * 
     * @param id 房源评价主键
     * @return 结果
     */
    @Override
    public int deleteHouseEvaluateById(Long id)
    {
        return houseEvaluateMapper.deleteHouseEvaluateById(id);
    }
}
