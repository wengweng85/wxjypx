package com.insigma.dto;

/**
 * ajax��Ϣ����
 * @author wengsh
 * @date 2012-9-27
 *
 */
public class AjaxReturnMsg<T> implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean success;//�Ƿ�ɹ�
	private String message;//�����������֮������Ϣ,�ɹ�Ҳ��������ʾ��Ϣ
	private T obj;//����
	private Long total;//��ҳ�����֮����
	
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
