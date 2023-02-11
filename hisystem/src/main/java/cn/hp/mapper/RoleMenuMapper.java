package cn.hp.mapper;

import cn.hp.entity.RoleMenu;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface RoleMenuMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public RoleMenu selectRoleMenuById(Integer id);

    /**
     * 查询列表
     *
     * @param roleMenu 
     * @return 集合
     */
    public List<RoleMenu> selectRoleMenuList(RoleMenu roleMenu);

    /**
     * 新增
     *
     * @param roleMenu 
     * @return 结果
     */
    public int insertRoleMenu(RoleMenu roleMenu);

    /**
     * 修改
     *
     * @param roleMenu 
     * @return 结果
     */
    public int updateRoleMenu(RoleMenu roleMenu);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteRoleMenuById(Integer id);

    int deleteRoleMenuByRoleId(Integer roleId);

    int deleteRoleMenuByMenuId(Integer id);
}
