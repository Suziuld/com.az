package com.bjpowernode.core.common.dao;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.core.common.model.common.UploadFile;
import com.bjpowernode.core.common.model.json.ComboTree;
import com.bjpowernode.core.common.model.json.ImportFile;
import com.bjpowernode.core.common.model.json.TreeGrid;
import com.bjpowernode.core.extend.template.Template;
import com.bjpowernode.tag.vo.easyui.ComboTreeModel;
import com.bjpowernode.tag.vo.easyui.TreeGridModel;
import com.bjpowernode.web.system.pojo.base.TSDepart;
import com.bjpowernode.web.system.pojo.base.TSUser;

public interface ICommonDao extends IGenericBaseCommonDao{
	
	
	/**
	 * admin账户密码初始化
	 * @param user
	 */
	public void pwdInit(TSUser user,String newPwd);
	/**
	 * 检查用户是否存在
	 * */
	public TSUser getUserByUserIdAndUserNameExits(TSUser user);
	public String getUserRole(TSUser user);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	public Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	public HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);
	public List<ComboTree> comTree(List<TSDepart> all,ComboTree comboTree);
	/**
	 * 根据模型生成JSON
	 * @param all 全部对象
	 * @param in  已拥有的对象
	 * @param comboBox 模型
	 * @return
	 */
	public  List<ComboTree> ComboTree(List all,ComboTreeModel comboTreeModel,List in);
	public  List<TreeGrid> treegrid(List all,TreeGridModel treeGridModel);
}

