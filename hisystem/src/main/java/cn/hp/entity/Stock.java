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
public class Stock {
    /*
     * id
     */
    private Integer id;
    /*
     * 药品id
     */
    private Integer drugId;
    /*
     * 数量
     */
    private Integer num;
    /*
     * 单价
     */
    private Double price;
    /*
     * 费用
     */
    private Double money;
    /*
     * 进货商
     */
    private String supplier;
    /*
     * 时间
     */
    private Date stockTime;
    //业务字段
    private String drugName;
}
