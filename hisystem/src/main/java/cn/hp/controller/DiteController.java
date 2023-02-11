package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.Dicttype;
import cn.hp.entity.Dite;
import cn.hp.service.IDicttypeService;
import cn.hp.service.IDiteService;
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
import java.util.List;

/**
 * 字典 Controller

 */
@RestController
@RequestMapping("/api/dite")
public class DiteController
{
    @Autowired
    private IDiteService diteService;
    @Autowired
    private IDicttypeService dicttypeService;
    @Resource
    private RedisUtil redisUtil;
/**
 * 查询字典信息
 */
@GetMapping("/getDite")
public Object getDite(Dite obj) {
    if(!redisUtil.hHasKey(Constants.SYS_DICT_CACHE,Constants.SYS_DICT_KEY+obj.getTypeNo())){
        List<Dite> list = diteService.selectDiteList(new Dite());
        List<Dicttype> dicttypes = dicttypeService.selectDicttypeList(new Dicttype());
        dicttypes.forEach(dicttype->{
            List<Dite> list2= new ArrayList<>();
            list.forEach(dite -> {
                if(dicttype.getTypeNo().equals(dite.getTypeNo())){
                    list2.add(dite);
                }
            });
            redisUtil.hset(Constants.SYS_DICT_CACHE ,Constants.SYS_DICT_KEY+dicttype.getTypeNo(), JSON.toJSONString(list2), RandomNumber.randomNum(null));
        });
    }
    Object o = redisUtil.hget(Constants.SYS_DICT_CACHE ,Constants.SYS_DICT_KEY+obj.getTypeNo());
    return Result.success(JSON.parseArray((String) o, Dite.class));
}
    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:dite:find")
    public Object find(Dite obj) {
        return Result.success(diteService.selectDiteList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:dite:page")
    public Object findPage(Dite obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        PageInfo<Dite> pageInfo = diteService.selectDiteList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:dite:findById")
    public Object findById(@PathVariable Integer id) {
        Dite dite = diteService.selectDiteById(id);
        return Result.success(dite);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:dite:add")
    public Object add(@RequestBody Dite obj) {
        redisUtil.del(Constants.SYS_DICTTYPE_KEY);
        int i = diteService.insertDite(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:dite:update")
    public Object update(@RequestBody Dite obj) {
        redisUtil.del(Constants.SYS_DICTTYPE_KEY);
        int i = diteService.updateDite(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:dite:del")
    public Object del(@PathVariable Integer id) {
        redisUtil.del(Constants.SYS_DICTTYPE_KEY);
        int i = diteService.deleteDiteById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
