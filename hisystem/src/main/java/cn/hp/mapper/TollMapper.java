package cn.hp.mapper;

import cn.hp.entity.Toll;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface TollMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Toll selectTollById(Integer id);

    /**
     * 查询列表
     *
     * @param toll 
     * @return 集合
     */
    public List<Toll> selectTollList(Toll toll);

    /**
     * 新增
     *
     * @param toll 
     * @return 结果
     */
    public int insertToll(Toll toll);

    /**
     * 修改
     *
     * @param toll 
     * @return 结果
     */
    public int updateToll(Toll toll);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteTollById(Integer id);

}
