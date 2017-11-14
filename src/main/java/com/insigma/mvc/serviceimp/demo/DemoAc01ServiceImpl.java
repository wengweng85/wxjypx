package com.insigma.mvc.serviceimp.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.demo.DemoAc01Mapper;
import com.insigma.mvc.model.DemoAc01;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.demo.DemoAc01Service;
import com.insigma.shiro.realm.SysUserUtil;


/**
 * demo ac01 service impl
 * @author wengsh
 *
 */

@Service
public class DemoAc01ServiceImpl extends MvcHelper<DemoAc01> implements DemoAc01Service {

	@Resource
	private DemoAc01Mapper demoDemoAc01Mapper;
	
	@Resource
	private FileLoadService fileLoadService;
	
	/**
	 * ��ҳ��ѯ
	 */
	
	@Override
	public HashMap<String, Object> getDemoAc01List(DemoAc01 ac01) {
		System.out.println(SysUserUtil.getCurrentUser().getUserid());
		System.out.println(SysUserUtil.getCurrentUser().getUserid());
		System.out.println(SysUserUtil.getCurrentUser().getUserid());
		System.out.println(SysUserUtil.getCurrentUser().getUserid());
		PageHelper.offsetPage(ac01.getOffset(), ac01.getLimit());
		if(StringUtils.isNotEmpty(ac01.getAac011())){
			ac01.setA_aac011(ac01.getAac011().split(","));
		}
		List<DemoAc01> list=demoDemoAc01Mapper.getDemoAc01List(ac01);
		PageInfo<DemoAc01> pageinfo = new PageInfo<DemoAc01>(list);
		return this.success_hashmap_response(pageinfo);
	}

	/**
	 * ͨ��idɾ��demo����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg<String> deleteDemoById(String aac001) {
		int deletenum=demoDemoAc01Mapper.deleteByPrimaryKey(aac001);
		if(deletenum==1){
			return this.success("ɾ���ɹ�");
		}else{
			return this.error("ɾ��ʧ��,��ȷ�������Ѿ���ɾ����");
		}
	
	}

	
	/**
	 * ����ɾ��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg<String> batDeleteDemoData(DemoAc01 ac01) {
		String [] ids=ac01.getSelectnodes().split(",");
		int batdeletenum=demoDemoAc01Mapper.batDeleteData(ids);
		if(batdeletenum==ids.length){
			return this.success("����ɾ���ɹ�");
		}else{
			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
		}
	}

	/**
	 * ͨ�����˱�Ż�ȡ��Ϣ
	 */
	@Override
	public DemoAc01 getDemoById(String aac001) {
		return demoDemoAc01Mapper.selectByPrimaryKey(aac001);
	}

	/**
	 * ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg<String> saveDemoData(DemoAc01 ac01) {
		ac01.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
		ac01.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
		ac01.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ac01.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
		ac01.setAae036(new Date());//����ʱ��
		//�ж����֤�����Ƿ��ظ�
		int aac002num=demoDemoAc01Mapper.selectByAac002(ac01);
		if(aac002num>0){
			return this.error("�����֤����"+ac01.getAac002()+"�Ѿ����ڣ������ظ�,��������ȷ�ĺ���");
		}
				
		//�ж��Ƿ��Ǹ���
		if(StringUtils.isEmpty(ac01.getAac001())){
			int insertnum= demoDemoAc01Mapper.insertSelective (ac01);
			if(insertnum==1){
				return this.success("�����ɹ�");
			}else{
				return this.error("����ʧ��,��ȷ�ϴ����Ѿ���ɾ��");
			}
		}else{
			int updatenum=demoDemoAc01Mapper.updateByPrimaryKey(ac01);
			//���¸��˸����ļ���Ϣ
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("file_bus_id", ac01.getAac001());
			if(ac01.getSelectnodes()!=null){
				map.put("bus_uuids",ac01.getSelectnodes().split(","));
				fileLoadService.batupdateBusIdByBusUuidArray(map);
			}
			if(updatenum==1){
				return this.success("���³ɹ�");
			}else{
				return this.error("����ʧ��,��ȷ�ϴ����Ѿ���ɾ��");
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public DemoAc01 getDemoNameById(String aac001) {
		return demoDemoAc01Mapper.selectNameByPrimaryKey(aac001);
	}

	
	/**
	 * ͨ��ҵ��id���ļ�id����
	 */
	@Override
	public AjaxReturnMsg<String> updateDemoAc01DemBusUuid(DemoAc01 ac01) {
		int updatenum= demoDemoAc01Mapper.updateDemoAc01DemBusUuid(ac01);
		if(updatenum==1){
			return this.success("���³ɹ�");
		}else{
			return this.error("����ʧ��");
		}
	}

	
	/**
	 * ͨ��ҵ��id���ļ�id�ϴ��ļ���¼
	 */
	@Override
	public AjaxReturnMsg<String> deletefile(DemoAc01 ac01) {
		fileLoadService.deleteFileByBusUuid(ac01.getBus_uuid());		
		ac01.setBus_uuid("");
		int updatenum=demoDemoAc01Mapper.updateDemoAc01DemBusUuid(ac01);
		if(updatenum==1){
			return this.success("ɾ���ɹ�");
		}else{
			return this.error("ɾ��ʧ��");
		}
	}

}
