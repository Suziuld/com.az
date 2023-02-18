package cn.hp.service.imp;

import java.util.List;

import cn.hp.util.StringUtils;
import cn.hutool.core.convert.Convert;
import cn.hp.entity.MedicalRecord;
import cn.hp.mapper.MedicalRecordMapper;
import cn.hp.service.IMedicalRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 */
@Service
public class MedicalRecordServiceImp implements IMedicalRecordService
{
    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    /**
     * 查询
     * @param id ID
     * @return 
     */
    @Override
    public MedicalRecord selectMedicalRecordById(Integer id)
    {
        return medicalRecordMapper.selectMedicalRecordById(id);
    }

    /**
     * 分页查询列表
     * @param medicalRecord 
     * @return 
     */
    @Override
    public PageInfo<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord, Integer page, Integer limit)
    {
        PageHelper.startPage(page,limit);
        List<MedicalRecord> list = medicalRecordMapper.selectMedicalRecordList(medicalRecord);
        list.forEach(medicalRecord1 -> {
            String remark = medicalRecord1.getRemark();
            if(StringUtils.isNotNull(remark)){
                medicalRecord1.setName(remark.split("#")[0]);
            }
        });
        return new PageInfo<MedicalRecord>(list);
    }

    /**
     * 查询列表
     * @param medicalRecord 
     * @return 
     */
    @Override
    public List<MedicalRecord> selectMedicalRecordList(MedicalRecord medicalRecord)
    {
        return medicalRecordMapper.selectMedicalRecordList(medicalRecord);
    }

    /**
     * 新增
     * @param medicalRecord 
     * @return 结果
     */
    @Override
    public int insertMedicalRecord(MedicalRecord medicalRecord)
    {
        return medicalRecordMapper.insertMedicalRecord(medicalRecord);
    }

    /**
     * 修改
     * @param medicalRecord 
     * @return 结果
     */
    @Override
    public int updateMedicalRecord(MedicalRecord medicalRecord)
    {
        return medicalRecordMapper.updateMedicalRecord(medicalRecord);
    }

    /**
     * 删除信息
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteMedicalRecordById(Integer id)
    {
        return medicalRecordMapper.deleteMedicalRecordById(id);
    }
}
