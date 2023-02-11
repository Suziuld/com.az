package cn.hp.mapper;

import cn.hp.entity.MedicalExamination;
import cn.hp.entity.MedicalRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface MedicalRecordMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public MedicalRecord selectMedicalRecordById(Integer id);

    /**
     * 查询列表
     *
     * @param medicalRecord 
     * @return 集合
     */
    public List<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord);

    /**
     * 新增
     *
     * @param medicalRecord 
     * @return 结果
     */
    public int insertMedicalRecord(MedicalRecord medicalRecord);

    /**
     * 修改
     *
     * @param medicalRecord 
     * @return 结果
     */
    public int updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteMedicalRecordById(Integer id);

    MedicalRecord selectMedicalRecordByRegisterId(Integer registerId);

    MedicalRecord selectMedicalRecordByPrescriptionNum(String prescriptionNum);

    void updateMedicalRecordByPrescriptionNum(MedicalRecord medicalRecord);
}
