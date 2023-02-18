package com.az.redisaz.controller;

import com.az.redisaz.pojo.RUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author:fs
 * @Date:2023/2/1322:48
 */
@RestController
public class RedisHandler {
    @Resource
    private RedisTemplate redisTemplate;
    //给redis数据库添加数据
    @PostMapping("/set")
    public void set(@RequestBody RUser rUser){
        redisTemplate.opsForValue().set("rUser", rUser);
    }

    //获取redis数据库中的数据
    @GetMapping("/get/{key}")
    public RUser get(@PathVariable("key") String key){
        return (RUser) redisTemplate.opsForValue().get(key);
    }

    @DeleteMapping("/del/{key}")
    public Object del(@PathVariable("key") String key){
        redisTemplate.delete(key);
       return redisTemplate.hasKey(key);
    }
}
