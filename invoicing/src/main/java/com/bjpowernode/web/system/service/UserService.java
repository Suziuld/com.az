package com.bjpowernode.web.system.service;

import com.bjpowernode.core.common.service.CommonService;
import com.bjpowernode.web.system.pojo.base.TSUser;
/**
 * 
 * @author   
 *
 */
public interface UserService extends CommonService{

	public TSUser checkUserExits(TSUser user);
	public String getUserRole(TSUser user);
	public void pwdInit(TSUser user, String newPwd);
	/**
	 * 判断这个角色是不是还有用户使用
	 *@param id
	 *@return
	 */
	public int getUsersOfThisRole(String id);
}
