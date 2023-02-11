package cn.hp.controller;

import cn.hp.entity.MedicalExamination;
import cn.hp.entity.MedicalRecord;
import cn.hp.entity.OutpatientQueue;
import cn.hp.entity.Patient;
import cn.hp.service.IOutpatientQueueService;
import cn.hp.util.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* 就诊队列 业务 Controller
*/
@RestController
@RequestMapping("/api/outpatient")
//@Api(tags = "就诊队列API")
public class OutpatientQueueBusinessController {
@Autowired
private IOutpatientQueueService outpatientService;

/**
 * 读取就诊卡,获取病人信息
 *
 * @return
 */
@PostMapping(value = "/getCardIdInfor")
public Object getCardIdInfor(@RequestBody Patient obj) throws Exception {
    return Result.success(outpatientService.getCardIdInfor(obj));
}

/**
 * 修改患者基础信息
 *
 * @return
 */
@PostMapping(value = "/changePatientInfor")
public Object changePatientInfor(@RequestBody Patient obj) {
    return Result.success(outpatientService.changePatientInfor(obj));
}

@PostMapping(value = "/getalloutpatientqueue")
@ApiOperation(value = "获取当前医生下所有门诊队列患者", httpMethod = "POST", notes = "获取当前医生下所有门诊队列患者")
public Object getAllOutpatientQueue() {
    return Result.success(outpatientService.getAllOutpatientQueue());
}
/**
 * 就诊，稍后处理
 * @return
 */
@PostMapping(value = "/ProcessLaterMedicalRecord")
public Object processLaterMedicalRecord(@RequestBody MedicalRecord obj) {
    return Result.success(outpatientService.processLaterMedicalRecord(obj));
}

/**
 * 从稍后处理恢复到队列
 * @param registerId
 * @return
 */
@PostMapping(value = "/restorePatientInfor")
public Object restorePatientInfor(@RequestParam Integer registerId) throws Exception {
    return Result.success(outpatientService.restorePatientInfor(registerId));
}
/**
 * 就诊完成，保存病历
 * @return
 */
@PostMapping(value = "/addMedicalRecord")
public Object addMedicalRecord(@RequestBody @Validated MedicalRecord reqVO) {
    return Result.success(outpatientService.addMedicalRecord(reqVO));
}
//
/**
 * 就诊获取体检信息
 * @param prescriptionNum
 * @return
 */
@PostMapping(value = "/getMedicalExamination")
public MedicalExamination getMedicalExamination(@RequestParam String prescriptionNum) {

    return outpatientService.getMedicalExamination(prescriptionNum);
}
}
