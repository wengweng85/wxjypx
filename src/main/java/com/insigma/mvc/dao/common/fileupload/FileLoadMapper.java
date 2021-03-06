package com.insigma.mvc.dao.common.fileupload;

import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.SExcelBatch;
import com.insigma.mvc.model.SFileRecord;



/**
 * 文件记录保存
 * @author wengsh
 *
 */
public interface FileLoadMapper {
	
	/**
	 * 保存文件记录
	 * @param sfilerecord
	 */
	public void saveFileRecord( SFileRecord sfilerecord);
	
	/**
	 * 保存业务记录
	 * @param sfilerecord
	 */
	public void saveBusRecord(SFileRecord sfilerecord);
	/**
	 * 通过文件md5查询文件
	 * @param file_md5
	 * @return
	 */
	public SFileRecord getFileUploadRecordByFileMd5(String file_md5);
	
	/**
	 * 通过业务id查询文件
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
	 * 通过文件编号删除文件
	 * @param fileuuid
	 * @return
	 */
	public int deleteFileByBusUuid(String bus_uuid);
	
	/**
	 * 批量删除数据
	 * @param ids
	 * @return
	 */
	public int batDeleteData(String[] ids);
	
	/**
	 *  通过文件id数组更新业务id及业务状态为有效状态 
	 * @return
	 */
	public int batupdateBusIdByBusUuidArray(Map<String,Object> map);
	
	
	/**
	 * 保存excel文件上传记录表
	 * @param sfilerecord
	 */
	public void saveExelBatch(SExcelBatch sExcelBatch);
	
	
	/**
	 * 更新文件记录表
	 * @param sExcelBatch
	 */
	public void updateExelBatch(SExcelBatch sExcelBatch);
	
	/**
	 * 通过id获取批次号
	 * @param id
	 * @return
	 */
	public SExcelBatch getExcelBatchById(String id);
	
	
	public SExcelBatch getExcelBatchByNumber(String number);
	/**
	 * 分页查询
	 * @param sExcelBatch
	 * @return
	 */
	public List<SExcelBatch> getExcelBatchList(SExcelBatch sExcelBatch);
	
	/**
	 * 通过批次号删除批次信息
	 * @param number
	 * @return
	 */
	public int deleteExcelBatchByNumber(String number);
	
	
	
	public int updateExelBatchErrorFilePath(SExcelBatch sExcelBatch);
}
