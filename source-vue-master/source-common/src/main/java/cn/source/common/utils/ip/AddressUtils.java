package cn.source.common.utils.ip;

import cn.source.common.config.RuoYiConfig;
import cn.source.common.utils.RegionUtil;
import cn.source.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (RuoYiConfig.isAddressEnabled()) {
            try {
                String rspStr = RegionUtil.getRegion(ip);
                if (StringUtils.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                String[] obj = rspStr.split("\\|");
                String region = obj[2];
                String city = obj[3];
                return String.format("%s %s", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", e);
            }
        }
        return address;
    }

    public static String getRealCityByIP(String ip) {
        String city = UNKNOWN;
        if (RuoYiConfig.isAddressEnabled()) {
            try {
                String rspStr = RegionUtil.getRegion(ip);
                if (StringUtils.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                String[] obj = rspStr.split("\\|");
                String region = obj[2] + "省";
                city = obj[3] + "市";
            } catch (Exception e) {
                log.error("获取城市位置异常 {}", ip);
            }
        }
        return city;
    }
}
