package com.insigma.mvc.service.common.fileupload;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.SExcelBatch;
import com.insigma.mvc.model.SFileRecord;




/**
 * �ļ��ϴ�service
 * @author wengsh
 *
 */
public interface FileLoadService {
	 public SFileRecord getFileInfo(String file_uuid);  
     public byte[]  download(String file_path);  
     public String upload(String originalFilename,String file_bus_id,String file_bus_type,InputStream in);  
     public String uploadexcel(String originalFilename,String excel_upload_type,String minColumns,InputStream in) throws Exception;  
 	 public HashMap<String,Object> getFileList( SFileRecord sFileRecord );
 	 public AjaxReturnMsg<String> deleteFileByBusUuid(String file_uuid);
 	 public AjaxReturnMsg<String> batDeleteData(SFileRecord sFileRecord) ;
 	 public AjaxReturnMsg<String> batupdateBusIdByBusUuidArray(Map<String,Object> map);
 	 public SExcelBatch getExcelBatchByNumber(String number);
 	 public int updateExelBatchErrorFilePath(SExcelBatch sExcelBatch);
}
