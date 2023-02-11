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
public class  TollTakedrug {
    /*
    * id
    */
    private Integer id;
    /*
    * 创建时间
    */
    private String createDatetime;
    /*
    * 处方号
    */
    private String prescriptionNum;
    /*
    * 拿药时间
    */
    private String takingDrugDateTime;
    /*
    * 药房操作员
    */
    private String takingDrugOperator;
    /*
    * 取药状态：0未取 1已取
    */
    private Integer takingDrugStatus;
    /*
    * 收费时间
    */
    private String tollDateTime;
    /*
    * 收费操作员
    */
    private String tollOperator;
    /*
    * 患者id
    */
    private Integer patientId;
    /*
    * 支付金额
    */
    private Double money;

}
