package cn.source.system.controller;

import cn.source.common.constant.Constants;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.redis.RedisCache;
import cn.source.common.utils.StringUtils;
import cn.source.system.utils.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: wx小程序 api控制类
 * @author: 詹Sir
 */
@RestController
@RequestMapping("/api/miniWxApi")
public class MiniWxApiController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniWxApiController.class);

    @Autowired
    private RedisCache redisCache;

    @Value("${wechat.mini.appId}")
    private String APPID;

    @Value("${wechat.mini.secret}")
    private String SECRET;

    @Value("${wechat.mini.accessTokenKey}")
    private String accessTokenKey;

    /**
     * 获取AccessToken
     */
    @GetMapping("/getAccessToken")
    public AjaxResult getAccessToken(){
        AjaxResult ajax = AjaxResult.success();
        Object token = redisCache.getCacheObject(accessTokenKey);
        if(StringUtils.isNull(token) || token.equals("null")){
            token = WxUtil.obtainAccessToken(APPID, SECRET);
            redisCache.setCacheObject(accessTokenKey,token,30,TimeUnit.MINUTES);
        }
        ajax.put(accessTokenKey,token);
        return ajax;
    }

    /**
     * 获取用户手机号码
     */
    @GetMapping("/getPhoneNum")
    public AjaxResult getPhoneNum(String code){
        AjaxResult ajax = AjaxResult.success();
        // Object token = getAccessToken();
        Object token = WxUtil.obtainAccessToken(APPID, SECRET);
        String phoneNum = WxUtil.getPhoneNum(token,code);
        redisCache.setCacheObject(Constants.WX_PHONE_NUM_KEY + phoneNum, code, Constants.WX_PHONE_CODE_EXPIRATION, TimeUnit.MINUTES);
        ajax.put("phoneNum",phoneNum);
        return ajax;
    }

    /**
     * 获取小程序码
     */
    @GetMapping("/getQrCode")
    public AjaxResult getQrCode(String qrCodePath) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        Object token = WxUtil.obtainAccessToken(APPID, SECRET);
        String qrCode = WxUtil.getQrCode(token,qrCodePath);
        ajax.put("qrCode",qrCode);
        return ajax;
    }

}
