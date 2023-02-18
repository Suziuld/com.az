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
public class  Drug {
    /*
    * 药物id
    */
    private Integer id;
    /*
    * 药物名称
    */
    private String name;
    /*
    * 药物类型
    */
    private String drugType;
    /*
    * 药物单位
    */
    private String unit;
    /*
    * 药物规范
    */
    private String specification;
    /*
    * 药物功效分类
    */
    private String efficacyClassification;
    /*
    * 药物价格
    */
    private Double price;
    /*
    * 药品库存
    */
    private Integer drugCount;
    /*
    * 药品生产日期
    */
    private String productionDate;
    /*
    * 有效期
    */
    private String qualityDate;
    /*
    * 是否限制药限制状态0是1否
    */
    private Integer limitStatus;
    /*
    * 制造商
    */
    private String manufacturer;
    /*
    * 批发商的价格
    */
    private Double wholesalePrice;
    /*
    * 手机号
    */
    private Integer phone;
    /*
    * 创建时间
    */
    private String createDatetime;

}
