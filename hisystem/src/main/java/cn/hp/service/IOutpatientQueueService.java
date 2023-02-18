package cn.hp.service;


import cn.hp.entity.MedicalExamination;
import cn.hp.entity.MedicalRecord;
import cn.hp.entity.OutpatientQueue;
import java.util.List;

import cn.hp.entity.Patient;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IOutpatientQueueService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public OutpatientQueue selectOutpatientQueueById(Integer id);

    /**
     * 查询列表
     * @param outpatientQueue 
     * @return 集合
     */
    public List<OutpatientQueue> selectOutpatientQueueList(OutpatientQueue outpatientQueue);

    /**
     * 分页查询列表
     * @param outpatientQueue 
     * @return 集合
     */
    public PageInfo<OutpatientQueue> selectOutpatientQueueList(OutpatientQueue outpatientQueue, Integer page, Integer limit);

    /**
     * 新增
     * @param outpatientQueue 
     * @return 结果
     */
    public int insertOutpatientQueue(OutpatientQueue outpatientQueue);

    /**
     * 修改
     * @param outpatientQueue 
     * @return 结果
     */
    public int updateOutpatientQueue(OutpatientQueue outpatientQueue);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteOutpatientQueueById(Integer id);

    Patient getCardIdInfor(Patient patient) throws Exception;

    int changePatientInfor(Patient obj);

    List<OutpatientQueue> getAllOutpatientQueue();

    String processLaterMedicalRecord(MedicalRecord obj);

    Patient restorePatientInfor(Integer registerId);

    String addMedicalRecord(MedicalRecord reqVO);

    MedicalExamination getMedicalExamination(String prescriptionNum);
}
