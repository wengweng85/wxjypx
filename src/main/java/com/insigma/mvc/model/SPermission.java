package com.insigma.mvc.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;


/**
 *  Ȩ�ޱ�
 * 
 */
public class SPermission implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**Ȩ�ޱ���*/
	@NotNull(message="Ȩ�ޱ��벻��Ϊ��")
	private String code;
	/**Ȩ������*/
	@NotNull(message="Ȩ�����Ʋ���Ϊ��")
	private String name;
	/**Ȩ������*/
	@NotNull(message="Ȩ����������Ϊ��")
	private String describe;
	/**Ȩ�޵�ַ*/
	@NotNull(message="Ȩ����������Ϊ��")
	private String url;
	/**Ȩ������*/
	@NotNull(message="Ȩ�����Ͳ���Ϊ��")
	private String type;
	
	@NotNull(message="��Ч��־����Ϊ��")
	private String enabled;
	
	private String iconcss;
	
	/**Ȩ�ޱ��*/
	private String permissionid;
	/**Ȩ�ޱ��*/
	private String id;
	/**Ȩ�޸������*/
	private String parentid;
	/**Ȩ�޸��������*/
	private String parentname;
	/**Ȩ�޸������*/
	private String pid;
	/**�Ƿ��*/
	private String open;
	/**�����*/
	private String sortnum;
	
	private Date updatetime;
	
	private List<SPermission> child;
	
	
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public List<SPermission> getChild() {
		return child;
	}
	public void setChild(List<SPermission> child) {
		this.child = child;
	}
	public String getIconcss() {
		return iconcss;
	}
	public void setIconcss(String iconcss) {
		this.iconcss = iconcss;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getSortnum() {
		return sortnum;
	}
	public void setSortnum(String sortnum) {
		this.sortnum = sortnum;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getPermissionid() {
		return permissionid;
	}
	public void setPermissionid(String permissionid) {
		this.permissionid = permissionid;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
