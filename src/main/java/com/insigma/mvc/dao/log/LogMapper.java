package com.insigma.mvc.dao.log;

import com.insigma.mvc.model.SErrorLog;
import com.insigma.mvc.model.SLog;



/**
 * 
 * ��־��¼mapper
 * @author wengsh
 *
 */
public interface LogMapper {
	public void saveLogInfo(SLog slog);
	public void saveSysErrorLog(SErrorLog sErrorLog);
}
