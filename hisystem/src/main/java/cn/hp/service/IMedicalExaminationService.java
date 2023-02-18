package cn.hp.service;


import cn.hp.entity.Idcard;
import cn.hp.entity.MedicalExamination;
import java.util.List;

import cn.hp.entity.Patient;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IMedicalExaminationService
{


    Patient getCardIdInfor(Patient obj);

    int saveMedicalExaminationInfo(MedicalExamination obj);
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public MedicalExamination selectMedicalExaminationById(Integer id);

    /**
     * 查询列表
     * @param medicalExamination 
     * @return 集合
     */
    public List<MedicalExamination> selectMedicalExaminationList(MedicalExamination medicalExamination);

    /**
     * 分页查询列表
     * @param medicalExamination 
     * @return 集合
     */
    public PageInfo<MedicalExamination> selectMedicalExaminationList(MedicalExamination medicalExamination, Integer page, Integer limit);

    /**
     * 新增
     * @param medicalExamination 
     * @return 结果
     */
    public int insertMedicalExamination(MedicalExamination medicalExamination);

    /**
     * 修改
     * @param medicalExamination 
     * @return 结果
     */
    public int updateMedicalExamination(MedicalExamination medicalExamination);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteMedicalExaminationById(Integer id);

}
