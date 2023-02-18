package cn.hp.service.imp;

import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hp.entity.Department;
import cn.hp.mapper.DepartmentMapper;
import cn.hp.service.IDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class DepartmentServiceImp implements IDepartmentService
{
    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public Department selectDepartmentById(Integer id)
    {
        return departmentMapper.selectDepartmentById(id);
    }

    /**
     * 分页查询列表
     * @param department 
     * @return 
     */
    @Override
    public PageInfo<Department> selectDepartmentList(Department department, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<Department> list = departmentMapper.selectDepartmentList(department);
        return new PageInfo<Department>(list);
    }

    /**
     * 查询列表
     * @param department 
     * @return 
     */
    @Override
    public List<Department> selectDepartmentList(Department department)
    {
        return departmentMapper.selectDepartmentList(department);
    }

    /**
     * 新增
     * @param department 
     * @return 结果
     */
    @Override
    public int insertDepartment(Department department)
    {
        return departmentMapper.insertDepartment(department);
    }

    /**
     * 修改
     * @param department 
     * @return 结果
     */
    @Override
    public int updateDepartment(Department department)
    {
        return departmentMapper.updateDepartment(department);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteDepartmentById(Integer id)
    {
        return departmentMapper.deleteDepartmentById(id);
    }
}
