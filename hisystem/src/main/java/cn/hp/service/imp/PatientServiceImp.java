package cn.hp.service.imp;

import java.util.List;

import cn.hp.common.constant.Constants;

import cn.hp.common.exception.BusinessException;
import cn.hp.entity.Idcard;
import cn.hp.mapper.IdcardMapper;
import cn.hp.util.DateUtil;
import cn.hp.util.IDCardUtil;
import cn.hp.entity.Patient;
import cn.hp.mapper.PatientMapper;
import cn.hp.service.IPatientService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service业务层处理
 */
@Service
public class PatientServiceImp implements IPatientService {
    @Resource
    private PatientMapper patientMapper;
    @Resource
    private IdcardMapper idcardMapper;

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Patient selectPatientById(Integer id) {
        return patientMapper.selectPatientById(id);
    }

    /**
     * 分页查询列表
     *
     * @param patient
     * @return
     */
    @Override
    public PageInfo<Patient> selectPatientList(Patient patient, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Patient> list = patientMapper.selectPatientList(patient);
        return new PageInfo<Patient>(list);
    }

    /**
     * 查询列表
     *
     * @param patient
     * @return
     */
    @Override
    public List<Patient> selectPatientList(Patient patient) {
        return patientMapper.selectPatientList(patient);
    }

/**
 * 新增
 *
 * @param patient
 * @return 结果
 */
@Override
@Transactional
public int insertPatient(Patient patient) {
    boolean bool = IDCardUtil.validateAllCard(patient.getIdCard());
    if (!bool) {
        throw new BusinessException("身份证号码格式有误！");
    }
    patient.setBirthday(IDCardUtil.getBirthday(patient.getIdCard()));
    patient.setSex(IDCardUtil.getSex(patient.getIdCard()));
    try {
        patient.setAge(DateUtil.getAge(patient.getBirthday()));
    } catch (Exception e) {
        e.printStackTrace();
    }
    //验证患者已注册过就诊卡
    Patient patientEntity2 = patientMapper.selectPatientByIdCard(patient.getIdCard());
    if (!StringUtils.isEmpty(patientEntity2)) {
        throw new BusinessException(Constants.REGISTER.COVER);
    }
    Idcard idcard = new Idcard();
    idcard.setName(patient.getName())
            .setAddress(patient.getAddress())
            .setBirthday(patient.getBirthday())
            .setSex(patient.getSex())
            .setIdCard(patient.getIdCard())
            .setNationality(patient.getNationality());
    idcardMapper.insertIdcard(idcard);
    patient.setCardId(idcard.getId().toString());
    patientMapper.insertPatient(patient);
    return idcard.getId();
}

    /**
     * 修改
     *
     * @param patient
     * @return 结果
     */
    @Override
    @Transactional
    public int updatePatient(Patient patient) {
        Idcard idcard = new Idcard();
        idcard.setId(Integer.parseInt(patient.getCardId()))
                .setName(patient.getName())
                .setAddress(patient.getAddress())
                .setBirthday(patient.getBirthday())
                .setSex(patient.getSex())
                .setIdCard(patient.getIdCard())
                .setNationality(patient.getNationality());
        idcardMapper.updateIdcard(idcard);
        return patientMapper.updatePatient(patient);
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public int deletePatientById(Integer id) {
        return patientMapper.deletePatientById(id);
    }

    /**
     * 补办就诊卡
     *
     * @param obj
     * @return
     */
    @Override
    @Transactional
    public int coverCardId(Patient obj) {
        Patient patientInfor = patientMapper.selectPatientByIdCard(obj.getIdCard());
        if (StringUtils.isEmpty(patientInfor)) {
            throw new BusinessException(Constants.USER.FAIL);
        }
        Idcard idcard = idcardMapper.selectIdcardById(Integer.valueOf(patientInfor.getCardId()));
        idcard.setId(null);
        idcardMapper.insertIdcard(idcard);
        patientInfor.setCardId(idcard.getId().toString());
        patientMapper.updatePatient(patientInfor);
        return idcard.getId();
    }


}
