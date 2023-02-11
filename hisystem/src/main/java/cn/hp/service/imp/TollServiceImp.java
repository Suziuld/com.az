package cn.hp.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import cn.hp.common.exception.BusinessException;
import cn.hp.entity.*;
import cn.hp.mapper.*;
import cn.hp.util.DateUtil;
import cn.hp.util.card.Card;
import cn.hp.service.ITollService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import cn.hp.util.StringUtils;

/**
 * Service业务层处理
 */
@Service
public class TollServiceImp implements ITollService {
    private static final Logger logger = LoggerFactory.getLogger(ITollService.class);
    @Resource
    private TollMapper tollMapper;
    @Resource
    private PatientMapper patientMapper;
    @Resource
    private TollTakedrugMapper tollTakedrugMapper;
    @Resource
    private RegisterMapper registerMapper;
    @Resource
    private MedicalRecordMapper medicalRecordMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private MedicalExaminationMapper medicalExaminationMapper;

    /**
     * 获取病历信息
     *
     * @param cardId
     * @param tollStatus
     * @return
     */
    @Override
    public List<Toll> getAllMedicalRecord(String cardId, String tollStatus) {
        if (StringUtils.isEmpty(cardId)) {
            return null;
        }
        Patient patient = patientMapper.selectPatientByCardId(cardId);

        //患者处方存在已划价收费未取药，禁止划价
        List<TollTakedrug> tollTakeDrugInfos = tollTakedrugMapper.findByPatientIdAndTakingDrugStatus(new TollTakedrug().setPatientId(patient.getId()).setTakingDrugStatus(0));
        if (!CollectionUtils.isEmpty(tollTakeDrugInfos)) {
            throw new BusinessException("该卡号存在已收费未取药的处方,处方号：" + tollTakeDrugInfos.get(0).getPrescriptionNum() + "，请取药后再试！");
        }
        Register r = new Register();
        r.setPatientId(patient.getId()).setRegisterStatus(1).setTreatmentStatus(1);
        if (!StringUtils.isEmpty(tollStatus)) {
            r.setChargeStatus(Integer.parseInt(tollStatus));
        }
        List<Register> registerList = registerMapper.selectRegisterList(r);

        if (registerList == null || registerList.size() <= 0) {
            return null;
        }
        List<Toll> tollMedicals = new ArrayList<>();

        for (Register register : registerList) {
            System.out.println(register.toString());
            Toll toll = new Toll();
            MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByRegisterId(register.getId());
            if (medicalRecord == null) {
                continue;
            }
            toll.setChargeStatus(register.getChargeStatus());
            toll.setPrescriptionNum(medicalRecord.getPrescriptionNum());
            toll.setRegisterId(register.getId());
            toll.setRegisterType(register.getRegisterType());
            toll.setStatus(CollectionUtils.isEmpty(tollTakeDrugInfos) ? 0 : 1);
            String departmentName = "";
            Department department = departmentMapper.selectDepartmentById(register.getDepartmentId());
            if (department != null) {
                departmentName = department.getName();
            }
            toll.setDepartment(departmentName);
            toll.setDoctorName(register.getDoctor());
            toll.setOutpatientDate(medicalRecord.getCreateDatetime());
            tollMedicals.add(toll);
        }
        return tollMedicals;
    }

    /**
     * 获取处方笺信息
     *
     * @param cardId
     * @param registerId
     */
    @Override
    public Toll getMedicalRecord(String cardId, String registerId) {

        Patient patient = patientMapper.selectPatientByCardId(cardId);

        MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByRegisterId(Integer.parseInt(registerId));

        if (StringUtils.isNull(patient) || StringUtils.isNull(medicalRecord)) {
            return null;
        }
        Toll recordRspVO = new Toll();
        try {
            recordRspVO.setAge(DateUtil.getAge(patient.getBirthday()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        recordRspVO.setCreateDate(DateUtil.DateTimeToDate(medicalRecord.getCreateDatetime()));
        recordRspVO.setDiagnosisResult(medicalRecord.getDiagnosisResult());
        recordRspVO.setDrugCost(medicalRecord.getMoney());
        recordRspVO.setMedicalOrder(medicalRecord.getMedicalOrder());
        recordRspVO.setName(patient.getName());
        recordRspVO.setNationality(patient.getNationality());
        recordRspVO.setPrescription(medicalRecord.getPrescription());
        recordRspVO.setSex(patient.getSex());
        recordRspVO.setNowDate(DateUtil.getCurrentDateSimpleToString());
        MedicalExamination medicalExamination = medicalExaminationMapper.findByprescriptionNum(medicalRecord.getPrescriptionNum());
        if (medicalExamination != null) {
            recordRspVO.setExaminationCost(medicalExamination.getExaminationCost());
        }
        return recordRspVO;
    }

    /**
     * 划价收费完成，保存信息
     *
     * @param reqVO
     * @return
     */
    @Override
    @Transactional
    public int saveTollInfo(Toll reqVO) {
        int i = 0;
        try {
            Register register = registerMapper.selectRegisterById(reqVO.getRegisterId());
            if (StringUtils.isNull(register)) {
                throw new BusinessException("未查询到相关挂号记录！");
            }
            MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByPrescriptionNum(reqVO.getPrescriptionNum());
            if (StringUtils.isNull(medicalRecord)) {
                throw new BusinessException("未查询到相关就诊记录！");
            }
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if (StringUtils.isNull(user)) {
                throw new BusinessException("登录信息异常！");
            }
            TollTakedrug tollTakedrug = new TollTakedrug();
            tollTakedrug.setPrescriptionNum(medicalRecord.getPrescriptionNum());
            tollTakedrug.setTakingDrugStatus(0);
            tollTakedrug.setTollOperator(user.getId() + "");
            tollTakedrug.setTollDateTime(DateUtil.getCurrentDateToString());
            tollTakedrug.setPatientId(register.getPatientId());
            //更新收费状态
            register.setChargeStatus(1);

            i += registerMapper.updateRegister(register);
            i += tollTakedrugMapper.insertTollTakedrug(tollTakedrug);
            return i;
        } catch (Exception e) {
            logger.error("req={},保存划价收费—拿药信息异常！", JSON.toJSONString(reqVO, true), e);
            throw new BusinessException("操作异常，请稍后重试！");
        }
    }

    @Override
    public MedicalExamination getExaminationToll(Idcard reqVO) {
        System.out.println(reqVO);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNull(user)) {
            throw new BusinessException("登录信息已过期，请重新登录！");
        }

        //获取患者信息
        Patient patient = patientMapper.selectPatientByCardId(reqVO.getId() + "");
        if (StringUtils.isNull(patient)) {
            throw new BusinessException("该卡号没有对应的患者信息！");
        }
        Register r = new Register();
        r.setPatientId(patient.getId())
                .setDepartmentId(11)
                .setRegisterStatus(1)
                .setTreatmentStatus(1)
                .setChargeStatus(0);
        List<Register> registers = registerMapper.selectRegisterList(r);
        if (StringUtils.isNull(registers)) {
            throw new BusinessException("未查询相关体检未收费记录！");
        }
        if (registers.size() != 1) {
            throw new BusinessException("该就诊卡体检未收费记录异常，请联系管理员！code=01！");
        }

        MedicalExamination medicalExamination = medicalExaminationMapper.selectMedicalExaminationByRegisterId(registers.get(0).getId());

        if (StringUtils.isNull(medicalExamination)) {
            throw new BusinessException("该就诊卡体检未收费记录异常，请联系管理员！code=02");
        }
        MedicalExamination rspVO = new MedicalExamination();
        BeanUtils.copyProperties(medicalExamination, rspVO);
        //BeanUtils.copyProperties会将第一个对象中的和第二对象字段相同的赋值过去，
        // 如果为空也会覆盖过去，所以将该空字段提前赋予值
        patient.setPrescriptionNum(medicalExamination.getPrescriptionNum());
        BeanUtils.copyProperties(patient, rspVO);
        rspVO.setRegisterId(registers.get(0).getId());
        try {
            rspVO.setAge(DateUtil.getAge(patient.getBirthday()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rspVO;

    }

    @Override
    public int saveExaminationTollInfo(Register register) {
        register.setChargeStatus(1);
        return registerMapper.updateRegister(register);
    }


    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Toll selectTollById(Integer id) {
        return tollMapper.selectTollById(id);
    }

    /**
     * 分页查询列表
     *
     * @param toll
     * @return
     */
    @Override
    public PageInfo<Toll> selectTollList(Toll toll, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Toll> list = tollMapper.selectTollList(toll);
        return new PageInfo<Toll>(list);
    }

    /**
     * 查询列表
     *
     * @param toll
     * @return
     */
    @Override
    public List<Toll> selectTollList(Toll toll) {
        return tollMapper.selectTollList(toll);
    }

    /**
     * 新增
     *
     * @param toll
     * @return 结果
     */
    @Override
    public int insertToll(Toll toll) {
        return tollMapper.insertToll(toll);
    }

    /**
     * 修改
     *
     * @param toll
     * @return 结果
     */
    @Override
    public int updateToll(Toll toll) {
        return tollMapper.updateToll(toll);
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteTollById(Integer id) {
        return tollMapper.deleteTollById(id);
    }

}
