package cn.source.system.controller;

import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.enums.BusinessType;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.system.domain.MallNavigate;
import cn.source.system.service.IMallNavigateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 导航管理Controller
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@RestController
@RequestMapping("/system/navigate")
public class MallNavigateController extends BaseController
{
    @Autowired
    private IMallNavigateService mallNavigateService;

    /**
     * 查询导航管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:navigate:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallNavigate mallNavigate)
    {
        startPage();
        List<MallNavigate> list = mallNavigateService.selectMallNavigateList(mallNavigate);
        return getDataTable(list);
    }

    /**
     * 导出导航管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:navigate:export')")
    @Log(title = "导航管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallNavigate mallNavigate)
    {
        List<MallNavigate> list = mallNavigateService.selectMallNavigateList(mallNavigate);
        ExcelUtil<MallNavigate> util = new ExcelUtil<MallNavigate>(MallNavigate.class);
        util.exportExcel(response, list, "导航管理数据");
    }

    /**
     * 获取导航管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:navigate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mallNavigateService.selectMallNavigateById(id));
    }

    /**
     * 新增导航管理
     */
    @PreAuthorize("@ss.hasPermi('system:navigate:add')")
    @Log(title = "导航管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallNavigate mallNavigate)
    {
        return toAjax(mallNavigateService.insertMallNavigate(mallNavigate));
    }

    /**
     * 修改导航管理
     */
    @PreAuthorize("@ss.hasPermi('system:navigate:edit')")
    @Log(title = "导航管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallNavigate mallNavigate)
    {
        return toAjax(mallNavigateService.updateMallNavigate(mallNavigate));
    }

    /**
     * 删除导航管理
     */
    @PreAuthorize("@ss.hasPermi('system:navigate:remove')")
    @Log(title = "导航管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallNavigateService.deleteMallNavigateByIds(ids));
    }
}
