package com.insigma.common.interceptor;

import java.util.UUID;


/**
 * token������
 * @author wengsh
 *
 */
public class TokenUtil {
	
	/**
	 * ����token
	 * @return
	 */
	public static String createToken(){
		return UUID.randomUUID().toString();
	}
}