package cn.hp.service;


import cn.hp.entity.Department;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IDepartmentService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Department selectDepartmentById(Integer id);

    /**
     * 查询列表
     * @param department 
     * @return 集合
     */
    public List<Department> selectDepartmentList(Department department);

    /**
     * 分页查询列表
     * @param department 
     * @return 集合
     */
    public PageInfo<Department> selectDepartmentList(Department department, Integer page, Integer limit);

    /**
     * 新增
     * @param department 
     * @return 结果
     */
    public int insertDepartment(Department department);

    /**
     * 修改
     * @param department 
     * @return 结果
     */
    public int updateDepartment(Department department);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteDepartmentById(Integer id);
}
