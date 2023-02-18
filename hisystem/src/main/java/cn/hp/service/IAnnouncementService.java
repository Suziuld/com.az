package cn.hp.service;


import cn.hp.entity.Announcement;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * Service接口
 */
public interface IAnnouncementService {
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
     * 分页查询列表
     *
     * @param announcement
     * @return 集合
     */
    public PageInfo<Announcement> selectAnnouncementList(Announcement announcement, Integer page, Integer limit);

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
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    public int deleteAnnouncementById(Integer id);
}
