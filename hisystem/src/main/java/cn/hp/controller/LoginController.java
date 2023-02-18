package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.Menu;
import cn.hp.entity.User;
import cn.hp.service.ILoginService;
import cn.hp.service.IUserRoleService;
import cn.hp.service.IUserService;
import cn.hp.task.AsyncTask;
import cn.hp.util.*;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录Controller
 */
@Controller
public class LoginController {

    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private ILoginService loginService;
    @Autowired
    private AsyncTask asyncTask;
    @Resource
    private RedisUtil redisUtil;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("main")
    public String main() {
        return "main";
    }

    @RequestMapping("error")
    public String error() {
        return "error";
    }

    @GetMapping("getUser")
    @ResponseBody
    public Result getUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return Result.success(user);
    }

    /**
     * 获取对应用户的菜单
     */
    @GetMapping("getUidMenu")
    @ResponseBody
    public Object getUidMenu() {
        User user = UserController.getUser();
        if (StringUtils.isNotNull(user)) {
            Integer id = user.getId();
            if (!redisUtil.hHasKey(Constants.USER_MENU_CACHE, Constants.USER_MENU_KEY + id)) {
                List<Menu> menu = loginService.getUidMenu(user);
                redisUtil.hset(Constants.USER_MENU_CACHE, Constants.USER_MENU_KEY + id, JSON.toJSONString(menu), RandomNumber.randomNum(null));
            }
            String o = (String) redisUtil.hget(Constants.USER_MENU_CACHE, Constants.USER_MENU_KEY + id);
            return Result.success(JSON.parseArray(o, Menu.class));
        }
        return Result.error();
    }

    @PostMapping("dologin")
    @ResponseBody
    public Result dologin(@RequestBody User user, boolean rememberMe) {
        boolean loginflag = false;
        String msg = "";
        // 创建Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();
        // 判断当前用户是否已登录
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
            try {
                token.setRememberMe(rememberMe);
                currentUser.login(token);
                loginflag = true;
            } catch (DisabledAccountException dax) {
                msg = "用户名为:" + dax.getMessage() + " 用户已经被禁用！";
            } catch (ExcessiveAttemptsException eae) {
                msg = "用户名为:" + eae + " 用户登录次数过多，有暴力破解的嫌疑！";
            } catch (AuthenticationException ae) {
                msg = "------------------身份认证失败-------------------";
                ae.printStackTrace();
            } catch (RedisConnectionFailureException e) {
                msg = "redis异常，请检查是否启动redis了";
            } catch (Exception e) {
                msg = e.getMessage();
            }
        }
        if (loginflag) {
            //保存用户登录信息
            asyncTask.saveLoginInfor(user.getIp(), user.getBroswer(), user.getEmail());
            return Result.success();
        } else {
            return Result.error(msg);
        }
    }
    public static void main(String[] args) {
        String salt = MD5Util.md5Encrypt32Lower("1208585122@qq.com");
        System.out.println("1208585122@qq.com");
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt); //加密的盐值
        System.out.println(credentialsSalt);
        // 使用SimpleHash类对原始密码进行加密
        String password = new SimpleHash("MD5", "123", credentialsSalt, 1024).toHex();
        System.out.println(password);
    }

    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    @ResponseBody
    public Object registered(@RequestBody @Validated User reqVO) {
//        userService.saveUserAndSendEmail(reqVO);
        Integer i = userService.saveUserAndSendEmail(reqVO);
        return i != null && i > 0 ? Result.success("注册成功，请在半个小时内进行邮箱激活账号！") : Result.error("注册失败");
    }

    /**
     * 提供为外部调用的邮件方法  应该把此方法提出来放置到service服务中
     *
     * @return 返回状态true为成功，false为失败
     */
    @CrossOrigin
    @RequestMapping(value = "/activationUserName")
    @ResponseBody
    public Object activationUserName(String code) {
        if (redisUtil.hasKey(code)) {
            String id = (String) redisUtil.get(code);
            Boolean aBoolean = userService.activationUserName(Integer.parseInt(id));
            if (aBoolean) {
                redisUtil.del(code);
                return Result.success("激活成功！");
            } else {
                return Result.error("激活失败！");
            }
        }
        return Result.error("激活失败！链接已超时！");
    }

    @GetMapping("logout")
    public String logout() {
        User user = UserController.getUser();
        if (StringUtils.isNotNull(user)) {
            Integer id = user.getId();
            redisUtil.hdel(Constants.USER_MENU_CACHE, Constants.USER_MENU_KEY + id);
        }
        return "redirect:login";
    }

}
