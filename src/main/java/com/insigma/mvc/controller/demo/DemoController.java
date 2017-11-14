package com.insigma.mvc.controller.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.DemoAc01;
import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.service.demo.DemoAc01Service;
import com.insigma.shiro.realm.SysUserUtil;


/**
 * demo���Գ���
 * @author wengsh
 *
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends MvcHelper<DemoAc01> {
	
	@Resource
	private DemoAc01Service demoAc01Service;
	
	
	/**
	 * ��ת����ѯҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/toquery")
	public ModelAndView draglist(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("demo/demoQuery");
		//���µ�ǰ�û�Ȩ��
		SysUserUtil.updateCurrentUserPerms();
        return modelAndView;
	}
	
	
	/**
	 * ��ȡ��Ա��Ϣ�б�
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAc01List")
	@ResponseBody
	public HashMap<String,Object> getAc01List(HttpServletRequest request,Model model, DemoAc01 ac01 ) throws Exception {
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		System.out.println(SysUserUtil.getCurrentUser().getUsername());
		return demoAc01Service.getDemoAc01List(ac01);
	}
	
	
	/**
	 * ͨ��idɾ����Աdemo��Ϣ
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping("/deletebyid/{id}")
	@ResponseBody
	public AjaxReturnMsg<String> deleteDemoDataById(HttpServletRequest request,Model model,@PathVariable String id){
		return demoAc01Service.deleteDemoById(id);
	}
	
	
	/**
	 * ��ת���༭ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/toedit/{id}")
	public ModelAndView toedit(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		ModelAndView modelAndView=new ModelAndView("demo/demoEdit");
		DemoAc01 ac01=demoAc01Service.getDemoById(id);
		modelAndView.addObject("ac01",ac01);  
        return modelAndView;
	}
	
	
	/**
	 * ��ת���鿴ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/toview/{id}")
	public ModelAndView toview(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		ModelAndView modelAndView=new ModelAndView("demo/demoView");
		DemoAc01 ac01=demoAc01Service.getDemoNameById(id);
		modelAndView.addObject("ac01",ac01);  
        return modelAndView;
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
	public AjaxReturnMsg<String> batDeleteDemodata(HttpServletRequest request,Model model,DemoAc01 ac01){
		return demoAc01Service.batDeleteDemoData(ac01);
	}
	
	
	/**
	 * ��ת���༭(����)ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/toadd")
	public ModelAndView toadd(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("demo/demoAdd");
        return modelAndView;
	}
	
	/**
	 * ��Աѡ���
	 * @param request
	 * @return
	 */
	@RequestMapping("/toselect")
	public ModelAndView selectindex(HttpServletRequest request,Model model) throws Exception {
		String callback_fun_name=request.getParameter("callback_fun_name");
		ModelAndView modelAndView=new ModelAndView("demo/demoSelect");
        modelAndView.addObject("callback_fun_name", callback_fun_name);
        return modelAndView;
	}
	
	/**
	 * ��ת���༭(����)ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDemoById/{id}")
	@ResponseBody
	public AjaxReturnMsg<DemoAc01> getDemoById(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		return this.success(demoAc01Service.getDemoById(id));
	}
	
	
	
	/**
	 * ���»򱣴�
	 * @param request
	 * @return
	 */
	@RequestMapping("/savedata")
	@ResponseBody
	public AjaxReturnMsg<String> savedata(HttpServletRequest request,Model model,@Valid DemoAc01 ac01,BindingResult result) throws Exception {
		//��������
		if (result.hasErrors()){
			return validate(result);
		}
		
		return demoAc01Service.saveDemoData(ac01);
		
	}
	
	/**
	 * ���»򱣴� ͨ�������༭����
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateDataByXedit")
	@ResponseBody
	public AjaxReturnMsg<String> updateDataByXedit(HttpServletRequest request,Model mode, DemoAc01 ac01) throws Exception {
		return demoAc01Service.saveDemoData(ac01);
	}
	
	
	/**
	 * ���¸���ͼƬ���
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatefile/{id}/{bus_uuid}")
	@ResponseBody
	public AjaxReturnMsg<String> updatefile(HttpServletRequest request,Model model,@PathVariable String id,@PathVariable String bus_uuid) throws Exception {
		DemoAc01 ac01=new DemoAc01();
		ac01.setAac001(id);
		ac01.setBus_uuid (bus_uuid);
		return demoAc01Service.updateDemoAc01DemBusUuid(ac01);
	}
	
	/**
	 * ɾ������ͼƬ
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletefile/{id}/{bus_uuid}")
	@ResponseBody
	public AjaxReturnMsg<String> deletefile(HttpServletRequest request,Model model,@PathVariable String id,@PathVariable String bus_uuid) throws Exception {
		DemoAc01 ac01=new DemoAc01();
		ac01.setAac001(id);
		ac01.setBus_uuid(bus_uuid);
		return demoAc01Service.deletefile(ac01);
	}
	
	/**
	 * api����ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/open_api_test_page")
	public ModelAndView open_api_test_page(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("demo/api_test_page");
        return modelAndView;
	}
	
	
	/**
	 * api����ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/sql_excel_export")
	public AjaxReturnMsg<String> sql_excel_export(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String sql="select * from DEMO_AC01";   
		return null;
	}
	
	
	

}
