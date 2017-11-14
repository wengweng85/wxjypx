package com.insigma.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.insigma.common.util.MD5Util;
import com.insigma.mvc.model.SUser;
import com.insigma.mvc.service.login.LoginService;
import com.insigma.shiro.realm.SysUserUtil;

/**
 * ͨ�õ�¼���session Interceptor������
 * @author wengsh
 * @date 2015-8-17
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

	Log log=LogFactory.getLog(SessionInterceptor.class);

	@Autowired
	private LoginService loginservice;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			request.setAttribute("contextpath", request.getContextPath());
			Subject subject = SecurityUtils.getSubject();  
			if(subject.isAuthenticated()){
				//����ǰ��¼��Ϣ���õ�threadlocal��
				SysUserUtil.setCurrentUser ((SUser)subject.getSession().getAttribute(SysUserUtil.SHIRO_CURRENT_USER_INFO));  
				if(loginservice.findLoginInfoByhashcode(getReqeustHashcode(request))!=null){
					return true;
				}else{
					return false;
				}
			}
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
    
	/**
	 * ��ȡip+usergent+sessionid��hashcode
	 * @param request
	 * @return
	 */
	public String getReqeustHashcode(HttpServletRequest request ){
		String ip=request.getRemoteHost();
		String useragent=request.getHeader("User-Agent");
		String sessionid=request.getSession().getId();
		return MD5Util.MD5Encode(ip+useragent+sessionid);
	}
}
