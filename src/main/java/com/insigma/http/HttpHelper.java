package com.insigma.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.insigma.shiro.realm.SysUserUtil;

/**
 * Http����������</br>
 * ����HttpClient 4.5.x�汾
 *
 * @author comven
 */
public class HttpHelper {
    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);
    private static final String DEFAULT_CHARSET = "UTF-8";// Ĭ���������
    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;// Ĭ�ϵȴ���Ӧʱ��(����)
    private static final int DEFAULT_RETRY_TIMES = 0;// Ĭ��ִ�����ԵĴ���

    /**
     * ����һ��Ĭ�ϵĿɹرյ�HttpClient
     *
     * @return
     */
    public static CloseableHttpClient createHttpClient() {
        return createHttpClient(DEFAULT_RETRY_TIMES, DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * ����һ���ɹرյ�HttpClient
     *
     * @param socketTimeout �����ȡ���ݵĳ�ʱʱ��
     * @return
     */
    public static CloseableHttpClient createHttpClient(int socketTimeout) {
        return createHttpClient(DEFAULT_RETRY_TIMES, socketTimeout);
    }

    /**
     * ����һ���ɹرյ�HttpClient
     *
     * @param socketTimeout �����ȡ���ݵĳ�ʱʱ��
     * @param retryTimes    ���Դ�����С�ڵ���0��ʾ������
     * @return
     */
    public static CloseableHttpClient createHttpClient(int retryTimes, int socketTimeout) {
        Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(5000);// �������ӳ�ʱʱ�䣬��λ����
        builder.setConnectionRequestTimeout(1000);// ���ô�connect Manager��ȡConnection ��ʱʱ�䣬��λ���롣����������¼ӵ����ԣ���ΪĿǰ�汾�ǿ��Թ������ӳصġ�
        if (socketTimeout >= 0) {
            builder.setSocketTimeout(socketTimeout);// �����ȡ���ݵĳ�ʱʱ�䣬��λ���롣 �������һ���ӿڣ�����ʱ�����޷��������ݣ���ֱ�ӷ����˴ε��á�
        }
        RequestConfig defaultRequestConfig = builder.setCookieSpec(CookieSpecs.STANDARD_STRICT).setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
        // ����HTTPS֧��
        enableSSL();
        // ��������Scheme
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        // ����ConnectionManager�����Connection������Ϣ
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (retryTimes > 0) {
            setRetryHandler(httpClientBuilder, retryTimes);
        }
        CloseableHttpClient httpClient = httpClientBuilder.setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig).build();
        return httpClient;
    }

    /**
     * ִ��GET����
     *
     * @param url    Զ��URL��ַ
     * @param appkey ��Ȩkey
     * @return HttpResult
     * @throws IOException
     */
    public static HttpResult executeGet(String url, String appkey) throws IOException {
        CloseableHttpClient httpClient = createHttpClient(DEFAULT_SOCKET_TIMEOUT);
        return executeGet(httpClient, url, appkey, null, null, DEFAULT_CHARSET, true);
    }

    /**
     * ִ��GET����
     *
     * @param url           Զ��URL��ַ
     * @param appkey        ��Ȩkey
     * @param charset       ����ı��룬Ĭ��UTF-8
     * @param socketTimeout ��ʱʱ�䣨���룩
     * @return HttpResult
     * @throws IOException
     */
    public static HttpResult executeGet(String url, String appkey, String charset, int socketTimeout) throws IOException {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executeGet(httpClient, url, appkey, null, null, charset, true);
    }

    /**
     * ִ��GET����
     *
     * @param url           Զ��URL��ַ
     * @param appkey        ��Ȩkey
     * @param charset       ����ı��룬Ĭ��UTF-8
     * @param socketTimeout ��ʱʱ�䣨���룩
     * @return String
     * @throws IOException
     */
    public static String executeGetString(String url, String appkey, String charset, int socketTimeout) throws IOException {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executeGetString(httpClient, url, appkey, null, null, charset, true);
    }

    /**
     * ִ��HttpGet����
     *
     * @param httpClient      HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param url             �����Զ�̵�ַ
     * @param appkey          ��Ȩkey
     * @param referer         referer��Ϣ���ɴ�null
     * @param cookie          cookies��Ϣ���ɴ�null
     * @param charset         ������룬Ĭ��UTF8
     * @param closeHttpClient ִ������������Ƿ�ر�HttpClient�ͻ���ʵ��
     * @return HttpResult
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult executeGet(CloseableHttpClient httpClient, String url, String appkey, String referer, String cookie, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executeGetResponse(httpClient, url, appkey, referer, cookie);
            //Http����״̬��
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param httpClient httpclient����
     * @param url        ִ��GET��URL��ַ
     * @param appkey     ��Ȩkey
     * @param referer    referer��ַ
     * @param cookie     cookie��Ϣ
     * @return CloseableHttpResponse
     * @throws IOException
     */
    public static CloseableHttpResponse executeGetResponse(CloseableHttpClient httpClient, String url, String appkey, String referer, String cookie) throws IOException {
        if (httpClient == null) {
            httpClient = createHttpClient();
        }
        logger.info("get����url:" + url);
        HttpGet get = new HttpGet(url);
        if (appkey != null && !"".equals(appkey)) {
            get.setHeader("appkey", appkey);
        }
        if (SysUserUtil.getCurrentUser() != null) {
            get.setHeader("Authorization", "bearer " + SysUserUtil.getCurrentUser().getToken());
        }
        if (cookie != null && !"".equals(cookie)) {
            get.setHeader("Cookie", cookie);
        }
        if (referer != null && !"".equals(referer)) {
            get.setHeader("referer", referer);
        }
        return httpClient.execute(get);
    }

    /**
     * ִ��HttpGet����
     *
     * @param httpClient      HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param url             �����Զ�̵�ַ
     * @param appkey          ��Ȩkey
     * @param referer         referer��Ϣ���ɴ�null
     * @param cookie          cookies��Ϣ���ɴ�null
     * @param charset         ������룬Ĭ��UTF8
     * @param closeHttpClient ִ������������Ƿ�ر�HttpClient�ͻ���ʵ��
     * @return String
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String executeGetString(CloseableHttpClient httpClient, String url, String appkey, String referer, String cookie, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executeGetResponse(httpClient, url, appkey, referer, cookie);
            return getResult(httpResponse, charset);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * �򵥷�ʽִ��POST����
     *
     * @param url       Զ��URL��ַ
     * @param paramsObj post�Ĳ�����֧��map<String,String>,JSON,XML
     * @param appkey    ��Ȩkey
     * @return HttpResult
     * @throws IOException
     */
    public static HttpResult executePost(String url, Object paramsObj, String appkey) throws Exception {
        CloseableHttpClient httpClient = createHttpClient(DEFAULT_SOCKET_TIMEOUT);
        return executePost(httpClient, url, paramsObj, appkey, null, null, DEFAULT_CHARSET, true);
    }

    /**
     * �򵥷�ʽִ��POST����
     *
     * @param url           Զ��URL��ַ
     * @param paramsObj     post�Ĳ�����֧��map<String,String>,JSON,XML
     * @param appkey        ��Ȩkey
     * @param charset       ����ı��룬Ĭ��UTF-8
     * @param socketTimeout ��ʱʱ��(����)
     * @return HttpResult
     * @throws IOException
     */
    public static HttpResult executePost(String url, Object paramsObj, String appkey, String charset, int socketTimeout) throws Exception {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executePost(httpClient, url, paramsObj, appkey, null, null, charset, true);
    }

    /**
     * �򵥷�ʽִ��POST����
     *
     * @param url           Զ��URL��ַ
     * @param paramsObj     post�Ĳ�����֧��map<String,String>,JSON,XML
     * @param appkey        ��Ȩkey
     * @param charset       ����ı��룬Ĭ��UTF-8
     * @param socketTimeout ��ʱʱ��(����)
     * @return HttpResult
     * @throws IOException
     */
    public static String executePostString(String url, Object paramsObj, String appkey, String charset, int socketTimeout) throws Exception {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executePostString(httpClient, url, paramsObj, appkey, null, null, charset, true);
    }

    /**
     * ִ��HttpPost����
     *
     * @param httpClient      HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param url             �����Զ�̵�ַ
     * @param paramsObj       �ύ�Ĳ�����Ϣ��Ŀǰ֧��Map,��String(JSON\xml)
     * @param appkey          appkey��Ϣ�����ɴ�null
     * @param referer         referer��Ϣ���ɴ�null
     * @param cookie          cookies��Ϣ���ɴ�null
     * @param charset         ������룬Ĭ��UTF8
     * @param closeHttpClient ִ������������Ƿ�ر�HttpClient�ͻ���ʵ��
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static HttpResult executePost(CloseableHttpClient httpClient, String url, Object paramsObj, String appkey, String referer, String cookie, String charset, boolean closeHttpClient) throws Exception {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executePostResponse(httpClient, url, paramsObj, appkey, referer, cookie, charset);
            //Http����״̬��
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * ִ��HttpPost����
     *
     * @param httpClient      HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param url             �����Զ�̵�ַ
     * @param paramsObj       �ύ�Ĳ�����Ϣ��Ŀǰ֧��Map,��String(JSON\xml)
     * @param referer         referer��Ϣ���ɴ�null
     * @param cookie          cookies��Ϣ���ɴ�null
     * @param charset         ������룬Ĭ��UTF8
     * @param closeHttpClient ִ������������Ƿ�ر�HttpClient�ͻ���ʵ��
     * @return String
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String executePostString(CloseableHttpClient httpClient, String url, Object paramsObj, String referer, String appkey, String cookie, String charset, boolean closeHttpClient) throws Exception {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executePostResponse(httpClient, url, paramsObj, appkey, referer, cookie, charset);
            return getResult(httpResponse, charset);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * @param httpClient HttpClient����
     * @param url        ����������ַ
     * @param paramsObj  ������Ϣ
     * @param referer    ��Դ��ַ
     * @param cookie     cookie��Ϣ
     * @param charset    ͨ�ű���
     * @return CloseableHttpResponse
     * @throws IOException
     */
    private static CloseableHttpResponse executePostResponse(CloseableHttpClient httpClient, String url, Object paramsObj, String appkey, String referer, String cookie, String charset) throws Exception {
        if (httpClient == null) {
            httpClient = createHttpClient();
        }
        HttpPost post = new HttpPost(url);
        if (appkey != null && !"".equals(appkey)) {
            post.setHeader("appkey", appkey);
        }
        if (SysUserUtil.getCurrentUser() != null) {
            post.setHeader("Authorization", "bearer " + SysUserUtil.getCurrentUser().getToken());
        }
        if (cookie != null && !"".equals(cookie)) {
            post.setHeader("Cookie", cookie);
        }
        if (referer != null && !"".equals(referer)) {
            post.setHeader("referer", referer);
        }
        logger.info("post����url:" + url);
        // ���ò���
        HttpEntity httpEntity = getEntity(paramsObj, charset);
        if (httpEntity != null) {
            post.setEntity(httpEntity);
        }
        return httpClient.execute(post);
    }


    /**
     * ִ���ļ��ϴ�
     *
     * @param httpClient    HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param remoteFileUrl Զ�̽����ļ��ĵ�ַ
     * @param localFilePath �����ļ���ַ
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult executeUploadFile(String remoteFileUrl, String appkey, File localFile, String file_name, String file_bus_type, String file_bus_id, String userid) throws IOException {
        return executeUploadFile(remoteFileUrl, appkey, localFile, file_name, file_bus_type, file_bus_id, userid, DEFAULT_CHARSET, true);
    }

    /**
     * ִ���ļ��ϴ�
     *
     * @param httpClient      HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param remoteFileUrl   Զ�̽����ļ��ĵ�ַ
     * @param localFilePath   �����ļ���ַ
     * @param charset         ������룬Ĭ��UTF-8
     * @param closeHttpClient ִ������������Ƿ�ر�HttpClient�ͻ���ʵ��
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult executeUploadFile(String url, String appkey, File localFile, String file_name, String file_bus_type, String file_bus_id, String userid, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = createHttpClient();
            HttpPost httpPost = new HttpPost(url);
            //header
            httpPost.setHeader("appkey", appkey);
            if (SysUserUtil.getCurrentUser() != null) {
                httpPost.setHeader("Authorization", "bearer " + SysUserUtil.getCurrentUser().getToken());
            }
            // ���ļ�ת����������FileBody
            FileBody fileBody = new FileBody(localFile);
            //form����
            StringBody file_name_body = new StringBody(URLEncoder.encode(file_name, "UTF-8"), ContentType.APPLICATION_FORM_URLENCODED);
            StringBody file_bus_type_body = new StringBody(file_bus_type, ContentType.APPLICATION_FORM_URLENCODED);
            StringBody file_bus_id_body = new StringBody(file_bus_id, ContentType.APPLICATION_FORM_URLENCODED);
            StringBody userid_body = new StringBody(userid, ContentType.APPLICATION_FORM_URLENCODED);
            // �����������ģʽ���У���ֹ�ļ������롣
            HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("uploadFile", fileBody)
                    .addPart("file_name", file_name_body)
                    .addPart("file_bus_type", file_bus_type_body)
                    .addPart("file_bus_id", file_bus_id_body)
                    .addPart("userid", userid_body).setCharset(CharsetUtils.get("GBK")).build();
            httpPost.setEntity(reqEntity);
            httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * ִ���ļ��ϴ�(�Զ���������ʽ)
     *
     * @param httpClient      HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param remoteFileUrl   Զ�̽����ļ��ĵ�ַ
     * @param localFilePath   �����ļ���ַ
     * @param charset         ������룬Ĭ��UTF-8
     * @param closeHttpClient ִ������������Ƿ�ر�HttpClient�ͻ���ʵ��
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult executeUploadFileStream(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
        CloseableHttpResponse httpResponse = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            if (httpClient == null) {
                httpClient = createHttpClient();
            }
            // ���ļ�ת����������FileBody
            File localFile = new File(localFilePath);
            fis = new FileInputStream(localFile);
            byte[] tmpBytes = new byte[1024];
            byte[] resultBytes = null;
            baos = new ByteArrayOutputStream();
            int len;
            while ((len = fis.read(tmpBytes, 0, 1024)) != -1) {
                baos.write(tmpBytes, 0, len);
            }
            resultBytes = baos.toByteArray();
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(resultBytes, ContentType.APPLICATION_OCTET_STREAM);
            HttpPost httpPost = new HttpPost(remoteFileUrl);
            httpPost.setEntity(byteArrayEntity);
            httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * ִ���ļ�����
     *
     * @param httpClient    HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param remoteFileUrl Զ�������ļ���ַ
     * @param localdir      ���ش洢�ļ�·��
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static File executeDownloadFile(String url, String appkey, String localdir) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        InputStream in = null;
        FileOutputStream fout = null;
        CloseableHttpClient httpClient = null;
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("appkey", appkey);
            if (SysUserUtil.getCurrentUser() != null) {
                httpget.setHeader("Authorization", "bearer " + SysUserUtil.getCurrentUser().getToken());
            }
            httpClient = createHttpClient();
            response = httpClient.execute(httpget);
            logger.info("get����url:" + url);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            in = entity.getContent();
            String filename = getFileName(response);
            File file = new File(localdir, filename);
            fout = new FileOutputStream(file);
            int l;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
                // ע�����������OutputStream.write(buff)�Ļ���ͼƬ��ʧ��
            }
            // ���ļ����������
            fout.flush();
            EntityUtils.consume(entity);
            return file;
        } finally {
            // �رյͲ�����
            if (fout != null) {
                try {
                    fout.close();
                } catch (Exception e) {
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * ��ȡresponse header��Content-Disposition�е�filenameֵ
     *
     * @param response
     * @return
     */
    public static String getFileName(HttpResponse response) {
        org.apache.http.Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            org.apache.http.HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "GBK");  
                        filename = URLDecoder.decode(param.getValue(), "utf-8");
                        //filename = param.getValue();  
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            filename = getRandomFileName();
        }
        return filename;
    }

    /**
     * ��ȡ����ļ���
     *
     * @return
     */
    public static String getRandomFileName() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * ִ���ļ�����
     *
     * @param httpClient      HttpClient�ͻ���ʵ��������null���Զ�����һ��
     * @param remoteFileUrl   Զ�������ļ���ַ
     * @param localFilePath   ���ش洢�ļ���ַ
     * @param charset         ������룬Ĭ��UTF-8
     * @param closeHttpClient ִ������������Ƿ�ر�HttpClient�ͻ���ʵ��
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static boolean executeDownloadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        InputStream in = null;
        FileOutputStream fout = null;
        try {
            HttpGet httpget = new HttpGet(remoteFileUrl);
            response = httpClient.execute(httpget);
            logger.info("get����url:" + remoteFileUrl);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return false;
            }
            in = entity.getContent();
            File file = new File(localFilePath);
            fout = new FileOutputStream(file);
            int l;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
                // ע�����������OutputStream.write(buff)�Ļ���ͼƬ��ʧ��
            }
            // ���ļ����������
            fout.flush();
            EntityUtils.consume(entity);
            return true;
        } finally {
            // �رյͲ�����
            if (fout != null) {
                try {
                    fout.close();
                } catch (Exception e) {
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * ���ݲ�����ȡ�����Entity
     *
     * @param paramsObj
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static HttpEntity getEntity(Object paramsObj, String charset) throws Exception {
        if (paramsObj == null) {
            logger.info("��ǰδ���������Ϣ���޷�����HttpEntity");
            return null;
        }
        if (Map.class.isInstance(paramsObj)) {// ��ǰ��map����
            @SuppressWarnings("unchecked")
            Map<String, String> paramsMap = (Map<String, String>) paramsObj;
            List<NameValuePair> list = getNameValuePairs(paramsMap);
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list, charset);
            httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            return httpEntity;
        } else if (String.class.isInstance(paramsObj)) {// ��ǰ��string���󣬿�����
            String paramsStr = (String) paramsObj;
            StringEntity httpEntity = new StringEntity(paramsStr, charset);
            if (paramsStr.startsWith("{")) {
                httpEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            } else if (paramsStr.startsWith("<")) {
                httpEntity.setContentType(ContentType.APPLICATION_XML.getMimeType());
            } else {
                httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            }
            return httpEntity;
        } else if (Object.class.isInstance(paramsObj)) {
            StringEntity httpEntity = new StringEntity(parseURLPair(paramsObj), charset);
            httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            return httpEntity;
        } else {
            logger.info("��ǰ�����������ʶ�����ͣ��޷�����HttpEntity");
        }
        return null;
    }

    /**
     * ����ת����url k=v��ʽ
     *
     * @param o
     * @return
     * @throws Exception
     */
    public static String parseURLPair(Object t) throws Exception {
        StringBuffer sb = new StringBuffer();
        parseClass(t, t.getClass(), sb);
        if (sb.length() > 0) {
            String param = sb.deleteCharAt(sb.length() - 1).toString();
            logger.info("�������:" + param);
            return param;
        } else {
            return "";
        }
    }

    /**
     * ����class
     *
     * @param c
     * @param sb
     */
    public static void parseClass(Object t, Class c, StringBuffer sb) throws Exception {
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> map = new TreeMap<String, Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if ("serialVersionUID".equals(name) || "token".equals(name)) {
                continue;
            }
            Object value = field.get(t);
            if (value != null) {
                if (field.getType().equals(Date.class)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    map.put(name, sdf.format(value));
                } else {
                    map.put(name, value);
                }
            }
        }
        Set<Entry<String, Object>> set = map.entrySet();
        Iterator<Entry<String, Object>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, Object> e = it.next();
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }

        if (c.getSuperclass() != Object.class) {
            parseClass(t, c.getSuperclass(), sb);
        }
    }

    /**
     * �ӽ���л�ȡ��String����
     *
     * @param httpResponse http�������
     * @param charset      ������Ϣ
     * @return String
     * @throws ParseException
     * @throws IOException
     */
    private static String getResult(CloseableHttpResponse httpResponse, String charset) throws ParseException, IOException {
        String result = null;
        if (httpResponse == null) {
            return result;
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return result;
        }
        result = EntityUtils.toString(entity, charset);
        EntityUtils.consume(entity);// �ر�Ӧ�ùرյ���Դ���ʵ����ͷ���Դ ;Ҳ���԰ѵײ�������ر���
        return result;
    }

    /**
     * ת���������
     *
     * @param charset ������Ϣ
     * @return String
     */
    private static String getCharset(String charset) {
        return charset == null ? DEFAULT_CHARSET : charset;
    }

    /**
     * ��map���Ͳ���ת��ΪNameValuePair���Ϸ�ʽ
     *
     * @param paramsMap
     * @return
     */
    private static List<NameValuePair> getNameValuePairs(Map<String, String> paramsMap) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (paramsMap == null || paramsMap.isEmpty()) {
            return list;
        }
        for (Entry<String, String> entry : paramsMap.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    /**
     * ����SSL֧��
     */
    private static void enableSSL() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{manager}, null);
            socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SSLConnectionSocketFactory socketFactory;

    // HTTPS��վһ�������ʹ���˰�ȫϵ���ϵ͵�SHA-1ǩ����������������ڵ���SSL֮ǰ��Ҫ��д��֤������ȡ�����SSL��
    private static TrustManager manager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //

        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //

        }
    };

    /**
     * Ϊhttpclient����������Ϣ
     *
     * @param httpClientBuilder
     * @param retryTimes
     */
    private static void setRetryHandler(HttpClientBuilder httpClientBuilder, final int retryTimes) {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= retryTimes) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // ���������Ϊ���ݵȵģ���ô������
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };
        httpClientBuilder.setRetryHandler(myRetryHandler);
    }

}
