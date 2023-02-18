package cn.hp.service;


import cn.hp.entity.RoleMenu;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IRoleMenuService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public RoleMenu selectRoleMenuById(Integer id);

    /**
     * 查询列表
     * @param roleMenu 
     * @return 集合
     */
    public List<RoleMenu> selectRoleMenuList(RoleMenu roleMenu);

    /**
     * 分页查询列表
     * @param roleMenu 
     * @return 集合
     */
    public PageInfo<RoleMenu> selectRoleMenuList(RoleMenu roleMenu, Integer page, Integer limit);

    /**
     * 新增
     * @param roleMenu 
     * @return 结果
     */
    public int insertRoleMenu(RoleMenu roleMenu);

    /**
     * 修改
     * @param roleMenu 
     * @return 结果
     */
    public int updateRoleMenu(RoleMenu roleMenu);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteRoleMenuById(Integer id);
}
