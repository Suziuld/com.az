package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.Role;
import cn.hp.service.IRoleService;
import cn.hp.util.RandomNumber;
import cn.hp.util.RedisUtil;
import cn.hp.util.Result;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色 Controller
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/getRole")
    public Object getMenu(Role obj) {
        if (!redisUtil.hasKey(Constants.SYS_ROLE_KEY)) {
            List<Role> list = roleService.selectRoleList(obj);
            List<Map<String, Object>> Mmap = new ArrayList<>();
            for (Role m : list) {
                if ("0".equals(m.getStatus())) {
                    Map map = new HashMap();
                    map.put("id", m.getId() + ":" + m.getRole());
                    map.put("title", m.getRemark());
                    Mmap.add(map);
                }
            }
            redisUtil.set(Constants.SYS_ROLE_KEY, JSON.toJSONString(Mmap), RandomNumber.randomNum(null));
        }
        String o = (String) redisUtil.get(Constants.SYS_ROLE_KEY);
        return Result.success(JSON.parseArray(o, Map.class));
    }

    /**
     * 条件查询
     */
    @GetMapping
    public Object find(Role obj) {
        return Result.success(roleService.selectRoleList(obj));
    }

    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    public Object findPage(Role obj, @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        obj.setDelFlag("0");
        PageInfo<Role> pageInfo = roleService.selectRoleList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        Role role = roleService.selectRoleById(id);
        return Result.success(role);
    }

    /**
     * 添加
     */
    @PostMapping
    public Object add(@RequestBody Role obj) {
        redisUtil.del(Constants.SYS_ROLE_KEY);
        int i = roleService.insertRole(obj);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    public Object update(@RequestBody Role obj) {
        redisUtil.del(Constants.SYS_ROLE_KEY);
        int i = roleService.updateRole(obj);
        return i > 0 ? Result.success("修改成功！") : Result.error("修改失败！");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Object del(@PathVariable Integer id) {
        redisUtil.del(Constants.SYS_ROLE_KEY);
        int i = roleService.deleteRoleById(id);
        return i > 0 ? Result.success("删除成功！") : Result.error("删除失败！");
    }

}
