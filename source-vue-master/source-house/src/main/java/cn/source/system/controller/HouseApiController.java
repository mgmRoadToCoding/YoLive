package cn.source.system.controller;

import cn.source.common.constant.HttpStatus;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.domain.entity.SysUser;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.utils.DateUtils;
import cn.source.common.utils.StringUtils;
import cn.source.system.domain.HouseEvaluate;
import cn.source.system.domain.HouseRoom;
import cn.source.system.domain.HouseUser;
import cn.source.system.domain.HouseVillage;
import cn.source.system.service.*;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: house api控制类
 * @author: 詹Sir
 */
@RestController
@RequestMapping("/api/houseApi")
public class HouseApiController extends BaseController {

    @Autowired
    private IHouseVillageService houseVillageService;

    @Autowired
    private IHouseRoomService houseRoomService;

    @Autowired
    private IHouseEvaluateService houseEvaluateService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IHouseUserService houseUserService;

    /**
     * @Description: 获取小区列表
     */
    @GetMapping("/findVillageList")
    public TableDataInfo findVillageList(HouseVillage houseVillage)
    {
        startPage();
        List<HouseVillage> list = houseVillageService.selectHouseVillageList(houseVillage);
        return getDataTable(list);
    }

    /**
     * @Description: 获取房源列表
     */
    @GetMapping("/findHouseRoomList")
    public TableDataInfo findHouseRoomList(HouseRoom houseRoom)
    {
        startPage();
        List<HouseRoom> list = houseRoomService.findHouseRoomList(houseRoom);
        return getDataTable(list);
    }


    /**
     * @Description: 添加房源信息
     */
    @PostMapping("/saveHouse")
    public AjaxResult saveHouse(@RequestBody HouseRoom houseRoom)
    {
        String msg = "房源添加成功";
        houseRoomService.apiInsertHouseRoom(houseRoom);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS,msg,houseRoom);
        return ajaxResult;
    }

    /**
     * @Description: 修改房源信息
     */
    @PostMapping("/updateHouse")
    public AjaxResult updateHouse(@RequestBody HouseRoom houseRoom)
    {
        String msg = "房源修改成功";
        houseRoomService.updateHouseRoom(houseRoom);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS,msg,houseRoom);
        return ajaxResult;
    }

    /**
     * @Description: 获取房源信息by id
     */
    @GetMapping("/findHouseById")
    public AjaxResult findHouseById(@RequestParam Long id)
    {
        String msg = "房源查找成功";
        HouseRoom houseRoom = houseRoomService.selectHouseRoomById(id);
        houseRoom = houseRoomService.selectDetailHouseRoom(houseRoom);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS,msg,houseRoom);
        return ajaxResult;
    }

    /**
     * @Description: 收藏/取消收藏
     */
    @PostMapping("/saveHeart")
    public AjaxResult saveHeart(@RequestBody JSONObject json)
    {
        boolean heart = json.getBoolean("heart");
        Long userId = json.getLong("userId");
        String houseId = json.getString("houseId");
        String msg = houseRoomService.saveHeart(heart,userId,houseId);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS,msg);
        return ajaxResult;
    }

    /**
     * @Description: 判断是否收藏
     */
    @GetMapping("/selectHouseHeart")
    public AjaxResult selectHouseHeart(Long userId,String houseId)
    {
        String msg = "是否收藏";
        Map<String,Object> houseMap=new HashMap<String,Object>();
        houseMap.put("houseId",houseId);
        houseMap.put("userId",userId);
        // 判断是否点赞
        Long id = houseRoomService.selectHouseHeart(houseMap);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS,msg, StringUtils.isNotNull(id)?true:false);
        return ajaxResult;
    }

    /**
     * @Description: 获取收藏房源列表
     */
    @GetMapping("/findHouseHeartList")
    public TableDataInfo findHouseHeartList(Long userId)
    {
        startPage();
        List<HouseRoom> list = houseRoomService.findHouseHeartList(userId);
        return getDataTable(list);
    }

    /**
     * 查询房源评价列表
     * @param houseId
     * @return
     */
    @GetMapping("/selectHouseEvals")
    public TableDataInfo selectHouseEvals(Long houseId)
    {
        HouseEvaluate houseEvaluate = new HouseEvaluate();
        houseEvaluate.setHouseId(houseId);
        houseEvaluate.setState(1);//只查询显示的
        startPage();
        List<HouseEvaluate> list = houseEvaluateService.selectHouseEvaluateList(houseEvaluate);
        return getDataTable(list);
    }

    /**
     * 新增评价
     * @param houseId
     * @param evalu
     * @return
     */
    @GetMapping("/saveHouseEvals")
    public AjaxResult saveHouseEvals(Long houseId,String evalu,Long userId)
    {
        HouseEvaluate houseEvaluate = new HouseEvaluate();
        houseEvaluate.setHouseId(houseId);
        houseEvaluate.setEvaluate(evalu);
        houseEvaluate.setState(1);//只查询显示的
        houseEvaluate.setCreateTime(DateUtils.getNowDate());
        houseEvaluate.setEvalUserId(userId);
        SysUser sysUser = userService.selectUserById(userId);
        houseEvaluate.setEvalUser(sysUser.getUserName());
        houseEvaluateService.insertHouseEvaluate(houseEvaluate);
        return AjaxResult.success(houseEvaluate);
    }

    /**
     * 评价鉴权，只有历史租客才能评价
     * @param houseId
     * @param userId
     * @return
     */
    @GetMapping("/checkAuthEvals")
    public AjaxResult checkAuthEvals(Long houseId,Long userId)
    {
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(houseId);
        houseUser.setUserId(userId);
        List<HouseUser> list = houseUserService.selectHouseUserList(houseUser);
        return AjaxResult.success(list);
    }
}
