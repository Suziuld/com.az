package cn.hp.mapper;

import cn.hp.entity.Menu;
import cn.hp.entity.Role;
import cn.hp.entity.User;

import java.util.List;
import java.util.Set;

public interface LoginMapper {

    Set<String> findRoles(User user);

    Set<String> findMenu(User user);

    List<Menu> getUidMenu(User user);
}
