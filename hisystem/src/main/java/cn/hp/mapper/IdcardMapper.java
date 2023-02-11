package cn.hp.mapper;

import cn.hp.entity.Idcard;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface IdcardMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Idcard selectIdcardById(Integer id);

    /**
     * 查询列表
     *
     * @param idcard 
     * @return 集合
     */
    public List<Idcard> selectIdcardList(Idcard idcard);

    /**
     * 新增
     *
     * @param idcard 
     * @return 结果
     */
    public int insertIdcard(Idcard idcard);

    /**
     * 修改
     *
     * @param idcard 
     * @return 结果
     */
    public int updateIdcard(Idcard idcard);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteIdcardById(Integer id);

}
