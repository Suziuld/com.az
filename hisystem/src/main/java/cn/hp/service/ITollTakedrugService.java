package cn.hp.service;


import cn.hp.entity.MedicalRecord;
import cn.hp.entity.TollTakedrug;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface ITollTakedrugService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public TollTakedrug selectTollTakedrugById(Integer id);

    /**
     * 查询列表
     * @param tollTakedrug 
     * @return 集合
     */
    public List<TollTakedrug> selectTollTakedrugList(TollTakedrug tollTakedrug);

    /**
     * 分页查询列表
     * @param tollTakedrug 
     * @return 集合
     */
    public PageInfo<TollTakedrug> selectTollTakedrugList(TollTakedrug tollTakedrug, Integer page, Integer limit);

    /**
     * 新增
     * @param tollTakedrug 
     * @return 结果
     */
    public int insertTollTakedrug(TollTakedrug tollTakedrug);

    /**
     * 修改
     * @param tollTakedrug 
     * @return 结果
     */
    public int updateTollTakedrug(TollTakedrug tollTakedrug);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteTollTakedrugById(Integer id);

    MedicalRecord getMedicalRecord(String prescriptionNum);

    int saveTakingDrugInfo(String prescriptionNum);
}
