package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.Drug;
import cn.hp.mapper.DrugMapper;
import cn.hp.service.IDrugService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class DrugServiceImp implements IDrugService
{
    @Resource
    private DrugMapper drugMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public Drug selectDrugById(Integer id)
    {
        return drugMapper.selectDrugById(id);
    }

    /**
     * 分页查询列表
     * @param drug 
     * @return 
     */
    @Override
    public PageInfo<Drug> selectDrugList(Drug drug, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<Drug> list = drugMapper.selectDrugList(drug);
        return new PageInfo<Drug>(list);
    }

    /**
     * 查询列表
     * @param drug 
     * @return 
     */
    @Override
    public List<Drug> selectDrugList(Drug drug)
    {
        return drugMapper.selectDrugList(drug);
    }

    /**
     * 新增
     * @param drug 
     * @return 结果
     */
    @Override
    public int insertDrug(Drug drug)
    {
        return drugMapper.insertDrug(drug);
    }

    /**
     * 修改
     * @param drug 
     * @return 结果
     */
    @Override
    public int updateDrug(Drug drug)
    {
        return drugMapper.updateDrug(drug);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteDrugById(Integer id)
    {
        return drugMapper.deleteDrugById(id);
    }
}
