package com.insigma.mvc.controller.excel.EXCEL_IMPORT_001_001;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.Ac60ExcelTemp;
import com.insigma.mvc.model.SExcelBatch;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.excel.EXCEL_IMPORT_001_001.ExcelImportService;
import com.insigma.resolver.AppException;

/**
 * 贫困人员信息管理-导入
 * @author pangyuying
 *
 */
@Controller
@RequestMapping("/excelimport/EXCEL_IMPORT_001_001")
public class ExcelImportController extends MvcHelper{

	private static Log log=LogFactory.getLog(ExcelImportController.class);
	
	@Resource
	private ExcelImportService excelImportService;
	
	@Resource
	private FileLoadService fileLoadService;
	
	/**
	 * 主页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/index")
	public ModelAndView toCodeValuesuggest(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView modelAndView=new ModelAndView("excel/ExcelImport");
        return modelAndView;
	}
	
	/**
	 * 列表查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public HashMap<String,Object> getList(HttpServletRequest request,Model model, SExcelBatch sExcelBatch ) throws Exception {
		return excelImportService.getList(sExcelBatch);
	}
	
	/**
	 * 执行过程
	 * @param request
	 * @return
	 */
	@RequestMapping("/executeProc")
	@ResponseBody
	public AjaxReturnMsg<String> executeProc(HttpServletRequest request,Model model, SExcelBatch sExcelBatch ) throws Exception {
		 excelImportService.executeProc(sExcelBatch);
		 return this.success("执行成功");
	}
	
	
	/**
	 * 列表查询 导入数据总体情况 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPovertyImprtDataTotalList")
	@ResponseBody
	public HashMap<String,Object> queryPovertyDataTotalByexcelBatchNumber(HttpServletRequest request,Model model, Ac60ExcelTemp ac60ExcelTemp ) throws Exception {
		return excelImportService.queryPovertyDataTotalByexcelBatchNumber(ac60ExcelTemp);
	}
	
	/**
	 * 列表查询 导入数据明细情况 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPovertyImprtDataList")
	@ResponseBody
	public HashMap<String,Object> getPovertyImprtDataList(HttpServletRequest request,Model model, Ac60ExcelTemp ac60ExcelTemp ) throws Exception {
		return excelImportService.getPovertyImprtDataList(ac60ExcelTemp);
	}
	
	/**
	 * 删除临时表数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteTempDataByNumber/{excel_batch_number}")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> deleteTempDataByNumber(HttpServletRequest request ,@PathVariable String excel_batch_number) throws Exception {
		return excelImportService.deleteTempDataByNumber(excel_batch_number);
	}
	
	
    /** 
     * 生成excel 
     * @return 
     */  
    @RequestMapping("/genExcel/{excel_batch_number}")  
	@ResponseBody
    public 	AjaxReturnMsg<String> exportData(HttpServletRequest request, HttpServletResponse response,@PathVariable final String excel_batch_number) {  
    	return excelImportService.exportData(excel_batch_number);
    }  
	
    /**
	 * 查看扶贫人员信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/view/{aac002}")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response,@PathVariable String aac002) throws AppException {
		ModelAndView modelAndView=new ModelAndView("excel/ExcelInfoView");
		Ac60ExcelTemp ac60=excelImportService.queryImpDataById(aac002);
		modelAndView.addObject("ac60",ac60);  
        return modelAndView;
	}
}
