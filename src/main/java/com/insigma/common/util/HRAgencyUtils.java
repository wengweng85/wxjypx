package com.insigma.common.util;

import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeValue;


public class HRAgencyUtils {

	private static SysCodeTypeMapper sysCodeTypeMapper;
	// 审核状态
	/**
	 * 未审核
	 */
	public static final String EAE052_0 = "0";
	/**
	 * 审核通过
	 */
	public static final String EAE052_1 = "1";
	/**
	 * 审核不通过
	 */
	public static final String EAE052_9 = "9";

	// 审核级别，1代表初审，2代表复审，3代表终审。
	/**
	 * 初审状态
	 */
	public static final String AEF133_1 = "1";
	/**
	 * 复审
	 */
	public static final String AEF133_2 = "2";
	/**
	 * 终审
	 */
	public static final String AEF133_3 = "3";
	// 有效标志
	/**
	 * 有效
	 */
	public static final String AAE100_1 = "1";
	/**
	 * 无效
	 */
	public static final String AAE100_0 = "0";
	// 机构业务变更状态


	public static final String AEF132_1 = "1";
	/**
	 * 机构变更
	 */
	public static final String AEF132_2 = "2";
	/**
	 * 机构业务报告
	 */
	public static final String AEF132_3 = "3";
	/**
	 * 机构注销
	 */
	public static final String AEF132_4 = "4";
	/**
	 * 网上申报进度-待审核
	 */
	public static final String AEF135_0 = "0";
	/**
	 * 网上申报进度-初审通过，待复审
	 */
	public static final String AEF135_1 = "1";
	/**
	 * 网上申报进度-初审不通过
	 */
	public static final String AEF135_2 = "2";
	/**
	 * 网上申报进度-复审通过，待终审
	 */
	public static final String AEF135_3 = "3";
	/**
	 * 网上申报进度-复审不通过
	 */
	public static final String AEF135_4 = "4";
	/**
	 * 网上申报进度-终审通过
	 */
	public static final String AEF135_5 = "5";
	/**
	 * 网上申报进度-终审不通过
	 */
	public static final String AEF135_6 = "6";
	//是否回复
	/**
	  * 意见未回复
	  */
	 public static final String AEE152_0 ="0";
	 /**
	  * 意见已回复
	  */
	 public static final String AEE152_1 ="1";
	// 受理状态
	/**
	 * 未受理
	 */
	public static final String AEE115_0 = "0";
	/**
	 * 已受理
	 */
	public static final String AEE115_1 = "1";
	
	/**
	 * 网上申报业务角色ID
	 */
	public static final String ROLEID = "6A429C4813BF43FB8B923A9545C429BD";
	/**
	 * 用户密码重置默认值123456
	 */
	public static final String RESET_PWD_DEFAULT = "123456";
	
	/**
	 * 机构工作人员信息有效状态
	 */
	/**
	 * 无效
	 */
	public static final String AEC100_0 = "0";
	
	/**
	 * 有效
	 */
	public static final String AEC100_1 = "1";
	
	/**
	 * 根据code_Type获取code_name
	 */
	public static String getCodeName(String code_type,String code_value) {
    	CodeValue codevalue = new CodeValue();
    	codevalue.setCode_type(code_type);
		codevalue.setCode_value(code_value);
    	CodeValue  codeValueResult = sysCodeTypeMapper.getCodeValueByValue(codevalue);
		return codeValueResult.getCode_name();
    }

}
