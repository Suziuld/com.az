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
public class  UserRole {
    /*
    * 角色用户中间表id
    */
    private Integer id;
    /*
    * 用户编号
    */
    private Integer uid;
    /*
    * 角色编号
    */
    private Integer roleId;
    /*
    * 邮箱+角色描述
    */
    private String description;
    /*
    * 注册时间
    */
    private Date createDatetime;

}
