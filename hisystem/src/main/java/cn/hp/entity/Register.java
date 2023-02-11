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
public class  Register {
    /*
    * ID
    */
    private Integer id;
    /*
    * 科室Id
    */
    private Integer departmentId;
    /*
    * 医生id
    */
    private Integer doctorId;
    /*
    * 医生名称
    */
    private String doctor;
    /*
    * 付费方式
    */
    private String payType;
    /*
    * 挂号类型
    */
    private String registerType;

    /*
     * 诊查费
     */
    private String price;
    /*
     * 挂号费
     */
    private String registerPriice;
    /*
     * 实付费用
     */
    private String payPrice;
    /*
     * 找零
     */
    private String changePrice;
    /*
    * 病人ID
    */
    private Integer patientId;
    /*
    * 创建人id（挂号护士id）
    */
    private Integer createId;
    /*
    * 挂号状态。-1:过期，1:挂号成功
    */
    private Integer registerStatus;
    /*
    * 就诊状态,包括门诊，体检。0:未就诊，1:已就诊
    */
    private Integer treatmentStatus;
    /*
    * 创建人名字
    */
    private String createName;
    /*
    * 门诊收费状态：0：未收费  1：已收费
    */
    private Integer chargeStatus;
    /*
    * 创建时间
    */
    private String createDatetime;


    /*
     * 门诊登记编号(废弃字段)
     */
    private String registeredNum;

    //医生表的字段
    /*
     * 医生：可挂号数
     */
    private Integer allowNum;
    /*
     * 医生：已挂号数
     */
    private Integer nowNum;
    /*
     * 就业时间
     */
    private String workDateTime;
    /*
     * 工作地址
     */
    private String workAddress;

    //业务字段
    private String cardId;
    private String startTime;
    private String endTime;
    private String name;
    private String departmentName;
    //挂号系列表的id和状态
    private Integer queueId;
    private Integer status;

}
