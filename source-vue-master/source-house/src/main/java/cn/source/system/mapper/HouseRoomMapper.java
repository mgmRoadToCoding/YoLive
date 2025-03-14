package cn.source.system.mapper;

import cn.source.system.domain.HouseFeature;
import cn.source.system.domain.HouseImage;
import cn.source.system.domain.HouseRoom;

import java.util.List;
import java.util.Map;

/**
 * 房源详情Mapper接口
 *
 * @author 詹Sir
 * @date 2022-03-27
 */
public interface HouseRoomMapper
{
    /**
     * 查询房源详情
     *
     * @param id 房源详情主键
     * @return 房源详情
     */
    public HouseRoom selectHouseRoomById(Long id);

    /**
     * 查询房源详情
     *
     * @param code 代号
     * @return 房源详情
     */
    public HouseRoom selectHouseRoomByCode(String code);

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
    public int updateHouseAgent(Map<String,Object> houseMap);


    /**
     * 修改房源详情
     *
     * @param houseMap 房源详情
     * @return 结果
     */
    public int updateHouseRoomByIds(Map<String,Object> houseMap);

    /**
     * 删除房源详情
     *
     * @param id 房源详情主键
     * @return 结果
     */
    public int deleteHouseRoomById(Long id);

    /**
     * 批量删除房源详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHouseRoomByIds(Long[] ids);

    /**
     * 新增房源图片
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    public int insertHouseImage(HouseRoom houseRoom);

    /**
     * 删除房源图片
     *
     * @param id 房源详情主键
     * @return 结果
     */
    public int deleteHouseImageByHouseId(Long id);

    /**
     * 删除收藏记录
     *
     * @param id 房源详情主键
     * @return 结果
     */
    public int deleteHouseHeartByHouseId(Long id);

    /**
     * 新增房源特色
     *
     * @param houseRoom 房源详情
     * @return 结果
     */
    public int insertHouseFeature(HouseRoom houseRoom);

    /**
     * 删除房源亮点
     *
     * @param id 房源详情主键
     * @return 结果
     */
    public int deleteHouseFeatureByHouseId(Long id);

    /**
     * 查询房源图片详情
     *
     * @param houseRoom 房源详情
     * @return 房源详情
     */
    public List<HouseImage> selectHouseImage(HouseRoom houseRoom);

    /**
     * 查询房源亮点详情
     *
     * @param houseRoom 房源详情
     * @return 房源详情
     */
    public List<HouseFeature> selectHouseFeature(HouseRoom houseRoom);

    /**
     * 房源收藏
     */
    public int saveHeart(Map<String,Object> houseMap);

    /**
     * 房源取消收藏
     */
    public int cancelHeart(Map<String,Object> houseMap);

    /**
     * 查询收藏数据
     */
    public Long selectHouseHeart(Map<String,Object> houseMap);

    /**
     * 获取收藏房源列表
     */
    public List<HouseRoom> findHouseHeartList(Long userId);
}
