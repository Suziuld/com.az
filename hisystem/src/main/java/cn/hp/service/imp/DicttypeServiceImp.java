package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.Dicttype;
import cn.hp.mapper.DicttypeMapper;
import cn.hp.service.IDicttypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class DicttypeServiceImp implements IDicttypeService {
    @Resource
    private DicttypeMapper dicttypeMapper;

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Dicttype selectDicttypeById(Integer id) {
        return dicttypeMapper.selectDicttypeById(id);
    }

    /**
     * 分页查询列表
     *
     * @param dicttype
     * @return
     */
    @Override
    public PageInfo<Dicttype> selectDicttypeList(Dicttype dicttype, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Dicttype> list = dicttypeMapper.selectDicttypeList(dicttype);
        return new PageInfo<Dicttype>(list);
    }

    /**
     * 查询列表
     *
     * @param dicttype
     * @return
     */
    @Override
    public List<Dicttype> selectDicttypeList(Dicttype dicttype) {
        return dicttypeMapper.selectDicttypeList(dicttype);
    }

    /**
     * 新增
     *
     * @param dicttype
     * @return 结果
     */
    @Override
    public int insertDicttype(Dicttype dicttype) {
        return dicttypeMapper.insertDicttype(dicttype);
    }

    /**
     * 修改
     *
     * @param dicttype
     * @return 结果
     */
    @Override
    public int updateDicttype(Dicttype dicttype) {
        return dicttypeMapper.updateDicttype(dicttype);
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteDicttypeById(Integer id) {
        return dicttypeMapper.updateDicttype(new Dicttype().setId(id).setFlog(0));
//        return dicttypeMapper.deleteDicttypeById(id);
    }
}
