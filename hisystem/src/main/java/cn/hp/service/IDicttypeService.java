package cn.hp.service;


import cn.hp.entity.Dicttype;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IDicttypeService {
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
     * 分页查询列表
     *
     * @param dicttype
     * @return 集合
     */
    public PageInfo<Dicttype> selectDicttypeList(Dicttype dicttype, Integer page, Integer limit);

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
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    public int deleteDicttypeById(Integer id);
}
