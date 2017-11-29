package com.insigma.mvc.dao.init;

import java.util.List;

import com.insigma.mvc.model.Aa01;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;



/**
 * @author wengsh
 *
 */
public interface InitMapper {
	
	List<CodeType> getInitcodetypeList();
	List<CodeValue> getInitCodeValueList(String code_type);
	List<CodeValue> queryCodeValueByCodeTypeAndParent(CodeValue codevalue);
	List<Aa01> getInitParamList();
	List<CodeValue> getCodeValueTree(CodeValue codevalue);
}
