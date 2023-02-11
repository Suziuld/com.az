package cn.hp.controller;

import java.util.List;

import cn.hp.entity.RoleMenu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.UserRole;
import cn.hp.service.IUserRoleService;
import cn.hp.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户角色Controller

 */
@RestController
@RequestMapping("/api/userRole")
public class UserRoleController
{
    @Autowired
    private IUserRoleService userRoleService;
    @GetMapping("/getUserRole")
    public Object getUserRole(UserRole obj) {
        List<UserRole> list = userRoleService.selectUserRoleList(obj);
        String [] roleIds = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            UserRole userRole = list.get(i);
            roleIds[i]=userRole.getRoleId()+":"+userRole.getDescription();
        }
        return Result.success(roleIds);
    }
    /**
     * 条件查询
     */
    @GetMapping
    public Object find(UserRole obj) {
        return Result.success(userRoleService.selectUserRoleList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    public Object findPage(UserRole obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
        PageInfo<UserRole> pageInfo = userRoleService.selectUserRoleList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        UserRole userRole = userRoleService.selectUserRoleById(id);
        return Result.success(userRole);
    }
    /**
     * 添加
     */
    @PostMapping
    public Object add(@RequestBody UserRole obj) {
        int i = userRoleService.insertUserRole(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    public Object update(@RequestBody UserRole obj) {
        int i = userRoleService.updateUserRole(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Object del(@PathVariable Integer id) {
        int i = userRoleService.deleteUserRoleById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
