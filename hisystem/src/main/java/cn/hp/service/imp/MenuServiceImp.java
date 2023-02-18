package cn.hp.service.imp;

import cn.hp.entity.Menu;
import cn.hp.mapper.MenuMapper;
import cn.hp.mapper.RoleMenuMapper;
import cn.hp.service.IMenuService;
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
public class MenuServiceImp implements IMenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Menu selectMenuById(Integer id) {
        return menuMapper.selectMenuById(id);
    }

    /**
     * 分页查询列表
     *
     * @param menu
     * @return
     */
    @Override
    public PageInfo<Menu> selectMenuList(Menu menu, Integer page, Integer limit) {
        page = page == 0 ? 1 : page;
        PageHelper.startPage(page, limit);
        List<Menu> list = menuMapper.selectMenuPageList(menu);
        return new PageInfo<Menu>(list);
    }

    /**
     * 查询列表
     *
     * @param menu
     * @return
     */
    @Override
    public List<Menu> selectMenuList(Menu menu) {
        return menuMapper.selectMenuList(menu);
    }

    /**
     * 新增
     *
     * @param menu
     * @return 结果
     */
    @Override
    public int insertMenu(Menu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改
     *
     * @param menu
     * @return 结果
     */
    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 删除信息，删除的同时，将角色菜单中间表进行删除相关数据
     *
     * @param id ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteMenuById(Integer id) {
        menuMapper.deleteMenuById(id);
        return roleMenuMapper.deleteRoleMenuByMenuId(id);
    }

    @Override
    public List<Menu> getMenu(Menu obj) {
        return menuMapper.getMenu(obj);
    }
}
