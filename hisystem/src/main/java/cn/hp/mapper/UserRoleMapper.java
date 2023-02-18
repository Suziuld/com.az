package cn.hp.mapper;

import cn.hp.entity.UserRole;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface UserRoleMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public UserRole selectUserRoleById(Integer id);

    /**
     * 查询列表
     *
     * @param userRole 
     * @return 集合
     */
    public List<UserRole> selectUserRoleList(UserRole userRole);

    /**
     * 新增
     *
     * @param userRole 
     * @return 结果
     */
    public int insertUserRole(UserRole userRole);

    /**
     * 修改
     *
     * @param userRole 
     * @return 结果
     */
    public int updateUserRole(UserRole userRole);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteUserRoleById(Integer id);

    public int deleteUserRoleByUid(Integer id);
}
