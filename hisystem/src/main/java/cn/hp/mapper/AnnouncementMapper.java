package cn.hp.mapper;

import cn.hp.entity.Announcement;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper接口
 */
//@Mapper
public interface AnnouncementMapper {
    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    public Announcement selectAnnouncementById(Integer id);

    /**
     * 查询列表
     *
     * @param announcement
     * @return 集合
     */
    public List<Announcement> selectAnnouncementList(Announcement announcement);

    /**
     * 新增
     *
     * @param announcement
     * @return 结果
     */
    public int insertAnnouncement(Announcement announcement);

    /**
     * 修改
     *
     * @param announcement
     * @return 结果
     */
    public int updateAnnouncement(Announcement announcement);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteAnnouncementById(Integer id);

}
