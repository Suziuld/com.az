package com.ajax.service;

import com.ajax.entity.Goodsinfo;
import com.ajax.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImp implements GoodsService{
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goodsinfo> findAll() {
        return goodsMapper.findAll();
    }
}
