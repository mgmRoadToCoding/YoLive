package cn.source.system.service.impl;

import cn.source.common.core.domain.entity.SysRole;
import cn.source.common.core.domain.entity.SysUser;
import cn.source.common.core.domain.model.LoginUser;
import cn.source.common.exception.ServiceException;
import cn.source.common.utils.DateUtils;
import cn.source.common.utils.SecurityUtils;
import cn.source.common.utils.StringUtils;
import cn.source.common.utils.uuid.CodeUtil;
import cn.source.system.domain.HouseRoom;
import cn.source.system.domain.HouseVillage;
import cn.source.system.enums.HouseStatus;
import cn.source.system.mapper.HouseRoomMapper;
import cn.source.system.service.IHouseRoomService;
import cn.source.system.service.IHouseVillageService;
import cn.source.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房源详情Service业务层处理
 *
 * @author 詹Sir
 * @date 2022-03-27
 */
@Service
public class HouseRoomServiceImpl implements IHouseRoomService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private IHouseVillageService houseVillageService;

    @Autowired
    private HouseRoomMapper houseRoomMapper;

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 查询房源详情
     *
     * @param id 房源详情主键
     * @return 房源详情
     */
    @Override
    public HouseRoom selectHouseRoomById(Long id)
    {
        HouseRoom houseRoom = houseRoomMapper.selectHouseRoomById(id);
        houseRoom.setFeatureList(houseRoomMapper.selectHouseFeature(houseRoom));
        houseRoom.setImageList(houseRoomMapper.selectHouseImage(houseRoom));
        return houseRoom;
    }

    /**
     * 查询房源详情列表
     *
     * @param houseRoom 房源详情
     * @return 房源详情
     */
    @Override
    public List<HouseRoom> findHouseRoomList(HouseRoom houseRoom)
    {
        return houseRoomMapper.selectHouseRoomList(houseRoom);
    }

    /**
     * 查询房源详情列表
     *
     * @param houseRoom 房源详情
     * @return 房源详情
     */
    @Override
    public List<HouseRoom> selectHouseRoomList(HouseRoom houseRoom)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin())
            {
                for (SysRole role : currentUser.getRoles())
                {
                    String dataScope = role.getDataScope();
                    // 仅有查询自己的数据权限时，将本人的房源，与负责的房源(经纪人)都查询出来
                    if (DATA_SCOPE_SELF.equals(dataScope))
                    {
                        String appendSql = StringUtils.format("house.agent_User_Id = {} ", currentUser.getUserId());
                        appendSql += StringUtils.format(" OR house.publish_id = {} ", currentUser.getUserId());
                        houseRoom.getParams().put(DATA_SCOPE, " AND (" + appendSql + ")");
                    }
                }
            }
        }
        return houseRoomMapper.selectHouseRoomList(houseRoom);
    }

    /**
     * 新增房源详情
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    @Override
    public int insertHouseRoom(HouseRoom houseRoom)
    {
        return insertRoom(houseRoom);
    }

    /**
     * 修改房源详情
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    @Override
    public int updateHouseRoom(HouseRoom houseRoom)
    {
        houseRoom.setUpdateTime(DateUtils.getNowDate());
        if(StringUtils.isNotNull(houseRoom.getFeatureList()) && houseRoom.getFeatureList().size()>0){
            // 先删除，再插入
            houseRoomMapper.deleteHouseFeatureByHouseId(houseRoom.getId());
            houseRoomMapper.insertHouseFeature(houseRoom);
        }
        if(StringUtils.isNotNull(houseRoom.getImageList()) && houseRoom.getImageList().size()>0){
            // 先删除，再插入
            houseRoomMapper.deleteHouseImageByHouseId(houseRoom.getId());
            houseRoom.setFaceUrl(houseRoom.getImageList().get(0).getImgUrl());
            houseRoomMapper.insertHouseImage(houseRoom);
        }
        return houseRoomMapper.updateHouseRoom(houseRoom);
    }

    /**
     * 设置房源经纪人
     * @param userId 选中经纪人ID
     */
    @Override
    public int updateHouseAgent(Long[] ids,Long userId)
    {
        Map<String,Object> houseMap=new HashMap<String,Object>();
        houseMap.put("ids",ids);
        SysUser sysUser = userService.selectUserById(userId);
        houseMap.put("agentUserId",userId);
        houseMap.put("agentName",sysUser.getNickName());
        houseMap.put("agentPhone",sysUser.getPhonenumber());
        houseMap.put("agentAvatar",sysUser.getAvatar());
        return houseRoomMapper.updateHouseAgent(houseMap);
    }

    /**
     * 修改房源详情
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    @Override
    public int updateHouseRoomByIds(Long[] ids,Integer state)
    {
        Map<String,Object> houseMap=new HashMap<String,Object>();
        houseMap.put("ids",ids);
        houseMap.put("state",state);
        return houseRoomMapper.updateHouseRoomByIds(houseMap);
    }

    /**
     * 批量删除房源详情
     *
     * @param ids 需要删除的房源详情主键
     * @return 结果
     */
    @Override
    public int deleteHouseRoomByIds(Long[] ids)
    {
        for (Long id : ids) {
            houseRoomMapper.deleteHouseFeatureByHouseId(id);
            houseRoomMapper.deleteHouseImageByHouseId(id);
        }
        return houseRoomMapper.deleteHouseRoomByIds(ids);
    }

    /**
     * 删除房源详情信息
     *
     * @param id 房源详情主键
     * @return 结果
     */
    @Override
    public int deleteHouseRoomById(Long id)
    {
        houseRoomMapper.deleteHouseFeatureByHouseId(id);
        houseRoomMapper.deleteHouseImageByHouseId(id);
        houseRoomMapper.deleteHouseHeartByHouseId(id);
        return houseRoomMapper.deleteHouseRoomById(id);
    }

    /**
     * API新增房源详情
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    @Override
    public int apiInsertHouseRoom(HouseRoom houseRoom)
    {
        return insertRoom(houseRoom);
    }

    /**
     * 查询房源相关详情信息
     *
     * @param houseRoom 房源
     * @return 房源详情
     */
    @Override
    public HouseRoom selectDetailHouseRoom(HouseRoom houseRoom) {
        houseRoom.setVillage(houseVillageService.selectHouseVillageById(houseRoom.getVillageId()));
        // houseRoom.setImageList(houseRoomMapper.selectHouseImage(houseRoom));
        // houseRoom.setFeatureList(houseRoomMapper.selectHouseFeature(houseRoom));
        return houseRoom;
    }


    /**
    * @Description: 将保存房源的方法统一到此方法中
    * @author: 詹Sir
    */
    public int insertRoom(HouseRoom houseRoom){
        // 待审核
        houseRoom.setState(HouseStatus.AUDIT.getCode());
        houseRoom.setCreateTime(DateUtils.getNowDate());
        if(StringUtils.isNull(houseRoom.getPublishId())){
            houseRoom.setPublishId(SecurityUtils.getUserId());
        }
        if(StringUtils.isNull(houseRoom.getStartDate())){
            houseRoom.setStartDate(new Date());
        }
        if(StringUtils.isEmpty(houseRoom.getVillageName())){
            HouseVillage houseVillage = houseVillageService.selectHouseVillageById(houseRoom.getVillageId());
            houseRoom.setVillageName(houseVillage.getName());
        }
        if(StringUtils.isNull(houseRoom.getVillageId())){
            HouseVillage houseVillage = new HouseVillage();
            houseVillage.setName(houseRoom.getVillageName());
            houseVillage = houseVillageService.selectHouseVillage(houseVillage);
            houseRoom.setVillageId(houseVillage.getId());
        }
        houseRoom.setCode(CodeUtil.getCodeByUUId());
        // 封面图设置默认值
        houseRoom.setFaceUrl("https://sourcebyte.vip/profile/customer/file/loading.png");
        houseRoomMapper.insertHouseRoom(houseRoom);
        if(StringUtils.isNotNull(houseRoom.getFeatureList()) && houseRoom.getFeatureList().size()>0){
            houseRoomMapper.insertHouseFeature(houseRoom);
        }
        if(StringUtils.isNotNull(houseRoom.getImageList()) && houseRoom.getImageList().size()>0){
            houseRoom.setFaceUrl(houseRoom.getImageList().get(0).getImgUrl());
            houseRoomMapper.insertHouseImage(houseRoom);
        }
        return houseRoomMapper.updateHouseRoom(houseRoom);
    }
    /**
     * 导入房源数据
     *
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importHouse(List<HouseRoom> houseList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(houseList) || houseList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int execlSortNo = 1;
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (HouseRoom houseRoom : houseList)
        {
            execlSortNo++;
            try{
                if(StringUtils.isNull(houseRoom.getType())){
                    failureNum++;
                    failureMsg.append("<br/>Execl第 "+execlSortNo+" 行类型不能为空,需填写整租/合租");
                }
                if(StringUtils.isEmpty(houseRoom.getHouseNum())){
                    failureNum++;
                    failureMsg.append("<br/>Execl第 "+execlSortNo+" 行居室不能为空");
                }
                if(StringUtils.isEmpty(houseRoom.getHouseHall())){
                    failureNum++;
                    failureMsg.append("<br/>Execl第 "+execlSortNo+" 行厅室不能为空");
                }
                if(StringUtils.isEmpty(houseRoom.getCode())){
                    successNum++;
                    successMsg.append("<br/>Execl第 "+execlSortNo+" 行新增成功");
                    insertRoom(houseRoom);
                }else{
                    HouseRoom house = houseRoomMapper.selectHouseRoomByCode(houseRoom.getCode());
                    if(StringUtils.isNotNull(house) && isUpdateSupport){
                        successNum++;
                        successMsg.append("<br/>Execl第 "+execlSortNo+" 行更新成功");
                        houseRoom.setId(house.getId());
                        updateHouseRoom(houseRoom);
                    }else{
                        failureNum++;
                        failureMsg.append("<br/>Execl第 "+execlSortNo+" 行房源已存在，如需覆盖请勾选确认更新");
                    }
                }
            }catch (Exception e){
                failureNum++;
                String msg = "<br/>Execl第 "+execlSortNo+" 行导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0){
            failureMsg.insert(0, "很抱歉，导入出现错误！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }else{
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 收藏/取消收藏
     */
    @Override
    public String saveHeart(boolean heart,Long userId,String houseId)
    {
        String msg = "收藏成功";
        Map<String,Object> houseMap=new HashMap<String,Object>();
        houseMap.put("houseId",houseId);
        houseMap.put("userId",userId);
        if(!heart){
            // 收藏
            houseRoomMapper.saveHeart(houseMap);
        }else {
            // 取消收藏
            houseRoomMapper.cancelHeart(houseMap);
            msg = "取消收藏";
        }
        return msg;
    }

    /**
     * 查询收藏数据
     */
    @Override
    public Long selectHouseHeart(Map<String,Object> houseMap)
    {
        return houseRoomMapper.selectHouseHeart(houseMap);
    }


    /**
     * 获取收藏房源列表
     */
    @Override
    public List<HouseRoom> findHouseHeartList(Long userId)
    {
        return houseRoomMapper.findHouseHeartList(userId);
    }
}
