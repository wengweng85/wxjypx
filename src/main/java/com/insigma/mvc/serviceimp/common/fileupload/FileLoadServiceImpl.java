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
	 * ͨ��ҵ��id��ȡ�ļ��б�
	 */
	@Override
	public HashMap<String, Object> getFileList(SFileRecord sFileRecord) {
	    //PageHelper.offsetPage(sFileRecord.getOffset(), sFileRecord.getLimit());
		List<SFileRecord> list=fileLoadMapper.getBusFileRecordListByBusId(sFileRecord);
		PageInfo<SFileRecord> pageinfo = new PageInfo<SFileRecord>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	
	

	/**
	 * ����
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
	 * �ϴ�
	 */
	@Override
	@Transactional
	public String upload(String originalFilename,String file_bus_id,String file_bus_type,InputStream in){
		try {
            /**����������*/
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			in.close();
			/**���Ƴ�������������һ�����ڼ���md5,һ�����������ļ�*/
			InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
			InputStream is2 = new ByteArrayInputStream(baos.toByteArray());
			String file_md5 = DigestUtils.md5Hex(is1);
			is1.close();

			SFileRecord sfilerecord = fileLoadMapper.getFileUploadRecordByFileMd5(file_md5);
			/** ���ͨ��md5�ж��ļ����Ѿ��ڷ������ϴ��ڴ��ļ������ظ����� **/
			if (sfilerecord == null) {
				sfilerecord = new SFileRecord();
				sfilerecord.setFile_name(originalFilename);// �ļ���
				
				/** ����ͼƬ�����Ŀ¼ **/
				/** ��ǰ�·� **/
				String ym = new SimpleDateFormat("yyyyMM").format(new Date());
				/** ������ʵ·������Ŀ¼ **/
				File fileuploadDir = new File(localdir + "/" + ym);
				if (!fileuploadDir.exists()) {
					fileuploadDir.mkdirs();
				}
				/** ���ر���������ļ�����·�� **/
				String filepath = localdir + "/" + ym + "/" + originalFilename;
				File file = new File(filepath);
				//���ͬ�����ļ�����
				if(file.exists()){
					int indexofdoute = originalFilename.lastIndexOf(".");
					/**�ļ�������׺*/
					String prefix = originalFilename.substring(0, indexofdoute);
					String endfix = originalFilename.substring(indexofdoute).toLowerCase();
					sfilerecord.setFile_type(endfix);
					/**���ļ���������+�������*/
					prefix += new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ '_' + RandomNumUtil.getRandomString(6);
					filepath=localdir + "/" + ym + "/"+prefix+endfix;
					file=new File(filepath);
				}
				sfilerecord.setFile_path(filepath);
				sfilerecord.setFile_status("0");//��Ч
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
				sfilerecord.setFile_md5(file_md5);// �ļ�MD5������
				//�����ļ���¼
				fileLoadMapper.saveFileRecord(sfilerecord);
				//sysCodeTypeService.setSelectCacheData("FILENAME");
			}
			
			sfilerecord.setFile_bus_id(file_bus_id);
			sfilerecord.setFile_bus_type(file_bus_type);
			sfilerecord.setBus_status("0");
			//����ҵ���¼
			fileLoadMapper.saveBusRecord(sfilerecord);
			return JSONObject.fromObject(sfilerecord).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * �õ��ļ���Ϣ
	 */
	@Override
	public SFileRecord getFileInfo(String file_uuid) {
		return fileLoadMapper.getBusFileRecordByBusId(file_uuid);
	}

	
	@Override
	public AjaxReturnMsg<String> deleteFileByBusUuid(String bus_uuid) {
		//��¼ɾ��
		int deletenum= fileLoadMapper.deleteFileByBusUuid(bus_uuid);
		if(deletenum==1){
			return this.success("ɾ���ɹ�");
		}else{
			return this.error("ɾ��ʧ��,��ȷ���ļ��Ƿ����");
		}
	}

	/**
	 * ����ɾ��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg<String> batDeleteData(SFileRecord sFileRecord) {
		String [] ids=sFileRecord.getSelectnodes().split(",");
		for(int i=0;i<ids.length;i++){
			deleteFileByBusUuid(ids[i]);
		}
		sysCodeTypeService.setSelectCacheData("FILENAME");
		return this.success("����ɾ���ɹ�");
		/*int batdeletenum=fileLoadMapper.batDeleteData(ids);
		if(batdeletenum==ids.length){
			return this.success("����ɾ���ɹ�");
		}else{
			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
		}*/
	}

	
	@Override
	@Transactional
	public AjaxReturnMsg<String> batupdateBusIdByBusUuidArray( Map<String,Object> map) {
		  fileLoadMapper.batupdateBusIdByBusUuidArray(map);
		  return this.success("���³ɹ�");
	}

	/**
	 * excel�ļ��ϴ���¼��
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
				/** ��ǰ�·� **/
				String ym = new SimpleDateFormat("yyyyMM").format(new Date());
				/** ������ʵ·������Ŀ¼ **/
				File fileuploadDir = new File(localdir + "/" + ym);
				if (!fileuploadDir.exists()) {
					fileuploadDir.mkdirs();
				}
				int indexofdoute = originalFilename.lastIndexOf(".");
				/**�ļ�������׺*/
				String prefix = originalFilename.substring(0, indexofdoute);
				String endfix = originalFilename.substring(indexofdoute).toLowerCase();
				/**���ļ��������ڵ�����*/
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
				//���κ�Ϊ��ǰʱ�䵽����
				sexcelbatch.setExcel_batch_number(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
				//��¼excel�����Ϣ �����ļ���С��ҵ�����͡���ȡ�������ϴ�״̬��
				sexcelbatch.setExcel_batch_file_length(file.length());
				sexcelbatch.setExcel_batch_excel_type(excel_batch_excel_type);
				sexcelbatch.setExcel_batch_aae011(SysUserUtil.getCurrentUser().getCnname());
				sexcelbatch.setExcel_batch_status("0");//ת��xlsx
				//�����ļ���¼
				fileLoadMapper.saveExelBatch(sexcelbatch);
				
				//��excelת����cvs��ʽ
				final List<String[]> list = XLSXCovertCSVReader.readerExcel( filepath, "Sheet1", new Integer(minColumns).intValue());
				if(list==null){
					sexcelbatch.setExcel_batch_status("4");//�����쳣
					sexcelbatch.setExcel_batch_rt_msg("���õ�excel��ʽ����ȷ,��ȷ��excel��һ��sheet����ΪSheet1");
					fileLoadMapper.updateExelBatch(sexcelbatch);
					throw new AppException("���õ�excel��ʽ����ȷ,��ȷ��excel��һ��sheet����ΪSheet1");
				}
				//������
				sexcelbatch.setExcel_batch_total_count(new Long(list.size()));
				sexcelbatch.setExcel_batch_data_status(10);//���ݵ���״̬10%
				sexcelbatch.setExcel_batch_status("1");//������ʱ��
				//�����ļ���¼
				fileLoadMapper.updateExelBatch(sexcelbatch);
				//�����߳�ִ��
				taskExecutor.execute(new Runnable() {  
				    @Override  
				    public void run() {  
				        // TODO Auto-generated method stub  
				        try {  
				        	//���ݴ���
							if(excel_batch_excel_type.equals("sxpt_excel_imp")){
								//���ݱ��浽��ʱ��
								log.info("���浽��ʱ��ʼʱ��"+new Date().toLocaleString());
								Date start=new Date();
								excelImportService.executeListToTemp(list, sexcelbatch);
								Date end=new Date();
								Long cost=end.getTime()-start.getTime();
								log.info("���浽��ʱ��ʼ����"+new Date().toLocaleString()+"����"+cost/1000+"s");
								//���ù��̴�������
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
				sexcelbatch.setExcel_batch_status("4");//�����쳣
				if(e.getMessage().equals(minColumns)){
					sexcelbatch.setExcel_batch_rt_msg("���õ�excel��ʽ����ȷ,�����г���Ҫ���"+minColumns+"��,��ȷ��");
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