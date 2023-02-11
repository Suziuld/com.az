package cn.hp.mapper;

import cn.hp.entity.Dite;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 */
//@Mapper
public interface DiteMapper {
    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    public Dite selectDiteById(Integer id);

    /**
     * 查询列表
     *
     * @param dite
     * @return 集合
     */
    public List<Dite> selectDiteList(Dite dite);

    /**
     * 新增
     *
     * @param dite
     * @return 结果
     */
    public int insertDite(Dite dite);

    /**
     * 修改
     *
     * @param dite
     * @return 结果
     */
    public int updateDite(Dite dite);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteDiteById(Integer id);

}
