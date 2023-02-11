package cn.hp.mapper;

import cn.hp.entity.Patient;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface PatientMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Patient selectPatientById(Integer id);

    /**
     * 查询列表
     *
     * @param patient 
     * @return 集合
     */
    public List<Patient> selectPatientList(Patient patient);

    /**
     * 新增
     *
     * @param patient 
     * @return 结果
     */
    public int insertPatient(Patient patient);

    /**
     * 修改
     *
     * @param patient 
     * @return 结果
     */
    public int updatePatient(Patient patient);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deletePatientById(Integer id);

    Patient selectPatientByCardId(String cardId);

    Patient selectPatientByIdCard(String idCard);

    void updateByCardIdPatient(Patient patient);
}
