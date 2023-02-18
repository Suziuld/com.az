package cn.hp.service;


import cn.hp.entity.User;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IUserService {
    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param reqVO
     * @return
     */
    Integer saveUserAndSendEmail(User reqVO);

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    public User selectUserById(Integer id);

    /**
     * 查询列表
     *
     * @param user
     * @return 集合
     */
    public List<User> selectUserList(User user);

    /**
     * 分页查询列表
     *
     * @param user
     * @return 集合
     */
    public PageInfo<User> selectUserList(User user, Integer page, Integer limit);

    /**
     * 新增
     *
     * @param user
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改
     *
     * @param user
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    public int deleteUserById(Integer id);

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 结果
     */
    User login(String email);

    /**
     * 重置密码
     *
     * @param user user
     * @return 结果
     */
    int resetPassword(User user);

    /**
     * 判断邮箱是否存在。true存在，false不存在
     *
     * @param email 邮箱
     * @return 结果
     */
    Boolean checkEmail(String email);
    Boolean activationUserName(int id);
}
