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
public class  Idcard {
    /*
    * id
    */
    private Integer id;
    /*
    * 名字
    */
    private String name;
    /*
    * 性别
    */
    private String sex;
    /*
    * 民族
    */
    private String nationality;
    /*
    * 身份证
    */
    private String idCard;
    /*
    * 地址
    */
    private String address;
    /*
    * 生日
    */
    private String birthday;


    private String command;

}
