package cn.hp.service;


import cn.hp.entity.Idcard;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IIdcardService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Idcard selectIdcardById(Integer id);

    /**
     * 查询列表
     * @param idcard 
     * @return 集合
     */
    public List<Idcard> selectIdcardList(Idcard idcard);

    /**
     * 分页查询列表
     * @param idcard 
     * @return 集合
     */
    public PageInfo<Idcard> selectIdcardList(Idcard idcard, Integer page, Integer limit);

    /**
     * 新增
     * @param idcard 
     * @return 结果
     */
    public int insertIdcard(Idcard idcard);

    /**
     * 修改
     * @param idcard 
     * @return 结果
     */
    public int updateIdcard(Idcard idcard);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteIdcardById(Integer id);
}
