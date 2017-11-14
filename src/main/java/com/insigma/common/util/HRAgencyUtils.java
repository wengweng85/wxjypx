package com.insigma.common.util;

import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeValue;


public class HRAgencyUtils {

	private static SysCodeTypeMapper sysCodeTypeMapper;
	// ���״̬
	/**
	 * δ���
	 */
	public static final String EAE052_0 = "0";
	/**
	 * ���ͨ��
	 */
	public static final String EAE052_1 = "1";
	/**
	 * ��˲�ͨ��
	 */
	public static final String EAE052_9 = "9";

	// ��˼���1�������2������3��������
	/**
	 * ����״̬
	 */
	public static final String AEF133_1 = "1";
	/**
	 * ����
	 */
	public static final String AEF133_2 = "2";
	/**
	 * ����
	 */
	public static final String AEF133_3 = "3";
	// ��Ч��־
	/**
	 * ��Ч
	 */
	public static final String AAE100_1 = "1";
	/**
	 * ��Ч
	 */
	public static final String AAE100_0 = "0";
	// ����ҵ����״̬


	public static final String AEF132_1 = "1";
	/**
	 * �������
	 */
	public static final String AEF132_2 = "2";
	/**
	 * ����ҵ�񱨸�
	 */
	public static final String AEF132_3 = "3";
	/**
	 * ����ע��
	 */
	public static final String AEF132_4 = "4";
	/**
	 * �����걨����-�����
	 */
	public static final String AEF135_0 = "0";
	/**
	 * �����걨����-����ͨ����������
	 */
	public static final String AEF135_1 = "1";
	/**
	 * �����걨����-����ͨ��
	 */
	public static final String AEF135_2 = "2";
	/**
	 * �����걨����-����ͨ����������
	 */
	public static final String AEF135_3 = "3";
	/**
	 * �����걨����-����ͨ��
	 */
	public static final String AEF135_4 = "4";
	/**
	 * �����걨����-����ͨ��
	 */
	public static final String AEF135_5 = "5";
	/**
	 * �����걨����-����ͨ��
	 */
	public static final String AEF135_6 = "6";
	//�Ƿ�ظ�
	/**
	  * ���δ�ظ�
	  */
	 public static final String AEE152_0 ="0";
	 /**
	  * ����ѻظ�
	  */
	 public static final String AEE152_1 ="1";
	// ����״̬
	/**
	 * δ����
	 */
	public static final String AEE115_0 = "0";
	/**
	 * ������
	 */
	public static final String AEE115_1 = "1";
	
	/**
	 * �����걨ҵ���ɫID
	 */
	public static final String ROLEID = "6A429C4813BF43FB8B923A9545C429BD";
	/**
	 * �û���������Ĭ��ֵ123456
	 */
	public static final String RESET_PWD_DEFAULT = "123456";
	
	/**
	 * ����������Ա��Ϣ��Ч״̬
	 */
	/**
	 * ��Ч
	 */
	public static final String AEC100_0 = "0";
	
	/**
	 * ��Ч
	 */
	public static final String AEC100_1 = "1";
	
	/**
	 * ����code_Type��ȡcode_name
	 */
	public static String getCodeName(String code_type,String code_value) {
    	CodeValue codevalue = new CodeValue();
    	codevalue.setCode_type(code_type);
		codevalue.setCode_value(code_value);
    	CodeValue  codeValueResult = sysCodeTypeMapper.getCodeValueByValue(codevalue);
		return codeValueResult.getCode_name();
    }

}
