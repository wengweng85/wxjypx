package com.insigma.http;

import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.model.SFileType;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 文件上传工具类
 * Created by liuds on 2017/9/11.
 */
public class FileUploadUtils {

    //http工具类
    @Resource
    private HttpRequestUtils httpRequestUtils;

    public SFileRecord uploadFile(HttpServletRequest request,String userid, String file_bus_id, String file_bus_type, String url) throws Exception {
        SFileType sFileType = (SFileType) httpRequestUtils.httpGetObject("/file/getFileType/"
                + file_bus_type, SFileType.class);
        if (sFileType == null) {
            throw new Exception("文件类型编号不存在");
        }

        long MAX_SIZE = (long) (sFileType.getFilemaxsize() * 1024 * 1024);
        // **********************参数初始化*********************
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile multipartFile = multiRequest.getFile(iter.next());
                if (multipartFile.getSize() > MAX_SIZE) {
                    throw new Exception("文件尺寸超过规定大小:" + sFileType.getFilemaxsize() + "M");
                }

                // 得到去除路径的文件名
                String originalFilename = multipartFile.getOriginalFilename();
                int indexofdoute = originalFilename.lastIndexOf(".");

                if (indexofdoute < 0) {
                    throw new Exception("文件格式错误");
                }

                /**文件名及后缀*/
                String prefix = originalFilename.substring(0, indexofdoute);
                /**获取文件的后缀**/
                String endfix = originalFilename.substring(indexofdoute).toLowerCase();
                String[] arr = {".jpg", ".jpeg", ".gif", ".png", ".bmp", ".pdf", ".doc", ".docx",
                        ".xls", ".xlsx", ".rar", ".zip", ".mp4"};
                if (!Arrays.asList(arr).contains(endfix)) {
                    throw new Exception("文件格式不正确,请确认");
                }
                if (prefix.length() < 3) {
                    prefix = prefix + "-001";
                }

                //上传并记录日志
                File file = File.createTempFile(prefix, endfix);
                multipartFile.transferTo(file);
                JSONObject result = httpRequestUtils.httpUploadFile(url, file, originalFilename, file_bus_type, file_bus_id, userid);
                if (!result.getBoolean("success")) {
                    throw new Exception(result.getString("message"));
                }
                return (SFileRecord) JSONObject.toBean(result.getJSONObject("obj"), SFileRecord.class);
            }
        }
        return null;
    }
}
