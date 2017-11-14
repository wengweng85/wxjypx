package com.insigma.mvc.service.init;

import java.util.HashMap;
import java.util.List;

import com.insigma.mvc.model.Aa01;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;


/**
 * ��ҳservice
 * @author wengsh
 *
 */
public interface InitService {
	
	public List<CodeType> getInitcodetypeList();
	public List<CodeValue> getInitCodeValueList(String code_type);
	public  List<CodeValue> queryCodeValueByCodeTypeAndParent(CodeValue codevalue);
	public HashMap getCodeValueFromCache(CodeValue codevalue);
	public List<Aa01> getInitParamList();
	public Aa01 getInitParamById(String aaa001);
	public List<CodeValue> getCodeValueTree(CodeValue codevalue);
	
}
