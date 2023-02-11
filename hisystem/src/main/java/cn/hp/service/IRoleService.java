package cn.hp.service;


import cn.hp.entity.Role;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IRoleService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Role selectRoleById(Integer id);

    /**
     * 查询列表
     * @param role 
     * @return 集合
     */
    public List<Role> selectRoleList(Role role);

    /**
     * 分页查询列表
     * @param role 
     * @return 集合
     */
    public PageInfo<Role> selectRoleList(Role role, Integer page, Integer limit);

    /**
     * 新增
     * @param role 
     * @return 结果
     */
    public int insertRole(Role role);

    /**
     * 修改
     * @param role 
     * @return 结果
     */
    public int updateRole(Role role);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteRoleById(Integer id);
}
