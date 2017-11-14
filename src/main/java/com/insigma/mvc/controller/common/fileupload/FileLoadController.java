package com.insigma.mvc.controller.common.fileupload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.controller.common.suggest.SuggestSearchController;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;
import com.insigma.resolver.AppException;

/**
 * �ļ��ϴ�������
 */
@Controller
@RequestMapping(value = "/common/fileload")
public class FileLoadController extends MvcHelper<SFileRecord> {


    Log log = LogFactory.getLog(SuggestSearchController.class);

    @Resource
    private FileLoadService fileloadservice;
    
    @Resource
    private SysCodeTypeService syscodetypeservice;
    
    
    /**
   	 * ��ת���ϴ�ҳ��
   	 * @param request
   	 * @return
   	 */
   	@RequestMapping("/tofilelist")
   	public ModelAndView tofilelist(HttpServletRequest request,Model model,SFileRecord sFileRecord ) throws Exception {
   		ModelAndView modelAndView=new ModelAndView("common/fileupload/filelist");
   		if(sFileRecord.getFile_bus_id()==null){
   			throw new Exception("ҵ���Ų���Ϊ��");
   		}
   		if(sFileRecord.getFile_bus_type()==null){
   			throw new Exception("ҵ��ͼƬ���Ͳ���Ϊ��");
   		}
   		CodeValue codevalue=new CodeValue();
   		codevalue.setCode_type("FILE_BUS_TYPE");
   		codevalue.setCode_value(sFileRecord.getFile_bus_type());
   		if(syscodetypeservice.getCodeValueByValue(codevalue)==null){
   			throw new Exception("ҵ��ͼƬ���ͱ���"+sFileRecord.getFile_bus_type()+"������,�����������FILE_BUS_TYPE���Ƿ����!");
   		}
   		sFileRecord.setFile_name("����");
   		modelAndView.addObject("filerecord", sFileRecord);
   		return modelAndView;
   	}
   	
	/**
	 * ͨ����Աid��ȡ�ļ��б�
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFileList")
	@ResponseBody
	public HashMap<String,Object> getUserListByGroupid(HttpServletRequest request,Model model,SFileRecord sFileRecord ) throws Exception {
		return fileloadservice.getFileList(sFileRecord);
	}
	
    
    /**
	 * ��ת��ͼƬ�ϴ�ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/toImgUpload")
	public ModelAndView toupload(HttpServletRequest request,Model modell,SFileRecord sFileRecord) throws Exception {
		ModelAndView modelAndView=new ModelAndView("common/fileupload/imgUpload");
		modelAndView.addObject("filerecord", sFileRecord);
		return modelAndView;
	}
	
	   /**
		 * ��ת���ļ��ϴ�ҳ��
		 * @param request
		 * @return
		 */
		@RequestMapping("/toFileUpload")
		public ModelAndView toFileUpload(HttpServletRequest request,Model modell,SFileRecord sFileRecord) throws Exception {
			ModelAndView modelAndView=new ModelAndView("common/fileupload/fileUpload");
			modelAndView.addObject("filerecord", sFileRecord);
			return modelAndView;
		}
	
	
	/**
     * �ļ��ϴ�
     *
     * @param request
     * @param response
     * @return
     * @throws AppException
     */
    @RequestMapping("/upload")
    @ResponseBody
    public AjaxReturnMsg<String>  upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String file_bus_id=request.getParameter("file_bus_id");
		String file_bus_type=request.getParameter("file_bus_type");
        //���ҵ���Ų���
        /*if(null==file_bus_id||file_bus_id.equals("")){
        	this.error( "ҵ���Ų���Ϊ��,����");
        	return ;
        }
        
      //���ҵ���Ų���
        if(null==file_bus_type||file_bus_type.equals("")){
        	this.error( "�ļ�ҵ�����Ͳ���Ϊ��,����");
        	return ;
        }
        
        CodeValue codevalue=new CodeValue();
   		codevalue.setCode_type("FILE_BUS_TYPE");
   		codevalue.setCode_value(file_bus_type);
   		if(syscodetypeservice.getCodeValueByValue(codevalue)==null){
   			this.error("ҵ��ͼƬ���ͱ���"+file_bus_type+"������,�����������FILE_BUS_TYPE���Ƿ����!");
   			return ;
   		}*/
        
        
		long MAX_SIZE = 20* 1024 * 1024L;//100m
		
    	try {
            //����һ��ͨ�õĶಿ�ֽ�����
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //�ж� request �Ƿ����ļ��ϴ�,���ಿ������
            if (multipartResolver.isMultipart(request)) {
                //ת���ɶಿ��request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //ȡ��request�е������ļ���
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    //ȡ���ϴ��ļ�
                    MultipartFile multipartFile = multiRequest.getFile(iter.next());
                    if (multipartFile.getSize() > MAX_SIZE) {
                    	return this.error( "�ļ��ߴ糬���涨��С:" + MAX_SIZE / 1024 / 1024 + "M");
                    } else {
                       
                        // �õ�ȥ��·�����ļ���
                        String originalFilename = multipartFile.getOriginalFilename();
                        int indexofdoute = originalFilename.lastIndexOf(".");
                        
                        /**��ȡ�ļ��ĺ�׺**/
                        String endfix = "";
                        if (indexofdoute != -1) {
                            // β
                            endfix = originalFilename.substring(indexofdoute).toLowerCase();
                            if(endfix.equals(".jpg")||endfix.equals(".jpeg")||endfix.equals(".gif")||endfix.equals(".png") ||endfix.equals(".pdf")||endfix.equals(".doc")||endfix.equals(".docx")||endfix.equals(".xls")||endfix.equals(".xlsx")||endfix.equals(".rar")||endfix.equals(".zip")) {
	     						//�ϴ�����¼��־
                            	String recordjson=fileloadservice.upload(originalFilename,file_bus_id,file_bus_type,multipartFile.getInputStream() );
                            	System.out.println(recordjson);
                                return this.success(recordjson);
                            }else{
                            	return this.error("�ļ���ʽ����ȷ,��ȷ��,ֻ�����ϴ���ʽΪjpg��jpeg��gif��png��pdf��doc��docx��xls��xlsx��rar��zip��ʽ���ļ�");
                            }
                        }else{
                        	return this.error("�ļ���ʽ����");
                        }
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
			// �����ļ��ߴ�����쳣
			if (e instanceof SizeLimitExceededException) {
				return this.error( "�ļ��ߴ糬���涨��С:" + MAX_SIZE / 1024 / 1024 + "M");
			}
			return this.error(e.getMessage());
        }
    	return null;

    }

    
    /**
	 * ͨ��idɾ���ļ���Ϣ
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping("/deletebyid/{id}")
	@ResponseBody
	public AjaxReturnMsg<String> deleteFileByid(HttpServletRequest request,Model model,@PathVariable String id){
		return fileloadservice.deleteFileByBusUuid(id);
	}
	
	/**
	 * ����ɾ��
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping("/batdelete")
	@ResponseBody
	public AjaxReturnMsg<String> batdelete(HttpServletRequest request,Model model,SFileRecord sFileRecord){
		return fileloadservice.batDeleteData(sFileRecord);
	}
	
    /**
     * �ļ�����
     * @param request
     * @param response
     * @throws AppException
     */
    @RequestMapping(value = "/download/{bus_uuid}")
    public void download(@PathVariable(value="bus_uuid") String bus_uuid, HttpServletRequest request ,HttpServletResponse response) throws  AppException{
        try{
        	SFileRecord filerecord=fileloadservice.getFileInfo(bus_uuid);
        	if(filerecord!=null){
        		 byte[] temp=fileloadservice.download(filerecord.getFile_path());
                 if(temp!=null){
                 	//���д����Ƿ�ֹ��������Ĺؼ�����
                     response.setHeader("Content-disposition","attachment; filename="+ new String(filerecord.getFile_name().getBytes("GBK"),"iso-8859-1"));
                     BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(temp));
                     BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                     //�½�һ��2048�ֽڵĻ�����
                     byte[] buff = new byte[2048];
                     int bytesRead=0;
                     while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                         bos.write(buff,0,bytesRead);
                     }
                     bos.flush();
                     if (bis != null)
                         bis.close();
                     if (bos != null)
                         bos.close();
                 }else{
                 	throw new Exception("���ش���,�����ڵ�id");
                 }
        	}
        }catch(Exception e){
            //log.error(e.getMessage());
        }
    }

}
