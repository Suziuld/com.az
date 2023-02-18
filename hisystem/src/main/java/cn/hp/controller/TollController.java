package cn.hp.controller;

import java.util.List;

import cn.hp.entity.Idcard;
import cn.hp.entity.MedicalExamination;
import cn.hp.entity.Register;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.Toll;
import cn.hp.service.ITollService;
import cn.hp.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * Controller
 * /api/toll
 */
@Api(tags = "收费管理API")
@RestController
@RequestMapping("/api/toll")
public class TollController {
//    @Resource
//    private ITollService tollService;
    @Autowired
    private ITollService tollService;

    /**
     * 读取就诊卡
     @RequestMapping("/getCardIdInfor") public Object getCardIdInfor(Toll obj) {
     return Result.success(tollService.getCardIdInfor(obj));
     }*/

/**
 * 获取病历信息
 * @param cardId
 * @param tollStatus
 * @return
 */
@RequestMapping("/getAllMedicalRecord")
public Object getAllMedicalRecord(@RequestParam String cardId, @RequestParam String tollStatus) {
    List<Toll> list = tollService.getAllMedicalRecord(cardId, tollStatus);
    return list!=null?Result.RowsSuccess(list,list.size()):Result.error("没有查到对应的数据！");
}

/**
 * 获取处方笺信息
 * @param cardId
 * @param registerId
 */
@RequestMapping(value = "/getMedicalRecord")
public Result getMedicalRecord(@RequestParam String cardId, @RequestParam String registerId) throws Exception {
    return Result.success(tollService.getMedicalRecord(cardId, registerId));
}
    /**
     * 划价收费完成，保存信息
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/saveTollInfo")
    public Result saveTollInfo(@RequestBody @Validated Toll reqVO) {
        int i= tollService.saveTollInfo(reqVO);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }
    @RequestMapping(value = "/getexaminationtoll")
    @ApiOperation(value = "获取体检收费信息", httpMethod = "POST", notes = "获取体检收费信息")
    @ApiImplicitParam(name = "reqVO",value = "获取体检收费信息", dataType = "Idcard")
    public Result getexaminationtoll( Idcard reqVO) {
        MedicalExamination medicalExamination = tollService.getExaminationToll(reqVO);
        return medicalExamination !=null? Result.success(medicalExamination) : Result.error("添加失败！");
    }

    @PostMapping(value = "/saveexaminationtollinfo")
    @ApiOperation(value = "保存体检收费记录", httpMethod = "POST", notes = "保存体检收费记录")
    public Result saveExaminationTollInfo(Register register) {
        System.out.println(register);
        int i= tollService.saveExaminationTollInfo(register);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:toll:find")
    public Object find(Toll obj) {
        return Result.success(tollService.selectTollList(obj));
    }

    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:toll:page")
    public Object findPage(Toll obj, @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer limit) {
        PageInfo<Toll> pageInfo = tollService.selectTollList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:toll:findById")
    public Object findById(@PathVariable Integer id) {
        Toll toll = tollService.selectTollById(id);
        return Result.success(toll);
    }

    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:toll:add")
    public Object add(@RequestBody Toll obj) {
        int i = tollService.insertToll(obj);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:toll:update")
    public Object update(@RequestBody Toll obj) {
        int i = tollService.updateToll(obj);
        return i > 0 ? Result.success("修改成功！") : Result.error("修改失败！");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:toll:del")
    public Object del(@PathVariable Integer id) {
        int i = tollService.deleteTollById(id);
        return i > 0 ? Result.success("删除成功！") : Result.error("删除失败！");
    }

}
