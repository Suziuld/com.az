package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.Idcard;
import cn.hp.mapper.IdcardMapper;
import cn.hp.service.IIdcardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class IdcardServiceImp implements IIdcardService
{
    @Resource
    private IdcardMapper idcardMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public Idcard selectIdcardById(Integer id)
    {
        return idcardMapper.selectIdcardById(id);
    }

    /**
     * 分页查询列表
     * @param idcard 
     * @return 
     */
    @Override
    public PageInfo<Idcard> selectIdcardList(Idcard idcard, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<Idcard> list = idcardMapper.selectIdcardList(idcard);
        return new PageInfo<Idcard>(list);
    }

    /**
     * 查询列表
     * @param idcard 
     * @return 
     */
    @Override
    public List<Idcard> selectIdcardList(Idcard idcard)
    {
        return idcardMapper.selectIdcardList(idcard);
    }

    /**
     * 新增
     * @param idcard 
     * @return 结果
     */
    @Override
    public int insertIdcard(Idcard idcard)
    {
        return idcardMapper.insertIdcard(idcard);
    }

    /**
     * 修改
     * @param idcard 
     * @return 结果
     */
    @Override
    public int updateIdcard(Idcard idcard)
    {
        return idcardMapper.updateIdcard(idcard);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteIdcardById(Integer id)
    {
        return idcardMapper.deleteIdcardById(id);
    }
}
