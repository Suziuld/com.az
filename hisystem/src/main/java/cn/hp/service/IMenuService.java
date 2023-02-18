package cn.hp.service;


import cn.hp.entity.Menu;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Service接口
 */
public interface IMenuService {
    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    public Menu selectMenuById(Integer id);

    /**
     * 查询列表
     *
     * @param menu
     * @return 集合
     */
    public List<Menu> selectMenuList(Menu menu);

    /**
     * 分页查询列表
     *
     * @param menu
     * @return 集合
     */
    public PageInfo<Menu> selectMenuList(Menu menu, Integer page, Integer limit);

    /**
     * 新增
     *
     * @param menu
     * @return 结果
     */
    public int insertMenu(Menu menu);

    /**
     * 修改
     *
     * @param menu
     * @return 结果
     */
    public int updateMenu(Menu menu);

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    public int deleteMenuById(Integer id);

    List<Menu> getMenu(Menu obj);
}
