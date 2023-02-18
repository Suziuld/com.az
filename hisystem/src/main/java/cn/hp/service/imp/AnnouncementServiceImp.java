    package cn.hp.service.imp;

    import java.util.List;

    import cn.hutool.core.convert.Convert;
    import cn.hp.entity.Announcement;
    import cn.hp.mapper.AnnouncementMapper;
    import cn.hp.service.IAnnouncementService;
    import com.github.pagehelper.PageHelper;
    import com.github.pagehelper.PageInfo;

    import javax.annotation.Resource;

    import org.springframework.stereotype.Service;

    /**
     * Service业务层处理
     */
    @Service
    public class AnnouncementServiceImp implements IAnnouncementService {
        @Resource
        private AnnouncementMapper announcementMapper;

        /**
         * 查询
         *
         * @param id ID
         * @return
         */
        @Override
        public Announcement selectAnnouncementById(Integer id) {
            return announcementMapper.selectAnnouncementById(id);
        }

        /**
         * 分页查询列表
         *
         * @param announcement
         * @return
         */
        @Override
        public PageInfo<Announcement> selectAnnouncementList(Announcement announcement, Integer page, Integer limit) {
            PageHelper.startPage(page, limit);
            List<Announcement> list = announcementMapper.selectAnnouncementList(announcement);
            return new PageInfo<Announcement>(list);
        }

        /**
         * 查询列表
         *
         * @param announcement
         * @return
         */
        @Override
        public List<Announcement> selectAnnouncementList(Announcement announcement) {
            return announcementMapper.selectAnnouncementList(announcement);
        }

        /**
         * 新增
         *
         * @param announcement
         * @return 结果
         */
        @Override
        public int insertAnnouncement(Announcement announcement) {
            return announcementMapper.insertAnnouncement(announcement);
        }

        /**
         * 修改
         *
         * @param announcement
         * @return 结果
         */
        @Override
        public int updateAnnouncement(Announcement announcement) {
            return announcementMapper.updateAnnouncement(announcement);
        }

        /**
         * 删除信息
         *
         * @param id ID
         * @return 结果
         */
        @Override
        public int deleteAnnouncementById(Integer id) {
            return announcementMapper.deleteAnnouncementById(id);
        }
    }
