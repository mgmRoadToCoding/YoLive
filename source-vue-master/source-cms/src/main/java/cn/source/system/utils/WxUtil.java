package cn.source.system.utils;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.source.common.config.RuoYiConfig;
import cn.source.common.utils.DateUtils;
import cn.source.common.utils.file.FileUploadUtils;
import cn.source.common.utils.http.HttpUtils;
import cn.source.common.utils.uuid.IdUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名认证工具类
 * @ClassName:  WxCheckUtil
 *
 */
public class WxUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxUtil.class);

    // 文件默认保存路径，读取配置文件
    private static String defaultBaseDir = RuoYiConfig.getProfile();

	// 与接口配置信息中的Token要一致
    private static String token = "sourcebyte";

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr);
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信

        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    // 获取AccessToken
    public static String obtainAccessToken(String APPID,String SECRET){
        String tokenData = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;
        // 返回的用户信息json字符串
        String resp = HttpUtils.sendGet(tokenData);
        JSONObject jsonObject = JSON.parseObject(resp);
        return String.valueOf(jsonObject.get("access_token"));
    }

    // 获取手机号码
    public static String getPhoneNum(Object accessToken,String code){
        String path = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="+accessToken;
        //封装参数
        JSONObject jsonData = new JSONObject();
        jsonData.put("code", code);
        // 返回的用户信息json字符串，此处调用Hutool工具类
        String resp = HttpUtil.post(path, jsonData.toJSONString());
        JSONObject jsonObject = JSON.parseObject(resp);
        JSONObject phoneInfo = JSON.parseObject(jsonObject.get("phone_info").toString());
        return String.valueOf(phoneInfo.get("phoneNumber"));
    }

    // 获取订阅用户的openid和unionid
    public static Map obtainUserDetail(String accessToken, String openId){
        String openInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        String resp = HttpUtils.sendGet(openInfoUrl);
        JSONObject userDetail = JSON.parseObject(resp);
        String openid = userDetail.getString("openid");
        Integer subscribe = userDetail.getInteger("subscribe");
        Integer subscribeTime = userDetail.getInteger("subscribe_time");
        String unionid = userDetail.getString("unionid");
        HashMap<String, Object> userDetailMap = new HashMap<>();
        userDetailMap.put("openid",openid);
        // subscribe 1:关注 0：取关
        userDetailMap.put("subscribe",subscribe);
        userDetailMap.put("subscribeTime",subscribeTime);
        userDetailMap.put("unionid",unionid);
        return userDetailMap;
    }

    // 获取小程序码
    public static String getQrCode(Object accessToken,String qrCodePath) throws IOException {
        String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
        // 封装参数
        JSONObject jsonData = new JSONObject();
        jsonData.put("path", qrCodePath);
        jsonData.put("width", 430);
        jsonData.put("auto_color", false);
        jsonData.put("auto_color", false);
        Map<String,Object> line_color = new HashMap<>();
        line_color.put("r", 0);
        line_color.put("g", 0);
        line_color.put("b", 0);
        jsonData.put("line_color", line_color);
        // 这里要特别注意，调用二维码接口返回的是二进制，所以返回值必须为Byte类型，返回String类型就会变成乱码
        byte[] qrCode = HttpRequest.post(url).header("post", "application/json").body(jsonData.toJSONString()).execute().bodyBytes();
        // 这里是自定义保存的文件路径
        String fileName = "/qrcode/"+ DateUtils.datePath() + "/" + IdUtils.fastUUID() + ".png";
        // 这里就直接把二进制保存为文件了
        FileUtil.writeBytes(qrCode, FileUploadUtils.getAbsoluteFile(defaultBaseDir, fileName));
        // 返回图片路径
        String pathFileName = FileUploadUtils.getPathFileName(defaultBaseDir, fileName);
        return pathFileName;
    }

    /*    **//**
     * 给关注该公众好的用户发送信息
     * @throws Exception
     *//*
    public static void sendTemplateInfo() throws Exception{

        //获取小程序accessToken
        String accessToken = obtainAccessToken();
        //消息推送接口
        String path = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

        //封装参数
        JSONObject jsonData = new JSONObject();
        jsonData.put("touser", "公众号openid");
        jsonData.put("template_id", "公众号消息模板id");
        JSONObject miniprogram = new JSONObject();
        miniprogram.put("appid","小程序ID");
        miniprogram.put("pagepath","小程序调转路径");
        jsonData.put("miniprogram",miniprogram);

        JSONObject data = new JSONObject();

        JSONObject first = new JSONObject();
        first.put("value","测试购票支付详情！");
        first.put("color","#173177");

        JSONObject keyword1 = new JSONObject();
        keyword1.put("value","2112323832748239");
        keyword1.put("color","#173177");

        JSONObject remark = new JSONObject();
        remark.put("value","温馨提示：请不要爽约哦！");
        remark.put("color","#173177");

        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("keyword3",keyword3);
        data.put("keyword4",keyword4);
        data.put("remark",remark);
        jsonData.put("data",data);
        HttpUtil.post(path, jsonData.toJSONString());
    }*/

    public static void main(String[] args) {
        String G_APPID = "G_APPID";
        String G_SECRET = "G_SECRET";
        String tokenData = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+G_APPID+"&secret="+G_SECRET;
        // 返回的用户信息json字符串
        String resp = HttpUtils.sendGet(tokenData);
        JSONObject jsonObject = JSON.parseObject(resp);
        System.out.println("token:"+jsonObject.get("access_token"));
        HttpUtils.sendGet(tokenData);
        String openInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+jsonObject.get("access_token")+"&openid=oM3w46__sVVjRWGUU4FGSwYOypTg&lang=zh_CN";
        String resp2 = HttpUtils.sendGet(openInfoUrl);
        JSONObject userDetail = JSON.parseObject(resp2);
        System.out.println(userDetail);
    }
}
