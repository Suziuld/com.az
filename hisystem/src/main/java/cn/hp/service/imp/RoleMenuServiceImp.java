package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.RoleMenu;
import cn.hp.mapper.RoleMenuMapper;
import cn.hp.service.IRoleMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class RoleMenuServiceImp implements IRoleMenuService
{
    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public RoleMenu selectRoleMenuById(Integer id)
    {
        return roleMenuMapper.selectRoleMenuById(id);
    }

    /**
     * 分页查询列表
     * @param roleMenu 
     * @return 
     */
    @Override
    public PageInfo<RoleMenu> selectRoleMenuList(RoleMenu roleMenu, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<RoleMenu> list = roleMenuMapper.selectRoleMenuList(roleMenu);
        return new PageInfo<RoleMenu>(list);
    }

    /**
     * 查询列表
     * @param roleMenu 
     * @return 
     */
    @Override
    public List<RoleMenu> selectRoleMenuList(RoleMenu roleMenu)
    {
        return roleMenuMapper.selectRoleMenuList(roleMenu);
    }

    /**
     * 新增
     * @param roleMenu 
     * @return 结果
     */
    @Override
    public int insertRoleMenu(RoleMenu roleMenu)
    {
        return roleMenuMapper.insertRoleMenu(roleMenu);
    }

    /**
     * 修改
     * @param roleMenu 
     * @return 结果
     */
    @Override
    public int updateRoleMenu(RoleMenu roleMenu)
    {
        return roleMenuMapper.updateRoleMenu(roleMenu);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteRoleMenuById(Integer id)
    {
        return roleMenuMapper.deleteRoleMenuById(id);
    }
}
