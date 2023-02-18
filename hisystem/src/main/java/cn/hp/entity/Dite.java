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
public class  Dite {
    /*
    * 字典表
    */
    private Integer id;
    /*
    * 编号
    */
    private String typeNo;
    /*
    * 权重
    */
    private Integer hierarchy;
    /*
    * 姓名
    */
    private String name;
    /*
    * 备注
    */
    private String remark;
    /*
    * 1否2是
    */
    private Integer flog;

    //业务字段，字典类型
    private String typeName;

}
