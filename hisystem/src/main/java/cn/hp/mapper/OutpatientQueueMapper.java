package cn.hp.mapper;

import cn.hp.entity.OutpatientQueue;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface OutpatientQueueMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public OutpatientQueue selectOutpatientQueueById(Integer id);

    /**
     * 查询列表
     *
     * @param outpatientQueue 
     * @return 集合
     */
    public List<OutpatientQueue> selectOutpatientQueueList(OutpatientQueue outpatientQueue);

    /**
     * 新增
     *
     * @param outpatientQueue 
     * @return 结果
     */
    public int insertOutpatientQueue(OutpatientQueue outpatientQueue);

    /**
     * 修改
     *
     * @param outpatientQueue 
     * @return 结果
     */
    public int updateOutpatientQueue(OutpatientQueue outpatientQueue);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteOutpatientQueueById(Integer id);

    OutpatientQueue selectOutpatientQueueByRegisterId(Integer id);
}
