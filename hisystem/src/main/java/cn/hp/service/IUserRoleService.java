package cn.hp.service;


import cn.hp.entity.UserRole;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IUserRoleService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public UserRole selectUserRoleById(Integer id);

    /**
     * 查询列表
     * @param userRole 
     * @return 集合
     */
    public List<UserRole> selectUserRoleList(UserRole userRole);

    /**
     * 分页查询列表
     * @param userRole 
     * @return 集合
     */
    public PageInfo<UserRole> selectUserRoleList(UserRole userRole, Integer page, Integer limit);

    /**
     * 新增
     * @param userRole 
     * @return 结果
     */
    public int insertUserRole(UserRole userRole);

    /**
     * 修改
     * @param userRole 
     * @return 结果
     */
    public int updateUserRole(UserRole userRole);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteUserRoleById(Integer id);

}
