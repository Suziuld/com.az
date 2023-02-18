package cn.hp.mapper;

import cn.hp.entity.Toll;
import cn.hp.entity.TollTakedrug;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface TollTakedrugMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public TollTakedrug selectTollTakedrugById(Integer id);

    /**
     * 查询列表
     *
     * @param tollTakedrug 
     * @return 集合
     */
    public List<TollTakedrug> selectTollTakedrugList(TollTakedrug tollTakedrug);

    /**
     * 新增
     *
     * @param tollTakedrug 
     * @return 结果
     */
    public int insertTollTakedrug(TollTakedrug tollTakedrug);

    /**
     * 修改
     *
     * @param tollTakedrug 
     * @return 结果
     */
    public int updateTollTakedrug(TollTakedrug tollTakedrug);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteTollTakedrugById(Integer id);

    List<TollTakedrug> findByPatientIdAndTakingDrugStatus(TollTakedrug tollTakedrug);
    /*
     * 根据处方号与取药状态查询
     */
    TollTakedrug findByPrescriptionNumAndTakingDrugStatus(TollTakedrug tollTakedrug);


    TollTakedrug findByPrescriptionNum(String prescriptionNum);
}
