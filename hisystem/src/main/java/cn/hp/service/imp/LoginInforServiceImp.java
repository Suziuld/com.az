package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.LoginInfor;
import cn.hp.mapper.LoginInforMapper;
import cn.hp.service.ILoginInforService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class LoginInforServiceImp implements ILoginInforService
{
    @Resource
    private LoginInforMapper loginInforMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public LoginInfor selectLoginInforById(Integer id)
    {
        return loginInforMapper.selectLoginInforById(id);
    }

    /**
     * 分页查询列表
     * @param loginInfor 
     * @return 
     */
    @Override
    public PageInfo<LoginInfor> selectLoginInforList(LoginInfor loginInfor, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<LoginInfor> list = loginInforMapper.selectLoginInforList(loginInfor);
        return new PageInfo<LoginInfor>(list);
    }

    /**
     * 查询列表
     * @param loginInfor 
     * @return 
     */
    @Override
    public List<LoginInfor> selectLoginInforList(LoginInfor loginInfor)
    {
        return loginInforMapper.selectLoginInforList(loginInfor);
    }

    /**
     * 新增
     * @param loginInfor 
     * @return 结果
     */
    @Override
    public int insertLoginInfor(LoginInfor loginInfor)
    {
        return loginInforMapper.insertLoginInfor(loginInfor);
    }

    /**
     * 修改
     * @param loginInfor 
     * @return 结果
     */
    @Override
    public int updateLoginInfor(LoginInfor loginInfor)
    {
        return loginInforMapper.updateLoginInfor(loginInfor);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteLoginInforById(Integer id)
    {
        return loginInforMapper.deleteLoginInforById(id);
    }

    @Override
    public LoginInfor findByLoginIpAndLoginBroswerAndUserId(LoginInfor loginInfor) {
        List<LoginInfor> list = loginInforMapper.selectLoginInforList(loginInfor);
        return list.size()>0?list.get(0):null;
    }

}
