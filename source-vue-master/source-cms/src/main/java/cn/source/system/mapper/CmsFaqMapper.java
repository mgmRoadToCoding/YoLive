package cn.source.system.mapper;

import cn.source.system.domain.CmsFaq;
import java.util.List;

/**
 * 常见问题Mapper接口
 */
public interface CmsFaqMapper {
    /**
     * 查询常见问题列表
     */
    List<CmsFaq> selectCmsFaqList(CmsFaq cmsFaq);

    /**
     * 新增常见问题
     */
    int insertCmsFaq(CmsFaq cmsFaq);

    /**
     * 删除常见问题
     */
    int deleteCmsFaqById(Long id);
}