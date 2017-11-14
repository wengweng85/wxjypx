package com.insigma.mvc.model;

import java.util.Date;
import java.util.List;



/**
 *  ”√ªß±Ì
 * 
 */
public class SUser implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String userid;
	private String cnname;
	private String password;
	private String username;
	private String enabled;
	private String groupid;
	private String groupname;
	private String groupparentid;
	private String type;
	private String isgrant;
	private String aab301;
	private String aab301name;
	private String aab998;
	
	private String isvercode;
	private String verifycode;
	
	private List<SPermission> permlist;
	
	private String usertype;
	private Date createdate;
	
	private String token;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String usergroupid;
	
	public List<SPermission> getPermlist() {
		return permlist;
	}
	public void setPermlist(List<SPermission> permlist) {
		this.permlist = permlist;
	}
	public String getIsvercode() {
		return isvercode;
	}
	public void setIsvercode(String isvercode) {
		this.isvercode = isvercode;
	}
	
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupparentid() {
		return groupparentid;
	}
	public void setGroupparentid(String groupparentid) {
		this.groupparentid = groupparentid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsgrant() {
		return isgrant;
	}
	public void setIsgrant(String isgrant) {
		this.isgrant = isgrant;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCnname() {
		return cnname;
	}
	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getAab998() {
		return aab998;
	}
	public void setAab998(String aab998) {
		this.aab998 = aab998;
	}
	public String getAab301() {
		return aab301;
	}
	public void setAab301(String aab301) {
		this.aab301 = aab301;
	}
	public String getAab301name() {
		return aab301name;
	}
	public void setAab301name(String aab301name) {
		this.aab301name = aab301name;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getUsergroupid() {
		return usergroupid;
	}
	public void setUsergroupid(String usergroupid) {
		this.usergroupid = usergroupid;
	}
	
	
}
