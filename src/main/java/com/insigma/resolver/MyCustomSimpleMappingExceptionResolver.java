package com.insigma.resolver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.service.log.LogService;

/**
 * �쳣��Ϣ������
 * @author wengsh
 *
 */
public class MyCustomSimpleMappingExceptionResolver  extends  SimpleMappingExceptionResolver {
	Log log=LogFactory.getLog(MyCustomSimpleMappingExceptionResolver.class);

	@Resource
    LogService logService;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
    	//�Ƿ��ǿ���ģʽ,������ǿ�������ģʽ�Ļ�������ӡ����̨��־
        e.printStackTrace();
        if(!( e instanceof IOException)){
	     	   //������־
	         logService.sysErrorLog(e, request);
	     }
        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(e, request);
        if (viewName != null) {// JSP��ʽ����
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With")!= null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            	// ��������첽����
                // Apply HTTP status code for error views, if specified.
                // Only apply it if we're processing a top-level request.
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                return getModelAndView(viewName, e, request);
            } else {// JSON��ʽ����
                try {
                    PrintWriter writer = response.getWriter();
                    AjaxReturnMsg dto = new AjaxReturnMsg();
                    dto.setSuccess(false);
                    dto.setMessage(e.getMessage());
                    writer.write(JSONObject.fromObject(dto).toString());
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        } else {
              viewName = "error/500";
        	  return getModelAndView(viewName, e, request);
        }
    }

}
