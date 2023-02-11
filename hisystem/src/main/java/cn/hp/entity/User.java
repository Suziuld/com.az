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
public class  User {
    /*
    * 用户id
    */
    private Integer id;
    /*
    * 姓名
    */
    private String username;
    /*
    * 手机号
    */
    private String phone;
    /*
    * 邮箱
    */
    private String email;

    /*
     * 原始密码
     */
    private String plainPassword;

    /*
    * 密码
    */
    private String password;
    /*
    * 盐值
    */
    private String salt;
    /*
    * 性别
    */
    private String sex;
    /*
    * 部门id
    */
    private Integer departmentId;
    /*
    * 科室类型
    */
    private Integer departmentType;
    /*
    * 医生：可挂号数
    */
    private Integer allowNum;
    /*
     *  医生：已挂号数
     */
    private Integer nowNum;
    /*
    * 地址
    */
    private String address;
    /*
    * 工作地址
    */
    private String workAddress;
    /*
    * 就业时间
    */
    private String workDateTime;
    /*
    * 就业状况 1工作  2 休息
    */
    private String workStatus;
    /*
    * 出生日期
    */
    private String birthday;
    /*
    * 政治面貌
    */
    private String politicalStatus;
    /*
    * 职位
    */
    private String grade;
    /*
    * 待遇工资
    */
    private String treatmentPrice;
    /*
    * 邮箱状态 0未激活 1已激活
    */
    private Integer emailStatus;
    /*
    * 验证码
    */
    private String validateCode;
    /*
    * 更新时间
    */
    private String updateTime;
    /*
    * 创建时间
    */
    private String createDatetime;
    /*
    * 是否锁定:Mybatis会自动将tinyint(1)字段值为0的转换成false，将字段值为1以上的转换为true
    */
    private Boolean locked;
    /*
    * 删除标志（0代表存在 2代表删除）
    */
    private String delFlag;
    /*
    * 最后登录IP
    */
    private String loginIp;
    /*
    * 最后登录时间
    */
    private Date loginDate;
    /*
    * 密码最后更新时间
    */
    private Date pwdUpdateDate;

    private String ip;
    private String broswer;
/*
 * 拥有的角色信息
 */
private String [] roleIds;
    /*
     * 新密码
     */
    private String newPassword;
    /*
     * 新密码再次确认
     */
    private String okPassword;
    //科室名称
    private String department;

    private String roleName;
}
