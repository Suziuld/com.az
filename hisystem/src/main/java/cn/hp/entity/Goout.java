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
public class  Goout {
    /*
    * id
    */
    private Integer id;
    /*
    * 药品编号
    */
    private Integer drugId;
    /*
    * 药名
    */
    private String drugName;
    /*
    * 出库数量
    */
    private Integer drugNum;
    /*
    * 小计（该商品数量X单价）
    */
    private String moner;
    /*
    * 处方号
    */
    private Integer tolltakedrugNo;
    /*
    * 患者id
    */
    private String patientId;
    /*
    * 售出时间
    */
    private Date createTime;

}
