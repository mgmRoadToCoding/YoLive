package cn.source.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.source.system.domain.vo.CmsToolVo;
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
import cn.source.system.domain.CmsTool;
import cn.source.system.service.ICmsToolService;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.common.core.page.TableDataInfo;

/**
 * 工具管理Controller
 *
 * @author sourcebyte.vip
 * @date 2024-12-29
 */
@RestController
@RequestMapping("/system/tool")
public class CmsToolController extends BaseController
{
    @Autowired
    private ICmsToolService cmsToolService;

    /**
     * 查询工具管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tool:list')")
    @GetMapping("/list")
    public TableDataInfo list(CmsTool cmsTool)
    {
        startPage();
        List<CmsToolVo> list = cmsToolService.selectCmsToolList(cmsTool);
        return getDataTable(list);
    }

    /**
     * 获取工具管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cmsToolService.selectCmsToolById(id));
    }

    /**
     * 新增工具管理
     */
    @PreAuthorize("@ss.hasPermi('system:tool:add')")
    @Log(title = "工具管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CmsTool cmsTool)
    {
        return toAjax(cmsToolService.insertCmsTool(cmsTool));
    }

    /**
     * 修改工具管理
     */
    @PreAuthorize("@ss.hasPermi('system:tool:edit')")
    @Log(title = "工具管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CmsTool cmsTool)
    {
        return toAjax(cmsToolService.updateCmsTool(cmsTool));
    }

    /**
     * 删除工具管理
     */
    @PreAuthorize("@ss.hasPermi('system:tool:remove')")
    @Log(title = "工具管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cmsToolService.deleteCmsToolByIds(ids));
    }

    /**
     * 导出工具管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tool:export')")
    @Log(title = "工具管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CmsTool cmsTool)
    {
        List<CmsToolVo> list = cmsToolService.selectCmsToolList(cmsTool);
        ExcelUtil<CmsToolVo> util = new ExcelUtil<CmsToolVo>(CmsToolVo.class);
        util.exportExcel(response, list, "工具管理数据");
    }
}
