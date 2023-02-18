package com.ajax.mapper;

import com.ajax.entity.Userinfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

    Userinfo findUser(@Param("username") String username,@Param("pwd") String pwd);
}
