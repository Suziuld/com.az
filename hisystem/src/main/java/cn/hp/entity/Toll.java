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
public class  Toll {
    /*
    * id
    */
    private Integer id;
    /*
    * 创建时间
    */
    private Date createDatetime;


    //业务字段
    private String outpatientDate;
    private Integer registerId;
    private String department;
    private String doctorName;
    private String registerType;
    private String prescriptionNum;
    private Integer status;
//是否已经付过款了
    private Integer chargeStatus;

//    收费医疗记录
    private String nowDate;
    private String createDate;
    private String name;
    private String sex;
    private String nationality;
    private Integer age;
    private String diagnosisResult;
    private Double drugCost;
    private String prescription;
    private String medicalOrder;
    private Double examinationCost;

}
