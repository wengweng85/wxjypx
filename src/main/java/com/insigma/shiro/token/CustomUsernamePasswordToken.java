package com.insigma.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created on 2014/11/11.
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6730381322353623113L;
	
	//���ڴ洢�û������У����
    private String verifycode;
    
    private String isvercode;



    public CustomUsernamePasswordToken(String loginname, char[] password,boolean rememberMe, String host, String verifycode,String isvercode) {
        //���ø���Ĺ��캯��
        super(loginname,password,rememberMe,host);
        this.verifycode=verifycode;
        this.isvercode=isvercode;
    }

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public String getIsvercode() {
		return isvercode;
	}

	public void setIsvercode(String isvercode) {
		this.isvercode = isvercode;
	}
}
