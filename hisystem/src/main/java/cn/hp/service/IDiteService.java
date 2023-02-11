package cn.hp.service;


import cn.hp.entity.Dite;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IDiteService {
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
     * 分页查询列表
     *
     * @param dite
     * @return 集合
     */
    public PageInfo<Dite> selectDiteList(Dite dite, Integer page, Integer limit);

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
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    public int deleteDiteById(Integer id);
}
