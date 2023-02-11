package cn.hp.controller;

import java.util.List;

import cn.hp.entity.Idcard;
import cn.hp.entity.Patient;
import cn.hp.service.IPatientService;
import cn.hp.util.card.Card;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.Register;
import cn.hp.service.IRegisterService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 挂号记录 Controller
 * /api/register
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    private IRegisterService registerService;
    @Autowired
    private IPatientService patientService;


    /**
     * 读取就诊卡
     *
     * @return
     */
    @PostMapping(value = "/getCardIdInfor")
    public Object getCardIdInfor(@RequestBody Patient patient) throws Exception {
        return Result.success(registerService.getCardIdInfor(patient));
    }

    /**
     * 通过读卡器：读取新卡
     *
     * @return
     */
    @PostMapping(value = "/getDefaultGetCardId")
    public Object getDefaultGetCardId() {
        String s = Card.defaultGetCardId();
        return Result.success(s, null);
    }

    /**
     * 通过读卡器：读取身份证
     *
     * @return
     */
    @PostMapping(value = "/getIDcardInfor")
    public Object getIDcardInfor() {
        return Result.success(registerService.getIDcardInfor());
    }

    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:register:find")
    public Object find(Register obj) {
        return Result.success(registerService.selectRegisterList(obj));
    }

    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:register:page")
    public Object findPage(Register obj, @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer limit) {
        PageInfo<Register> pageInfo = registerService.selectRegisterList(obj, page, limit);
        return Result.RowsSuccess(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:register:findById")
    public Object findById(@PathVariable Integer id) {
        Register register = registerService.selectRegisterById(id);
        return Result.success(register);
    }


    /**
     * 办就诊卡,同步录入身份证信息和患者信息
     */
    @PostMapping("/addPatientInfor")
    @RequiresPermissions("system:register:addPatientInfor")
    public Object addPatientInfor(@RequestBody Patient obj) {
        Integer i = patientService.insertPatient(obj);
        return i != null ? Result.success(i, "添加成功！") : Result.error("添加失败！");
    }

    /**
     * 补办就诊卡
     *
     * @return
     */
    @PostMapping(value = "/coverCardId")
    public Object coverCardId( Patient obj) {
        Integer i = patientService.coverCardId(obj);
        return i != null ? Result.success("补办就诊卡成功！") : Result.error("补办失败！");
    }

    /**
     * 获取医生（医生信息通过挂号表返回数据）
     *
     * @return
     */
    @GetMapping(value = "/getAllRegisterDoctor")
    public Object getAllRegisterDoctor(Register obj) {
        return registerService.getAllRegisterDoctor(obj);
    }

    /**
     * 提交挂号信息
     * @return
     */
    @PostMapping(value = "/addRegisterInfor")
    @RequiresPermissions("system:register:add")
    public Object addRegisterInfor(@RequestBody Register obj) {
        int i = registerService.addRegisterInfor(obj);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:register:add")
    public Object add(@RequestBody Register obj) {
        int i = registerService.insertRegister(obj);
        return i > 0 ? Result.success("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:register:update")
    public Object update(@RequestBody Register obj) {
        int i = registerService.updateRegister(obj);
        return i > 0 ? Result.success("修改成功！") : Result.error("修改失败！");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:register:del")
    public Object del(@PathVariable Integer id) {
        int i = registerService.deleteRegisterById(id);
        return i > 0 ? Result.success("删除成功！") : Result.error("删除失败！");
    }

}
