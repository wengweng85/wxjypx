package com.insigma.mvc.service.jms;

import java.io.Serializable;

/**
 * jms��Ϣ����
 * @author wengsh
 *
 */
public interface JmsProducerService {
       
    /**
     * ��Ĭ�϶��з�����Ϣ
     */
      public void sendMessage(final String msg) ;
      
      /**
  	 * ��ָ��Destination����map��Ϣ
  	 * 
  	 * @param destination
  	 * @param message
  	 */
  	public void sendMapMessage(final String message) ;

  	/**
  	 * ��ָ��Destination�������л��Ķ���
  	 * 
  	 * @param destination
  	 * @param object object �������л�
  	 */
  	public void sendObjectMessage(final Serializable object);

  	/**
  	 * ��ָ��Destination�����ֽ���Ϣ
  	 * 
  	 * @param destination
  	 * @param bytes
  	 */
  	public void sendBytesMessage(final byte[] bytes) ;

  	/**
  	 * ��Ĭ�϶��з���Stream��Ϣ
  	 */
  	public void sendStreamMessage() ;
}