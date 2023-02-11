package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.Department;
import cn.hp.service.IDepartmentService;
import cn.hp.util.RandomNumber;
import cn.hp.util.RedisUtil;
import cn.hp.util.Result;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科室 Controller
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController
{
    @Autowired
    private IDepartmentService departmentService;
    @Resource
    private RedisUtil redisUtil;

/**
 * redis缓存数据
 */
@GetMapping("/getDepartmente")
public Object getDepartmente(Department obj) {
    if(!redisUtil.hasKey(Constants.SYS_DEPARTMENT_KEY)){
        List<Department> list = departmentService.selectDepartmentList(obj);
        redisUtil.set(Constants.SYS_DEPARTMENT_KEY , JSON.toJSONString(list), RandomNumber.randomNum(null));
    }
    Object o = redisUtil.get(Constants.SYS_DEPARTMENT_KEY);
    return Result.success(JSON.parseArray((String) o, Department.class));
}
    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:department:find")
    public Object find(Department obj) {
        return Result.success(departmentService.selectDepartmentList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:department:page")
    public Object findPage(Department obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        PageInfo<Department> pageInfo = departmentService.selectDepartmentList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:department:findById")
    public Object findById(@PathVariable Integer id) {
        Department department = departmentService.selectDepartmentById(id);
        return Result.success(department);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:department:add")
    public Object add(@RequestBody Department obj) {
        redisUtil.del(Constants.SYS_DEPARTMENT_KEY);
        int i = departmentService.insertDepartment(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:department:update")
    public Object update(@RequestBody Department obj) {
        redisUtil.del(Constants.SYS_DEPARTMENT_KEY);
        int i = departmentService.updateDepartment(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:department:del")
    public Object del(@PathVariable Integer id) {
        redisUtil.del(Constants.SYS_DEPARTMENT_KEY);
        int i = departmentService.deleteDepartmentById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
