package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.CmsFaq;
import cn.source.system.mapper.CmsFaqMapper;
import cn.source.system.service.ICmsFaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsFaqServiceImpl implements ICmsFaqService {

    @Autowired
    private CmsFaqMapper cmsFaqMapper;

    @Override
    public int insertCmsFaq(CmsFaq cmsFaq) {
        cmsFaq.setCreateTime(DateUtils.getNowDate());
        return cmsFaqMapper.insertCmsFaq(cmsFaq);
    }

    @Override
    public List<CmsFaq> selectCmsFaqList(CmsFaq cmsFaq) {
        return cmsFaqMapper.selectCmsFaqList(cmsFaq);
    }

    @Override
    public int deleteCmsFaqById(Long id) {
        return cmsFaqMapper.deleteCmsFaqById(id);
    }
}