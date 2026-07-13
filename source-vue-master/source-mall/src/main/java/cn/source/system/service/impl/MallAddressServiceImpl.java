package cn.source.system.service.impl;

import cn.source.common.annotation.DataScope;
import cn.source.common.utils.DateUtils;
import cn.source.common.utils.SecurityUtils;
import cn.source.common.utils.StringUtils;
import cn.source.system.domain.MallAddress;
import cn.source.system.mapper.MallAddressMapper;
import cn.source.system.service.IMallAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址Service业务层处理
 *
 * @author 詹Sir
 * @date 2022-06-19
 */
@Service
public class MallAddressServiceImpl implements IMallAddressService
{
    @Autowired
    private MallAddressMapper mallAddressMapper;

    /**
     * 查询收货地址
     *
     * @param id 收货地址主键
     * @return 收货地址
     */
    @Override
    public MallAddress selectMallAddressById(Long id)
    {
        return mallAddressMapper.selectMallAddressById(id);
    }

    /**
     * 查询收货地址列表
     *
     * @param mallAddress 收货地址
     * @return 收货地址
     */
    @Override
    @DataScope(userAlias = "mall_address")
    public List<MallAddress> selectMallAddressList(MallAddress mallAddress)
    {
        return mallAddressMapper.selectMallAddressList(mallAddress);
    }

    @Override
    public List<MallAddress> selectApiMallAddressList(MallAddress mallAddress)
    {
        return mallAddressMapper.selectApiMallAddressList(mallAddress);
    }

    /**
     * 新增收货地址
     *
     * @param mallAddress 收货地址
     * @return 结果
     */
    @Override
    public int insertMallAddress(MallAddress mallAddress)
    {
        if(StringUtils.isNull(mallAddress.getUserId())){
            mallAddress.setUserId(SecurityUtils.getUserId());
        }
        // true与1转换一下
        if(mallAddress.getIsDefault().equals("true")){
            mallAddress.setIsDefault("1");
        }else{
            mallAddress.setIsDefault("0");
        }
        mallAddress.setCreateTime(DateUtils.getNowDate());
        int result = mallAddressMapper.insertMallAddress(mallAddress);
        // 如果当前新增记录是默认地址，将其他默认取消
        if(mallAddress.getIsDefault().equals("1")){
            result = mallAddressMapper.updateAddressDefault(mallAddress);
        }
        return result;
    }

    /**
     * 修改收货地址
     *
     * @param mallAddress 收货地址
     * @return 结果
     */
    @Override
    public int updateMallAddress(MallAddress mallAddress)
    {
        // true与1转换一下
        if(mallAddress.getIsDefault().equals("true")){
            mallAddress.setIsDefault("1");
        }else{
            mallAddress.setIsDefault("0");
        }
        mallAddress.setUpdateTime(DateUtils.getNowDate());
        // 如果当前新增记录是默认地址，将其他默认取消
        if(mallAddress.getIsDefault().equals("1")){
            mallAddressMapper.updateAddressDefault(mallAddress);
        }
        return mallAddressMapper.updateMallAddress(mallAddress);
    }

    /**
     * 批量删除收货地址
     *
     * @param ids 需要删除的收货地址主键
     * @return 结果
     */
    @Override
    public int deleteMallAddressByIds(Long[] ids)
    {
        return mallAddressMapper.deleteMallAddressByIds(ids);
    }

    /**
     * 删除收货地址信息
     *
     * @param id 收货地址主键
     * @return 结果
     */
    @Override
    public int deleteMallAddressById(Long id)
    {
        return mallAddressMapper.deleteMallAddressById(id);
    }
}
