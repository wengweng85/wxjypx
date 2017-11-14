package com.insigma.mvc.service.log;

import javax.servlet.http.HttpServletRequest;

import com.insigma.mvc.model.SLog;


/**
 * 日志服务
 * @author wengsh
 *
 */
public interface LogService  {
	
	public String saveLogInfo(SLog slog);
	
	public String sysErrorLog(Exception e, HttpServletRequest request);

}
