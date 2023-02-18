package cn.hp.controller;

import cn.hp.entity.User;
import cn.hp.service.IUserService;
import cn.hp.util.DateUtil;
import cn.hp.util.MD5Util;
import cn.hp.util.Result;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 管理员Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    private IUserService userService;

    public static User getUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * 条件查询
     */
    @GetMapping
    public Object find(User obj) {
        return Result.success(userService.selectUserList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    public Object findPage(User obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        PageInfo<User> pageInfo = userService.selectUserList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        User user = userService.selectUserById(id);
        return Result.success(user);
    }

    /**
     * 添加
     */
    @PostMapping
    public Object add(@RequestBody User obj) {
        if(userService.checkEmail(obj.getEmail())){
            return Result.error("添加失败！改邮箱已经存在了！");
        }else{
            //生成盐和加盐密码
            String salt = MD5Util.md5Encrypt32Lower(obj.getEmail());
            obj.setSalt(salt);
            obj.setPlainPassword(obj.getPassword());
            ByteSource credentialsSalt = ByteSource.Util.bytes(salt); //加密的盐值
            // 使用SimpleHash类对原始密码进行加密
            String password = new SimpleHash("MD5", obj.getPlainPassword(), credentialsSalt, 1024).toHex();
            obj.setPassword(password);
            int i = userService.insertUser(obj);
            return i>0?Result.success("添加成功！"):Result.error("添加失败！");
        }
    }

/**
 * 修改
 */
@PutMapping
public Object update(@RequestBody User obj) {
    int i = userService.updateUser(obj);
    return i>0?Result.success("修改成功！"):Result.error("修改失败！");
}

    /**
     * 个人信息修改
     */
    @PutMapping("/update")
    public Object update1(@RequestBody User obj) {
        obj.setId(getUser().getId());
        int i = userService.updateUser(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    public Object changePassword(@RequestBody User obj) {
        User user = getUser();
        if(!obj.getNewPassword().equals(obj.getOkPassword())){
            return Result.error("两次密码不一致！");
        }
        if(user!=null){
            String password = new SimpleHash("MD5", obj.getPassword(), ByteSource.Util.bytes(user.getSalt()), 1024).toHex();
            if(password.equals(user.getPassword())){
                obj.setId(user.getId())
                        .setPwdUpdateDate(new Date())
                        .setPassword(new SimpleHash("MD5", obj.getNewPassword(), ByteSource.Util.bytes(user.getSalt()), 1024).toHex());
                int i =userService.updateUser(obj);
                return i>0?Result.success("重置密码成功！"):Result.error("重置密码失败！");
            }else{
                return Result.error("原始密码不正确！");
            }
        }
        return Result.error();
    }

/**
 * 重置密码
 */
@GetMapping("/resetPassword/{id}")
public Object resetPassword(@PathVariable Integer id) {
    User user = userService.selectUserById(id);
    User user2 = new User();
    user2.setId(id);
    //生成盐和加盐密码
    String salt = MD5Util.md5Encrypt32Lower(user.getEmail());
    user2.setSalt(salt);
    ByteSource credentialsSalt = ByteSource.Util.bytes(salt); //加密的盐值
    // 使用SimpleHash类对原始密码进行加密
    String password = new SimpleHash("MD5", "123", credentialsSalt, 1024).toHex();
    user2.setPassword(password);
    int i = userService.resetPassword(user2);
    return i>0?Result.success("重置密码成功！"):Result.error("重置密码失败！");
}

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Object del(@PathVariable Integer id) {
        int i = userService.deleteUserById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
