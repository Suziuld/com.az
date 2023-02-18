package cn.hp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class  Role {
    /*
    * ID
    */
    private Integer id;

    /*
    * 显示顺序
    */
    private Integer roleSort;
    /*
    * 角色标识
    */
    private String role;
    /*
    * 备注
    */
    private String remark;
    /*
    * 角色状态（0正常 1停用）
    */
    private String status;
    /*
    * 删除标志（0代表存在 2代表删除）
    */
    private String delFlag;
    /*
    * 创建时间
    */
    private Date createDatetime;

    /*
     * 业务字段 拥有的菜单ID
     */
    private Integer [] menuIds;
}
