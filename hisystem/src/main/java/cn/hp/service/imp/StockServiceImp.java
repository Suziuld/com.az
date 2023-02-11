package cn.hp.service.imp;

import java.util.List;

import cn.hp.entity.Drug;
import cn.hp.mapper.DrugMapper;
import cn.hutool.core.convert.Convert;
import cn.hp.entity.Stock;
import cn.hp.mapper.StockMapper;
import cn.hp.service.IStockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 */
@Service
public class StockServiceImp implements IStockService
{
    @Resource
    private StockMapper stockMapper;
    @Resource
    private DrugMapper drugMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public Stock selectStockById(Integer id)
    {
        return stockMapper.selectStockById(id);
    }

    /**
     * 分页查询列表
     * @param stock 
     * @return 
     */
    @Override
    public PageInfo<Stock> selectStockList(Stock stock, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<Stock> list = stockMapper.selectStockList(stock);
        return new PageInfo<Stock>(list);
    }

    /**
     * 查询列表
     * @param stock 
     * @return 
     */
    @Override
    public List<Stock> selectStockList(Stock stock)
    {
        return stockMapper.selectStockList(stock);
    }

    /**
     * 新增
     * @param stock 
     * @return 结果
     */
    @Override
    @Transactional
    public int insertStock(Stock stock)
    {
        Drug drug = drugMapper.selectDrugById(stock.getDrugId());
        drug.setDrugCount(drug.getDrugCount()+stock.getNum());
        drugMapper.updateDrug(drug);
        if(stock.getMoney()==null){
            stock.setMoney(stock.getNum()*stock.getPrice());
        }
        return stockMapper.insertStock(stock);
    }

    /**
     * 修改
     * @param stock 
     * @return 结果
     */
    @Override
    public int updateStock(Stock stock)
    {
        return stockMapper.updateStock(stock);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteStockById(Integer id)
    {
        return stockMapper.deleteStockById(id);
    }
}
