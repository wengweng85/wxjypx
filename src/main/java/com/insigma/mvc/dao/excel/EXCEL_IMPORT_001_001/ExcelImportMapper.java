package com.insigma.mvc.dao.excel.EXCEL_IMPORT_001_001;

import java.util.List;

import com.insigma.mvc.model.Ac60ExcelTemp;
import com.insigma.mvc.model.ExcelExportModel;
import com.insigma.mvc.model.SExcelBatch;


/**
 * PovertyEmployInfoImportMapper
 * @author wengsh
 *
 */
public interface ExcelImportMapper {
	
    public void insertExcelTempData(Ac60ExcelTemp ac60Temp);
    
    public void executePovertyData(SExcelBatch sExcelBatch);
    
    public List<Ac60ExcelTemp> queryPovertyDataTotalByexcelBatchNumber(Ac60ExcelTemp ac60ExcelTemp);
    
    public List<Ac60ExcelTemp> queryPovertyDataByexcelBatchNumber(Ac60ExcelTemp ac60ExcelTemp);
    
    public int deleteTempDataByNumber(String number);
    
    public List<ExcelExportModel> queryExportDataByNumber(String number);
    
    public  Ac60ExcelTemp queryImpDataById(String aac001);
   
    
}
