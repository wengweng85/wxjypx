package com.insigma.mvc.component.log;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.insigma.mvc.model.SLog;
import com.insigma.mvc.service.log.LogService;

/**
 * ��־��¼��
 * 
 * @author wengsh
 *
 */
@Aspect
@Component
public class SystemLogAspect {

	Log log = LogFactory.getLog(SystemLogAspect.class);
	
	@Resource
	private LogService logservice;

	// Controller���е�
	@Pointcut("execution (* com.insigma.mvc.controller..*.*(..))")
	public void controllerAspect() {
	}

	/**
	 * ǰ��֪ͨ ��������Controller���¼�û��Ĳ���
	 * 
	 * @param joinPoint
	 */
	@Before("controllerAspect()")
	private void doBefore(JoinPoint joinPoint) {
		//log.info("ִ��controllerǰ��֪ͨ");
	}

	/**
	 * ����controller����֪ͨ,ʹ���ڷ���aspect()��ע��������
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	private Object doAround(ProceedingJoinPoint  pjp) throws Throwable {
		long start = System.currentTimeMillis();
        //���ú����߼�  
        Object retVal = pjp.proceed();  
        long end = System.currentTimeMillis();
		log.info(pjp + ",�ܹ�����ʱ��:" + (end - start)+ "����!");
		SLog slog=new SLog();
		slog.setContent(pjp + ",�ܹ�����ʱ��:" + (end - start)+ "����!");
		logservice.saveLogInfo(slog);
		return retVal;  
	}

	/**
	 * ����֪ͨ ��������Controller���¼�û��Ĳ���
	 * 
	 * @param joinPoint
	 */
	@After("controllerAspect()")
	private void doAfter(JoinPoint joinPoint) {
		//log.info("ִ��controller����֪ͨ");
	}

	/**
	 * ����ҵ���߼����������˳��󣬲����Ƿ��з���ֵ�������˳��󣬾�ִ�д�Advice
	 * 
	 * @param joinPoint
	 */
	@AfterReturning("controllerAspect()")
	private void doReturn(JoinPoint joinPoint) {
		//log.info("ִ��controller���÷���֪ͨ");
	}

	/**
	 * �쳣֪ͨ �������ؼ�¼�쳣��־
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	private void doThrowing(JoinPoint joinPoint, Throwable e) {
		log.info("�쳣֪ͨ��ʼ,�쳣����:" + e.getClass().getName()+"�쳣��Ϣ:" + e.getMessage());
	}
}
