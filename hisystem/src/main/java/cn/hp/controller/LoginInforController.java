package cn.hp.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hp.entity.User;
import cn.hp.task.AsyncTask;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.LoginInfor;
import cn.hp.service.ILoginInforService;
import cn.hp.util.Result;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 登录记录 Controller

 */
@RestController
@RequestMapping("/api/loginInfor")
public class LoginInforController
{
    private static final Logger logger = LoggerFactory.getLogger(LoginInforController.class);
    @Autowired
    private ILoginInforService loginInforService;

    /**
     * 条件查询
     */
    @GetMapping("/getUidLginInfo")
    public Object getUidLginInfo(LoginInfor obj) {
        User user = UserController.getUser();
        if(user!=null){
            obj.setUserId(user.getId()+"");
            return Result.success(loginInforService.selectLoginInforList(obj));
        }
        return Result.error();
    }

    /**
     * 条件查询
     */
    @GetMapping
    public Object find(LoginInfor obj) {
        return Result.success(loginInforService.selectLoginInforList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    public Object findPage(LoginInfor obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        PageInfo<LoginInfor> pageInfo = loginInforService.selectLoginInforList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        LoginInfor loginInfor = loginInforService.selectLoginInforById(id);
        return Result.success(loginInfor);
    }
    /**
     * 添加
     */
    @PostMapping
    public Object add(@RequestBody LoginInfor obj) {
        int i = loginInforService.insertLoginInfor(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    public Object update(@RequestBody LoginInfor obj) {
        int i = loginInforService.updateLoginInfor(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Object del(@PathVariable Integer id) {
        int i = loginInforService.deleteLoginInforById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }


    //导出Excel
    @RequestMapping("/export")
    public void exportExcel(LoginInfor loginInfor,HttpServletResponse response) throws IOException {
        //查询数据库查询所有数据
        List<LoginInfor> list = loginInforService.selectLoginInforList(loginInfor);
        logger.info("导出Excel,当前数据库中数据总数为: [{}]",list.size());
        //生成excel
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("管理员登录信息", "登录信息"), LoginInfor.class, list);
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode("管理员登录信息列表.xls","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    /*//导入Excel文件
    @RequestMapping("/import")
    public String importExcel(MultipartFile excelFile) throws Exception {
        logger.info("文件名: [{}]",excelFile.getOriginalFilename());

        //excel导入
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<User> users = ExcelImportUtil.importExcel(excelFile.getInputStream(), User.class, params);
        //users.forEach(System.out::println);
//        logger.saveAll(users);
        return "redirect:/user/findAll";//上传完成之后,跳转到查询所有信息路径
    }*/



}
