package cn.source.system.controller;

import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.enums.BusinessType;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.system.domain.MallGoodsSpec;
import cn.source.system.service.IMallGoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品规格Controller
 *
 * @author Mr Sir
 * @date 2022-05-02
 */
@RestController
@RequestMapping("/system/spec")
public class MallGoodsSpecController extends BaseController
{
    @Autowired
    private IMallGoodsSpecService mallGoodsSpecService;

    /**
     * 查询商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('system:spec:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallGoodsSpec mallGoodsSpec)
    {
        startPage();
        List<MallGoodsSpec> list = mallGoodsSpecService.selectMallGoodsSpecList(mallGoodsSpec);
        return getDataTable(list);
    }

    /**
     * 导出商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('system:spec:export')")
    @Log(title = "商品规格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallGoodsSpec mallGoodsSpec)
    {
        List<MallGoodsSpec> list = mallGoodsSpecService.selectMallGoodsSpecList(mallGoodsSpec);
        ExcelUtil<MallGoodsSpec> util = new ExcelUtil<MallGoodsSpec>(MallGoodsSpec.class);
        util.exportExcel(response, list, "商品规格数据");
    }

    /**
     * 获取商品规格详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:spec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mallGoodsSpecService.selectMallGoodsSpecById(id));
    }

    /**
     * 新增商品规格
     */
    @PreAuthorize("@ss.hasPermi('system:spec:add')")
    @Log(title = "商品规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallGoodsSpec mallGoodsSpec)
    {
        return toAjax(mallGoodsSpecService.insertMallGoodsSpec(mallGoodsSpec));
    }

    /**
     * 修改商品规格
     */
    @PreAuthorize("@ss.hasPermi('system:spec:edit')")
    @Log(title = "商品规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallGoodsSpec mallGoodsSpec)
    {
        return toAjax(mallGoodsSpecService.updateMallGoodsSpec(mallGoodsSpec));
    }

    /**
     * 删除商品规格
     */
    @PreAuthorize("@ss.hasPermi('system:spec:remove')")
    @Log(title = "商品规格", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallGoodsSpecService.deleteMallGoodsSpecByIds(ids));
    }
}
