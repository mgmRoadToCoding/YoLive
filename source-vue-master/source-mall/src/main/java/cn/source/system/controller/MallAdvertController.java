package cn.source.system.controller;

import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.enums.BusinessType;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.system.domain.MallAdvert;
import cn.source.system.service.IMallAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 广告管理Controller
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@RestController
@RequestMapping("/system/advert")
public class MallAdvertController extends BaseController
{
    @Autowired
    private IMallAdvertService mallAdvertService;

    /**
     * 查询广告管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:advert:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallAdvert mallAdvert)
    {
        startPage();
        List<MallAdvert> list = mallAdvertService.selectMallAdvertList(mallAdvert);
        return getDataTable(list);
    }

    /**
     * 导出广告管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:advert:export')")
    @Log(title = "广告管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallAdvert mallAdvert)
    {
        List<MallAdvert> list = mallAdvertService.selectMallAdvertList(mallAdvert);
        ExcelUtil<MallAdvert> util = new ExcelUtil<MallAdvert>(MallAdvert.class);
        util.exportExcel(response, list, "广告管理数据");
    }

    /**
     * 获取广告管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:advert:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mallAdvertService.selectMallAdvertById(id));
    }

    /**
     * 新增广告管理
     */
    @PreAuthorize("@ss.hasPermi('system:advert:add')")
    @Log(title = "广告管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallAdvert mallAdvert)
    {
        return toAjax(mallAdvertService.insertMallAdvert(mallAdvert));
    }

    /**
     * 修改广告管理
     */
    @PreAuthorize("@ss.hasPermi('system:advert:edit')")
    @Log(title = "广告管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallAdvert mallAdvert)
    {
        return toAjax(mallAdvertService.updateMallAdvert(mallAdvert));
    }

    /**
     * 删除广告管理
     */
    @PreAuthorize("@ss.hasPermi('system:advert:remove')")
    @Log(title = "广告管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallAdvertService.deleteMallAdvertByIds(ids));
    }
}
