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
import cn.source.system.domain.HouseEvaluate;
import cn.source.system.service.IHouseEvaluateService;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.common.core.page.TableDataInfo;

/**
 * 房源评价Controller
 * 
 * @author wind
 * @date 2023-04-14
 */
@RestController
@RequestMapping("/system/evaluate")
public class HouseEvaluateController extends BaseController
{
    @Autowired
    private IHouseEvaluateService houseEvaluateService;

    /**
     * 查询房源评价列表
     */
    @PreAuthorize("@ss.hasPermi('system:evaluate:list')")
    @GetMapping("/list")
    public TableDataInfo list(HouseEvaluate houseEvaluate)
    {
        startPage();
        List<HouseEvaluate> list = houseEvaluateService.selectHouseEvaluateList(houseEvaluate);
        return getDataTable(list);
    }

    /**
     * 导出房源评价列表
     */
    @PreAuthorize("@ss.hasPermi('system:evaluate:export')")
    @Log(title = "房源评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HouseEvaluate houseEvaluate)
    {
        List<HouseEvaluate> list = houseEvaluateService.selectHouseEvaluateList(houseEvaluate);
        ExcelUtil<HouseEvaluate> util = new ExcelUtil<HouseEvaluate>(HouseEvaluate.class);
        util.exportExcel(response, list, "房源评价数据");
    }

    /**
     * 获取房源评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:evaluate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(houseEvaluateService.selectHouseEvaluateById(id));
    }

    /**
     * 新增房源评价
     */
    @PreAuthorize("@ss.hasPermi('system:evaluate:add')")
    @Log(title = "房源评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HouseEvaluate houseEvaluate)
    {
        houseEvaluate.setEvalUserId(getUserId());
        houseEvaluate.setEvalUser(getUsername());
        return toAjax(houseEvaluateService.insertHouseEvaluate(houseEvaluate));
    }

    /**
     * 修改房源评价
     */
    @PreAuthorize("@ss.hasPermi('system:evaluate:edit')")
    @Log(title = "房源评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HouseEvaluate houseEvaluate)
    {
        return toAjax(houseEvaluateService.updateHouseEvaluate(houseEvaluate));
    }

    /**
     * 删除房源评价
     */
    @PreAuthorize("@ss.hasPermi('system:evaluate:remove')")
    @Log(title = "房源评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(houseEvaluateService.deleteHouseEvaluateByIds(ids));
    }
}
