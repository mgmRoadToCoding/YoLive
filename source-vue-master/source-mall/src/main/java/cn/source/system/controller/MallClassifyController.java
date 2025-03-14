package cn.source.system.controller;

import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.enums.BusinessType;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.system.domain.MallClassify;
import cn.source.system.service.IMallClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分类管理Controller
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@RestController
@RequestMapping("/system/classify")
public class MallClassifyController extends BaseController
{
    @Autowired
    private IMallClassifyService mallClassifyService;

    /**
     * 查询分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:classify:list')")
    @GetMapping("/list")
    public AjaxResult list(MallClassify mallClassify)
    {
        List<MallClassify> list = mallClassifyService.selectMallClassifyList(mallClassify);
        return AjaxResult.success(list);
    }

    /**
     * 导出分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:classify:export')")
    @Log(title = "分类管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallClassify mallClassify)
    {
        List<MallClassify> list = mallClassifyService.selectMallClassifyList(mallClassify);
        ExcelUtil<MallClassify> util = new ExcelUtil<MallClassify>(MallClassify.class);
        util.exportExcel(response, list, "分类管理数据");
    }

    /**
     * 获取分类管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:classify:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mallClassifyService.selectMallClassifyById(id));
    }

    /**
     * 新增分类管理
     */
    @PreAuthorize("@ss.hasPermi('system:classify:add')")
    @Log(title = "分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallClassify mallClassify)
    {
        return toAjax(mallClassifyService.insertMallClassify(mallClassify));
    }

    /**
     * 修改分类管理
     */
    @PreAuthorize("@ss.hasPermi('system:classify:edit')")
    @Log(title = "分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallClassify mallClassify)
    {
        return toAjax(mallClassifyService.updateMallClassify(mallClassify));
    }

    /**
     * 删除分类管理
     */
    @PreAuthorize("@ss.hasPermi('system:classify:remove')")
    @Log(title = "分类管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallClassifyService.deleteMallClassifyByIds(ids));
    }
}
