package cn.hp.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.Goout;
import cn.hp.service.IGooutService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Controller
 * /api/goout
 */
@RestController
@RequestMapping("/api/goout")
public class GooutController
{
    @Autowired
    private IGooutService gooutService;
    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:goout:find")
    public Object find(Goout obj) {
        return Result.success(gooutService.selectGooutList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:goout:page")
    public Object findPage(Goout obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
        PageInfo<Goout> pageInfo = gooutService.selectGooutList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:goout:findById")
    public Object findById(@PathVariable Integer id) {
        Goout goout = gooutService.selectGooutById(id);
        return Result.success(goout);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:goout:add")
    public Object add(@RequestBody Goout obj) {
        int i = gooutService.insertGoout(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:goout:update")
    public Object update(@RequestBody Goout obj) {
        int i = gooutService.updateGoout(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:goout:del")
    public Object del(@PathVariable Integer id) {
        int i = gooutService.deleteGooutById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
