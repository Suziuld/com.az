package cn.hp.service;


import cn.hp.entity.*;

import java.util.List;

import cn.hp.util.Result;
import cn.hp.util.card.Card;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface ITollService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Toll selectTollById(Integer id);

    /**
     * 查询列表
     * @param toll 
     * @return 集合
     */
    public List<Toll> selectTollList(Toll toll);

    /**
     * 分页查询列表
     * @param toll 
     * @return 集合
     */
    public PageInfo<Toll> selectTollList(Toll toll, Integer page, Integer limit);

    /**
     * 新增
     * @param toll 
     * @return 结果
     */
    public int insertToll(Toll toll);

    /**
     * 修改
     * @param toll 
     * @return 结果
     */
    public int updateToll(Toll toll);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteTollById(Integer id);
    /**
     * 获取病历信息
     * @param cardId
     * @param tollStatus
     * @return
     */
    List<Toll> getAllMedicalRecord(String cardId, String tollStatus);
    /**
     * 获取处方笺信息
     * @param cardId
     * @param registerId
     */
    Toll getMedicalRecord(String cardId, String registerId);
    /**
     * 划价收费完成，保存信息
     * @param reqVO
     * @return
     */
    int saveTollInfo(Toll reqVO);

    int saveExaminationTollInfo(Register register);


    MedicalExamination getExaminationToll(Idcard obj);
}
