package cn.source.system.mapper;

import cn.source.system.domain.MallAddress;

import java.util.List;

/**
 * 收货地址Mapper接口
 *
 * @author 詹Sir
 * @date 2022-06-19
 */
public interface MallAddressMapper
{
    /**
     * 查询收货地址
     *
     * @param id 收货地址主键
     * @return 收货地址
     */
    public MallAddress selectMallAddressById(Long id);

    /**
     * 查询收货地址列表
     *
     * @param mallAddress 收货地址
     * @return 收货地址集合
     */
    public List<MallAddress> selectMallAddressList(MallAddress mallAddress);

    /**
     * api查询收货地址列表,不同于pc的aop,使用用户id查询
     *
     * @param mallAddress 收货地址
     * @return 收货地址集合
     */
    public List<MallAddress> selectApiMallAddressList(MallAddress mallAddress);

    /**
     * 新增收货地址
     *
     * @param mallAddress 收货地址
     * @return 结果
     */
    public int insertMallAddress(MallAddress mallAddress);

    /**
     * 修改收货地址
     *
     * @param mallAddress 收货地址
     * @return 结果
     */
    public int updateMallAddress(MallAddress mallAddress);

    /**
     * 取消其他收货的默认状态
     *
     * @param mallAddress 收货地址
     * @return 结果
     */
    public int updateAddressDefault(MallAddress mallAddress);

    /**
     * 删除收货地址
     *
     * @param id 收货地址主键
     * @return 结果
     */
    public int deleteMallAddressById(Long id);

    /**
     * 批量删除收货地址
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallAddressByIds(Long[] ids);
}
