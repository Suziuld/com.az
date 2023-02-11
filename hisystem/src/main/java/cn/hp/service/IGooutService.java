package cn.hp.service;


import cn.hp.entity.Goout;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IGooutService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Goout selectGooutById(Integer id);

    /**
     * 查询列表
     * @param goout 
     * @return 集合
     */
    public List<Goout> selectGooutList(Goout goout);

    /**
     * 分页查询列表
     * @param goout 
     * @return 集合
     */
    public PageInfo<Goout> selectGooutList(Goout goout, Integer page, Integer limit);

    /**
     * 新增
     * @param goout 
     * @return 结果
     */
    public int insertGoout(Goout goout);

    /**
     * 修改
     * @param goout 
     * @return 结果
     */
    public int updateGoout(Goout goout);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteGooutById(Integer id);
}
