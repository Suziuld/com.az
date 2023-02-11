package cn.hp.mapper;

import cn.hp.entity.Drug;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface DrugMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Drug selectDrugById(Integer id);

    /**
     * 查询列表
     *
     * @param drug 
     * @return 集合
     */
    public List<Drug> selectDrugList(Drug drug);

    /**
     * 新增
     *
     * @param drug 
     * @return 结果
     */
    public int insertDrug(Drug drug);

    /**
     * 修改
     *
     * @param drug 
     * @return 结果
     */
    public int updateDrug(Drug drug);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteDrugById(Integer id);

}
