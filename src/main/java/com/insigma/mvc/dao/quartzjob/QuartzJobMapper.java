package com.insigma.mvc.dao.quartzjob;


import java.util.List;

import com.insigma.mvc.model.QrtzTrigger;


/**
 * ��¼service�ӿ�
 * @author wengsh
 *
 */
public interface QuartzJobMapper {
	
	public List<QrtzTrigger> queryJobList( QrtzTrigger qrtztrigger);
	

}
