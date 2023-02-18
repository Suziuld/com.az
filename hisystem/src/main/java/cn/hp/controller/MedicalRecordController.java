package cn.hp.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.MedicalRecord;
import cn.hp.service.IMedicalRecordService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Controller
 * /api/medicalRecord
 */
@RestController
@RequestMapping("/api/medicalRecord")
public class MedicalRecordController
{
    @Autowired
    private IMedicalRecordService medicalRecordService;
    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:medicalRecord:find")
    public Object find(MedicalRecord obj) {
        return Result.success(medicalRecordService.selectMedicalRecordList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:medicalRecord:page")
    public Object findPage(MedicalRecord obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
        PageInfo<MedicalRecord> pageInfo = medicalRecordService.selectMedicalRecordList(obj, page, limit);
        return Result.RowsSuccess(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:medicalRecord:findById")
    public Object findById(@PathVariable Integer id) {
        MedicalRecord medicalRecord = medicalRecordService.selectMedicalRecordById(id);
        return Result.success(medicalRecord);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:medicalRecord:add")
    public Object add(@RequestBody MedicalRecord obj) {
        int i = medicalRecordService.insertMedicalRecord(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:medicalRecord:update")
    public Object update(@RequestBody MedicalRecord obj) {
        int i = medicalRecordService.updateMedicalRecord(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:medicalRecord:del")
    public Object del(@PathVariable Integer id) {
        int i = medicalRecordService.deleteMedicalRecordById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
