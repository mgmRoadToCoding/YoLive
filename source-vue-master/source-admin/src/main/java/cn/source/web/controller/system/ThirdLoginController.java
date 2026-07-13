package cn.source.web.controller.system;

import cn.source.common.constant.Constants;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.domain.entity.SysUser;
import cn.source.common.core.domain.model.LoginBody;
import cn.source.common.core.domain.model.LoginUser;
import cn.source.common.core.redis.RedisCache;
import cn.source.common.utils.SecurityUtils;
import cn.source.common.utils.StringUtils;
import cn.source.framework.web.service.SysLoginService;
import cn.source.framework.web.service.SysPermissionService;
import cn.source.framework.web.service.TokenService;
import cn.source.system.service.ISysNoticeService;
import cn.source.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 三方登录验证
 *
 * @author ruoyi
 */
@RequestMapping("/api")
@RestController
public class ThirdLoginController extends BaseController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 三方登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/thirdLogin")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        return loginService.thirdLogin(loginBody.getUsername(), loginBody.getPassword());
    }


    /**
     * 手机注册/登录
     */
    @PostMapping("/thirdRegister")
    public AjaxResult thirdRegister(HttpServletRequest request,
                                    @RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        String msg = "登录成功";
        if (StringUtils.isEmpty(loginBody.getUsername()) || StringUtils.isEmpty(loginBody.getCode())){
            msg = "用户名/验证码不能为空";
            return error(msg);
        }
        // 首先验证验证码是否正确
        if(redisCache.getCacheObject(loginBody.getUsername()) == null || !redisCache.getCacheObject(loginBody.getUsername()).equals(loginBody.getCode())){
            msg = "验证码过期/错误";
            return error(msg);
        }
        // 验证码正确则判断是否为新用户
        SysUser sysUser = userService.selectUserByUserName(loginBody.getUsername());


        // 不是新用户，创建用户
        if(sysUser == null){

            sysUser = new SysUser();
            sysUser.setUserName(loginBody.getUsername());
            sysUser.setNickName(loginBody.getUsername());
//            sysUser.setPassword(loginBody.getPassword());
            sysUser.setPhonenumber(loginBody.getUsername());
//            sysUser.setPassword(SecurityUtils.encryptPassword(loginBody.getUsername()));
            // ✅ 用用户输入的密码加密存储
            sysUser.setPassword(SecurityUtils.encryptPassword(loginBody.getPassword()));
            sysUser.setDeptId(110L);
            Long[] postIds = new Long[1];
            postIds[0] = 8L;
            Long[] roleIds = new Long[1];
            roleIds[0] = 5L;
            sysUser.setPostIds(postIds);
            sysUser.setRoleIds(roleIds);
            //  保存完用户后，还需要设置用户的角色，部门与岗位
            // userService.registerUser(sysUser);
            userService.insertUser(sysUser);

//            return AjaxResult.error("用户不存在"); // 仅判断用户是否存在，不校验密码
            System.out.println("注册密码加密后：" + sysUser.getPassword());
            System.out.println(SecurityUtils.matchesPassword("用户输入密码", sysUser.getPassword()));

        }
        // 生成token
        LoginUser loginUser = new LoginUser(sysUser,null);
        String token = tokenService.createToken(loginUser);
        ajax.put(Constants.TOKEN, token);
        ajax.put("loginUser", loginUser);
        return ajax;

    }

    @PostMapping("/thirdLoginByCode")
    public AjaxResult thirdLoginByCode(@RequestBody LoginBody loginBody) {
        String username = loginBody.getUsername();
        String code = loginBody.getCode();

        // 1. 校验手机号和验证码
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(code)) {
            return AjaxResult.error("手机号/验证码不能为空");
        }
        String redisCode = redisCache.getCacheObject(username);
        if (redisCode == null || !redisCode.equals(code)) {
            return AjaxResult.error("验证码过期/错误");
        }

        // 2. 查询用户（不存在则自动注册）
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            // 自动注册逻辑（复用thirdRegister中的用户创建逻辑）
            user = new SysUser();
            user.setUserName(username);
            user.setNickName(username);
            user.setPhonenumber(username);
            user.setPassword(SecurityUtils.encryptPassword(username)); // 默认密码（不影响，无需输入）
            user.setDeptId(110L);
            Long[] postIds = {8L}, roleIds = {5L};
            user.setPostIds(postIds);
            user.setRoleIds(roleIds);
            userService.insertUser(user);
        }

        // 3. 生成Token返回
        LoginUser loginUser = new LoginUser(user, null);
        String token = tokenService.createToken(loginUser);
        AjaxResult ajax = AjaxResult.success();
        ajax.put(Constants.TOKEN, token);
        ajax.put("loginUser", loginUser);
        return ajax;
    }

    @PostMapping("/sendCode")
    public AjaxResult sendCode(@RequestBody LoginBody loginBody) {
        String phone = loginBody.getUsername();
        if (StringUtils.isEmpty(phone)) {
            return AjaxResult.error("手机号不能为空");
        }

        // 生成随机6位验证码
        String code = String.valueOf((int)((Math.random()*9+1)*100000));

        // 存入Redis，有效期5分钟
        redisCache.setCacheObject(phone, code, 5, TimeUnit.MINUTES);

        // **打印到控制台（方便开发调试）**
        System.out.println("手机号：" + phone + "，验证码：" + code);

        // **返回给前端（仅测试用）**
        return AjaxResult.success("验证码已生成", code);
    }


    /**
     * 微信小程序注册/登录
     */
    @PostMapping("/weChatLogin")
    public AjaxResult weChatLogin(HttpServletRequest request,@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        String msg = "登录成功";
        if (StringUtils.isEmpty(loginBody.getUsername()) || StringUtils.isEmpty(loginBody.getCode())){
            msg = "用户名/code不能为空";
            return error(msg);
        }
        if (!redisCache.getCacheObject(Constants.WX_PHONE_NUM_KEY + loginBody.getUsername()).equals(loginBody.getCode())){
           msg = "code过期/错误";
           return error(msg);
        }
        // 判断是否为新用户
        SysUser sysUser = userService.selectUserByUserName(loginBody.getUsername());
        // 不是新用户，创建用户
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setUserName(loginBody.getUsername());
            sysUser.setNickName(loginBody.getUsername());
            sysUser.setPassword(loginBody.getUsername());
            sysUser.setPhonenumber(loginBody.getUsername());
            sysUser.setPassword(SecurityUtils.encryptPassword(loginBody.getUsername()));
            sysUser.setDeptId(110L);
            Long[] postIds = new Long[1];
            postIds[0] = 8L;
            Long[] roleIds = new Long[1];
            roleIds[0] = 5L;
            sysUser.setPostIds(postIds);
            sysUser.setRoleIds(roleIds);
            //  保存完用户后，还需要设置用户的角色，部门与岗位
            userService.insertUser(sysUser);
        }
        // 生成token
        LoginUser loginUser = new LoginUser(sysUser,null);
        String token = tokenService.createToken(loginUser);
        ajax.put(Constants.TOKEN, token);
        ajax.put("loginUser", loginUser);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }
}
