package cn.hp.service.imp;

import cn.hp.common.constant.Constants;

import cn.hp.common.exception.BusinessException;
import cn.hp.entity.Patient;
import cn.hp.mapper.PatientMapper;
import cn.hp.service.IGetPatientInfoService;
import cn.hp.util.card.Card;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class GetPatientInfoServiceImpl implements IGetPatientInfoService {

    @Resource
    private PatientMapper patientMapper;
    @Override
    public Patient getPatientInfo(Patient patient) {
        String myCardId = patient.getCardId();
        String command = patient.getCommand();
        //手动输入卡号
        if (Constants.COMMON_STATUS_ONE.equals(command)) {
            if (StringUtils.isEmpty(myCardId)) {
                throw new BusinessException("请输入就诊卡号！");
            }
        }
        //读卡器输入
        if (Constants.COMMON_STATUS_ZERO.equals(command)) {
            //读卡
            String message = Card.defaultGetCardId();
            if (Constants.IC_READ_FAIIL.equals(message)) {
                throw new BusinessException("读卡失败！请刷新页面重试！！");
            } else if (Constants.IC_READ_NONE.equals(message)) {
                throw new BusinessException("未识别到有效就诊卡，请重试！");
            } else {
                myCardId = message;
            }
        }
        Patient patientInfor = patientMapper.selectPatientByCardId(myCardId);
        if (patientInfor == null) {
            throw new BusinessException("该就诊卡不存在，请注册后查询！");
        }
        return patientInfor;
    }
}
