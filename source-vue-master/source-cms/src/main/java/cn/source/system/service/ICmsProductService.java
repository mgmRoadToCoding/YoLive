package cn.source.system.service;

import java.util.List;
import cn.source.system.domain.CmsProduct;

/**
 * 产品管理Service接口
 * 
 * @author sourcebyte.vip
 * @date 2024-06-08
 */
public interface ICmsProductService 
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
     * 批量删除产品管理
     * 
     * @param ids 需要删除的产品管理主键集合
     * @return 结果
     */
    public int deleteCmsProductByIds(Long[] ids);

    /**
     * 删除产品管理信息
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    public int deleteCmsProductById(Long id);
}
