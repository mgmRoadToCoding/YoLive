package cn.source.system.service;

import cn.source.system.domain.HouseRoom;

import java.util.List;
import java.util.Map;

/**
 * 房源详情Service接口
 *
 * @author 詹Sir
 * @date 2022-03-27
 */
public interface IHouseRoomService
{
    /**
     * 查询房源详情
     *
     * @param id 房源详情主键
     * @return 房源详情
     */
    public HouseRoom selectHouseRoomById(Long id);

    /**
     * 查询房源详情列表
     *
     * @param houseRoom 房源详情
     * @return 房源详情集合
     */
    public List<HouseRoom> findHouseRoomList(HouseRoom houseRoom);

    /**
     * 查询房源详情列表
     *
     * @param houseRoom 房源详情
     * @return 房源详情集合
     */
    public List<HouseRoom> selectHouseRoomList(HouseRoom houseRoom);

    /**
     * 新增房源详情
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    public int insertHouseRoom(HouseRoom houseRoom);

    /**
     * 修改房源详情
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    public int updateHouseRoom(HouseRoom houseRoom);

    /**
     * 设置房源经纪人
     */
    public int updateHouseAgent(Long[] ids,Long userId);

    /**
     * 批量删除房源详情
     *
     * @param ids 需要删除的房源详情主键集合
     * @return 结果
     */
    public int deleteHouseRoomByIds(Long[] ids);

    /**
     * 批量修改房源详情
     *
     * @param ids 需要删除的房源详情主键集合
     */
    public int updateHouseRoomByIds(Long[] ids,Integer state);

    /**
     * 删除房源详情信息
     *
     * @param id 房源详情主键
     * @return 结果
     */
    public int deleteHouseRoomById(Long id);

    /**
     * api新增房源详情
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    public int apiInsertHouseRoom(HouseRoom houseRoom);

    /**
     * 查询房源相关详情信息
     *
     * @param houseRoom 房源
     * @return 房源详情
     */
    public HouseRoom selectDetailHouseRoom(HouseRoom houseRoom);

    /**
     * 导入房源
     */
    public String importHouse(List<HouseRoom> houseList, Boolean isUpdateSupport);

    /**
     * 收藏/取消收藏
     */
    public String saveHeart(boolean heart,Long userId,String houseId);

    /**
     * 查询收藏数据
     */
    public Long selectHouseHeart(Map<String,Object> houseMap);

    /**
     * 获取收藏房源列表
     */
    public List<HouseRoom> findHouseHeartList(Long userId);
}
