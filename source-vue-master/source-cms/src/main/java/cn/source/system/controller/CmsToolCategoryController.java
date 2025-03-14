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
import cn.source.system.domain.CmsToolCategory;
import cn.source.system.service.ICmsToolCategoryService;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.common.core.page.TableDataInfo;

/**
 * 工具分类Controller
 *
 * @author sourcebyte.vip
 * @date 2024-12-29
 */
@RestController
@RequestMapping("/system/toolCategory")
public class CmsToolCategoryController extends BaseController
{
    @Autowired
    private ICmsToolCategoryService cmsToolCategoryService;

    /**
     * 查询工具分类列表
     */
    @PreAuthorize("@ss.hasPermi('system:toolCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(CmsToolCategory cmsToolCategory)
    {
        startPage();
        List<CmsToolCategory> list = cmsToolCategoryService.selectCmsToolCategoryList(cmsToolCategory);
        return getDataTable(list);
    }

    /**
     * 获取工具分类详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cmsToolCategoryService.selectCmsToolCategoryById(id));
    }

    /**
     * 新增工具分类
     */
    @PreAuthorize("@ss.hasPermi('system:toolCategory:add')")
    @Log(title = "工具分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CmsToolCategory cmsToolCategory)
    {
        return toAjax(cmsToolCategoryService.insertCmsToolCategory(cmsToolCategory));
    }

    /**
     * 修改工具分类
     */
    @PreAuthorize("@ss.hasPermi('system:toolCategory:edit')")
    @Log(title = "工具分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CmsToolCategory cmsToolCategory)
    {
        return toAjax(cmsToolCategoryService.updateCmsToolCategory(cmsToolCategory));
    }

    /**
     * 删除工具分类
     */
    @PreAuthorize("@ss.hasPermi('system:toolCategory:remove')")
    @Log(title = "工具分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cmsToolCategoryService.deleteCmsToolCategoryByIds(ids));
    }

    /**
     * 导出工具分类列表
     */
    @PreAuthorize("@ss.hasPermi('system:toolCategory:export')")
    @Log(title = "工具分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CmsToolCategory cmsToolCategory)
    {
        List<CmsToolCategory> list = cmsToolCategoryService.selectCmsToolCategoryList(cmsToolCategory);
        ExcelUtil<CmsToolCategory> util = new ExcelUtil<CmsToolCategory>(CmsToolCategory.class);
        util.exportExcel(response, list, "工具分类数据");
    }
}
