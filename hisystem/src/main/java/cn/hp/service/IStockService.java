package cn.hp.service;


import cn.hp.entity.Stock;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IStockService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Stock selectStockById(Integer id);

    /**
     * 查询列表
     * @param stock 
     * @return 集合
     */
    public List<Stock> selectStockList(Stock stock);

    /**
     * 分页查询列表
     * @param stock 
     * @return 集合
     */
    public PageInfo<Stock> selectStockList(Stock stock, Integer page, Integer limit);

    /**
     * 新增
     * @param stock 
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 修改
     * @param stock 
     * @return 结果
     */
    public int updateStock(Stock stock);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteStockById(Integer id);
}
