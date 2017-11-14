package com.insigma.mvc.serviceimp.quartzjob;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.quartzjob.QuartzJobMapper;
import com.insigma.mvc.model.QrtzTrigger;
import com.insigma.mvc.service.quartzjob.QuartzJobService;



/**
 * quartz�������
 * @author wengsh
 *
 */
@Service
public class QuartzJobServiceImpl   extends MvcHelper implements QuartzJobService {

	@Resource
	private QuartzJobMapper quartzJobMapper;
	
	@Autowired
	private Scheduler quartzScheduler;

	
	
	@Override
	public HashMap<String,Object> queryJobList( QrtzTrigger qrtztrigger) {
		PageHelper.offsetPage(qrtztrigger.getOffset(), qrtztrigger.getLimit());
		List<QrtzTrigger> list =quartzJobMapper.queryJobList(qrtztrigger);
		PageInfo<QrtzTrigger> pageinfo = new PageInfo<QrtzTrigger>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	

	/**
	 * ��������
	 */
	@Override
	@Transactional
	public AjaxReturnMsg addJob(QrtzTrigger qrtzTrigger) throws SchedulerException {
		//��ʼ��JobDetail
		JobDataMap dataMap = new JobDataMap();
		Class jobclass=null;
		try{
			jobclass=Class.forName(qrtzTrigger.getJob_class_name());
		}catch(ClassNotFoundException e){
			throw new SchedulerException("�Ҳ�������Ϊ"+qrtzTrigger.getJob_class_name()+"����,��ȷ�������Ƽ������Ƿ���ȷ");
		}
		
		String uuid=UUID.randomUUID().toString();
		//�ж��Դ������������Ƿ����
		JobKey jobkey=new JobKey(uuid+"_job", Scheduler.DEFAULT_GROUP);
		JobDetail jobDetail=quartzScheduler.getJobDetail(jobkey);
		if(jobDetail!=null){
			throw new SchedulerException("�Ѿ���������Ϊ"+qrtzTrigger.getJob_name()+"������,�뻻һ����������");
		}
		
		//JobDetail
		jobDetail = JobBuilder.newJob(jobclass)
		.withIdentity(jobkey).withDescription(qrtzTrigger.getDescription())
		.usingJobData(dataMap).build();
		
		
		//��ʼ��CronTrigger
		CronTrigger trigger = TriggerBuilder
		.newTrigger()
		.withIdentity(uuid + "_trigger", Scheduler.DEFAULT_GROUP)
		.forJob(jobDetail)
		.withSchedule(CronScheduleBuilder.cronSchedule(qrtzTrigger.getCron_expression()))
		.build();
		
		//scheduleJob
		quartzScheduler.scheduleJob(jobDetail, trigger);
		
		return this.success("��������ɹ�");
	}
	
	/**
	 * ɾ������
	 */
	@Override
	@Transactional
	public AjaxReturnMsg deleteJob(String job_name) throws SchedulerException {
		quartzScheduler.deleteJob(new JobKey(job_name, Scheduler.DEFAULT_GROUP));
		return this.success("ɾ���ɹ�");
	}
	
	/**
	 * ��ͣ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg pauseJob(String job_name) throws SchedulerException {
		quartzScheduler.pauseJob(new JobKey(job_name, Scheduler.DEFAULT_GROUP));
		return this.success("��ͣ�ɹ�");
	}
	
	
	/**
	 * �ָ�����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg resumeJob(String job_name) throws SchedulerException {
		quartzScheduler.resumeJob(new JobKey(job_name, Scheduler.DEFAULT_GROUP));
		return this.success("�ָ��ɹ�");
	}

	/**
	 * ����ɾ��
	 */
	@Override
	public AjaxReturnMsg batchdeleteJob(QrtzTrigger qrtzTrigger) throws SchedulerException {
		// TODO Auto-generated method stub
		String[] ids=qrtzTrigger.getIds().split(",");
		for (int i=0;i<ids.length;i++){
			quartzScheduler.deleteJob(new JobKey(ids[i], Scheduler.DEFAULT_GROUP));
		}
		return this.success("����ɾ���ɹ�");
	}
	
}
