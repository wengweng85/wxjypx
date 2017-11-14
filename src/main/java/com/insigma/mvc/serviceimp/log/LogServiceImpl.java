package com.insigma.mvc.serviceimp.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.common.util.IPUtil;
import com.insigma.mvc.dao.log.LogMapper;
import com.insigma.mvc.model.SErrorLog;
import com.insigma.mvc.model.SLog;
import com.insigma.mvc.service.log.LogService;

/**
 *
 * @author wengsh
 *
 */

@Service
public class LogServiceImpl implements LogService {

	
	@Resource
	private LogMapper logMapper;
	
	//@Resource
	//private LogDao logdao;

	@Override
	@Transactional
	public String saveLogInfo(SLog slog){
		logMapper.saveLogInfo(slog);
		slog.setContent(slog.getContent()+"2");
		slog.setLogtime(new Date());
		//logdao.save(slog);
		return slog.getLogid();
	}
	
	
	@Override
	public String sysErrorLog(Exception e, HttpServletRequest request) {
		// TODO Auto-generated method stub
		SErrorLog sysLog=new SErrorLog();
        if(e.getMessage()!=null){
        	 sysLog.setMessage(e.getMessage().length()>500?e.getMessage().substring(0,499):e.getMessage()); 
        }
        sysLog.setStackmsg(getStackMsg(e));
        sysLog.setExceptiontype(e.getClass().getName());
        String ip=IPUtil.getClientIpAddr(request);
        /*IPSeekerUtil util=new IPSeekerUtil();*/
        sysLog.setIpaddr(ip);
        /*String country=util.getIpCountry(ip);
        sysLog.setIpaddr(country+"("+ip+")");*/
        sysLog.setUsergent(request.getHeader("user-agent"));
        sysLog.setReferer(request.getHeader("Referer"));
        StringBuffer url=request.getRequestURL();
        if(request.getQueryString()!=null&&!("").equals(request.getQueryString())){
            url.append("?"+request.getQueryString());
        }
        sysLog.setUrl(url.toString());
        String cookie="";
        if(request.getCookies()!=null){
            Cookie[] cookies=request.getCookies();
            for(int i=0;i<cookies.length;i++){
                Cookie tempcookie=cookies[i];
                cookie+=tempcookie.getName()+":"+tempcookie.getValue();
            }
            sysLog.setCookie(cookie.length() >500?cookie.substring(0,499):cookie);
        }
        logMapper.saveSysErrorLog(sysLog);
		return sysLog.getLogid();
	}
	
	
	  /**
     * 将异常打印出来
     * @param e
     * @return
     */
    private static String getStackMsg(Exception e) {
    	if(e!=null){
    		StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        e.printStackTrace(pw);
	        return sw.toString();
    	}else{
    		return "";
    	}
    }

	
}