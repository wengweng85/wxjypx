package com.insigma.mvc.component.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
public class ParseModelJob implements Job {


/**
 * ��ʱ����
 */
@Override
public void execute(JobExecutionContext context) throws JobExecutionException {
      System.out.println("��ǰʱ��"+new Date().toLocaleString());
}

}
