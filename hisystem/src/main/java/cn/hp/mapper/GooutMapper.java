package cn.hp.mapper;

import cn.hp.entity.Goout;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface GooutMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Goout selectGooutById(Integer id);

    /**
     * 查询列表
     *
     * @param goout 
     * @return 集合
     */
    public List<Goout> selectGooutList(Goout goout);

    /**
     * 新增
     *
     * @param goout 
     * @return 结果
     */
    public int insertGoout(Goout goout);

    /**
     * 修改
     *
     * @param goout 
     * @return 结果
     */
    public int updateGoout(Goout goout);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteGooutById(Integer id);

}
