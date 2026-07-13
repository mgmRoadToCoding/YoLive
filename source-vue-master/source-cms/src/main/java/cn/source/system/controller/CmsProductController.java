package cn.source.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.enums.BusinessType;
import cn.source.system.domain.CmsProduct;
import cn.source.system.service.ICmsProductService;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.common.core.page.TableDataInfo;

/**
 * 产品管理Controller
 *
 * @author sourcebyte.vip
 * @date 2024-06-08
 */
@RestController
@RequestMapping("/system/product")
public class CmsProductController extends BaseController
{
    @Autowired
    private ICmsProductService cmsProductService;

    /**
     * 查询产品管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(CmsProduct cmsProduct)
    {
        startPage();
        List<CmsProduct> list = cmsProductService.selectCmsProductList(cmsProduct);
        return getDataTable(list);
    }

    /**
     * 获取产品管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cmsProductService.selectCmsProductById(id));
    }

    /**
     * 新增产品管理
     */
    @PreAuthorize("@ss.hasPermi('system:product:add')")
    @Log(title = "产品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CmsProduct cmsProduct)
    {
        return toAjax(cmsProductService.insertCmsProduct(cmsProduct));
    }

    /**
     * 修改产品管理
     */
    @PreAuthorize("@ss.hasPermi('system:product:edit')")
    @Log(title = "产品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CmsProduct cmsProduct)
    {
        return toAjax(cmsProductService.updateCmsProduct(cmsProduct));
    }

    /**
     * 删除产品管理
     */
    @PreAuthorize("@ss.hasPermi('system:product:remove')")
    @Log(title = "产品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cmsProductService.deleteCmsProductByIds(ids));
    }

    /**
     * 导出产品管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:product:export')")
    @Log(title = "产品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CmsProduct cmsProduct)
    {
        List<CmsProduct> list = cmsProductService.selectCmsProductList(cmsProduct);
        ExcelUtil<CmsProduct> util = new ExcelUtil<CmsProduct>(CmsProduct.class);
        util.exportExcel(response, list, "产品管理数据");
    }
}
