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
public class  RoleMenu {
    /*
    * 
    */
    private Integer id;
    /*
    * 角色ID
    */
    private Integer roleId;
    /*
    * 菜单ID
    */
    private Integer menuId;

}
