package cn.hp.service;


import cn.hp.entity.Patient;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IPatientService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Patient selectPatientById(Integer id);

    /**
     * 查询列表
     * @param patient 
     * @return 集合
     */
    public List<Patient> selectPatientList(Patient patient);

    /**
     * 分页查询列表
     * @param patient 
     * @return 集合
     */
    public PageInfo<Patient> selectPatientList(Patient patient, Integer page, Integer limit);

    /**
     * 新增
     * @param patient 
     * @return 结果
     */
    public int insertPatient(Patient patient);

    /**
     * 修改
     * @param patient 
     * @return 结果
     */
    public int updatePatient(Patient patient);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deletePatientById(Integer id);

    int coverCardId(Patient obj);
}
