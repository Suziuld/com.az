package cn.hp.mapper;

import cn.hp.entity.Stock;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface StockMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Stock selectStockById(Integer id);

    /**
     * 查询列表
     *
     * @param stock 
     * @return 集合
     */
    public List<Stock> selectStockList(Stock stock);

    /**
     * 新增
     *
     * @param stock 
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 修改
     *
     * @param stock 
     * @return 结果
     */
    public int updateStock(Stock stock);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteStockById(Integer id);

}
