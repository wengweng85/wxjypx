package com.insigma.mvc.serviceimp.jms.listener.queue;


import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

import com.insigma.mvc.model.SLog;
import com.insigma.mvc.service.log.LogService;

/**
 * ��Ϣ���շ���
 * @author wengsh
 *
 */
@Component
public class JmsQueueMessageListener  implements MessageListener {
	
    @Resource
	private LogService logservice;
     
    /**
     * ������Ϣ
     */
    public void onMessage(Message message) {
        try{
	        // ������ı���Ϣ
	        if (message instanceof TextMessage) {
	            TextMessage tm = (TextMessage) message;
	            SLog slog=new SLog();
	    		slog.setContent("JmsQueueMessageListener_��Ĭ�϶����յ�����Ϣ��\t"+ tm.getText());
	    		logservice.saveLogInfo(slog);
	        }
	
	        // �����Map��Ϣ
	        if (message instanceof MapMessage) {
	            MapMessage mm = (MapMessage) message;
	            System.out.println("��Ĭ�϶����л�ȡMapMessage��\t" + mm.getString("msgId"));
	        }
	
	        // �����Object��Ϣ
	        if (message instanceof ObjectMessage) {
	            ObjectMessage om = (ObjectMessage) message;
	            Object exampleUser = (Object) om.getObject();
	            System.out.println("��Ĭ�϶����л�ȡ ObjectMessage��\t" + ToStringBuilder.reflectionToString(exampleUser));
	        }
	
	        // �����bytes��Ϣ
	        if (message instanceof BytesMessage) {
	            byte[] b = new byte[1024];
	            int len = -1;
	            BytesMessage bm = (BytesMessage) message;
	            while ((len = bm.readBytes(b)) != -1) {
	                System.out.println(new String(b, 0, len));
	            }
	        }
	
	        // �����Stream��Ϣ
	        if (message instanceof StreamMessage) {
	            StreamMessage sm = (StreamMessage) message;
	            System.out.println(sm.readString());
	            System.out.println(sm.readInt());
	        }
        }catch(JMSException e){
        	e.printStackTrace();
        }
    }
}
