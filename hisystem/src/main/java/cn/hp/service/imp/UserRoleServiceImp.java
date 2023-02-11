package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.UserRole;
import cn.hp.mapper.UserRoleMapper;
import cn.hp.service.IUserRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class UserRoleServiceImp implements IUserRoleService
{
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public UserRole selectUserRoleById(Integer id)
    {
        return userRoleMapper.selectUserRoleById(id);
    }

    /**
     * 分页查询列表
     * @param userRole 
     * @return 
     */
    @Override
    public PageInfo<UserRole> selectUserRoleList(UserRole userRole, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<UserRole> list = userRoleMapper.selectUserRoleList(userRole);
        return new PageInfo<UserRole>(list);
    }

    /**
     * 查询列表
     * @param userRole 
     * @return 
     */
    @Override
    public List<UserRole> selectUserRoleList(UserRole userRole)
    {
        return userRoleMapper.selectUserRoleList(userRole);
    }

    /**
     * 新增
     * @param userRole 
     * @return 结果
     */
    @Override
    public int insertUserRole(UserRole userRole)
    {
        return userRoleMapper.insertUserRole(userRole);
    }

    /**
     * 修改
     * @param userRole 
     * @return 结果
     */
    @Override
    public int updateUserRole(UserRole userRole)
    {
        return userRoleMapper.updateUserRole(userRole);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteUserRoleById(Integer id)
    {
        return userRoleMapper.deleteUserRoleById(id);
    }

}
