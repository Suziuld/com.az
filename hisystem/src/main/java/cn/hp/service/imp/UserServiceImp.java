package cn.hp.service.imp;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.hp.common.constant.Constants;

import cn.hp.common.exception.BusinessException;
import cn.hp.entity.Role;
import cn.hp.entity.UserRole;
import cn.hp.mapper.RoleMapper;
import cn.hp.mapper.UserRoleMapper;
import cn.hp.task.AsyncTask;
import cn.hp.util.MD5Util;
import cn.hp.util.RedisUtil;
import cn.hp.entity.User;
import cn.hp.mapper.UserMapper;
import cn.hp.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 */
@Service
public class UserServiceImp implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private AsyncTask asyncTask;

    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param reqVO
     * @return
     */
    @Transactional
    @Override
    public Integer saveUserAndSendEmail(User reqVO) {
        Integer i = 0;
        String email = reqVO.getEmail();
        String roleName = reqVO.getRoleName();
        //验证角色
        Role role = roleMapper.findByRemark(roleName);
        if (role == null) {
            throw new BusinessException("您选择的角色不存在，请重试！");
        }

        //检查该账户是否已存在
        Map map = userMapper.checkEmail(email);
        String num = map.get("num").toString();
        if (!num.equals("0")) {
            throw new BusinessException(Constants.USER.ACCOUNT_EXIST);
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(reqVO.getUsername());
        user.setPlainPassword(reqVO.getPassword());
        //生成盐和加盐密码
        String salt = MD5Util.md5Encrypt32Lower(reqVO.getEmail());
        // 使用SimpleHash类对原始密码进行加密
        String password = new SimpleHash("MD5", reqVO.getPassword(), salt, 1024).toHex();

        user.setPassword(password);
        user.setSalt(salt);
        //生成激活码
        String validateCode = MD5Util.md5Encrypt32Upper(reqVO.getEmail());
        user.setValidateCode(validateCode);
        user.setEmailStatus(0);

        i += userMapper.insertUser(user);

        //保存用户与角色信息
        UserRole userRole = new UserRole();
        userRole.setUid(user.getId());
        userRole.setRoleId(role.getId());
        userRole.setDescription(role.getRole());
//            userRole.setRoleStatus(0);
        i += userRoleMapper.insertUserRole(userRole);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        redisUtil.set(uuid, user.getId().toString(), 30 * 60);
        // pop3协议如果权限认证失败，会报535错误
        // 启动一个子线程发送邮件，第一个参数为发送方用户账户，第二参数为被接收的邮件地址
//        new Thread(new MyJavaMail("账户注册通知", reqVO.getEmail(), uuid)).start();
        asyncTask.sendMail("账户注册通知", reqVO.getEmail(), uuid);
        return i;
    }

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    /**
     * 分页查询列表
     *
     * @param user
     * @return
     */
    @Override
    public PageInfo<User> selectUserList(User user, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<User> list = userMapper.selectUserList(user);
        return new PageInfo<User>(list);
    }

    /**
     * 查询列表
     *
     * @param user
     * @return
     */
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增
     *
     * @param user
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(User user) {
        int k = userMapper.insertUser(user);
        if (user.getRoleIds() != null) {
            for (String roleId : user.getRoleIds()) {
                String[] s = roleId.split(":");
                UserRole userRole = new UserRole();
                userRole.setUid(user.getId()).setRoleId(Integer.valueOf(s[0])).setDescription(s[1]);
                k += userRoleMapper.insertUserRole(userRole);
            }
        }
        return k;
    }

    /**
     * 修改
     *
     * @param user
     * @return 结果
     */
@Override
@Transactional
public int updateUser(User user) {
    int k = userMapper.updateUser(user);
    if (user.getRoleIds() != null) {
        k += userRoleMapper.deleteUserRoleByUid(user.getId());
        for (String roleId : user.getRoleIds()) {
            String[] s = roleId.split(":");
            UserRole userRole = new UserRole();
            userRole.setUid(user.getId()).setRoleId(Integer.valueOf(s[0])).setDescription(s[1]);
            k += userRoleMapper.insertUserRole(userRole);
        }
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
    public int deleteUserById(Integer id) {
        int k = userRoleMapper.deleteUserRoleByUid(id);
        User user = new User();
        user.setId(id).setDelFlag("2");
        k += userMapper.updateUser(user);
        return k;
    }

    @Override
    public User login(String email) {
        List<User> list = userMapper.login(email);

        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public int resetPassword(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public Boolean checkEmail(String email) {
        Map map = userMapper.checkEmail(email);
        Integer num = Integer.valueOf(map.get("num").toString());
        return num > 0 ? true : false;
    }

    @Override
    public Boolean activationUserName(int id) {
        User user = new User();
        user.setId(id).setEmailStatus(1);
        int i = userMapper.updateUser(user);
        return i > 0 ? true : false;
    }

}
