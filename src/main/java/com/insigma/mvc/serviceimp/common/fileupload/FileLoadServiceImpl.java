package com.insigma.mvc.serviceimp.common.fileupload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.insigma.common.excel.XLSXCovertCSVReader;
import com.insigma.common.util.RandomNumUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.common.fileupload.FileLoadMapper;
import com.insigma.mvc.model.SExcelBatch;
import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.excel.EXCEL_IMPORT_001_001.ExcelImportService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;
import com.insigma.resolver.AppException;
import com.insigma.shiro.realm.SysUserUtil;

/**
 *
 * @author wengsh
 *
 */
@Service
public class FileLoadServiceImpl  extends MvcHelper<SFileRecord> implements FileLoadService {

	
	private static Log log=LogFactory.getLog(FileLoadServiceImpl.class);
	
	
	@Value("${localdir}")
	private String localdir;

	@Resource
	private FileLoadMapper fileLoadMapper;
	
	@Resource
	private SysCodeTypeService sysCodeTypeService;
	
	@Resource
    private ExcelImportService excelImportService;
	
	
	@Resource(name = "taskExecutor")  
	private TaskExecutor taskExecutor; 

	public String getLocaldir() {
		return localdir;
	}

	public void setLocaldir(String localdir) {
		this.localdir = localdir;
	}
	
	
	/**
	 * 通过业务id获取文件列表
	 */
	@Override
	public HashMap<String, Object> getFileList(SFileRecord sFileRecord) {
	    //PageHelper.offsetPage(sFileRecord.getOffset(), sFileRecord.getLimit());
		List<SFileRecord> list=fileLoadMapper.getBusFileRecordListByBusId(sFileRecord);
		PageInfo<SFileRecord> pageinfo = new PageInfo<SFileRecord>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	
	

	/**
	 * 下载
	 */
	@Override
	public byte[] download(String file_path) {
		InputStream data = null;
		try {
			data = new FileInputStream(file_path);
			int size = data.available();
			byte[] buffer = new byte[size];
			IOUtils.read(data, buffer);
			return buffer;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(data);
		}
	}

	/**
	 * 上传
	 */
	@Override
	@Transactional
	public String upload(String originalFilename,String file_bus_id,String file_bus_type,InputStream in){
		try {
            /**拷贝输入流*/
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			in.close();
			/**复制成两个输入流，一个用于计算md5,一个用于生成文件*/
			InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
			InputStream is2 = new ByteArrayInputStream(baos.toByteArray());
			String file_md5 = DigestUtils.md5Hex(is1);
			is1.close();

			SFileRecord sfilerecord = fileLoadMapper.getFileUploadRecordByFileMd5(file_md5);
			/** 如果通过md5判断文件，已经在服务器上存在此文件，不重复保存 **/
			if (sfilerecord == null) {
				sfilerecord = new SFileRecord();
				sfilerecord.setFile_name(originalFilename);// 文件名
				
				/** 构建图片保存的目录 **/
				/** 当前月份 **/
				String ym = new SimpleDateFormat("yyyyMM").format(new Date());
				/** 根据真实路径创建目录 **/
				File fileuploadDir = new File(localdir + "/" + ym);
				if (!fileuploadDir.exists()) {
					fileuploadDir.mkdirs();
				}
				/** 本地保存的最终文件完整路径 **/
				String filepath = localdir + "/" + ym + "/" + originalFilename;
				File file = new File(filepath);
				//如果同名的文件存在
				if(file.exists()){
					int indexofdoute = originalFilename.lastIndexOf(".");
					/**文件名及后缀*/
					String prefix = originalFilename.substring(0, indexofdoute);
					String endfix = originalFilename.substring(indexofdoute).toLowerCase();
					sfilerecord.setFile_type(endfix);
					/**新文件名按日期+随机生成*/
					prefix += new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ '_' + RandomNumUtil.getRandomString(6);
					filepath=localdir + "/" + ym + "/"+prefix+endfix;
					file=new File(filepath);
				}
				sfilerecord.setFile_path(filepath);
				sfilerecord.setFile_status("0");//无效
				OutputStream os = new FileOutputStream(file);
				int bytesRead = 0;
				buffer = new byte[8192];
				while ((bytesRead = is2.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.flush();
				os.close();
				is2.close();
				baos.close();
				
				sfilerecord.setFile_length(new Long(file.length()).toString());
				sfilerecord.setFile_type(sfilerecord.getFile_name().substring(sfilerecord.getFile_name().lastIndexOf(".")+1));
				sfilerecord.setFile_md5(file_md5);// 文件MD5计算器
				//保存文件记录
				fileLoadMapper.saveFileRecord(sfilerecord);
				//sysCodeTypeService.setSelectCacheData("FILENAME");
			}
			
			sfilerecord.setFile_bus_id(file_bus_id);
			sfilerecord.setFile_bus_type(file_bus_type);
			sfilerecord.setBus_status("0");
			//保存业务记录
			fileLoadMapper.saveBusRecord(sfilerecord);
			return JSONObject.fromObject(sfilerecord).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到文件信息
	 */
	@Override
	public SFileRecord getFileInfo(String file_uuid) {
		return fileLoadMapper.getBusFileRecordByBusId(file_uuid);
	}

	
	@Override
	public AjaxReturnMsg<String> deleteFileByBusUuid(String bus_uuid) {
		//记录删除
		int deletenum= fileLoadMapper.deleteFileByBusUuid(bus_uuid);
		if(deletenum==1){
			return this.success("删除成功");
		}else{
			return this.error("删除失败,请确定文件是否存在");
		}
	}

	/**
	 * 批量删除
	 */
	@Override
	@Transactional
	public AjaxReturnMsg<String> batDeleteData(SFileRecord sFileRecord) {
		String [] ids=sFileRecord.getSelectnodes().split(",");
		for(int i=0;i<ids.length;i++){
			deleteFileByBusUuid(ids[i]);
		}
		sysCodeTypeService.setSelectCacheData("FILENAME");
		return this.success("批量删除成功");
		/*int batdeletenum=fileLoadMapper.batDeleteData(ids);
		if(batdeletenum==ids.length){
			return this.success("批量删除成功");
		}else{
			return this.success("批量删除成功,但存在失败的数据,请检查");
		}*/
	}

	
	@Override
	@Transactional
	public AjaxReturnMsg<String> batupdateBusIdByBusUuidArray( Map<String,Object> map) {
		  fileLoadMapper.batupdateBusIdByBusUuidArray(map);
		  return this.success("更新成功");
	}

	/**
	 * excel文件上传记录表
	 * @param originalFilename
	 * @param excel_batch_excel_type
	 * @param in
	 * @return
	 */
	@Override
	public String uploadexcel(String originalFilename,final String excel_batch_excel_type, String minColumns, InputStream in) throws AppException {
		// TODO Auto-generated method stub
	    final SExcelBatch sexcelbatch = new SExcelBatch();
	    File file=null;
	    try{
				/** 当前月份 **/
				String ym = new SimpleDateFormat("yyyyMM").format(new Date());
				/** 根据真实路径创建目录 **/
				File fileuploadDir = new File(localdir + "/" + ym);
				if (!fileuploadDir.exists()) {
					fileuploadDir.mkdirs();
				}
				int indexofdoute = originalFilename.lastIndexOf(".");
				/**文件名及后缀*/
				String prefix = originalFilename.substring(0, indexofdoute);
				String endfix = originalFilename.substring(indexofdoute).toLowerCase();
				/**新文件名按日期到毫秒*/
				prefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				String filepath=localdir + "/" + ym + "/"+prefix+endfix;
				 file=new File(filepath);
				sexcelbatch.setExcel_batch_file_path(filepath);
				sexcelbatch.setExcel_batch_file_name(originalFilename);
				OutputStream os = new FileOutputStream(file);
				int bytesRead = 0;
				byte [] buffer = new byte[8192];
				while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.flush();
				os.close();
				in.close();
				//批次号为当前时间到毫秒
				sexcelbatch.setExcel_batch_number(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
				//记录excel相关信息 包括文件大小、业务类型、获取行数、上传状态等
				sexcelbatch.setExcel_batch_file_length(file.length());
				sexcelbatch.setExcel_batch_excel_type(excel_batch_excel_type);
				sexcelbatch.setExcel_batch_aae011(SysUserUtil.getCurrentUser().getCnname());
				sexcelbatch.setExcel_batch_status("0");//转换xlsx
				//保存文件记录
				fileLoadMapper.saveExelBatch(sexcelbatch);
				
				//将excel转换成cvs格式
				final List<String[]> list = XLSXCovertCSVReader.readerExcel( filepath, "Sheet1", new Integer(minColumns).intValue());
				if(list==null){
					sexcelbatch.setExcel_batch_status("4");//发生异常
					sexcelbatch.setExcel_batch_rt_msg("所用的excel格式不正确,请确定excel第一列sheet名字为Sheet1");
					fileLoadMapper.updateExelBatch(sexcelbatch);
					throw new AppException("所用的excel格式不正确,请确定excel第一列sheet名字为Sheet1");
				}
				//行总数
				sexcelbatch.setExcel_batch_total_count(new Long(list.size()));
				sexcelbatch.setExcel_batch_data_status(10);//数据导入状态10%
				sexcelbatch.setExcel_batch_status("1");//导入临时表
				//更新文件记录
				fileLoadMapper.updateExelBatch(sexcelbatch);
				//开启线程执行
				taskExecutor.execute(new Runnable() {  
				    @Override  
				    public void run() {  
				        // TODO Auto-generated method stub  
				        try {  
				        	//数据处理
							if(excel_batch_excel_type.equals("sxpt_excel_imp")){
								//数据保存到临时表
								log.info("保存到临时表开始时间"+new Date().toLocaleString());
								Date start=new Date();
								excelImportService.executeListToTemp(list, sexcelbatch);
								Date end=new Date();
								Long cost=end.getTime()-start.getTime();
								log.info("保存到临时表开始结束"+new Date().toLocaleString()+"花费"+cost/1000+"s");
								//调用过程处理数据
								excelImportService.executeProc(sexcelbatch);
							}
				        } catch (Exception e) {  
				            // TODO Auto-generated catch block  
				            e.printStackTrace();  
				        }  
				    }  
				});  
			}catch(Exception e){
				e.printStackTrace();
				sexcelbatch.setExcel_batch_status("4");//发生异常
				if(e.getMessage().equals(minColumns)){
					sexcelbatch.setExcel_batch_rt_msg("所用的excel格式不正确,数据列超过要求的"+minColumns+"列,请确认");
				}else{
					sexcelbatch.setExcel_batch_rt_msg(e.getMessage());
				}
				fileLoadMapper.updateExelBatch(sexcelbatch);
				throw new AppException(e.getMessage());
			}finally{
		         if(file.exists()){
		        	file.delete();
		         }
			}
		   return JSONObject.fromObject(sexcelbatch).toString();

	}

	@Override
	public SExcelBatch getExcelBatchByNumber(String number) {
		return fileLoadMapper.getExcelBatchByNumber(number);
	}
	

	@Override
	public int updateExelBatchErrorFilePath(SExcelBatch sExcelBatch) {
		return fileLoadMapper.updateExelBatchErrorFilePath(sExcelBatch);
	}
}