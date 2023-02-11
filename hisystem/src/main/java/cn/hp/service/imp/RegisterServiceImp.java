package cn.hp.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hp.common.constant.Constants;

import cn.hp.common.exception.BusinessException;
import cn.hp.entity.*;
import cn.hp.mapper.*;
import cn.hp.service.IGetPatientInfoService;
import cn.hp.util.DateUtil;
import cn.hp.util.StringUtils;
import cn.hp.util.card.Card;
import cn.hp.service.IRegisterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 */
@Service
public class RegisterServiceImp implements IRegisterService {
    @Resource
    private RegisterMapper registerMapper;
    @Resource
    private IGetPatientInfoService getPatientInfo;
    @Resource
    private OutpatientQueueMapper outpatientQueueMapper;
    @Resource
    private IdcardMapper idcardMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PatientMapper patientMapper;


    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Register selectRegisterById(Integer id) {
        return registerMapper.selectRegisterById(id);
    }

    /**
     * 分页查询列表
     *
     * @param register1
     * @return
     */
@Override
public PageInfo<Register> selectRegisterList(Register register1, Integer page, Integer limit) {
    PageHelper.startPage(page, limit);
    List<Register> list = registerMapper.selectPageRegisterList(register1);
    for (Register register : list) {
        int registerStatus = register.getRegisterStatus();
        int treatmentStatus = register.getTreatmentStatus();
        if (registerStatus == 1) {//已挂号
            if (treatmentStatus == 0) {//未就诊情况下
                String createDate = DateUtil.DateTimeToDate(register.getCreateDatetime());
                String nowDate = DateUtil.getCurrentDateSimpleToString();
                //不是当天则修改挂号状态为：-1 （过期），门诊队列状态改成过期
                if (!nowDate.equals(createDate)) {
                    //检查门诊队列是否待处理状态
                    OutpatientQueue outpatientQueue = new OutpatientQueue();
                    if (register.getQueueId() != null && register.getStatus() == Constants.QUEUE.NORMAL) {
                        outpatientQueueMapper.updateOutpatientQueue(outpatientQueue.setId(register.getQueueId()).setStatus(Constants.QUEUE.OVERDUE));
                    }
                    register.setRegisterStatus(-1);
                    registerMapper.updateRegister(register);
                }
            }
        }
    }

    return new PageInfo<Register>(list);
}

    /**
     * 查询列表
     *
     * @param register
     * @return
     */
    @Override
    public List<Register> selectRegisterList(Register register) {
        return registerMapper.selectRegisterList(register);
    }

    /**
     * 新增
     *
     * @param register
     * @return 结果
     */
    @Override
    public int insertRegister(Register register) {
        return registerMapper.insertRegister(register);
    }

    /**
     * 修改
     *
     * @param register
     * @return 结果
     */
    @Override
    public int updateRegister(Register register) {
        return registerMapper.updateRegister(register);
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public int deleteRegisterById(Integer id) {
        return registerMapper.deleteRegisterById(id);
    }

/**
 * 获取就诊卡信息
 */
@Override
public Patient getCardIdInfor(Patient patient) {
    Patient patientinfo = new Patient();
    //获取患者信息
    Patient patient1 = getPatientInfo.getPatientInfo(patient);
    int patientId = patient1.getId();
    List<Register> registerList = registerMapper.selectPageRegisterList(new Register().setPatientId(patientId));
    if (registerList != null && !registerList.isEmpty()) {
        //过期的挂号
        List<Register> expiredList = new ArrayList<>();
        for (Register register : registerList) {
            int registerStatus = register.getRegisterStatus();
            int treatmentStatus = register.getTreatmentStatus();
            int chargeStatus = register.getChargeStatus();
            Integer status = register.getStatus();
            //已挂号
            if (registerStatus == 1) {
                //未就诊情况下
                if (treatmentStatus == 0) {
                    String createDate = DateUtil.DateTimeToDate(register.getCreateDatetime());
                    String nowDate = DateUtil.getCurrentDateSimpleToString();
                    //当天情况下
                    if (nowDate.equals(createDate)) {
                        //检查门诊队列是否待处理状态
                        OutpatientQueue outpatientQueue = outpatientQueueMapper.selectOutpatientQueueByRegisterId(register.getId());
                        if (outpatientQueue != null && outpatientQueue.getStatus() == Constants.QUEUE.NORMAL) {
                            String doctorName = Arrays.asList(outpatientQueue.getRemark().split("#")).get(1);
                            throw new BusinessException("当日有未完成的就诊，请完成就诊！门诊医生：" + doctorName);
                        }
                    }
                    //不是当天则修改挂号状态为：-1 （过期）
                    else {
                        register.setRegisterStatus(-1);
                        expiredList.add(register);
                    }
                }
                //已就诊未缴费
                if (treatmentStatus == 1 && chargeStatus == 0&&status!=-1) {
                    throw new BusinessException("存在已就诊未收费的记录，请及时缴费！");
                }
            }
        }
        expiredList.forEach(e -> {
            registerMapper.updateRegister(e);
        });
    }
    try {
        patientinfo.setAge(DateUtil.getAge(patient1.getBirthday()));
    } catch (Exception e) {
        throw new BusinessException("该患者出生日日期格式错误！信息获取失败！");

    }
    BeanUtils.copyProperties(patient1, patientinfo);
    return patientinfo;
}


/**
 * 【没有身份证阅读器，将普通IC卡与病人信息绑定】
 * 获取身份证信息
 *
 * @return
 */
@Override
public Idcard getIDcardInfor() {

    String CardId = Card.defaultGetCardId();

    if ("fail".equals(CardId)) {
        throw new BusinessException("读卡失败！请刷新页面重试！");
    } else if ("none".equals(CardId)) {
        throw new BusinessException("未识别到卡片！");
    } else if (!StringUtils.isNumeric2(CardId)) {
        throw new BusinessException("读卡数据为：" + CardId + ",不符合现有数据的规则！");
    } else {
        Idcard idcard = idcardMapper.selectIdcardById(Integer.parseInt(CardId));
        if (idcard == null) {
            throw new BusinessException("未从该卡片识别到证件信息！");
        }
        return idcard;
    }
}

@Override
public List<Register> getAllRegisterDoctor(Register obj) {
    List<Register> registerDoctorRspList = new ArrayList<>();
    List<User> userList = userMapper.selectUserList(new User().setDepartmentId(obj.getDepartmentId()).setDepartmentType(Integer.parseInt(obj.getRegisterType())));
    if (userList != null && userList.size() > 0) {
        userList.forEach(user -> {
            //更新已挂号数
            System.out.println(DateUtil.getCurrentDateSimpleToString());
            System.out.println(user.getUpdateTime());
            System.out.println(DateUtil.getCurrentDateSimpleToString().equals(user.getUpdateTime()));
            if (!DateUtil.getCurrentDateSimpleToString().equals(user.getUpdateTime())) {
                User user2 = new User();
                user2.setId(user.getId());
                user2.setNowNum(0);
                user2.setUpdateTime(DateUtil.getCurrentDateSimpleToString());
                userMapper.updateUser(user2);
            }
            Register register = new Register();
            register.setDoctor(user.getUsername());
            register.setAllowNum(user.getAllowNum());
            register.setNowNum(user.getNowNum());
            register.setWorkDateTime(user.getWorkDateTime());
            register.setPrice(user.getTreatmentPrice());
            register.setDoctorId(user.getId());
            register.setWorkAddress(user.getWorkAddress());
            registerDoctorRspList.add(register);
        });
    }
    return registerDoctorRspList;
}

/**
 * 保存挂号记录
 *
 * @return
 */
@Override
@Transactional
public int addRegisterInfor(Register obj) {
    int k = 0;
    User u = (User) SecurityUtils.getSubject().getPrincipal();
    if (org.springframework.util.StringUtils.isEmpty(u)) {
        throw new BusinessException("登录信息已过期！");
    }
    User userDoctor = userMapper.selectUserById(obj.getDoctorId());
    if (!StringUtils.isNotNull(userDoctor)) {
        throw new BusinessException("未查询到相关医生信息，请稍后重试！");
    }
    int allowNum = userDoctor.getAllowNum();
    int nowNum = userDoctor.getNowNum();
    if (nowNum == allowNum) {
        throw new BusinessException("该医生已挂号人数已达上限，请刷新页面重新选择！");
    }

    Patient patient = patientMapper.selectPatientByCardId(obj.getCardId());
    //当天
    String nowDate = DateUtil.getCurrentDateSimpleToString();
    Register r = new Register();
    r.setPatientId(patient.getId()).setTreatmentStatus(0).setRegisterStatus(1).setStartTime(nowDate.concat(" 00:00:00")).setEndTime(nowDate.concat(" 23:59:59"));
    //患者当前门诊队列状态是待处理，验证第二次挂的是否为体检号
    List<Register> registerTemp = registerMapper.selectRegisterList(r);
    if (registerTemp != null && !registerTemp.isEmpty()) {
        if (registerTemp.size() != 1) {
            throw new BusinessException("挂号记录异常，请联系管理员！");
        }

        if (11!=obj.getDepartmentId()) {
            throw new BusinessException("门诊待处理状态，只允许再挂体检号！");
        }
    }
    //更新已挂号数量
    User user = new User();
    user.setId(userDoctor.getId()).setNowNum(nowNum + 1);
    k += userMapper.updateUser(user);
    //保存挂号记录
    String registeredNum = "RE" + System.currentTimeMillis() + (int) (Math.random() * 900 + 100);
    obj.setCreateId(u.getId())
            .setCreateName(u.getUsername())
            .setPatientId(patient.getId())
            .setRegisterStatus(1)
            .setRegisteredNum(registeredNum);
    k += registerMapper.insertRegister(obj);

    //将患者加入门诊队列
    OutpatientQueue outpatientQueue = new OutpatientQueue();
    outpatientQueue.setPatientId(patient.getId())
            .setRegisterId(obj.getId())
            .setUserId(userDoctor.getId())
            .setRemark(patient.getName() + '#' + userDoctor.getUsername())
            .setStatus(Constants.QUEUE.NORMAL);
    k += outpatientQueueMapper.insertOutpatientQueue(outpatientQueue);

    return k;
}

}
