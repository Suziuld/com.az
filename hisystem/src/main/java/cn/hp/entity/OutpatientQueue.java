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
public class  OutpatientQueue {
    /*
    * id
    */
    private Integer id;
    /*
    * 门诊登记id
    */
    private Integer registerId;
    /*
    * 医生id
    */
    private Integer userId;
    /*
    * 患者id
    */
    private Integer patientId;
    /*
    * 门诊队列状态 1表示正常状态，-1表示稍后处理，0过期
    */
    private Integer status;
    /*
    * 描述
    */
    private String remark;
    /*
    * 创建时间
    */
    private String createDatetime;
    //业务字段
    private String startTime;
    private String endTime;
    private String department;
    private String patientName;
    private Integer cardId;
    private Integer departmentId;
    /*
     * 医生名称
     */
    private String userName;
}
