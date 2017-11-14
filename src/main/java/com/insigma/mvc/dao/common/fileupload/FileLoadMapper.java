package com.insigma.mvc.dao.common.fileupload;

import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.SFileRecord;



/**
 * �ļ���¼����
 * @author wengsh
 *
 */
public interface FileLoadMapper {
	
	/**
	 * �����ļ���¼
	 * @param sfilerecord
	 */
	public void saveFileRecord( SFileRecord sfilerecord);
	
	/**
	 * ����ҵ���¼
	 * @param sfilerecord
	 */
	public void saveBusRecord(SFileRecord sfilerecord);
	/**
	 * ͨ���ļ�md5��ѯ�ļ�
	 * @param file_md5
	 * @return
	 */
	public SFileRecord getFileUploadRecordByFileMd5(String file_md5);
	
	/**
	 * ͨ��ҵ��id��ѯ�ļ�
	 * @param file_uuid
	 * @return
	 */
	public SFileRecord getBusFileRecordByBusId(String bus_uuid);
	
	/**
	 * 
	 * @param sFileRecord
	 * @return
	 */
	public List<SFileRecord> getBusFileRecordListByBusId(SFileRecord sFileRecord);
	
	/**
	 * ͨ���ļ����ɾ���ļ�
	 * @param fileuuid
	 * @return
	 */
	public int deleteFileByBusUuid(String bus_uuid);
	
	/**
	 * ����ɾ������
	 * @param ids
	 * @return
	 */
	public int batDeleteData(String[] ids);
	
	/**
	 *  ͨ���ļ�id�������ҵ��id��ҵ��״̬Ϊ��Ч״̬ 
	 * @return
	 */
	public int batupdateBusIdByBusUuidArray(Map<String,Object> map);
}
