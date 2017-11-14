package com.insigma.mvc.dao.login;


import java.util.List;

import com.insigma.mvc.model.LoginInf;
import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.resolver.AppException;


/**
 * ��¼service�ӿ�
 * @author wengsh
 *
 */
public interface LoginMapper {
	/**
	 * ͨ���û�����ȡ��Ա����Ϣ
	 * @param username
	 * @return �û���
	 * @ 
	 */
	public SUser getUserAndGroupInfo(String loginname)  ;
	
	
	/**
	 * ��ȡ��ǰ����������������Ϣ
	 * @param groupid
	 * @return
	 * @
	 */
	public List<SGroup> getGroupAreaInfo(String groupid)   ;
	
	
	/**
	 * ͨ���û�id��ȡ�û���ɫ����
	 * @param userid
	 * @return ��ɫ����
	 * @ 
	 */
	public List<SRole> findRolesStr(String loginname) ;
	
	
	/**
	 * ͨ���û�id��ȡ�û�Ȩ�޼���
	 * @param userid
	 * @return Ȩ�޼���
	 * @ 
	 */
	public List<SPermission> findPermissionStr(String loginname) ;
	
	
	/**
	 * ����hashinfo
	 * @param hashinfo
	 */
	public void saveLoginHashInfo(LoginInf inf);
	
	
	public List<LoginInf> findLoginInfoByhashcode(String loginhash);

}
