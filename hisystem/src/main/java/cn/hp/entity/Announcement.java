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
public class  Announcement {
    /*
    * 公告id
    */
    private Integer id;
    /*
    * 公告标题
    */
    private String title;
    /*
    * 公告内容
    */
    private String contents;
    /*
    * 公告创建的时间
    */
    private Date createDatetime;
    /*
    * 公告状态 0正常 1废弃
    */
    private Integer annStatus;
    /*
    * 公告状态修改时间
    */
    private String annDate;

}
