package cn.hp.mapper;

import cn.hp.entity.Department;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 *
 */
//@Mapper
public interface DepartmentMapper
{
    /**
     * 查询
     *
     * @param id ID
     * @return 
     */
    public Department selectDepartmentById(Integer id);

    /**
     * 查询列表
     *
     * @param department 
     * @return 集合
     */
    public List<Department> selectDepartmentList(Department department);

    /**
     * 新增
     *
     * @param department 
     * @return 结果
     */
    public int insertDepartment(Department department);

    /**
     * 修改
     *
     * @param department 
     * @return 结果
     */
    public int updateDepartment(Department department);

    /**
     * 删除
     * @param id ID
     * @return 结果
     */
    public int deleteDepartmentById(Integer id);

}
