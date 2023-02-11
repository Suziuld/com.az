package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.Dicttype;
import cn.hp.service.IDicttypeService;
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
 * 字典类型 Controller
 */
@RestController
@RequestMapping("/api/dicttype")
public class DicttypeController {
    @Autowired
    private IDicttypeService dicttypeService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * redis缓存数据
     */
    @GetMapping("/getDictType")
    public Object getDictType(Dicttype obj) {
        if (!redisUtil.hasKey(Constants.SYS_DICTTYPE_KEY)) {
            List<Dicttype> list = dicttypeService.selectDicttypeList(obj);
            redisUtil.set(Constants.SYS_DICTTYPE_KEY, JSON.toJSONString(list), RandomNumber.randomNum(null));
        }
        Object o = redisUtil.get(Constants.SYS_DICTTYPE_KEY);
        return Result.success(JSON.parseArray((String) o, Dicttype.class));
    }

    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:dicttype:find")
    public Object find(Dicttype obj) {
        return Result.success(dicttypeService.selectDicttypeList(obj));
    }

    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:dicttype:page")
    public Object findPage(Dicttype obj, @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        PageInfo<Dicttype> pageInfo = dicttypeService.selectDicttypeList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:dicttype:findById")
    public Object findById(@PathVariable Integer id) {
        Dicttype dicttype = dicttypeService.selectDicttypeById(id);
        return Result.success(dicttype);
    }

    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:dicttype:add")
    public Object add(@RequestBody Dicttype obj) {
        redisUtil.del(Constants.SYS_DICTTYPE_KEY);
        int i = dicttypeService.insertDicttype(obj);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:dicttype:update")
    public Object update(@RequestBody Dicttype obj) {
        redisUtil.del(Constants.SYS_DICTTYPE_KEY);
        int i = dicttypeService.updateDicttype(obj);
        return i > 0 ? Result.success("修改成功！") : Result.error("修改失败！");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:dicttype:del")
    public Object del(@PathVariable Integer id) {
        redisUtil.del(Constants.SYS_DICTTYPE_KEY);
        int i = dicttypeService.deleteDicttypeById(id);
        return i > 0 ? Result.success("删除成功！") : Result.error("删除失败！");
    }

}
