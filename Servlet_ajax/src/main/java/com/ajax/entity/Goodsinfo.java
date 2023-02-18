package com.ajax.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * (Goodsinfo)表实体类
 *
 * @author makejava
 * @since 2022-10-31 15:27:02
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Goodsinfo {
    //物品编号
    private Integer goodsid;
    //外键关联用户表的用户编号
    private Integer userid;
    //物品名称
    private String goodsname;
    //物品类型
    private String goodstype;
    //售价
    private Integer goodsrent;
    //发布时间
    private Date releasetime;
    //出售状态 0表示未出售 1表示已出售
    private Integer state;
    //出售说明
    private String remark;

}

