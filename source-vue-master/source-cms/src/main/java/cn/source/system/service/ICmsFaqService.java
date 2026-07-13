package cn.source.system.service;

import cn.source.system.domain.CmsFaq;
import java.util.List;

/**
 * 常见问题Service接口
 */
public interface ICmsFaqService {
    /** 新增常见问题 */
    int insertCmsFaq(CmsFaq cmsFaq);

    /** 查询常见问题列表（按排序） */
    List<CmsFaq> selectCmsFaqList(CmsFaq cmsFaq);

    /** 删除常见问题 */
    int deleteCmsFaqById(Long id);
}