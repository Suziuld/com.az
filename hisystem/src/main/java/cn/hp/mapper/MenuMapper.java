package cn.hp.mapper;

import cn.hp.entity.Menu;

import java.util.List;

/**
 * Mapper接口
 */
//@Mapper
public interface MenuMapper {
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
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteMenuById(Integer id);

    List<Menu> selectMenuPageList(Menu menu);

    List<Menu> getMenu(Menu obj);
}
