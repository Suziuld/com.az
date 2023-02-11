package cn.hp.controller;

import java.util.List;

import cn.hp.entity.Idcard;
import cn.hp.entity.Patient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.MedicalExamination;
import cn.hp.service.IMedicalExaminationService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
* Controller
* /api/medicalExamination
*/
@RestController
@RequestMapping("/api/medicalExamination")
public class MedicalExaminationController
{
@Autowired
private IMedicalExaminationService medicalExaminationService;

/**
 * 条件查询
 */
@PostMapping("/getCardIdInfor")
public Object getCardIdInfor(@RequestBody Patient obj) {
    Patient patient =medicalExaminationService.getCardIdInfor(obj);
    return Result.success(patient);
}

/**
 * 条件查询
 */
@PostMapping("/saveMedicalExaminationInfo")
public Object saveMedicalExaminationInfo(@RequestBody MedicalExamination obj) {
    int i=medicalExaminationService.saveMedicalExaminationInfo(obj);
    return i>0?Result.success("成功！"):Result.error("失败！");
}



/**
 * 条件查询
 */
@GetMapping
@RequiresPermissions("system:medicalExamination:find")
public Object find(MedicalExamination obj) {
    return Result.success(medicalExaminationService.selectMedicalExaminationList(obj));
}
/**
 * 条件+分页查询
 */
@RequestMapping("/page")
@RequiresPermissions("system:medicalExamination:page")
public Object findPage(MedicalExamination obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                   @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
    PageInfo<MedicalExamination> pageInfo = medicalExaminationService.selectMedicalExaminationList(obj, page, limit);
    return Result.success(pageInfo);
}

/**
 * 查询指定信息
 */
@GetMapping("/{id}")
@RequiresPermissions("system:medicalExamination:findById")
public Object findById(@PathVariable Integer id) {
    MedicalExamination medicalExamination = medicalExaminationService.selectMedicalExaminationById(id);
    return Result.success(medicalExamination);
}
/**
 * 添加
 */
@PostMapping
@RequiresPermissions("system:medicalExamination:add")
public Object add(@RequestBody MedicalExamination obj) {
    int i = medicalExaminationService.insertMedicalExamination(obj);
    return i>0?Result.success("添加成功！"):Result.error("添加失败！");
}

/**
 * 修改
 */
@PutMapping
@RequiresPermissions("system:medicalExamination:update")
public Object update(@RequestBody MedicalExamination obj) {
    int i = medicalExaminationService.updateMedicalExamination(obj);
    return i>0?Result.success("修改成功！"):Result.error("修改失败！");
}
/**
 * 删除
 */
@DeleteMapping("/{id}")
@RequiresPermissions("system:medicalExamination:del")
public Object del(@PathVariable Integer id) {
    int i = medicalExaminationService.deleteMedicalExaminationById(id);
    return i>0?Result.success("删除成功！"):Result.error("删除失败！");
}

}
