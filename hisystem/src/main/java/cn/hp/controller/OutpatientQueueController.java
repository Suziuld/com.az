package cn.hp.controller;

import java.util.List;

import cn.hp.entity.MedicalRecord;
import cn.hp.entity.Patient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.OutpatientQueue;
import cn.hp.service.IOutpatientQueueService;
import cn.hp.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 就诊队列 Controller
 */
@RestController
@RequestMapping("/api/outpatientQueue")
public class OutpatientQueueController {
    @Autowired
    private IOutpatientQueueService outpatientService;

    /**
     * 条件查询业务
     */
    @GetMapping
    @RequiresPermissions("system:outpatientQueue:find")
    public Object find(OutpatientQueue obj) {
        return Result.success(outpatientService.selectOutpatientQueueList(obj));
    }

    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:outpatientQueue:page")
    public Object findPage(OutpatientQueue obj, @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer limit) {
        PageInfo<OutpatientQueue> pageInfo = outpatientService.selectOutpatientQueueList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:outpatientQueue:findById")
    public Object findById(@PathVariable Integer id) {
        OutpatientQueue outpatientQueue = outpatientService.selectOutpatientQueueById(id);
        return Result.success(outpatientQueue);
    }

    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:outpatientQueue:add")
    public Object add(@RequestBody OutpatientQueue obj) {
        int i = outpatientService.insertOutpatientQueue(obj);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:outpatientQueue:update")
    public Object update(@RequestBody OutpatientQueue obj) {
        int i = outpatientService.updateOutpatientQueue(obj);
        return i > 0 ? Result.success("修改成功！") : Result.error("修改失败！");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:outpatientQueue:del")
    public Object del(@PathVariable Integer id) {
        int i = outpatientService.deleteOutpatientQueueById(id);
        return i > 0 ? Result.success("删除成功！") : Result.error("删除失败！");
    }

}
