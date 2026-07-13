package cn.source.system.service.impl;

import java.util.List;
import cn.source.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.source.system.mapper.CmsProductMapper;
import cn.source.system.domain.CmsProduct;
import cn.source.system.service.ICmsProductService;

/**
 * 产品管理Service业务层处理
 * 
 * @author sourcebyte.vip
 * @date 2024-06-08
 */
@Service
public class CmsProductServiceImpl implements ICmsProductService 
{
    @Autowired
    private CmsProductMapper cmsProductMapper;

    /**
     * 查询产品管理
     * 
     * @param id 产品管理主键
     * @return 产品管理
     */
    @Override
    public CmsProduct selectCmsProductById(Long id)
    {
        return cmsProductMapper.selectCmsProductById(id);
    }

    /**
     * 查询产品管理列表
     * 
     * @param cmsProduct 产品管理
     * @return 产品管理
     */
    @Override
    public List<CmsProduct> selectCmsProductList(CmsProduct cmsProduct)
    {
        return cmsProductMapper.selectCmsProductList(cmsProduct);
    }

    /**
     * 新增产品管理
     * 
     * @param cmsProduct 产品管理
     * @return 结果
     */
    @Override
    public int insertCmsProduct(CmsProduct cmsProduct)
    {
        cmsProduct.setCreateTime(DateUtils.getNowDate());
        return cmsProductMapper.insertCmsProduct(cmsProduct);
    }

    /**
     * 修改产品管理
     * 
     * @param cmsProduct 产品管理
     * @return 结果
     */
    @Override
    public int updateCmsProduct(CmsProduct cmsProduct)
    {
        cmsProduct.setUpdateTime(DateUtils.getNowDate());
        return cmsProductMapper.updateCmsProduct(cmsProduct);
    }

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的产品管理主键
     * @return 结果
     */
    @Override
    public int deleteCmsProductByIds(Long[] ids)
    {
        return cmsProductMapper.deleteCmsProductByIds(ids);
    }

    /**
     * 删除产品管理信息
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    @Override
    public int deleteCmsProductById(Long id)
    {
        return cmsProductMapper.deleteCmsProductById(id);
    }
}
