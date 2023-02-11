package cn.hp.service;


import cn.hp.entity.Idcard;
import cn.hp.entity.Patient;
import cn.hp.entity.Register;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IRegisterService
{
    /**
     * 查询
     * @param id ID
     * @return 
     */
    public Register selectRegisterById(Integer id);

    /**
     * 查询列表
     * @param register 
     * @return 集合
     */
    public List<Register> selectRegisterList(Register register);

    /**
     * 分页查询列表
     * @param register 
     * @return 集合
     */
    public PageInfo<Register> selectRegisterList(Register register, Integer page, Integer limit);

    /**
     * 新增
     * @param register 
     * @return 结果
     */
    public int insertRegister(Register register);

    /**
     * 修改
     * @param register 
     * @return 结果
     */
    public int updateRegister(Register register);

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    public int deleteRegisterById(Integer id);

Patient getCardIdInfor(Patient patient);

Idcard getIDcardInfor();

List<Register> getAllRegisterDoctor(Register obj);

int addRegisterInfor(Register obj);
}
