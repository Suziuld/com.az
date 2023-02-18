package com.ajax.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (Userinfo)表实体类
 *
 * @author makejava
 * @since 2022-10-31 15:26:44
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Userinfo {
    //序号
    private Integer userid;
    //用户名
    private String username;
    //用户密码
    private String pwd;
    //用户电话
    private String userphone;

}

