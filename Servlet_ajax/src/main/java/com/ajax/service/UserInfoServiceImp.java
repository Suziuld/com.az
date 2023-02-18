package com.ajax.service;

import com.ajax.entity.Userinfo;
import com.ajax.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImp implements UserInfoService{

    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    public Userinfo login(String username, String pwd) {
        Userinfo user = userInfoMapper.findUser(username, pwd);
        return user;
    }
}
