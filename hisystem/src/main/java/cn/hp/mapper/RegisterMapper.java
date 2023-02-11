package cn.hp.mapper;

import cn.hp.entity.Register;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface RegisterMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Register selectRegisterById(Integer id);

    /**
     * 查询列表
     *
     * @param register 
     * @return 集合
     */
    public List<Register> selectRegisterList(Register register);

    /**
     * 新增
     *
     * @param register 
     * @return 结果
     */
    public int insertRegister(Register register);

    /**
     * 修改
     *
     * @param register 
     * @return 结果
     */
    public int updateRegister(Register register);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteRegisterById(Integer id);

    List<Register> selectPageRegisterList(Register register);
}
