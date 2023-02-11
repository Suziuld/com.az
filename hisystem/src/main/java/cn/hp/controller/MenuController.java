package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.Menu;
import cn.hp.service.IMenuService;
import cn.hp.util.RandomNumber;
import cn.hp.util.RedisUtil;
import cn.hp.util.Result;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单 Controller

 */
@RestController
@RequestMapping("/api/menu")
public class MenuController
{
    @Autowired
    private IMenuService menuService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取树形结构的菜单数据
     * @param obj
     * @return
     */
    @GetMapping("/getMenu")
    public Object getMenu(Menu obj) {
        Integer id = UserController.getUser().getId();
        if (!redisUtil.hasKey(Constants.SYS_MENU_KEY)) {
            List<Menu> list = menuService.selectMenuList(obj);
            List<Map<String,Object>> Mmap = new ArrayList<>();
            for (Menu m : list) {
                if(m.getMenuType().equals("M")){
                    Map map = new HashMap();
                    map.put("id", m.getMenuId());
                    map.put("title",m.getMenuName());
                    List<Map<String,Object>> Cmap = new ArrayList<>();
                    for (Menu n : list) {
                        if(n.getMenuType().equals("C")&&(m.getMenuId()-n.getParentId()==0)){
                            Map map2 = new HashMap();
                            map2.put("id", n.getMenuId());
                            map2.put("title",n.getMenuName());
                            Cmap.add(map2);
                        }
                    }
                    map.put("children",Cmap);
                    Mmap.add(map);
                }
            }
            redisUtil.set(Constants.SYS_MENU_KEY , JSON.toJSONString(Mmap), RandomNumber.randomNum(null));
        }
        String o = (String) redisUtil.get(Constants.SYS_MENU_KEY);
        return Result.success(JSON.parseArray(o, Map.class));
    }
    
    /**
     * 条件查询
     */
    @GetMapping
    public Object find(Menu obj) {
        return Result.success(menuService.selectMenuList(obj));
    }
    /**
     * 条件+分页查询 page=2&limit=10
     */
//    @RequiresRoles("admin")
//    @RequiresPermissions(value={"system:menu:page"},logical = Logical.OR) //用来判断权限字符串
    @RequiresPermissions("system:menu:page")
    @RequestMapping("/page")
    public Object findPage(Menu obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                           @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        System.out.println(obj);
        PageInfo<Menu> pageInfo = menuService.selectMenuList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        Menu menu = menuService.selectMenuById(id);
        return Result.success(menu);
    }
    /**
     * 添加
     */
    @PostMapping
    public Object add(@RequestBody Menu obj) {
        redisUtil.del(Constants.SYS_MENU_KEY);
        int i = menuService.insertMenu(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    public Object update(@RequestBody Menu obj) {
        redisUtil.del(Constants.SYS_MENU_KEY);
        int i = menuService.updateMenu(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Object del(@PathVariable Integer id) {
        redisUtil.del(Constants.SYS_MENU_KEY);
        int i = menuService.deleteMenuById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
