package cn.hp.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class  MedicalExamination {
    /*
    * id
    */
    private Integer id;
    /*
    * 血压
    */
    private String bloodPressure;
    /*
    * 体温
    */
    private String bodyTemperature;
    /*
    * 心率
    */
    private String heartRate;
    /*
    * 脉搏
    */
    private String pulse;
    /*
    * 门诊登记id
    */
    private Integer registerId;
    /*
     * 检查费用
     */
    private Double examinationCost;
    /*
     * 处方号
     */
    private String prescriptionNum;
    /*
    * 创建时间
    */
    private Date createDatetime;

//    业务字段
    private String name;
    private String sex;
    private String nationality;
    private int age;

    /**
     * 队列Id
     */
    private Integer queueId;
}
