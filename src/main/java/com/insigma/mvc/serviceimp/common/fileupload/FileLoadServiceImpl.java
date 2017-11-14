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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.insigma.common.util.RandomNumUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.fastdfsclient.Fastdfs;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.common.fileupload.FileLoadMapper;
import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;

/**
 *
 * @author wengsh
 *
 */
@Service
public class FileLoadServiceImpl  extends MvcHelper<SFileRecord> implements FileLoadService {

	@Value("${localdir}")
	private String localdir;

	@Resource
	private FileLoadMapper fileLoadMapper;
	
	@Resource
	private SysCodeTypeService sysCodeTypeService;

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
	
	@Override
	public List<SFileRecord> getBusFileRecordListByBusId(SFileRecord sFileRecord) {
	    //PageHelper.offsetPage(sFileRecord.getOffset(), sFileRecord.getLimit());
		List<SFileRecord> list=fileLoadMapper.getBusFileRecordListByBusId(sFileRecord);
		return  list;
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
				sfilerecord.setFile_name(originalFilename);// �ļ���,ֻ������һ���ϴ�ʱ���ļ���
				
				/** ����ͼƬ�����Ŀ¼ **/
				/** ��ǰ�·� **/
				String ym = new SimpleDateFormat("yyyyMM").format(new Date());
				/** ������ʵ·������Ŀ¼ **/
				File fileuploadDir = new File(localdir + "/" + ym);
				if (!fileuploadDir.exists()) {
					fileuploadDir.mkdirs();
				}
				/** ���ر���������ļ�����·�� **/
				/*String filepath = localdir + "/" + ym + "/" + originalFilename;
				File file = new File(filepath);
				//���ͬ�����ļ�����
				if(file.exists()){*/
					int indexofdoute = originalFilename.lastIndexOf(".");
					/**�ļ�������׺*/
					//String prefix = originalFilename.substring(0, indexofdoute);
					/**���ļ���������+�������*/
					String prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ RandomNumUtil.getRandomString(6);
					String endfix = originalFilename.substring(indexofdoute).toLowerCase();
					sfilerecord.setFile_type(endfix);
					String local_file_relative_path=  "/" + ym + "/"+prefix+endfix;
					String local_file_path=localdir+local_file_relative_path;
					File file=new File(local_file_path);
				/*}*/
			    
				//�����ļ������ط�������
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
				//�����ļ��ӿڽ��ļ��ϴ���ͼƬ������
				String serverfilepath=local_file_path;
				long start=new Date().getTime();
				Fastdfs fastdfs=Fastdfs.getInstance();
				serverfilepath=fastdfs.uploadFile(local_file_path);
				long end=new Date().getTime();
				System.out.println("�ķ�:"+(end-start)+"����");
				
				//�ϴ��ɹ��󽫷����ļ�·��
				//����·��
				//�ļ��������·��
				sfilerecord.setFile_path(serverfilepath);
				sfilerecord.setFile_status("0");//��Ч
				
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

}