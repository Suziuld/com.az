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
public class  Department {
    /*
    * id
    */
    private Integer id;
    /*
    * 部门名称
    */
    private String name;
    /*
    * 部门地址
    */
    private String address;
    /*
    * 类型 1.内科  2.外科 3。急诊 4。其他（体检）
    */
    private Integer type;
    /*
    * 部门创建时间
    */
    private String createDatetime;

//业务字段
    private String typeName;

}
