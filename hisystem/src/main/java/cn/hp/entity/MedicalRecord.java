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
public class  MedicalRecord {
    /*
    * id
    */
    private Integer id;
    /*
    * 处方号（用来关联处方中的具体药物信息）
    */
    private String prescriptionNum;
    /*
    * 药方、处方信息
    */
    private String prescription;
    /*
     * 药品id集合
     */
    private String drugIds;
    /*
    * 主诉
    */
    private String conditionDescription;
    /*
    * 诊断结果
    */
    private String diagnosisResult;
    /*
    * 医嘱
    */
    private String medicalOrder;
    /*
    * 总价格
    */
    private Double money;
    /*
    * 门诊登记id
    */
    private Integer registerId;
    /*
    * 创建时间
    */
    private String createDatetime;


    //业务字段
    private Integer queueId;
    private Integer cardId;
    private String name;
    private String remark;
    private Integer takingDrugStatus;//'取药状态：-1未付款 0未取药 1已取药',

    //    患者信息
    /*
     * 职业
     */
    private String career;
    /*
     * 家族历史
     */
    private String familyHistory;
    /*
     * 婚姻状况
     */
    private String maritalStatus;
    /*
     * 过去病史
     */
    private String pastHistory;
    /*
     * 个人史  生活史
     */
    private String personalHistory;
    //保存拿药信息的业务字段
    private String nowDate;

    private String createDate;

    private String sex;

    private String nationality;

    private int age;

    private double drugCost;

    private String doctorName;

    private String department;

    private double examinationCost;

    private String message;

}
