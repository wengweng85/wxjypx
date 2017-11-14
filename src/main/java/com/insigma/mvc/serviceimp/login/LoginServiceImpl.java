package com.insigma.mvc.serviceimp.login;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.insigma.mvc.dao.login.LoginMapper;
import com.insigma.mvc.model.LoginInf;
import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.mvc.service.login.LoginService;


/**
 * ��¼service�ӿ�
 * @author wengsh
 *
 */

@Service
public class LoginServiceImpl implements LoginService {

	//��¼dao
	@Resource
	private LoginMapper loginmapper;
	
	@Override
	public SUser getUserAndGroupInfo(String loginname)  {
		SUser suser=loginmapper.getUserAndGroupInfo(loginname);
		if(suser==null){
			return null;
		}else if(suser.getAab998()==null){
			List<SGroup> list=loginmapper.getGroupAreaInfo(suser.getGroupid());
			for (SGroup sgroup : list) {
				Object type =sgroup.getType();
				if("1".equals(type)){//��ǰ����Ϊ��������
					suser.setAab301(sgroup.getGroupid());
					suser.setAab301name(sgroup.getName());
				    break;
				}
			}
			return suser;
		}else {
			return suser;
		}
		
	}

	@Override
	public List<SRole> findRolesStr(String loginname)  {
		List<SRole> list= loginmapper.findRolesStr(loginname);
		return list;
	}

	@Override
	public List<SPermission>  findPermissionStr(String loginname)  {
		List<SPermission> list=loginmapper.findPermissionStr(loginname);
		return list;
	}

	@Override
	public void saveLoginHashInfo(LoginInf inf) {
		loginmapper.saveLoginHashInfo(inf);
	}

	@Override
	public List<LoginInf> findLoginInfoByhashcode(String loginhash) {
		return loginmapper.findLoginInfoByhashcode(loginhash);
	}


}
