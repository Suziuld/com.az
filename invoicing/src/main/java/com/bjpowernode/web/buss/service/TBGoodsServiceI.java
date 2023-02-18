package com.bjpowernode.web.buss.service;

import java.io.Serializable;

import com.bjpowernode.core.common.service.CommonService;
import com.bjpowernode.web.buss.entity.base.TBGoodsEntity;
import com.bjpowernode.web.buss.entity.base.TBStockEntity;

public interface TBGoodsServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity, TBStockEntity tBStock);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBGoodsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBGoodsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBGoodsEntity t);
}
