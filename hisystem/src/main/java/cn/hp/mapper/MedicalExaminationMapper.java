package cn.hp.mapper;

import cn.hp.entity.MedicalExamination;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface MedicalExaminationMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public MedicalExamination selectMedicalExaminationById(Integer id);

    /**
     * 查询列表
     *
     * @param medicalExamination 
     * @return 集合
     */
    public List<MedicalExamination> selectMedicalExaminationList(MedicalExamination medicalExamination);

    /**
     * 新增
     *
     * @param medicalExamination 
     * @return 结果
     */
    public int insertMedicalExamination(MedicalExamination medicalExamination);

    /**
     * 修改
     *
     * @param medicalExamination 
     * @return 结果
     */
    public int updateMedicalExamination(MedicalExamination medicalExamination);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteMedicalExaminationById(Integer id);

    MedicalExamination findByprescriptionNum(String prescription);

    MedicalExamination selectMedicalExaminationByRegisterId(Integer id);
}
