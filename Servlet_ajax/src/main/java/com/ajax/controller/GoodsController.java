package com.ajax.controller;

import com.ajax.entity.Goodsinfo;
import com.ajax.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    public GoodsService goodsService;

    @RequestMapping("/find")
    @ResponseBody
    public ModelAndView find(){
        ModelAndView mv = new ModelAndView();
        List<Goodsinfo> goodsinfo = goodsService.findAll();
        System.out.println("goodsinfo = " + goodsinfo);
        mv.addObject("goodsinfo",goodsinfo);
        mv.setViewName("/goods");
        return mv;
    }
}
