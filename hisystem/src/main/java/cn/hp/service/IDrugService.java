package cn.hp.service;


import cn.hp.entity.Drug;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IDrugService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Drug selectDrugById(Integer id);

    /**
     * 查询列表
     * @param drug 
     * @return 集合
     */
    public List<Drug> selectDrugList(Drug drug);

    /**
     * 分页查询列表
     * @param drug 
     * @return 集合
     */
    public PageInfo<Drug> selectDrugList(Drug drug, Integer page, Integer limit);

    /**
     * 新增
     * @param drug 
     * @return 结果
     */
    public int insertDrug(Drug drug);

    /**
     * 修改
     * @param drug 
     * @return 结果
     */
    public int updateDrug(Drug drug);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteDrugById(Integer id);
}
