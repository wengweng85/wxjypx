package com.insigma.mvc.serviceimp.jms;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.StreamMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.insigma.mvc.service.jms.JmsProducerService;

/**
 * jms��Ϣ����
 * 
 * @author wengsh
 *
 */
//@Service
public class JmsProducerServiceImpl implements JmsProducerService {

	private Log log = LogFactory.getLog(JmsProducerServiceImpl.class);

	//@Resource(name = "jmsQueueTemplate")
	//@Resource(name="jmsTopicTemplate")
    private JmsTemplate jmsTemplate;

	/**
	 * ��Ĭ�϶��з�����Ϣ
	 */
	public void sendMessage(final String msg) {
		String destination = jmsTemplate.getDefaultDestination().toString();
		log.info("�����" + destination + "��������Ϣ------------" + msg);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	/**
	 * ��ָ��Destination����map��Ϣ
	 * 
	 * @param destination
	 * @param message
	 */
	public void sendMapMessage(final String message) {
		String destination = jmsTemplate.getDefaultDestination().toString();
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("msgId", message);
				return mapMessage;
			}
		});
		System.out.println("springJMS send map message...");
	}

	/**
	 * ��ָ��Destination�������л��Ķ���
	 * 
	 * @param destination
	 * @param object object �������л�
	 */
	public void sendObjectMessage(final Serializable object) {
		String destination = jmsTemplate.getDefaultDestination().toString();
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(object);
			}
		});
		System.out.println("springJMS send object message...");
	}

	/**
	 * ��ָ��Destination�����ֽ���Ϣ
	 * 
	 * @param destination
	 * @param bytes
	 */
	public void sendBytesMessage(final byte[] bytes) {
		String destination = jmsTemplate.getDefaultDestination().toString();
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				BytesMessage bytesMessage = session.createBytesMessage();
				bytesMessage.writeBytes(bytes);
				return bytesMessage;

			}
		});
		System.out.println("springJMS send bytes message...");
	}

	/**
	 * ��Ĭ�϶��з���Stream��Ϣ
	 */
	public void sendStreamMessage() {
		String destination = jmsTemplate.getDefaultDestination().toString();
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				StreamMessage message = session.createStreamMessage();
				message.writeString("stream string");
				message.writeInt(11111);
				return message;
			}
		});
		System.out.println("springJMS send Strem message...");
	}
}