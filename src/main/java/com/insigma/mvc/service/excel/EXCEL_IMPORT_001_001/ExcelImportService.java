package com.insigma.mvc.service.excel.EXCEL_IMPORT_001_001;

import java.util.HashMap;
import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.Ac60ExcelTemp;
import com.insigma.mvc.model.ExcelExportModel;
import com.insigma.mvc.model.SExcelBatch;
import com.insigma.resolver.AppException;




/**
 * excel״̬service
 * @author wengsh
 *
 */
public interface ExcelImportService {
	
	public HashMap<String,Object> getList( SExcelBatch sExcelBatch );
	
	public void executeListToTemp(List<String[]> list,SExcelBatch sExcelBatch) throws AppException;
	
	public void executeProc(SExcelBatch sExcelBatch)throws AppException;

	public HashMap<String,Object> queryPovertyDataTotalByexcelBatchNumber( Ac60ExcelTemp ac60ExcelTemp );
	
	public HashMap<String,Object> getPovertyImprtDataList( Ac60ExcelTemp ac60ExcelTemp );
	
	public AjaxReturnMsg<String> deleteTempDataByNumber(String number);
	
	 public List<ExcelExportModel> queryExportDataByNumber(String number);
	 
	 public AjaxReturnMsg<String> exportData(String number);
	 
	 public  Ac60ExcelTemp queryImpDataById(String aac002);
}
