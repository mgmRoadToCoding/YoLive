package cn.source.system.mapper;

import java.util.List;
import cn.source.system.domain.CmsProduct;

/**
 * 产品管理Mapper接口
 * 
 * @author sourcebyte.vip
 * @date 2024-06-08
 */
public interface CmsProductMapper 
{
    /**
     * 查询产品管理
     * 
     * @param id 产品管理主键
     * @return 产品管理
     */
    public CmsProduct selectCmsProductById(Long id);

    /**
     * 查询产品管理列表
     * 
     * @param cmsProduct 产品管理
     * @return 产品管理集合
     */
    public List<CmsProduct> selectCmsProductList(CmsProduct cmsProduct);

    /**
     * 新增产品管理
     * 
     * @param cmsProduct 产品管理
     * @return 结果
     */
    public int insertCmsProduct(CmsProduct cmsProduct);

    /**
     * 修改产品管理
     * 
     * @param cmsProduct 产品管理
     * @return 结果
     */
    public int updateCmsProduct(CmsProduct cmsProduct);

    /**
     * 删除产品管理
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    public int deleteCmsProductById(Long id);

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCmsProductByIds(Long[] ids);
}
