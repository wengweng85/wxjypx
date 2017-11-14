package com.insigma.mvc.service.sysmanager.perm;

import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.SPermission;




/**
 * ������֮Ȩ�޹���service
 * @author wengsh
 *
 */
public interface SysPermService {
	
	public List<SPermission>  getPermTreeList();
	
	public AjaxReturnMsg getPermDataById(String id);
	
	public AjaxReturnMsg saveOrUpdatePermData(SPermission spermission);
	
    public AjaxReturnMsg getPermListDataByParentid(String parentid);
	
	public AjaxReturnMsg deletePermDataById(String id);

}
