package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.Dite;
import cn.hp.mapper.DiteMapper;
import cn.hp.service.IDiteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class DiteServiceImp implements IDiteService {
    @Resource
    private DiteMapper diteMapper;

    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Dite selectDiteById(Integer id) {
        return diteMapper.selectDiteById(id);
    }

    /**
     * 分页查询列表
     *
     * @param dite
     * @return
     */
    @Override
    public PageInfo<Dite> selectDiteList(Dite dite, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Dite> list = diteMapper.selectDiteList(dite);
        return new PageInfo<Dite>(list);
    }

    /**
     * 查询列表
     *
     * @param dite
     * @return
     */
    @Override
    public List<Dite> selectDiteList(Dite dite) {
        return diteMapper.selectDiteList(dite);
    }

    /**
     * 新增
     *
     * @param dite
     * @return 结果
     */
    @Override
    public int insertDite(Dite dite) {
        return diteMapper.insertDite(dite);
    }

    /**
     * 修改
     *
     * @param dite
     * @return 结果
     */
    @Override
    public int updateDite(Dite dite) {
        return diteMapper.updateDite(dite);
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteDiteById(Integer id) {
        return diteMapper.updateDite(new Dite().setId(id).setFlog(0));
//        return diteMapper.deleteDiteById(id);
    }
}
