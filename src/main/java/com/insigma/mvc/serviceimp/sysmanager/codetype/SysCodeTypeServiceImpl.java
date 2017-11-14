package com.insigma.mvc.serviceimp.sysmanager.codetype;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Element;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.common.util.EhCacheUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;

/**
 * ϵͳ����֮�������
 * @author wengsh
 *
 */
@Service
public class SysCodeTypeServiceImpl extends MvcHelper<CodeValue> implements SysCodeTypeService {

	@Resource
	private SysCodeTypeMapper sysCodeTypeMapper;
	
	
	public List<CodeValue> queryCodeValueByCodeTypeAndParent(CodeValue codevalue){
		return sysCodeTypeMapper.queryCodeValueByCodeTypeAndParent(codevalue);
	}
	
	public HashMap<String,List<CodeValue>> getCodeValueFromCache(CodeValue codevalue){
		Element element = EhCacheUtil.getManager().getCache("webcache").get(codevalue.getCode_type().toUpperCase());
		List<CodeValue> list = (List<CodeValue>) element.getValue();
		HashMap map=new HashMap();
		map.put("value", list);
		return map;
	}


	
	@Override
	public List<CodeValue> getCodeValueTree(CodeValue codevalue) {
		 return sysCodeTypeMapper.getCodeValueTree(codevalue);
	}

	@Override
	public List<CodeType> getInitcodetypeList() {
		// TODO Auto-generated method stub
	   return sysCodeTypeMapper.getInitcodetypeList();
	}

	@Override
	public List<CodeValue> getInitCodeValueList(CodeType codetype) {
		// TODO Auto-generated method stub
		return sysCodeTypeMapper.getInitCodeValueList(codetype);
	}

	@Override
	public List<CodeType> getCodeTypeTreeData(CodeType codetype) {
		return sysCodeTypeMapper.getCodeTypeTreeData(codetype);
	}

	@Override
	public List<CodeType> getCodeValueTreeData(CodeType codetype) {
		//���μ���
		if(codetype.getId()==null&& codetype.getCode_root_value()==null){
			return sysCodeTypeMapper.getCodeValueByType(codetype);
		}else{
			if(codetype.getId()!=null){
				codetype.setCode_root_value(codetype.getId());
			}
			return sysCodeTypeMapper.getCodeValueByTypeAndRoot(codetype);
		}
	}

	@Override
	public CodeType getCodeTypeInfo(String code_type) {
		return sysCodeTypeMapper.getCodeTypeInfo(code_type);
	}

	
	@Override
	public AjaxReturnMsg<String> saveOrUpdateCodeType(CodeType codetype) {
		//����
		if(codetype.getIsupdate() .equals("1")){
			sysCodeTypeMapper.updateCodeType(codetype);
			return this.success_obj("�޸ĳɹ�",codetype);
		}
		//����
		else{
			//�ж�codetype�Ƿ��ظ�
			if(sysCodeTypeMapper.getCodeTypeInfo(codetype.getCode_type())!=null){
                  return this.error("�Ѿ����ڴ�������Ϊ"+codetype.getCode_type()+"�Ĵ���,�����ظ�,��ȷ������");				
			}
			sysCodeTypeMapper.addCodeType(codetype);
			return this.success_obj("�����ɹ�",codetype);
		}
	}
	
	
	@Override
	public CodeValue getCodeTypeDetailInfo(String code_seq) {
		return sysCodeTypeMapper.getCodeTypeDetailInfo(code_seq);
	}
	
	@Override
	public AjaxReturnMsg<String> saveOrUpdateCodeTypeDetail(CodeValue codevalue) {
		//����
		if(codevalue.getCode_seq() .equals("")){
			//�ж�codetype�Ƿ��ظ�
			if(sysCodeTypeMapper.getCodeTypeDetailByValue(codevalue)!=null){
                  return this.error("�Ѿ����ڴ���ֵΪ"+codevalue.getCode_value()+"�Ĵ���,�����ظ�,��ȷ������");				
			}
			
			sysCodeTypeMapper.addCodeTypeDetail(codevalue);
			//���´��뻺��
			setSelectCacheData(codevalue.getCode_type());
			return this.success_obj("�����ɹ�",codevalue);
		}
		//�޸�
		else{
			sysCodeTypeMapper.updateCodeTypeDetail(codevalue);
			//���´��뻺��
			setSelectCacheData(codevalue.getCode_type());
			return this.success_obj("�޸ĳɹ�",codevalue);
		}
	}
	
	
	@Override
	@Transactional
	public AjaxReturnMsg<String> deleteCodeType(String code_type){
		sysCodeTypeMapper.deleteCodeTypeByType(code_type);
		sysCodeTypeMapper.deleteCodeValueByType(code_type);
		//���´��뻺��
		setSelectCacheData(code_type);
		return this.success("ɾ������"+code_type+"�ɹ�");
	}
	
	
	@Override
	public AjaxReturnMsg<String> deleteCodeValue(String code_seq){
		CodeValue codevalue=sysCodeTypeMapper.getCodeTypeDetailInfo(code_seq);
		if(codevalue!=null){
			sysCodeTypeMapper.deleteCodeValueBySeq(code_seq);
			//���´��뻺��
			setSelectCacheData(codevalue.getCode_type());
			return this.success("ɾ��"+codevalue.getCode_type()+"��"+codevalue.getCode_name()+"����ɹ�");
		}else{
			return this.error("ɾ��ʧ��,");
		}
		
	}
	
	/**
	 * �޸Ĵ��뻺��
	 */
	@Override
	public  void setSelectCacheData(String code_type){
		CodeType codetype=new CodeType();
		codetype.setCode_type(code_type);
		List<CodeValue> list_code_value =getInitCodeValueList(codetype);
		if (list_code_value.size() > 0) {
			try{
				//������μӼ��ص�ehcache������
				EhCacheUtil.getManager().getCache("webcache").put(new Element(code_type,list_code_value));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public CodeValue getCodeValueByValue(CodeValue codevalue) {
		return sysCodeTypeMapper.getCodeValueByValue(codevalue);
	}
	
}