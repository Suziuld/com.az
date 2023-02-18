package cn.hp.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.Announcement;
import cn.hp.service.IAnnouncementService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 公告 Controller
 */
@RestController
@RequestMapping("/api/announcement")
@CrossOrigin
public class AnnouncementController
{
    @Autowired
    private IAnnouncementService announcementService;
    /**
     * 条件查询
     */
    @GetMapping
    public Object find(Announcement obj) {
        return Result.success(announcementService.selectAnnouncementList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
//    @RequiresPermissions("system:announcement:page")
    public Object findPage(Announcement obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
        PageInfo<Announcement> pageInfo = announcementService.selectAnnouncementList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
//    @RequiresPermissions("system:announcement:findById")
    public Object findById(@PathVariable Integer id) {
        Announcement announcement = announcementService.selectAnnouncementById(id);
        return Result.success(announcement);
    }
    /**
     * 添加
     */
    @PostMapping
//    @RequiresPermissions("system:announcement:add")
    public Object add(@RequestBody Announcement obj) {
        int i = announcementService.insertAnnouncement(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
//    @RequiresPermissions("system:announcement:update")
    public Object update(@RequestBody Announcement obj) {
        int i = announcementService.updateAnnouncement(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
//    @RequiresPermissions("system:announcement:del")
    public Object del(@PathVariable Integer id) {
        int i = announcementService.deleteAnnouncementById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
