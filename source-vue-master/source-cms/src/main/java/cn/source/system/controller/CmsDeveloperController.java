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
import cn.source.system.domain.CmsDeveloper;
import cn.source.system.service.ICmsDeveloperService;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.common.core.page.TableDataInfo;

/**
 * 成员管理Controller
 *
 * @author sourcebyte.vip
 * @date 2024-04-27
 */
@RestController
@RequestMapping("/system/developer")
public class CmsDeveloperController extends BaseController
{
    @Autowired
    private ICmsDeveloperService cmsDeveloperService;

    /**
     * 查询成员管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:developer:list')")
    @GetMapping("/list")
    public TableDataInfo list(CmsDeveloper cmsDeveloper)
    {
        startPage();
        List<CmsDeveloper> list = cmsDeveloperService.selectCmsDeveloperList(cmsDeveloper);
        return getDataTable(list);
    }

    /**
     * 导出成员管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:developer:export')")
    @Log(title = "成员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CmsDeveloper cmsDeveloper)
    {
        List<CmsDeveloper> list = cmsDeveloperService.selectCmsDeveloperList(cmsDeveloper);
        ExcelUtil<CmsDeveloper> util = new ExcelUtil<CmsDeveloper>(CmsDeveloper.class);
        util.exportExcel(response, list, "成员管理数据");
    }

    /**
     * 获取成员管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:developer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cmsDeveloperService.selectCmsDeveloperById(id));
    }

    /**
     * 新增成员管理
     */
    @PreAuthorize("@ss.hasPermi('system:developer:add')")
    @Log(title = "成员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CmsDeveloper cmsDeveloper)
    {
        return toAjax(cmsDeveloperService.insertCmsDeveloper(cmsDeveloper));
    }

    /**
     * 修改成员管理
     */
    @PreAuthorize("@ss.hasPermi('system:developer:edit')")
    @Log(title = "成员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CmsDeveloper cmsDeveloper)
    {
        return toAjax(cmsDeveloperService.updateCmsDeveloper(cmsDeveloper));
    }

    /**
     * 删除成员管理
     */
    @PreAuthorize("@ss.hasPermi('system:developer:remove')")
    @Log(title = "成员管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cmsDeveloperService.deleteCmsDeveloperByIds(ids));
    }
}
