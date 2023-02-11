package cn.hp.mapper;

import cn.hp.entity.Dicttype;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 */
//@Mapper
public interface DicttypeMapper {
    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    public Dicttype selectDicttypeById(Integer id);

    /**
     * 查询列表
     *
     * @param dicttype
     * @return 集合
     */
    public List<Dicttype> selectDicttypeList(Dicttype dicttype);

    /**
     * 新增
     *
     * @param dicttype
     * @return 结果
     */
    public int insertDicttype(Dicttype dicttype);

    /**
     * 修改
     *
     * @param dicttype
     * @return 结果
     */
    public int updateDicttype(Dicttype dicttype);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteDicttypeById(Integer id);

}
