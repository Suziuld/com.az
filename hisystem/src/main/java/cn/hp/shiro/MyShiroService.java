package cn.hp.shiro;

import java.util.Set;

import cn.hp.common.constant.Constants;

import cn.hp.entity.User;
import cn.hp.service.ILoginService;
import cn.hp.service.IUserService;
import cn.hp.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
//@Scope("prototype")
public class MyShiroService extends AuthorizingRealm{

    private static final Logger logger = LoggerFactory.getLogger(MyShiroService.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginService loginService;
    @Resource
    private RedisUtil redisUtil;

   /**
     * 授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User userEntity= (User) principalCollection.getPrimaryPrincipal();
        System.out.println(userEntity);
        User user= userService.login(userEntity.getEmail());
        System.out.println(user);
        if (user==null){
            return null;
        }
        //2. 利用登录的用户的信息来获取当前用户的角色或权限(可能需要查询数据库)
        Set<String> roleName = loginService.findRoles(user);
        Set<String> menu = loginService.findMenu(user);
        System.out.println(menu);
        //3. 创建 SimpleAuthorizationInfo, 并设置其 reles ，Permissions属性.
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(roleName);
        info.setStringPermissions(menu);
        //获取权限
        return info;
    }

    /**
     * 登录认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        // UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) arg0;
        System.out.println(new String((char[])token.getCredentials()));
        try {
            // 到数据库查是否有此对象
            User user  = userService.login(token.getUsername());
            if (null == user) {
                throw new UnknownAccountException("用户名或者密码错误");
            }
            if(user.getEmailStatus()==0){
                throw new UnknownAccountException("该邮箱未激活！");
            }
            String realmName = user.getEmail().toLowerCase();
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt()); //加密的盐值
            // 若存在，将此用户存放到登录认证info中
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, realmName);
            redisUtil.hdel(Constants.USER_MENU_CACHE, Constants.USER_MENU_KEY + user.getId());
            return info;
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
