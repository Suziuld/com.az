package cn.hp.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.Patient;
import cn.hp.service.IPatientService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *  患者 Controller

 */
@RestController
@RequestMapping("/api/patient")
public class PatientController
{
    @Autowired
    private IPatientService patientService;
    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:patient:find")
    public Object find(Patient obj) {
        return Result.success(patientService.selectPatientList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:patient:page")
    public Object findPage(Patient obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        PageInfo<Patient> pageInfo = patientService.selectPatientList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:patient:findById")
    public Object findById(@PathVariable Integer id) {
        Patient patient = patientService.selectPatientById(id);
        return Result.success(patient);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:patient:add")
    public Object add(@RequestBody Patient obj) {
        int i = patientService.insertPatient(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:patient:update")
    public Object update(@RequestBody Patient obj) {
        int i = patientService.updatePatient(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:patient:del")
    public Object del(@PathVariable Integer id) {
        int i = patientService.deletePatientById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
