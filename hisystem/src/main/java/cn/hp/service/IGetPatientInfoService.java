package cn.hp.service;

import cn.hp.entity.Patient;

import java.util.List;

/**
 * 通过 command  0:表示读卡器输入卡号 1:表示手动输入卡号  获取对应卡号
 * 然后获取对应卡号的患者信息
 * @description: 统一获取卡号
 */
public interface IGetPatientInfoService {
    Patient getPatientInfo(Patient patient);
}
