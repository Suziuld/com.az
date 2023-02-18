package cn.hp.service.imp;

import cn.hp.entity.Menu;
import cn.hp.entity.User;
import cn.hp.mapper.LoginMapper;
import cn.hp.service.ILoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class LoginServiceImp implements ILoginService {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public Set<String> findRoles(User user) {
        return loginMapper.findRoles(user);
    }

    @Override
    public Set<String> findMenu(User user) {
        return loginMapper.findMenu(user);
    }

    @Override
    public List<Menu> getUidMenu(User user) {
        return loginMapper.getUidMenu(user);
    }
}
