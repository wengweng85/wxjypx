package com.insigma.mvc.service.quartzjob;


import java.util.HashMap;

import org.quartz.SchedulerException;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.QrtzTrigger;


/**
 * ��¼service�ӿ�
 * @author wengsh
 *
 */
public interface QuartzJobService {
	
	public HashMap<String,Object> queryJobList( QrtzTrigger qrtztrigger);
	public AjaxReturnMsg addJob(QrtzTrigger qrtzTrigger) throws SchedulerException;
	public AjaxReturnMsg deleteJob(String jobName) throws SchedulerException;
	public AjaxReturnMsg batchdeleteJob(QrtzTrigger qrtzTrigger) throws SchedulerException;
	public AjaxReturnMsg pauseJob(String jobName) throws SchedulerException;
	public AjaxReturnMsg resumeJob(String jobName) throws SchedulerException;

}
