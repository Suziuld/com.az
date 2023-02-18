package cn.hp.controller;

import java.util.List;

import cn.hp.entity.MedicalRecord;
import cn.hp.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.TollTakedrug;
import cn.hp.service.ITollTakedrugService;
import cn.hp.util.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Controller
 * /api/tollTakedrug
 */
@RestController
@RequestMapping("/api/tollTakedrug")
@Api(tags = "拿药管理API")
public class TollTakedrugController
{
    @Autowired
    private ITollTakedrugService tollTakedrugService;


/**
 * 获取处方笺信息
 *
 * @param prescriptionNum
 * @return
 * @throws Exception
 */
@PostMapping(value = "/getMedicalRecord")
public Result getMedicalRecord(@RequestParam String prescriptionNum) throws Exception {
    MedicalRecord medicalRecord = tollTakedrugService.getMedicalRecord(prescriptionNum);
    return StringUtils.isNotNull(medicalRecord)?Result.success(medicalRecord):Result.error();
}

/**
 * 保存拿药信息
 *
 * @param prescriptionNum
 * @return
 */
@PostMapping(value = "/saveTakingDrugInfo")
public Result saveTakingDrugInfo(@RequestParam String prescriptionNum) {
    int i=tollTakedrugService.saveTakingDrugInfo(prescriptionNum);
    return i>0?Result.success("添加成功！"):Result.error("添加失败！");
}

    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:tollTakedrug:find")
    public Object find(TollTakedrug obj) {
        return Result.success(tollTakedrugService.selectTollTakedrugList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:tollTakedrug:page")
    public Object findPage(TollTakedrug obj,@RequestParam(value = "pageNum",defaultValue = "1")Integer page,
                       @RequestParam(value = "pageSize",defaultValue = "10")Integer limit) {
        PageInfo<TollTakedrug> pageInfo = tollTakedrugService.selectTollTakedrugList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:tollTakedrug:findById")
    public Object findById(@PathVariable Integer id) {
        TollTakedrug tollTakedrug = tollTakedrugService.selectTollTakedrugById(id);
        return Result.success(tollTakedrug);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:tollTakedrug:add")
    public Object add(@RequestBody TollTakedrug obj) {
        int i = tollTakedrugService.insertTollTakedrug(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:tollTakedrug:update")
    public Object update(@RequestBody TollTakedrug obj) {
        int i = tollTakedrugService.updateTollTakedrug(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:tollTakedrug:del")
    public Object del(@PathVariable Integer id) {
        int i = tollTakedrugService.deleteTollTakedrugById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
