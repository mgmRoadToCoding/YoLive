package cn.source.system.controller;

import cn.source.common.constant.HttpStatus;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.page.TableDataInfo;
import cn.source.system.domain.*;
import cn.source.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: mall api控制类
 * @author: 詹Sir
 */
@RestController
@RequestMapping("/api/mallApi")
public class MallApiController extends BaseController {

    @Autowired
    private IMallClassifyService mallClassifyService;
    @Autowired
    private IMallGoodsService mallGoodsService;
    @Autowired
    private IMallNavigateService mallNavigateService;
    @Autowired
    private IMallAdvertService mallAdvertService;
    @Autowired
    private IMallAddressService mallAddressService;
    @Autowired
    private IMallOrderService mallOrderService;


    /**
     * @Description: 获取首页初始化数据MallIndex
     */
    @GetMapping("/findMallIndexList")
    public AjaxResult findMallIndexList(MallAdvert mallAdvert, MallNavigate mallNavigate, MallGoods mallGoods)
    {
        MallIndex mllIndex = new MallIndex();
        /**广告*/
        mllIndex.setAdvertList(mallAdvertService.selectMallAdvertList(mallAdvert));
        /**导航*/
        mllIndex.setNavigateList(mallNavigateService.selectMallNavigateList(mallNavigate));
        // 分页
        startPage();
        /**商品*/
        mllIndex.setGoodsList(mallGoodsService.selectMallGoodsList(mallGoods));
        return AjaxResult.success(mllIndex);
    }


    /**
     * @Description: 获取首页初始化数据MallIndex
     */
    @GetMapping("/findGoodsList")
    public TableDataInfo findGoodsList(MallGoods mallGoods)
    {
        // 分页
        startPage();
        /**商品*/
        List<MallGoods>  goodsList = mallGoodsService.selectMallGoodsList(mallGoods);
        return getDataTable(goodsList);
    }

    /**
     * @Description: 获取分类数据
     */
    @GetMapping("/findClassifyList")
    public TableDataInfo findClassifyList(MallClassify mallClassify)
    {
        // 分页
        startPage();
        /**分类*/
        mallClassify.setParentId(0L);
        List<MallClassify>  classifyList = mallClassifyService.selectMallClassifyList(mallClassify);
        for(MallClassify classify:classifyList){
            MallClassify child = new MallClassify();
            child.setParentId(classify.getId());
            classify.setChildren(mallClassifyService.selectMallClassifyList(child));
        }
        return getDataTable(classifyList);
    }


    /**
     * @Description: 获取商品信息by id
     */
    @GetMapping("/findGoodsById")
    public AjaxResult findGoodsById(@RequestParam Long id)
    {
        String msg = "商品查找成功";
        MallGoods mallGoods = mallGoodsService.selectMallGoodsById(id);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS,msg,mallGoods);
        return ajaxResult;
    }

    /**
     * @Description: 获取地址列表
     */
    @GetMapping("/findAddressList")
    public TableDataInfo findAddressList(MallAddress mallAddress)
    {
        startPage();
        List<MallAddress> list = mallAddressService.selectApiMallAddressList(mallAddress);
        return getDataTable(list);
    }

    /**
     * @Description: 新增收货地址
     */
    @PostMapping("/insertAddress")
    public AjaxResult insertAddress(@RequestBody MallAddress mallAddress)
    {
        return toAjax(mallAddressService.insertMallAddress(mallAddress));
    }

    /**
     * @Description: 修改收货地址
     */
    @PostMapping("/updateAddress")
    public AjaxResult updateAddress(@RequestBody MallAddress mallAddress)
    {
        return toAjax(mallAddressService.updateMallAddress(mallAddress));
    }

    /**
     * @Description: 删除收货地址by id
     */
    @DeleteMapping("deleteAddress/{id}")
    public AjaxResult deleteAddress(@PathVariable Long id)
    {
        return toAjax(mallAddressService.deleteMallAddressById(id));
    }

    /**
     * @Description: 获取收货地址by id
     */
    @GetMapping("/findAddressById")
    public AjaxResult findAddressById(@RequestParam Long id)
    {
        String msg = "收货地址查找成功";
        MallAddress mallAddress = mallAddressService.selectMallAddressById(id);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS,msg,mallAddress);
        return ajaxResult;
    }

    /**
     * @Description: 新增订单
     */
    @PostMapping("/insertMallOrder")
    public AjaxResult insertMallOrder(@RequestBody MallOrder mallOrder)
    {
        return toAjax(mallOrderService.insertMallOrder(mallOrder));
    }
}
