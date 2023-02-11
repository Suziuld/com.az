package cn.hp.service;

import cn.hp.entity.Menu;
import cn.hp.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 登录进行获取权限的部分方法
 */
public interface ILoginService {
    Set<String> findRoles(User user);

    Set<String> findMenu(User user);

    List<Menu> getUidMenu(User user);
}
