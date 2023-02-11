package com.bjpowernode.web.system.service;

import java.util.List;

import com.bjpowernode.core.common.service.CommonService;
import com.bjpowernode.web.system.pojo.base.TSAttachment;

/**
 * 
 * @author   
 *
 */
public interface DeclareService extends CommonService{
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description);
	
}
