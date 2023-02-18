package cn.hp.mapper;

import cn.hp.entity.LoginInfor;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface LoginInforMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public LoginInfor selectLoginInforById(Integer id);

    /**
     * 查询列表
     *
     * @param loginInfor 
     * @return 集合
     */
    public List<LoginInfor> selectLoginInforList(LoginInfor loginInfor);

    /**
     * 新增
     *
     * @param loginInfor 
     * @return 结果
     */
    public int insertLoginInfor(LoginInfor loginInfor);

    /**
     * 修改
     *
     * @param loginInfor 
     * @return 结果
     */
    public int updateLoginInfor(LoginInfor loginInfor);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteLoginInforById(Integer id);

}
