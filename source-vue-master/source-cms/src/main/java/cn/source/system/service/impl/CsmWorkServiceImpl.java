package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.common.utils.SecurityUtils;
import cn.source.system.domain.CsmWork;
import cn.source.system.mapper.CsmWorkMapper;
import cn.source.system.service.ICsmWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 工单管理Service业务层处理
 *
 * @author sourcebyte.vip
 * @date 2024-05-07
 */
@Service
public class CsmWorkServiceImpl implements ICsmWorkService {
    @Autowired
    private CsmWorkMapper csmWorkMapper;

    /**
     * 查询工单管理
     *
     * @param id 工单管理主键
     * @return 工单管理
     */
    @Override
    public CsmWork selectCsmWorkById(Long id) {
        return csmWorkMapper.selectCsmWorkById(id);
    }

    /**
     * 查询工单管理列表
     *
     * @param csmWork 工单管理
     * @return 工单管理
     */
    @Override
    public List<CsmWork> selectCsmWorkList(CsmWork csmWork) {
        return csmWorkMapper.selectCsmWorkList(csmWork);
    }

    /**
     * 新增工单管理
     *
     * @param csmWork 工单管理
     * @return 结果
     */
    @Override
    public int insertCsmWork(CsmWork csmWork) {
        Integer serialNo = csmWorkMapper.selectSerialNo();
        csmWork.setSerialNo(serialNo);
        csmWork.setWorkNo(String.format("%06d", serialNo));
        csmWork.setHandleResult("待处理");
        csmWork.setCreateTime(DateUtils.getNowDate());
        csmWork.setCreateBy(SecurityUtils.getLoginUser().getUser().getNickName());
        return csmWorkMapper.insertCsmWork(csmWork);
    }

    /**
     * 修改工单管理
     *
     * @param csmWork 工单管理
     * @return 结果
     */
    @Override
    public int updateCsmWork(CsmWork csmWork) {
        csmWork.setUpdateTime(DateUtils.getNowDate());
        csmWork.setUpdateBy(SecurityUtils.getLoginUser().getUser().getNickName());
        return csmWorkMapper.updateCsmWork(csmWork);
    }

    /**
     * 批量删除工单管理
     *
     * @param ids 需要删除的工单管理主键
     * @return 结果
     */
    @Override
    public int deleteCsmWorkByIds(Long[] ids) {
        return csmWorkMapper.deleteCsmWorkByIds(ids);
    }

    /**
     * 删除工单管理信息
     *
     * @param id 工单管理主键
     * @return 结果
     */
    @Override
    public int deleteCsmWorkById(Long id) {
        return csmWorkMapper.deleteCsmWorkById(id);
    }

    /**
     * 处理工单
     */
    @Override
    public int feedWork(Long[] ids, String handleResult) {
        return csmWorkMapper.feedWork(ids, handleResult, new Date(), SecurityUtils.getLoginUser().getUser().getNickName());
    }
}
