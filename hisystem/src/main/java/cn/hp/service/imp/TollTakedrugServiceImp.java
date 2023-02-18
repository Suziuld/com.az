package cn.hp.service.imp;

import java.util.List;

import cn.hp.common.exception.BusinessException;
import cn.hp.entity.*;
import cn.hp.mapper.*;
import cn.hp.service.ITollService;
import cn.hp.util.DateUtil;
import cn.hutool.core.convert.Convert;
import cn.hp.service.ITollTakedrugService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service业务层处理
 */
@Service
public class TollTakedrugServiceImp implements ITollTakedrugService
{
    private static final Logger logger = LoggerFactory.getLogger(TollTakedrugServiceImp.class);
    @Resource
    private TollTakedrugMapper tollTakedrugMapper;
    @Resource
    private MedicalRecordMapper medicalRecordMapper;
    @Resource
    private RegisterMapper registerMapper;
    @Resource
    private PatientMapper patientMapper;
    @Resource
    private MedicalExaminationMapper medicalExaminationMapper;

@Override
public MedicalRecord getMedicalRecord(String prescriptionNum) {

    MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByPrescriptionNum(prescriptionNum);
    MedicalRecord recordRspVO = new MedicalRecord();

    if (StringUtils.isEmpty(medicalRecord)) {
        throw new BusinessException("该处方号未查询到任何信息！");
    }
    TollTakedrug tollTakeDrugInfo = tollTakedrugMapper.findByPrescriptionNum(medicalRecord.getPrescriptionNum());

    if (tollTakeDrugInfo == null) {
        throw new BusinessException("该处方未查询到最新划价收费信息！");
    }
    if (tollTakeDrugInfo.getTakingDrugStatus() == 1) {
        throw new BusinessException("该处方已取药！");
    }
    List<Register> list = registerMapper.selectPageRegisterList(new Register().setId(medicalRecord.getRegisterId()));
    Register register = list.get(0);
    Patient patient = patientMapper.selectPatientById(register.getPatientId());

    try {
        recordRspVO.setAge(DateUtil.getAge(patient.getBirthday()));
    } catch (Exception e) {
        e.printStackTrace();
    }
    recordRspVO.setCreateDate(DateUtil.DateTimeToDate(medicalRecord.getCreateDatetime()));
    recordRspVO.setDiagnosisResult(medicalRecord.getDiagnosisResult());
    recordRspVO.setDrugCost(medicalRecord.getDrugCost());
    recordRspVO.setMedicalOrder(medicalRecord.getMedicalOrder());
    recordRspVO.setName(patient.getName());
    recordRspVO.setNationality(patient.getNationality());
    recordRspVO.setPrescription(medicalRecord.getPrescription());
    recordRspVO.setSex(patient.getSex());
    recordRspVO.setNowDate(DateUtil.getCurrentDateSimpleToString());

    MedicalExamination medicalExamination = medicalExaminationMapper.findByprescriptionNum(medicalRecord.getPrescriptionNum());
    if (!StringUtils.isEmpty(medicalExamination)) {
        recordRspVO.setExaminationCost(medicalExamination.getExaminationCost());
    }
    recordRspVO.setDoctorName(register.getDoctor());
    recordRspVO.setDepartment(register.getDepartmentName());
    return recordRspVO;
}

@Override
@Transactional
public int saveTakingDrugInfo(String prescriptionNum) {
    if (StringUtils.isEmpty(prescriptionNum)) {
        throw new BusinessException("请填写处方号！");
    }
    try {
        MedicalRecord medicalRecord = medicalRecordMapper.selectMedicalRecordByPrescriptionNum(prescriptionNum);
        if (StringUtils.isEmpty(medicalRecord)) {
                throw new BusinessException("未查询到相关就诊记录！");
        }
        TollTakedrug tollTakeDrugInfo = tollTakedrugMapper.findByPrescriptionNumAndTakingDrugStatus(new TollTakedrug().setPrescriptionNum(medicalRecord.getPrescriptionNum()).setTakingDrugStatus(0));
        if (tollTakeDrugInfo == null) {
            throw new BusinessException("该处方未查询到最新划价收费信息！");
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new BusinessException("登录信息已过期，请重新登录！");
        }
        tollTakeDrugInfo.setTakingDrugDateTime(DateUtil.getCurrentDateToString());
        tollTakeDrugInfo.setTakingDrugOperator(user.getId()+"");
        tollTakeDrugInfo.setTakingDrugStatus(1);
        return tollTakedrugMapper.updateTollTakedrug(tollTakeDrugInfo);
    } catch (Exception e) {
        logger.error("处方号={},保存划价收费—拿药信息异常！", prescriptionNum, e);
        throw new BusinessException("操作异常，请稍后重试！");
    }

}

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public TollTakedrug selectTollTakedrugById(Integer id)
    {
        return tollTakedrugMapper.selectTollTakedrugById(id);
    }

    /**
     * 分页查询列表
     * @param tollTakedrug 
     * @return 
     */
    @Override
    public PageInfo<TollTakedrug> selectTollTakedrugList(TollTakedrug tollTakedrug, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<TollTakedrug> list = tollTakedrugMapper.selectTollTakedrugList(tollTakedrug);
        return new PageInfo<TollTakedrug>(list);
    }

    /**
     * 查询列表
     * @param tollTakedrug 
     * @return 
     */
    @Override
    public List<TollTakedrug> selectTollTakedrugList(TollTakedrug tollTakedrug)
    {
        return tollTakedrugMapper.selectTollTakedrugList(tollTakedrug);
    }

    /**
     * 新增
     * @param tollTakedrug 
     * @return 结果
     */
    @Override
    public int insertTollTakedrug(TollTakedrug tollTakedrug)
    {
        return tollTakedrugMapper.insertTollTakedrug(tollTakedrug);
    }

    /**
     * 修改
     * @param tollTakedrug 
     * @return 结果
     */
    @Override
    public int updateTollTakedrug(TollTakedrug tollTakedrug)
    {
        return tollTakedrugMapper.updateTollTakedrug(tollTakedrug);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteTollTakedrugById(Integer id)
    {
        return tollTakedrugMapper.deleteTollTakedrugById(id);
    }
}
