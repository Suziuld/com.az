package cn.hp.mapper;

import cn.hp.entity.User;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface UserMapper
{
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
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteUserById(Integer id);

    List<User> login(String email);

    Map checkEmail(String email);

}
