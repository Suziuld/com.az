package cn.hp.service.imp;

import java.util.List;

import cn.hp.common.constant.Constants;

import cn.hp.common.exception.BusinessException;
import cn.hp.entity.*;
import cn.hp.mapper.MedicalRecordMapper;
import cn.hp.mapper.OutpatientQueueMapper;
import cn.hp.mapper.RegisterMapper;
import cn.hp.service.IGetPatientInfoService;
import cn.hp.util.DateUtil;
import cn.hp.util.StringUtils;
import cn.hp.mapper.MedicalExaminationMapper;
import cn.hp.service.IMedicalExaminationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 */
@Service
public class MedicalExaminationServiceImp implements IMedicalExaminationService {
    @Resource
    private MedicalExaminationMapper medicalExaminationMapper;
    @Resource
    private OutpatientQueueMapper outpatientQueueMapper;
    @Resource
    private MedicalRecordMapper medicalRecordMapper;
    @Resource
    private RegisterMapper registerMapper;
    @Resource
    private IGetPatientInfoService iGetPatientInfoService;

    @Override
    public Patient getCardIdInfor(Patient obj) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNull(user)) {
            throw new BusinessException("登录信息已过期，请重新登录！");
        }
        //获取患者信息
        Patient patient = iGetPatientInfoService.getPatientInfo(obj);

        //当天
        String nowDate = DateUtil.getCurrentDateSimpleToString();
        OutpatientQueue oq = new OutpatientQueue();
        oq.setPatientId(patient.getId())
                .setStatus(Constants.QUEUE.NORMAL)
                .setUserId(user.getId())
                .setStartTime(nowDate.concat(" 00:00:00"))
                .setEndTime(nowDate.concat(" 23:59:59"));
        List<OutpatientQueue> outpatientQueues = outpatientQueueMapper.selectOutpatientQueueList(oq);
        if (outpatientQueues.isEmpty()) {
            throw new BusinessException("未查询到挂号信息，请与患者核对挂号就诊医生！");
        }
        if (outpatientQueues.size() != 1) {
            throw new BusinessException("队列信息异常，请联系管理员！");
        }
        OutpatientQueue outpatientQueue = outpatientQueues.get(0);
        System.out.println(outpatientQueue.getId());
        patient.setQueueId(outpatientQueue.getId());
        //检查科室
        String deptCode = outpatientQueue.getDepartmentId() + "";
        System.out.println(deptCode);
        return patient;
    }

    @Override
    @Transactional
    public int saveMedicalExaminationInfo(MedicalExamination obj) {
        int i = 0;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNull(user)) {
            throw new BusinessException("登录信息已过期！");
        }

        //体检队列信息
        OutpatientQueue outpatientQueue = outpatientQueueMapper.selectOutpatientQueueById(obj.getQueueId());

        if (StringUtils.isNull(outpatientQueue)) {
            throw new BusinessException("队列信息异常，请联系管理员！");
        }

        //挂号信息
        Register register = registerMapper.selectRegisterById(outpatientQueue.getRegisterId());

        //体检信息
        MedicalExamination medicalExamination = new MedicalExamination();

        //校验处方号
        String prescriptionNum = obj.getPrescriptionNum();
        if (!StringUtils.isNull(prescriptionNum)&&!"".equals(prescriptionNum)) {
            MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByPrescriptionNum(prescriptionNum);
            if (medicalRecord == null) {
                throw new BusinessException("未查询相关就诊记录，处方号无效！");
            }
            //处方号对应门诊队列信息
            OutpatientQueue queue = outpatientQueueMapper.selectOutpatientQueueByRegisterId(medicalRecord.getRegisterId());
            if (queue == null) {
                throw new BusinessException("该处方号未查询相关门诊队列信息，信息异常请联系管理员！");
            }
            if (!outpatientQueue.getPatientId().equals(queue.getPatientId())) {
                throw new BusinessException("该处方号对应患者信息与体检队列患者信息不符！");
            }

            if (Constants.QUEUE.LATER != queue.getStatus()) {

                throw new BusinessException("该患者无待处理的门诊，处方号无效！");
            }
            medicalExamination.setPrescriptionNum(prescriptionNum);
        }
        medicalExamination.setRegisterId(register.getId());
        medicalExamination.setBloodPressure(obj.getBloodPressure());
        medicalExamination.setBodyTemperature(obj.getBodyTemperature());
        medicalExamination.setHeartRate(obj.getHeartRate());
        medicalExamination.setPulse(obj.getPulse());
        medicalExamination.setExaminationCost(obj.getExaminationCost());

        i += medicalExaminationMapper.insertMedicalExamination(medicalExamination);

        //修改体检队列状态为过期
        outpatientQueue.setStatus(Constants.QUEUE.OVERDUE);
        i += outpatientQueueMapper.updateOutpatientQueue(outpatientQueue);

        //更新就诊状态
        register.setTreatmentStatus(1);
        i += registerMapper.updateRegister(register);

        return i;
    }

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public MedicalExamination selectMedicalExaminationById(Integer id) {
        return medicalExaminationMapper.selectMedicalExaminationById(id);
    }

    /**
     * 分页查询列表
     *
     * @param medicalExamination
     * @return
     */
    @Override
    public PageInfo<MedicalExamination> selectMedicalExaminationList(MedicalExamination medicalExamination, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<MedicalExamination> list = medicalExaminationMapper.selectMedicalExaminationList(medicalExamination);
        return new PageInfo<MedicalExamination>(list);
    }

    /**
     * 查询列表
     *
     * @param medicalExamination
     * @return
     */
    @Override
    public List<MedicalExamination> selectMedicalExaminationList(MedicalExamination medicalExamination) {
        return medicalExaminationMapper.selectMedicalExaminationList(medicalExamination);
    }

    /**
     * 新增
     *
     * @param medicalExamination
     * @return 结果
     */
    @Override
    public int insertMedicalExamination(MedicalExamination medicalExamination) {
        return medicalExaminationMapper.insertMedicalExamination(medicalExamination);
    }

    /**
     * 修改
     *
     * @param medicalExamination
     * @return 结果
     */
    @Override
    public int updateMedicalExamination(MedicalExamination medicalExamination) {
        return medicalExaminationMapper.updateMedicalExamination(medicalExamination);
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteMedicalExaminationById(Integer id) {
        return medicalExaminationMapper.deleteMedicalExaminationById(id);
    }
}
