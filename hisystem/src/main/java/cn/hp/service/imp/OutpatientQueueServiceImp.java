package cn.hp.service.imp;

import java.util.ArrayList;
import java.util.List;

import cn.hp.common.constant.Constants;

import cn.hp.common.exception.BusinessException;
import cn.hp.entity.*;
import cn.hp.mapper.*;
import cn.hp.service.IGetPatientInfoService;
import cn.hp.util.DateUtil;
import cn.hp.util.StringUtils;
import cn.hp.service.IOutpatientQueueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 */
@Service
public class OutpatientQueueServiceImp implements IOutpatientQueueService {
    @Resource
    private OutpatientQueueMapper outpatientQueueMapper;
    @Resource
    private IGetPatientInfoService getPatientInfoService;
    @Resource
    private MedicalRecordMapper medicalRecordMapper;
    @Resource
    private MedicalExaminationMapper medicalExaminationMapper;
    @Resource
    private PatientMapper patientMapper;
    @Resource
    private RegisterMapper registerMapper;

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public OutpatientQueue selectOutpatientQueueById(Integer id) {
        return outpatientQueueMapper.selectOutpatientQueueById(id);
    }

    /**
     * 分页查询列表
     *
     * @param outpatientQueue
     * @return
     */
    @Override
    public PageInfo<OutpatientQueue> selectOutpatientQueueList(OutpatientQueue outpatientQueue, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<OutpatientQueue> list = outpatientQueueMapper.selectOutpatientQueueList(outpatientQueue);
        return new PageInfo<OutpatientQueue>(list);
    }

    /**
     * 查询列表
     *
     * @param outpatientQueue
     * @return
     */
    @Override
    public List<OutpatientQueue> selectOutpatientQueueList(OutpatientQueue outpatientQueue) {
        return outpatientQueueMapper.selectOutpatientQueueList(outpatientQueue);
    }

    /**
     * 新增
     *
     * @param outpatientQueue
     * @return 结果
     */
    @Override
    public int insertOutpatientQueue(OutpatientQueue outpatientQueue) {
        return outpatientQueueMapper.insertOutpatientQueue(outpatientQueue);
    }

    /**
     * 修改
     *
     * @param outpatientQueue
     * @return 结果
     */
    @Override
    public int updateOutpatientQueue(OutpatientQueue outpatientQueue) {
        return outpatientQueueMapper.updateOutpatientQueue(outpatientQueue);
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteOutpatientQueueById(Integer id) {
        return outpatientQueueMapper.deleteOutpatientQueueById(id);
    }

    @Override
    public Patient getCardIdInfor(Patient patient) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNull(user)) {
            throw new BusinessException("登录信息已过期，请重新登录！");
        }
        //获取患者信息
        Patient patientInfor = getPatientInfoService.getPatientInfo(patient);
        if (patientInfor == null) {
            throw new BusinessException("该患者信息不存在！");
        }
        //查询挂号队列信息
        OutpatientQueue oq = new OutpatientQueue();
        String nowDate = DateUtil.getCurrentDateSimpleToString();
        oq.setPatientId(patientInfor.getId())
                .setUserId(user.getId())
                .setStatus(1)
                .setStartTime(nowDate.concat(" 00:00:00"))
                .setEndTime(nowDate.concat(" 23:59:59"));
        List<OutpatientQueue> outpatientQueues = outpatientQueueMapper.selectOutpatientQueueList(oq);
        if (outpatientQueues.isEmpty()) {
            throw new BusinessException("未查询到挂号信息，请与患者核对挂号就诊医生！");
        }

        //符合条件的队列状态要么是正常，要么是待处理。不会同时存在
        if (outpatientQueues.size() == 1) {
            OutpatientQueue outpatientQueue = outpatientQueues.get(0);
            if (outpatientQueue.getStatus() == Constants.QUEUE.LATER) {
                throw new BusinessException("未完成就诊，请从左侧栏恢复！");
            }
            BeanUtils.copyProperties(patientInfor, patient);
            patient.setDate(DateUtil.getCurrentDateSimpleToString());
            patient.setDepartment(outpatientQueue.getDepartment());//挂号对象需要管理科室信息，获取科室信息
            patient.setAge(DateUtil.getAge(patientInfor.getBirthday()));
            //队列Id
            patient.setQueueId(outpatientQueue.getId());
            MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByRegisterId(outpatientQueue.getRegisterId());
            if (StringUtils.isNull(medicalRecord)) {
                patient.setPrescriptionNum("O".concat(String.valueOf(System.currentTimeMillis())));
            } else {
                patient.setPrescriptionNum(medicalRecord.getPrescriptionNum());
            }
            return patient;
        } else {
            throw new BusinessException("队列信息异常，请联系管理员！");
        }
    }

    @Override
    public int changePatientInfor(Patient obj) {
        try {
            Patient patient = patientMapper.selectPatientByCardId(obj.getCardId());
            obj.setId(patient.getId());
            System.out.println(obj);
            return patientMapper.updatePatient(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("信息提交异常！请稍后重试！");
        }
    }

    @Override
    @Transactional
    public List<OutpatientQueue> getAllOutpatientQueue() {
        List<OutpatientQueue> rsp = new ArrayList<>();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (!StringUtils.isNull(user)) {
            List<OutpatientQueue> outpatientQueueList = outpatientQueueMapper.selectOutpatientQueueList(new OutpatientQueue().setUserId(user.getId()));
            if (outpatientQueueList != null && !outpatientQueueList.isEmpty()) {
                //非当天病人
                List<OutpatientQueue> overdueQueues = new ArrayList<>();
                outpatientQueueList.forEach(outpatientQueue -> {
                    String createDate = DateUtil.DateTimeToDate(outpatientQueue.getCreateDatetime());
                    String nowDate = DateUtil.getCurrentDateSimpleToString();
                    if (nowDate.equals(createDate)) {
                        if (outpatientQueue.getStatus() != Constants.QUEUE.OVERDUE) {
                            OutpatientQueue vo = new OutpatientQueue();
                            vo.setCardId(outpatientQueue.getCardId());
                            String remark = outpatientQueue.getRemark();
                            if (remark != null) {
                                vo.setPatientName(remark.split("#")[0]);
                            }
                            vo.setRegisterId(outpatientQueue.getRegisterId());
                            vo.setStatus(outpatientQueue.getStatus());
                            rsp.add(vo);
                        }
                    } else {
                        if (outpatientQueue.getStatus() == 1 || outpatientQueue.getStatus() == -1) {
                            outpatientQueue.setStatus(Constants.QUEUE.OVERDUE);
                            overdueQueues.add(outpatientQueue);
                        }
                    }
                });
                if (overdueQueues.size() > 0) {
                    overdueQueues.forEach(o -> {
                        outpatientQueueMapper.updateOutpatientQueue(new OutpatientQueue().setId(o.getId()).setStatus(o.getStatus()));
                    });
                }
            }
        }
        return rsp;
    }

/**
 * 稍后处理
 *
 * @return
 */
@Override
public String processLaterMedicalRecord(MedicalRecord obj) {
    OutpatientQueue outpatientQueue = outpatientQueueMapper.selectOutpatientQueueById(obj.getQueueId());
    if (StringUtils.isNull(outpatientQueue)) {
        throw new BusinessException("信息提交异常！请稍后重试！");
    }
    //就诊记录
    MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByPrescriptionNum(obj.getPrescriptionNum());
    if (medicalRecord == null) {
        medicalRecord = new MedicalRecord();
        medicalRecord.setConditionDescription(obj.getConditionDescription());
        medicalRecord.setRegisterId(outpatientQueue.getRegisterId());
        medicalRecord.setPrescriptionNum(obj.getPrescriptionNum());
        medicalRecordMapper.insertMedicalRecord(medicalRecord);
    } else {
        medicalRecord.setRegisterId(outpatientQueue.getRegisterId());
        medicalRecord.setConditionDescription(obj.getConditionDescription());
        medicalRecordMapper.updateMedicalRecord(medicalRecord);
        //检查是否已体检过
        MedicalExamination medicalExamination = medicalExaminationMapper.findByprescriptionNum(medicalRecord.getPrescriptionNum());
        if (StringUtils.isNotNull(medicalExamination)) {
            throw new BusinessException("该患者已体检过，无需再稍后处理！");
        }
    }
    //更新为稍后处理状态
    outpatientQueueMapper.updateOutpatientQueue(new OutpatientQueue().setId(outpatientQueue.getId()).setStatus(Constants.QUEUE.LATER));
    return Constants.USER.SUCCESS;
}

    @Override
    @Transactional
    public Patient restorePatientInfor(Integer registerId) {
        Patient rspVO = new Patient();
        OutpatientQueue outpatientQueue = outpatientQueueMapper.selectOutpatientQueueByRegisterId(registerId);
        if (org.springframework.util.StringUtils.isEmpty(outpatientQueue) || Constants.QUEUE.LATER != outpatientQueue.getStatus()) {
            throw new BusinessException("队列信息异常，请刷新页面或联系管理员后重试！");
        }
        MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByRegisterId(registerId);
        if (StringUtils.isNull(medicalRecord)) {
            throw new BusinessException("就诊信息异常，请刷新页面或联系管理员后重试！");
        }
        //检查体检是否已收费
        MedicalExamination medicalExamination = medicalExaminationMapper.findByprescriptionNum(medicalRecord.getPrescriptionNum());
        if (StringUtils.isNotNull(medicalExamination)) {
            Register register = registerMapper.selectRegisterById(medicalExamination.getRegisterId());
            if (register.getChargeStatus() == 0) {
                throw new BusinessException("就诊该就诊所关联的体检项目未收费信息异常，请收费后再继续就诊！");
            }
        }
        Patient patientInfor = patientMapper.selectPatientById(outpatientQueue.getPatientId());
        BeanUtils.copyProperties(medicalRecord, rspVO);
        BeanUtils.copyProperties(patientInfor, rspVO);
        outpatientQueueMapper.updateOutpatientQueue(new OutpatientQueue().setId(outpatientQueue.getId()).setStatus(1));
        rspVO.setDate(DateUtil.getCurrentDateSimpleToString());
        rspVO.setDepartment(outpatientQueue.getDepartment());
        rspVO.setQueueId(outpatientQueue.getId());
        return rspVO;
    }

/**
 * 就诊完成，保存病历
 */
@Override
@Transactional
public String addMedicalRecord(MedicalRecord reqVO) {
    //门诊队列
    OutpatientQueue outpatientQueue = outpatientQueueMapper.selectOutpatientQueueById(reqVO.getQueueId());
    if (StringUtils.isNull(outpatientQueue)) {
        throw new BusinessException("门诊队列信息异常！");
    }
    //更新就诊状态
    Register register = new Register();
    register.setId(outpatientQueue.getRegisterId());
    register.setTreatmentStatus(1);
    registerMapper.updateRegister(register);
    //就诊记录表记录完善
    //通过处方号查询
    MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByPrescriptionNum(reqVO.getPrescriptionNum());
    if (medicalRecord == null) {
        medicalRecord = new MedicalRecord();
        medicalRecord.setPrescriptionNum(reqVO.getPrescriptionNum());
    }
    medicalRecord.setConditionDescription(reqVO.getConditionDescription());
    medicalRecord.setDiagnosisResult(reqVO.getDiagnosisResult());
    medicalRecord.setMoney(reqVO.getMoney());
    medicalRecord.setMedicalOrder(reqVO.getMedicalOrder());
    medicalRecord.setPrescription(reqVO.getPrescription());
    medicalRecord.setRegisterId(outpatientQueue.getRegisterId());
    medicalRecord.setPrescription(reqVO.getPrescription());
    //存在id则修改，否则添加
    if(medicalRecord.getId()!=null){
        medicalRecordMapper.updateMedicalRecordByPrescriptionNum(medicalRecord);
    }else{
        medicalRecordMapper.insertMedicalRecord(medicalRecord);
    }
    //修改队列状态为过期
    outpatientQueue.setStatus(Constants.QUEUE.OVERDUE);
    outpatientQueueMapper.updateOutpatientQueue(outpatientQueue);
    Patient patient = new Patient();
    patient.setCardId(reqVO.getCardId()+"")
            .setFamilyHistory(reqVO.getFamilyHistory())
            .setCareer(reqVO.getCareer())
            .setMaritalStatus(reqVO.getMaritalStatus())
            .setPastHistory(reqVO.getPastHistory())
            .setPersonalHistory(reqVO.getPersonalHistory());
    patientMapper.updateByCardIdPatient(patient);
    return Constants.USER.SUCCESS;
}

    @Override
    public MedicalExamination getMedicalExamination(String prescriptionNum) {
        MedicalExamination medicalExamination = medicalExaminationMapper.findByprescriptionNum(prescriptionNum);
        if (StringUtils.isNull(medicalExamination)) {
            throw new BusinessException("未查询到相关体检信息！");
        }
        return medicalExamination;
    }

}
