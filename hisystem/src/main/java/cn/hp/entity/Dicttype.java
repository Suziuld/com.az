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
public class  Dicttype {
    /*
    * id
    */
    private Integer id;
    /*
    * 自定义编号
    */
    private String typeNo;
    /*
    * 字典名称
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

}
