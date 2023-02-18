package com.ajax.service;

import com.ajax.entity.Userinfo;

public interface UserInfoService {
    Userinfo login(String username,String pwd);
}
