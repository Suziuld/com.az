package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.Goout;
import cn.hp.mapper.GooutMapper;
import cn.hp.service.IGooutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class GooutServiceImp implements IGooutService
{
    @Resource
    private GooutMapper gooutMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public Goout selectGooutById(Integer id)
    {
        return gooutMapper.selectGooutById(id);
    }

    /**
     * 分页查询列表
     * @param goout 
     * @return 
     */
    @Override
    public PageInfo<Goout> selectGooutList(Goout goout, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<Goout> list = gooutMapper.selectGooutList(goout);
        return new PageInfo<Goout>(list);
    }

    /**
     * 查询列表
     * @param goout 
     * @return 
     */
    @Override
    public List<Goout> selectGooutList(Goout goout)
    {
        return gooutMapper.selectGooutList(goout);
    }

    /**
     * 新增
     * @param goout 
     * @return 结果
     */
    @Override
    public int insertGoout(Goout goout)
    {
        return gooutMapper.insertGoout(goout);
    }

    /**
     * 修改
     * @param goout 
     * @return 结果
     */
    @Override
    public int updateGoout(Goout goout)
    {
        return gooutMapper.updateGoout(goout);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteGooutById(Integer id)
    {
        return gooutMapper.deleteGooutById(id);
    }
}
