package cn.hp.entity;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginInfor {
    /*
     * id
     */
    @Excel(name = "编号")
    private Integer id;
    /*
     * 登录IP
     */
    @Excel(name="登录IP")
    private String loginIp;
    /*
     * 登录客户端
     */
    @Excel(name="登录客户端")
    private String loginBroswer;
    /*
     * 登录地址
     */
    @Excel(name="登录地址")
    private String loginAddress;
    /*
     * 用户id
     */
    private String userId;
    /*
     * 描述
     */
    @Excel(name="描述",width = 20.0)
    private String description;
    /*
     * 创建时间
     */
    @Excel(name="登录时间",format = "yyyy年MM月dd日",width = 15.0)
    private String createDatetime;
    //    其他业务字段
    /*
     * 用户名称
     */
    @Excel(name="用户名称")
    private String username;


}
