package cn.hp.service.imp;

import cn.hp.entity.Role;
import cn.hp.entity.RoleMenu;
import cn.hp.mapper.RoleMapper;
import cn.hp.mapper.RoleMenuMapper;
import cn.hp.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service业务层处理
 */
@Service
public class RoleServiceImp implements IRoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Role selectRoleById(Integer id) {
        return roleMapper.selectRoleById(id);
    }

    /**
     * 分页查询列表
     *
     * @param role
     * @return
     */
    @Override
    public PageInfo<Role> selectRoleList(Role role, Integer page, Integer limit) {
        page = page == 0 ? 1 : page;
        PageHelper.startPage(page, limit);
        List<Role> list = roleMapper.selectRoleList(role);
        return new PageInfo<Role>(list);
    }

    /**
     * 查询列表
     *
     * @param role
     * @return
     */
    @Override
    public List<Role> selectRoleList(Role role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 新增
     *
     * @param role
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(Role role) {
        int k = roleMapper.insertRole(role);
        Integer[] menuIds = role.getMenuIds();
        for (int i = 0; i < menuIds.length; i++) {
            Integer menuId = menuIds[i];
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(role.getId()).setMenuId(menuIds[i]);
            k += roleMenuMapper.insertRoleMenu(roleMenu);
        }
        return k;
    }

    /**
     * 修改
     *
     * @param role
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(Role role) {
        int k = roleMenuMapper.deleteRoleMenuByRoleId(role.getId());
        k += roleMapper.updateRole(role);
        Integer[] menuIds = role.getMenuIds();
        for (int i = 0; i < menuIds.length; i++) {
            Integer menuId = menuIds[i];
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(role.getId()).setMenuId(menuIds[i]);
            k += roleMenuMapper.insertRoleMenu(roleMenu);
        }
        return k;
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleById(Integer id) {
        int k = roleMenuMapper.deleteRoleMenuByRoleId(id);
        return roleMapper.deleteRoleById(id);
    }
}
