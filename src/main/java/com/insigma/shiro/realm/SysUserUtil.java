package com.insigma.shiro.realm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SUser;

/**
 * ϵͳ������
 * @author wengsh
 *
 */
public class SysUserUtil {

	public static final String SHIRO_CURRENT_USER_INFO="SHIRO_CURRENT_USER_INFO";
	public static final String SHIRO_CURRENT_PERM_LIST_INFO="SHIRO_CURRENT_PERM_LIST_INFO";
	
	
	 /** * ���������ThreadLocal��������ͬһ�߳���ͬ������. */
    private static final ThreadLocal SESSION_MAP = new ThreadLocal();

    /** * �������protected���췽��. */
    protected SysUserUtil() {
    }

	 /**
	  *  setCurrentUser
	  * @param suser
	  */
    public static void setCurrentUser(SUser suser) {  
        Map map = (Map) SESSION_MAP.get();
        if (map == null) {
            map = new HashMap();
            SESSION_MAP.set(map);
        }

        map.put(SHIRO_CURRENT_USER_INFO, suser);
    }  
   
  
    /**
     * getCurrentUser
     * @return
     */
    public static SUser getCurrentUser() {  
    	  Map map = (Map) SESSION_MAP.get();
    	  if(map!=null){
              return (SUser)map.get(SHIRO_CURRENT_USER_INFO);
    	  }else{
    		  return null;
    	  }
    }  
    
    /**
     * ���µ�ǰ�û�Ȩ��,ϵͳ�ڶ��û�����Ȩ���޸ĺ����ͨ�����ô˷�ʽ��̬�޸ĵ�ǰ�û�Ȩ��
     */
   public static void updateCurrentUserPerms(){
		Subject subject = SecurityUtils.getSubject(); 
		RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();  
		MyShiroRealm shiroRealm = (MyShiroRealm)rsm.getRealms().iterator().next();  
		String realmName = subject.getPrincipals().getRealmNames().iterator().next(); 
		//��һ������Ϊ�û���,�ڶ�������ΪrealmName
		SimplePrincipalCollection principals = new SimplePrincipalCollection(SysUserUtil.getCurrentUser().getUsername(),realmName); 
		subject.runAs(principals); 
		shiroRealm.getAuthorizationCache().remove(subject.getPrincipals()); 
		subject.releaseRunAs();
   }
   
	/**
    * ���������˵�
    * @param sortlist
    */
   public static List<SPermission> filterPersmList(List< SPermission > permlist){
       List<SPermission> resultlist=new ArrayList<SPermission>();
       List<SPermission> firstTempPermlist=new ArrayList<SPermission>();
       
       //���˵���ť���
       for(int i=0;i<permlist.size();i++) {
           if(permlist.get(i).getType().equals("3")){
           	    permlist.remove(i);
           	    i--;
           }
       }
       
       //�Ƚ���һ�������˳���
       for(int i=0;i<permlist.size();i++) {
           //����ǵ�һ��
           if(permlist.get(i).getParentid().equals("0")){
             	firstTempPermlist.add(permlist.get(i));
           	    permlist.remove(i);
           	    i--;
           }
       }

       //�ٸ��ݵ�һ���ڵ���˳��ڶ������������
       for(int i=0;i<firstTempPermlist.size();i++){
       	   SPermission firstTempPerm=firstTempPermlist.get(i);
           List<SPermission> secondPersmList=new ArrayList<SPermission>();
           for(int j=0;j<permlist.size();j++) {
           	SPermission secondTempPerm=permlist.get(j);
               //�ڶ���
               if(secondTempPerm.getParentid().equals(firstTempPerm.getPermissionid())){
                  List<SPermission> thirdPermList=new ArrayList<SPermission>();
                  for(int k=0;k<permlist.size();k++){
                   //������
                   if(permlist.get(k).getParentid().equals(secondTempPerm.getPermissionid())){
                   		  thirdPermList.add(permlist.get(k));
                       }
                   }
                   secondTempPerm.setChild(thirdPermList);
                   secondPersmList.add(secondTempPerm);
                   permlist.remove(j);
                   j--;
               }
           }
           if(secondPersmList.size()>0){
           	   firstTempPerm.setChild(secondPersmList);
           }
           resultlist.add(firstTempPerm);
       }
       return resultlist;
   }

}
