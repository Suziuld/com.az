package cn.hp.service;


import cn.hp.entity.LoginInfor;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface ILoginInforService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public LoginInfor selectLoginInforById(Integer id);

    /**
     * 查询列表
     * @param loginInfor 
     * @return 集合
     */
    public List<LoginInfor> selectLoginInforList(LoginInfor loginInfor);

    /**
     * 分页查询列表
     * @param loginInfor 
     * @return 集合
     */
    public PageInfo<LoginInfor> selectLoginInforList(LoginInfor loginInfor, Integer page, Integer limit);

    /**
     * 新增
     * @param loginInfor 
     * @return 结果
     */
    public int insertLoginInfor(LoginInfor loginInfor);

    /**
     * 修改
     * @param loginInfor 
     * @return 结果
     */
    public int updateLoginInfor(LoginInfor loginInfor);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteLoginInforById(Integer id);

    LoginInfor findByLoginIpAndLoginBroswerAndUserId(LoginInfor loginInfor);
}
