package cn.hp.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hp.entity.Stock;
import cn.hp.service.IStockService;
import cn.hp.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Controller
 * /api/stock
 */
@RestController
@RequestMapping("/api/stock")
public class StockController
{
    @Autowired
    private IStockService stockService;
    /**
     * 条件查询
     */
    @GetMapping
    @RequiresPermissions("system:stock:find")
    public Object find(Stock obj) {
        return Result.success(stockService.selectStockList(obj));
    }
    /**
     * 条件+分页查询
     */
    @RequestMapping("/page")
    @RequiresPermissions("system:stock:page")
    public Object findPage(Stock obj,@RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "limit",defaultValue = "10")Integer limit) {
        PageInfo<Stock> pageInfo = stockService.selectStockList(obj, page, limit);
        return Result.success(pageInfo);
    }

    /**
     * 查询指定信息
     */
    @GetMapping("/{id}")
    @RequiresPermissions("system:stock:findById")
    public Object findById(@PathVariable Integer id) {
        Stock stock = stockService.selectStockById(id);
        return Result.success(stock);
    }
    /**
     * 添加
     */
    @PostMapping
    @RequiresPermissions("system:stock:add")
    public Object add(Stock obj) {
        int i = stockService.insertStock(obj);
        return i>0?Result.success("添加成功！"):Result.error("添加失败！");
    }

    /**
     * 修改
     */
    @PutMapping
    @RequiresPermissions("system:stock:update")
    public Object update(@RequestBody Stock obj) {
        int i = stockService.updateStock(obj);
        return i>0?Result.success("修改成功！"):Result.error("修改失败！");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:stock:del")
    public Object del(@PathVariable Integer id) {
        int i = stockService.deleteStockById(id);
        return i>0?Result.success("删除成功！"):Result.error("删除失败！");
    }

}
