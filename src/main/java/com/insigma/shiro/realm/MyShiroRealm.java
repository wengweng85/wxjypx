package com.insigma.shiro.realm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.StringUtil;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.mvc.service.login.LoginService;
import com.insigma.shiro.cache.RedisCache;
import com.insigma.shiro.token.CustomUsernamePasswordToken;

public class MyShiroRealm extends AuthorizingRealm  {
	
	
	
	@Autowired
	private LoginService loginservice;
	
	
	@Autowired
    private RedisCache<String, Set<String>> redisCache;
	

	 /**
     * ��֤
     */
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException,UnknownAccountException,LockedAccountException {
		CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authcToken;
		SimpleAuthenticationInfo authenticationInfo=null;
			
			//�Ƿ������֤��
			if(token.getIsvercode().equals("1")){
		        //ȡ���û������У����
		        String userInputValidCode =token.getVerifycode();

		        //ȡ����ʵ����ȷУ����
		        String realRightValidCode = (String) SecurityUtils.getSubject().getSession().getAttribute("session_validator_code");
		        
		        //���У����
		        SecurityUtils.getSubject().getSession().removeAttribute("session_validator_code");
		        
		        if (null == userInputValidCode || !userInputValidCode.equalsIgnoreCase(realRightValidCode)) {
		            throw new AuthenticationException("��֤�벻��ȷ");
		        }
			}
			
			SUser suser = loginservice.getUserAndGroupInfo(token.getUsername());
			if (suser == null) {
	            throw new UnknownAccountException();//û�ҵ��ʺ�
	        }
	
	        if (suser.getEnabled().equals("0") ) {
	            throw new LockedAccountException(); //�ʺ�����
	        }
	    	authenticationInfo = new SimpleAuthenticationInfo(
			suser.getUsername(), 
			suser.getPassword(), 
	        getName() ); //realm name
	    	setSession(SysUserUtil.SHIRO_CURRENT_USER_INFO,suser);
	    	
	    	//�û�Ȩ��
    	
	    	List<SPermission> permlist=SysUserUtil.filterPersmList(loginservice.findPermissionStr(suser.getUsername()));
	    	setSession(SysUserUtil.SHIRO_CURRENT_PERM_LIST_INFO,permlist);
	    	suser.setPermlist(permlist);
	    	
	    	SysUserUtil.setCurrentUser(suser);
	        //������
	    	clearCachedAuthorizationInfo(authenticationInfo.getPrincipals());
	        return authenticationInfo;
	}
	
	
	/**
	 * ��Ȩ
	 */
	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginname = (String) principals.getPrimaryPrincipal();
		try{
			if (StringUtil.isNotEmpty(loginname)) {
	            SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
 	           //�û���ɫ
 	            List<SRole> rolelist=loginservice.findRolesStr(loginname);
 	            if(rolelist!=null){
 	            	Set<String> roleset=new HashSet<String>();
 	 	            Iterator iterator_role=rolelist.iterator();
 	 	            while(iterator_role.hasNext()){
 	 	            	SRole  srole=(SRole) iterator_role.next();
 	 	            	roleset.add(srole.getCode());
 		            }
 	 	           authenticationInfo.setRoles(roleset);
 	            }
 	           
 	            //�û�Ȩ��
	            List<SPermission> permlist=loginservice.findPermissionStr(loginname);
	            if(permlist!=null){
	            	Set<String> set=new HashSet<String>();
	  	            Iterator iterator=permlist.iterator();
	  	            while(iterator.hasNext()){
	  	            	SPermission  spermission=(SPermission) iterator.next();
	  	            	set.add(spermission.getCode());
	  	            }
	   	            authenticationInfo.setStringPermissions(set);
	            }
	            return authenticationInfo;
	        }else{
	            return null;
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        return null;
	}
	
	/**
	 * ��Ȩ����redis,Ȩ����Ϣ�����浽redis��������
	 * @param principals
	 * @return
	 */
	public AuthorizationInfo doGetAuthorizationInfo_rediscache(PrincipalCollection principals) {
		String loginname = (String) principals.getPrimaryPrincipal();
		try{
			if (StringUtil.isNotEmpty(loginname)) {
	            SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
            	 //roles�ӻ���������л�ȡ
	            Set<String> rolesset = redisCache.get(Constants.getUserRolesCacheKey(loginname));
	            if(rolesset!=null){
	            	authenticationInfo.setRoles(rolesset);
	            }else{
	            	//�û���ɫ
	 	            List<SRole> rolelist=loginservice.findRolesStr(loginname);
	 	            if(rolelist!=null){
	 	            	 Set<String> roleset=new HashSet<String>();
	 	 	            Iterator iterator_role=rolelist.iterator();
	 	 	            while(iterator_role.hasNext()){
	 	 	            	SRole  srole=(SRole) iterator_role.next();
	 	 	            	roleset.add(srole.getName());
	 	 	            	roleset.add(srole.getCode());
	 		            }
	 		            authenticationInfo.setRoles(roleset);
	 		            redisCache.put(Constants.getUserRolesCacheKey(loginname), rolesset);
	 	            }
	 	           
	            }
	            //permissions�ӻ���������л�ȡ
	            Set<String> permissionsset = redisCache.get(Constants.getUserPermissionCacheKey(loginname));
	            if(permissionsset!=null){
	            	authenticationInfo.setStringPermissions(permissionsset);
	            }else{
	            	//�û�Ȩ��
		            List<SPermission> permlist=loginservice.findPermissionStr(loginname);
		            if(permlist!=null){
		                Set<String> set=new HashSet<String>();
			            Iterator iterator=permlist.iterator();
			            while(iterator.hasNext()){
			            	SPermission  spermission=(SPermission) iterator.next();
			            	set.add(spermission.getCode());
			            }
		 	            authenticationInfo.setStringPermissions(set);
		 	            redisCache.put(Constants.getUserPermissionCacheKey(loginname), permissionsset);
		            }
	            }
	            return authenticationInfo;
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        return null;
	}
	
	
	
	
	/**
	 * ������
	 * @param principal
	 */
    public void clearCachedAuthorizationInfo(String principal) {
        System.out.println("�����û���Ȩ��Ϣ����");
    	SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        super.clearCachedAuthorizationInfo(principals);
        super.clearCache(principals);
        super.clearCachedAuthenticationInfo(principals);
    }
    
	/**
	 * ������ redis
	 * @param principal
	 */
    public void clearCachedAuthorizationInfo_rediscache(String principal) {
        System.out.println("�����û���Ȩ��Ϣ����");
    	SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        super.clearCachedAuthorizationInfo(principals);
        super.clearCache(principals);
        super.clearCachedAuthenticationInfo(principals);
        redisCache.remove(Constants.getUserPermissionCacheKey(principal));
        redisCache.remove(Constants.getUserRolesCacheKey(principal));
    }

	/** 
     * ��һЩ���ݷŵ�ShiroSession��,�Ա��������ط�ʹ�� 
     * @see  ����Controller,ʹ��ʱֱ����HttpSession.getAttribute(key)�Ϳ���ȡ�� 
     */  
    private void setSession(Object key, Object value){  
        Subject subject = SecurityUtils.getSubject();  
        if(null != subject){  
            Session session = subject.getSession();  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }

}
