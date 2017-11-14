package com.insigma.http;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.github.pagehelper.PageInfo;
import com.insigma.dto.SysCode;
import com.insigma.json.JsonDateValueProcessor;
import com.insigma.resolver.AppException;
import com.insigma.shiro.realm.SysUserUtil;


/**
 * httprequest������
 *
 * @param <T>
 * @author wengsh
 */
public class HttpRequestUtils<T> {


    private Log log = LogFactory.getLog(HttpRequestUtils.class);    //��־��¼

    public JsonConfig jsonConfig;
    private String appkey;
    private String base_api_url;

    public HttpRequestUtils(String appkey, String base_api_url) {
        jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        this.appkey = appkey;
        this.base_api_url = base_api_url;
    }

    public HttpRequestUtils(String appkey) {
        jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        this.appkey = appkey;
        this.base_api_url = "";
    }

    /**
     * ����ת����jsonobject
     *
     * @param t
     * @return
     */
    private JSONObject toJsonObject(T t) {
        JSONObject jsonParam = JSONObject.fromObject(t, jsonConfig);
        return jsonParam;
    }

    /**
     * jsonobjectת���ɶ���
     *
     * @param t
     * @return
     */
    private T tobean(JSONObject jsonobject, T t) {
        return (T) JSONObject.toBean(jsonobject, t.getClass());
    }

    /**
     * jsonobjectת���ɶ�������
     *
     * @param t
     * @return
     */
    public List<T> toList(JSONArray jsonarray, T t) {
        return (List<T>) JSONArray.toList(jsonarray, t.getClass());
    }

    /**
     * jsonobjectת���ɶ�������
     *
     * @return
     */
    public List<T> toList(JSONArray jsonarray, Class c) {
        return (List<T>) JSONArray.toList(jsonarray, c);
    }


    /**
     * ����get���� ����json����
     *
     * @param url ·��
     * @return
     */
    private JSONArray httpGetReturnArray(String url) throws AppException {
        return httpGet(url).getJSONArray("obj");
    }

    /**
     * ����get���� ����json����
     *
     * @param url ·��
     * @param t   �������ݶ���
     * @return
     */
    private JSONArray httpPostReturnArray(String url, T t) throws AppException {
        return httpPost(url, t).getJSONArray("obj");
    }

    public PageInfo<T> httpPostReturnPage(String url, T t) throws AppException {
        JSONObject jsonObject = httpPost(url, t);
        PageInfo<T> pageInfo = (PageInfo<T>) JSONObject.toBean(jsonObject.getJSONObject("obj"), PageInfo.class);
        pageInfo.setList(toList(jsonObject.getJSONObject("obj").getJSONArray("list"), t.getClass()));
        return pageInfo;
    }


    /**
     * ����get���� ����json����
     *
     * @param url ·��
     * @param t   �������ݶ���
     * @return
     */
    public JSONObject httpPostReturnObject(String url, T t) throws AppException {
        return httpPost(url, t).getJSONObject("obj");
    }

    public List<T> httpGetReturnList(String url, T t) throws AppException {
        return toList(httpGetReturnArray(url), t);
    }


    /**
     * ���ض���list
     *
     * @param url
     * @param t
     * @return
     * @throws AppException
     */
    public List<T> httpPostReturnList(String url, T t) throws AppException {
        return toList(httpPostReturnArray(url, t), t);
    }

    /**
     * ����get���� ����json����
     *
     * @param url ·��
     * @return
     */
    public T httpPostObject(String url, T t, Class c) throws AppException {
        return (T) JSONObject.toBean(httpPost(url, t).getJSONObject("obj"), c);
    }


    /**
     * ����get���� ����json����
     *
     * @param url ·��
     * @return
     */
    public JSONObject httpGetReturnObject(String url) throws AppException {
        return httpGet(url).getJSONObject("obj");
    }


    /**
     * ����get���� ����json����
     *
     * @param url ·��
     * @return
     */
    public T httpGetObject(String url, Class c) throws AppException {
        return (T) JSONObject.toBean(httpGet(url).getJSONObject("obj"), c);
    }


    /**
     * ���ض���list
     *
     * @param url
     * @param c
     * @return
     * @throws AppException
     */
    public List<T> httpGetReturnList(String url, Class c) throws AppException {
        return toList(httpGetReturnArray(url), c);
    }


    /**
     * post����
     *
     * @param url         ��ַ
     * @param t           ����
     * @return
     * @throws AppException
     */
    public JSONObject httpPost(String url, T t) throws AppException {
        try {
            HttpResult httpresult = HttpHelper.executePost(base_api_url + url, t, appkey);
            return parseHttpResult(httpresult, url);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    /**
     * ����get����
     *
     * @param url
     * @return
     * @throws AppException
     */
    public JSONObject httpGet(String url) throws AppException {
        try {
            HttpResult httpresult = HttpHelper.executeGet(base_api_url + url, appkey);
            return parseHttpResult(httpresult, url);
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    /**
     * ����get����
     *
     * @param url
     * @return
     * @throws AppException
     */
    public JSONObject httpUploadFile(String url, File file, String file_name,String file_bus_type, String file_bus_id, String userid) throws AppException {
        try {
            HttpResult httpresult = HttpHelper.executeUploadFile(base_api_url + url, appkey, file, file_name,file_bus_type, file_bus_id, userid);
            return parseHttpResult(httpresult, url);
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    /**
     * �������ص�httpresult
     *
     * @param httpresult
     * @param url
     * @throws AppException
     */
    private JSONObject parseHttpResult(HttpResult httpresult, String url) throws AppException {
        if (httpresult.getStatusCode() == HttpStatus.SC_OK) {
            JSONObject jsonResult = JSONObject.fromObject(httpresult.getContent());
            /**�Ƿ�ɹ�*/
            String syscode = jsonResult.getString("syscode");
            if (!syscode.equals(SysCode.SYS_CODE_200.getCode())) {
            	if(syscode.equals(SysCode.SYS_TOKEN_EMPTY.getCode())||syscode.equals(SysCode.SYS_TOKEN_ERROR.getCode())){
            		//ע����ǰ��¼��Ϣ  
            		Subject user = SecurityUtils.getSubject();
            	    user.logout();
            	    SysUserUtil.removeCurrentUser();   
            	    throw new AppException("�ӿ�(" + url + ") ����ʧ��:״̬��" + syscode+"tokenΪ�ջ�ʱ,���¼");
            	}else{
            		throw new AppException("�ӿ�(" + url + ") ����ʧ��:״̬��" + syscode);
            	}
            }
            return jsonResult;
        } else {
        	 System.out.println(httpresult.getStatusCode());
			 System.out.println(httpresult.getContent());
             throw new AppException("�ӿ�(" + url + ") ����ʧ��,http״̬��:" + httpresult.getStatusCode());
        }
    }


    /**
     * �ļ�����
     *
     * @param url
     * @return
     * @throws AppException
     */
    public File httpDownLoadFile(String url, String localdir) throws AppException {
        File file = null;
        try {
            file = HttpHelper.executeDownloadFile(base_api_url + url, appkey, localdir);
        } catch (IOException e) {
            throw new AppException(e);
        }
        return file;
    }
}
