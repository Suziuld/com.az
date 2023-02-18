package cn.hp.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.Idcard;
import cn.hp.service.IIdcardService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Controller
 * /api/idcard
 */
@RestController
@RequestMapping("/api/idcard")
public class IdcardController
{
    @Autowired
    private IIdcardService idcardService;
    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:idcard:find")
    public Object find(Idcard obj) {
        return Result.success(idcardService.selectIdcardList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:idcard:page")
    public Object findPage(Idcard obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
        PageInfo<Idcard> pageInfo = idcardService.selectIdcardList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:idcard:findById")
    public Object findById(@PathVariable Integer id) {
        Idcard idcard = idcardService.selectIdcardById(id);
        return Result.success(idcard);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:idcard:add")
    public Object add(@RequestBody Idcard obj) {
        int i = idcardService.insertIdcard(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:idcard:update")
    public Object update(@RequestBody Idcard obj) {
        int i = idcardService.updateIdcard(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:idcard:del")
    public Object del(@PathVariable Integer id) {
        int i = idcardService.deleteIdcardById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
