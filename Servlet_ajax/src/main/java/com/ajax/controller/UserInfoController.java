package com.ajax.controller;

import com.ajax.entity.Userinfo;
import com.ajax.service.UserInfoService;
import com.ajax.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/login")
    public ModelAndView login(String username, String pwd, HttpSession session){
        Userinfo userinfo = userInfoService.login(username, pwd);
        ModelAndView mv = new ModelAndView();
        session.setAttribute("userinfo",userinfo);
        System.out.println("userinfo = " + userinfo);
        if (userinfo!=null){

            mv.setViewName("forward:/goods/find");
        }else {
            mv.setViewName("/index");
        }
        return mv;
    }
    @RequestMapping("/toindex")
    public String toindex(){
        return "index";
    }
    @RequestMapping("/loginout")
    public String loginout(HttpSession session){
        session.invalidate();
        return "/login2";
    }
}
