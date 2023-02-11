package cn.hp.service;


import cn.hp.entity.MedicalRecord;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IMedicalRecordService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public MedicalRecord selectMedicalRecordById(Integer id);

    /**
     * 查询列表
     * @param medicalRecord 
     * @return 集合
     */
    public List<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord);

    /**
     * 分页查询列表
     * @param medicalRecord 
     * @return 集合
     */
    public PageInfo<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord, Integer page, Integer limit);

    /**
     * 新增
     * @param medicalRecord 
     * @return 结果
     */
    public int insertMedicalRecord(MedicalRecord medicalRecord);

    /**
     * 修改
     * @param medicalRecord 
     * @return 结果
     */
    public int updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteMedicalRecordById(Integer id);
}
