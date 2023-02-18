package cn.hp.mapper;

import cn.hp.entity.Role;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface RoleMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Role selectRoleById(Integer id);

    /**
     * 查询列表
     *
     * @param role 
     * @return 集合
     */
    public List<Role> selectRoleList(Role role);

    /**
     * 新增
     *
     * @param role 
     * @return 结果
     */
    public int insertRole(Role role);

    /**
     * 修改
     *
     * @param role 
     * @return 结果
     */
    public int updateRole(Role role);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteRoleById(Integer id);

    Role findByRemark(String roleName);
//    List<Role> findByUserIdAndRoleStatus(String userId);
}
