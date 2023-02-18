package cn.hp.controller;

import cn.hp.common.constant.Constants;
import cn.hp.entity.Drug;
import cn.hp.service.IDrugService;
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
 * 药物 Controller

 */
@RestController
@RequestMapping("/api/drug")
public class DrugController
{
    @Autowired
    private IDrugService drugService;
    @Resource
    private RedisUtil redisUtil;
    /**
     * 获取药品信息，并存放到redis中
     */
    @GetMapping("/getDrug")
    public Object getDrug(Drug obj) {
        if(!redisUtil.hasKey(Constants.SYS_DRUG_KEY)){
            List<Drug> list = drugService.selectDrugList(obj);
            redisUtil.set(Constants.SYS_DRUG_KEY , JSON.toJSONString(list), RandomNumber.randomNum(null));
        }
        Object o = redisUtil.get(Constants.SYS_DRUG_KEY);
        return Result.success(JSON.parseArray((String) o, Drug.class));
    }

    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:drug:find")
    public Object find(Drug obj) {
        return Result.success(drugService.selectDrugList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:drug:page")
    public Object findPage(Drug obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        PageInfo<Drug> pageInfo = drugService.selectDrugList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:drug:findById")
    public Object findById(@PathVariable Integer id) {
        Drug drug = drugService.selectDrugById(id);
        return Result.success(drug);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:drug:add")
    public Object add(@RequestBody Drug obj) {
        redisUtil.del(Constants.SYS_DRUG_KEY);
        int i = drugService.insertDrug(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:drug:update")
    public Object update(@RequestBody Drug obj) {
        redisUtil.del(Constants.SYS_DRUG_KEY);
        int i = drugService.updateDrug(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:drug:del")
    public Object del(@PathVariable Integer id) {
        redisUtil.del(Constants.SYS_DRUG_KEY);
        int i = drugService.deleteDrugById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
