package com.insigma.mvc.serviceimp.sysmanager.role;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.sysmanager.role.SysRoleMapper;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.service.sysmanager.role.SysRoleService;
import com.mysql.jdbc.StringUtils;


/**
 * ������֮��ɫ����service impl 
 * @author wengsh
 *
 */

@Service
public class SysRoleServicelmpl extends MvcHelper  implements SysRoleService {

	@Resource
	private SysRoleMapper sysRoleMapper;

	/**
	 * ��ȡ���н�ɫ����
	 */
	@Override
	public HashMap<String,Object> getAllRoleList( SRole srole) {
		PageHelper.offsetPage(srole.getOffset(), srole.getLimit());
		List<SRole> list=sysRoleMapper.getAllRoleList();
		PageInfo<SRole> pageinfo = new PageInfo<SRole>(list);
		return this.success_hashmap_response (pageinfo);
	}
 
	/**
	 * ͨ����ɫid��ȡ��ɫ����
	 */
	@Override
	public AjaxReturnMsg getRoleDataById(String id) {
		return this.success(sysRoleMapper.getRoleDataById(id));
	}

	/**
	 * �������½�ɫ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveOrUpdateRoleData(SRole srole) {
		SRole ispermsionexist=sysRoleMapper.isRoleCodeExist(srole);
		if(ispermsionexist!=null){
			   return this.error("�˽�ɫ"+srole.getCode()+"����Ѿ�����,����������һ���µĽ�ɫ���");
		}else{
			//�ж��Ƿ���²���
			if(StringUtils.isNullOrEmpty(srole.getRoleid())){
				sysRoleMapper.saveRoleData(srole);
				 return this.success("�����ɹ�");
			}else{
				sysRoleMapper.updateRoleData(srole);
				return this.success("���³ɹ�");
			}
		}
	}

	/**
	 * ͨ����ɫidɾ����ɫ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg deleteRoleDataById(String id) {
		if(sysRoleMapper.isRoleUsedbyUser(id)!=null){
			return this.error("��ǰ��ɫ�Ѿ����û���ʹ�ã�������ɾ��,��ȷ��");
		}else{
			sysRoleMapper.deleteRoleDataById(id);
			sysRoleMapper.deleteRolePermbyRoleid(id);
			return this.success("ɾ���ɹ�");
		}
	}
	

	/**
	 * ��ȡ��ɫȨ����
	 */
	@Override
	public List<SRole> getRolePermTreeData(String roleid) {
		return sysRoleMapper.getRolePermTreeData(roleid);
	}

	/**
	 * �����ɫȨ��������
	 */
	@Transactional
	@Override
	public AjaxReturnMsg saveRolePermData(SRole srole) {
		//��ɾ����ɫ��Ӧ��ʷ����
		sysRoleMapper.deleteRolePermbyRoleid(srole.getRoleid());
		String[] selectnodes= srole.getSelectnodes().split(",");
		for(int i=0;i<selectnodes.length;i++){
			SRole temp=new SRole();
			temp.setRoleid(srole.getRoleid());
			temp.setPermissionid(selectnodes[i]);
			sysRoleMapper.saveRolePermData(temp);
		}
		return this.success("��ɫ����Ȩ�޳ɹ�");
	}
}
