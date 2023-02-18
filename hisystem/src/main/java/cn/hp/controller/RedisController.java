package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.User;
import cn.hp.util.RedisUtil;
import cn.hp.util.Result;
import cn.hp.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 缓存管理 Controller
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/getStringRedis")
    public Object getStringRedis(String key) {
        if (redisUtil.hasKey(key)) {
            String o = (String) redisUtil.get(key);
            return Result.success(JSON.parseObject(o));
        } else {
            return Result.error(key + "缓存不存在！");
        }
    }

    @RequestMapping("/getMapRedis")
    public Object getStringRedis(String name, String key) {
        if (redisUtil.hHasKey(name, key)) {
            String o = (String) redisUtil.hget(name, key);
            return Result.success(JSON.parseObject(o));
        } else {
            return Result.error(name + "下的" + key + "缓存不存在！");
        }
    }

    @RequestMapping("/clearRedis")
    @ResponseBody
    public Object clearRedis() {
        User user = UserController.getUser();
        if (StringUtils.isNotNull(user)) {
            Integer id = user.getId();
            redisUtil.hdel(Constants.USER_MENU_CACHE, Constants.USER_MENU_KEY + id);//清除缓存 用户权限
        }else{
            redisUtil.del(Constants.USER_MENU_CACHE);
        }
        redisUtil.del(Constants.SYS_MENU_KEY);//清除缓存    菜单
        redisUtil.del(Constants.SYS_ROLE_KEY);//清除缓存    角色
        redisUtil.del(Constants.SYS_DICTTYPE_KEY);//清除缓存    字典类型
        redisUtil.del(Constants.SYS_DICT_CACHE);//清除缓存  字典信息
        redisUtil.del(Constants.SYS_DEPARTMENT_KEY);//清除缓存  科室信息
        redisUtil.del(Constants.SYS_DRUG_KEY);//清除缓存  药物信息
        return Result.success();
    }
}
