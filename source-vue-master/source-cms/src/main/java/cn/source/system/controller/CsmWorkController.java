package cn.source.system.controller;

import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.enums.BusinessType;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.system.domain.CsmWork;
import cn.source.system.service.ICsmWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工单管理Controller
 *
 * @author sourcebyte.vip
 * @date 2024-05-07
 */
@RestController
@RequestMapping("/system/work")
public class CsmWorkController extends BaseController {
    @Autowired
    private ICsmWorkService csmWorkService;

    /**
     * 查询工单管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(CsmWork csmWork) {
        startPage();
        List<CsmWork> list = csmWorkService.selectCsmWorkList(csmWork);
        return getDataTable(list);
    }

    /**
     * 获取工单管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(csmWorkService.selectCsmWorkById(id));
    }

    /**
     * 新增工单管理
     */
    @PreAuthorize("@ss.hasPermi('system:work:add')")
    @Log(title = "工单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CsmWork csmWork) {
        return toAjax(csmWorkService.insertCsmWork(csmWork));
    }

    /**
     * 修改工单管理
     */
    @PreAuthorize("@ss.hasPermi('system:work:edit')")
    @Log(title = "工单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CsmWork csmWork) {
        return toAjax(csmWorkService.updateCsmWork(csmWork));
    }

    /**
     * 删除工单管理
     */
    @PreAuthorize("@ss.hasPermi('system:work:remove')")
    @Log(title = "工单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(csmWorkService.deleteCsmWorkByIds(ids));
    }

    /**
     * 处理工单
     */
    @PreAuthorize("@ss.hasPermi('system:work:edit')")
    @Log(title = "处理工单", businessType = BusinessType.UPDATE)
    @PutMapping("/feedWork/{ids}")
    public AjaxResult feedWork(@PathVariable Long[] ids, String handleResult) {
        return toAjax(csmWorkService.feedWork(ids, handleResult));
    }

    /**
     * 导出工单管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:work:export')")
    @Log(title = "工单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CsmWork csmWork) {
        List<CsmWork> list = csmWorkService.selectCsmWorkList(csmWork);
        ExcelUtil<CsmWork> util = new ExcelUtil<CsmWork>(CsmWork.class);
        util.exportExcel(response, list, "工单管理数据");
    }
}
