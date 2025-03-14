package cn.source.system.controller;

import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.enums.BusinessType;
import cn.source.common.utils.SecurityUtils;
import cn.source.common.utils.poi.ExcelUtil;
import cn.source.system.domain.MallAddress;
import cn.source.system.service.IMallAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 收货地址Controller
 *
 * @author 詹Sir
 * @date 2022-06-19
 */
@RestController
@RequestMapping("/system/address")
public class MallAddressController extends BaseController
{
    @Autowired
    private IMallAddressService mallAddressService;

    /**
     * 查询收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallAddress mallAddress)
    {
        startPage();
        List<MallAddress> list = mallAddressService.selectMallAddressList(mallAddress);
        return getDataTable(list);
    }

    /**
     * 导出收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:export')")
    @Log(title = "收货地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallAddress mallAddress)
    {
        List<MallAddress> list = mallAddressService.selectMallAddressList(mallAddress);
        ExcelUtil<MallAddress> util = new ExcelUtil<MallAddress>(MallAddress.class);
        util.exportExcel(response, list, "收货地址数据");
    }

    /**
     * 获取收货地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:address:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mallAddressService.selectMallAddressById(id));
    }

    /**
     * 新增收货地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:add')")
    @Log(title = "收货地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallAddress mallAddress)
    {
        return toAjax(mallAddressService.insertMallAddress(mallAddress));
    }

    /**
     * 修改收货地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:edit')")
    @Log(title = "收货地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallAddress mallAddress)
    {
        mallAddress.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(mallAddressService.updateMallAddress(mallAddress));
    }

    /**
     * 删除收货地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:remove')")
    @Log(title = "收货地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mallAddressService.deleteMallAddressByIds(ids));
    }

    /**
     * 设置默认地址isDefault
     */
    @PreAuthorize("@ss.hasPermi('system:address:edit')")
    @Log(title = "收货地址", businessType = BusinessType.UPDATE)
    @PutMapping("/changeIsDefaultStatus")
    public AjaxResult changeIsDefaultStatus(@RequestBody MallAddress mallAddress)
    {
        return toAjax(mallAddressService.updateMallAddress(mallAddress));
    }

    /**
     * 设置是否启用normalDisable
     */
    @PreAuthorize("@ss.hasPermi('system:address:edit')")
    @Log(title = "收货地址", businessType = BusinessType.UPDATE)
    @PutMapping("/changeNormalDisableStatus")
    public AjaxResult changeNormalDisableStatus(@RequestBody MallAddress mallAddress)
    {
        return toAjax(mallAddressService.updateMallAddress(mallAddress));
    }
}
