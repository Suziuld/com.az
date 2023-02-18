package cn.hp.controller;

import java.util.List;

import cn.hp.entity.Menu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.RoleMenu;
import cn.hp.service.IRoleMenuService;
import cn.hp.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 角色菜单Controller

 */
@RestController
@RequestMapping("/api/roleMenu")
public class RoleMenuController
{
    @Autowired
    private IRoleMenuService roleMenuService;

    @GetMapping("/getRoleMenu")
    public Object getMenu(RoleMenu obj) {
        List<RoleMenu> list = roleMenuService.selectRoleMenuList(obj);
        Integer[] menuIds = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            menuIds[i]=list.get(i).getMenuId();
        }
        return Result.success(menuIds);
    }
    /**
     * 条件查询
     */
    @GetMapping
    public Object find(RoleMenu obj) {
        return Result.success(roleMenuService.selectRoleMenuList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    public Object findPage(RoleMenu obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
        PageInfo<RoleMenu> pageInfo = roleMenuService.selectRoleMenuList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        RoleMenu roleMenu = roleMenuService.selectRoleMenuById(id);
        return Result.success(roleMenu);
    }
    /**
     * 添加
     */
    @PostMapping
    public Object add(@RequestBody RoleMenu obj) {
        int i = roleMenuService.insertRoleMenu(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    public Object update(@RequestBody RoleMenu obj) {
        int i = roleMenuService.updateRoleMenu(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Object del(@PathVariable Integer id) {
        int i = roleMenuService.deleteRoleMenuById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
