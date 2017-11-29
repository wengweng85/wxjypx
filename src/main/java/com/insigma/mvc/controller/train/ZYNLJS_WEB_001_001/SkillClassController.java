package com.insigma.mvc.controller.train.ZYNLJS_WEB_001_001;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.*;
import com.insigma.mvc.service.train.ZYNLJS_WEB_001_001.OpenClassService;
import com.insigma.shiro.realm.SysUserUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.insigma.common.annotation.AddToken;
import com.insigma.common.annotation.ValidateToken;
import com.insigma.common.util.ExcelUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.resolver.AppException;
import org.springframework.web.servlet.ModelAndView;

/**
 * 技能培训办班备案申报
 * @author xujiyu
 * 2016-09-20
 *
 */
@Controller
@RequestMapping("/skill")
public class SkillClassController extends MvcHelper {
	
	private static final String PREFIX = "train/ZYNLJS_WEB_001_001/";
	
	@Resource
	private OpenClassService service;
	
	/**
	 * 进入办班申报列表页面
	 */
	@AddToken
	@RequestMapping(value="main", method=RequestMethod.GET)
	//@RequiresPermissions("skill:main")
	public ModelAndView draglist(HttpServletRequest request, Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("train/ZYNLJS_WEB_001_001/main");
		return modelAndView;
	}

	/**
	 * 查看办班信息
	 */
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/look/{CHB100:\\d+}", method=RequestMethod.GET)
	public String look(HttpSession session, ModelMap map, @PathVariable String CHB100) 
			throws Exception{
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		map.put("base", data);
		return PREFIX + "look";
	}
	
	/**
	 * 进入办班申报基础信息
	 */
	@AddToken
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/apply/{CHB100:\\d+}", method=RequestMethod.GET)
	public String toApplyClass(HttpSession session, ModelMap map, @PathVariable String CHB100) 
			throws Exception{
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		map.put("base", data);
		return PREFIX+ "apply";
	}
	
	/**
	 * 进入办班申报基础信息
	 */
	@AddToken
	//@RequiresPermissions("skill:main")
	@RequestMapping(value="/apply", method=RequestMethod.GET)
	public String toAddClass(){
		return "train/ZYNLJS_WEB_001_001/apply";
	}
	
	/**
	 * 培训报名
	 */
	@AddToken
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/register/{CHB100:\\d+}",method=RequestMethod.GET)
	public String toRegister(HttpSession session, ModelMap map, @PathVariable String CHB100) 
			throws Exception{
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		map.put("CHB100", CHB100);
		return PREFIX + "student";
	}
	
	/**
	 * 进入课程信息界面
	 */
	@AddToken
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/toCourse/{CHB100:\\d+}", method=RequestMethod.GET)
	public String toCourse(HttpSession session, ModelMap modelMap, @PathVariable String CHB100)  
			throws Exception{
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		modelMap.put("CHB100", CHB100);
		return PREFIX + "course";
	}
	
	/**
	 * 办班备案基础信息列表 
	 */
	@ResponseBody
	//@RequiresPermissions("skilltrain")
	@RequestMapping(value="/getInfoList", method=RequestMethod.POST)
	public HashMap<String,Object> getInfoList(Search search) throws Exception{
		search.setChb103("1");//培训类型(1-技能培训、 5-创业培训、9-企业技能人才评价)
		return service.getInfoList(search);
	}
	
	/**
	 * 学员信息列表 
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getStudentList", method=RequestMethod.POST)
	public AjaxReturnMsg getStudentList(HttpSession session, String CHB100, Integer source) 
			throws Exception{
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//source,1-查询临时表，2-查询正式表,null的话默认临时表
			source = source == null ? 1 : source;
			Map<String, Object> map = service.getStudentList(CHB100,  source);
			return this.success(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}

	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getStuById/{CHC001:\\d+}", method=RequestMethod.GET)
	public String getStuById(HttpSession session,ModelMap map, @PathVariable String CHC001, 
			String CHB100) throws Exception{
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		Map<String, Object> stu = service.getStuById(CHC001);
		map.put("base", stu);
		return PREFIX + "layer_stu";
	}
	
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getByJobAndLevel", method=RequestMethod.POST)
	public AjaxReturnMsg getByJobAndLevel( String ACA111, String ACA11A) throws Exception{
		try{
			Map<String, Object> map = service.getByJobAndLevel(ACA111, ACA11A);
			return this.success(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getCondition/{CHB100:\\d+}", method=RequestMethod.GET)
	public String getCondition(HttpSession session,ModelMap map, @PathVariable String CHB100) 
			throws Exception{
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		Map<String, Object> list = service.getCondition(data.get("ACA111X").toString(), 
				data.get("ACA11AX").toString());
		map.put("list", list.get("datas"));
		return PREFIX + "condition";
	}
	
	/**
	 * 课程信息列表 
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getCourseList", method=RequestMethod.POST)
	public AjaxReturnMsg getCourseList(HttpSession session, String CHB100, Integer source) 
			throws Exception{
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//source,1-查询临时表，2-查询正式表，null的话默认临时表
			source = source == null ? 1 : source;
			Map<String, Object> map = service.getCourseList(CHB100, source);
			return this.success(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * 保存办班申报基础信息
	 */
	@ValidateToken
	@ResponseBody
	//@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/saveBaseInfo", method=RequestMethod.POST)
	public AjaxReturnMsg saveBaseInfo(HttpSession session, Hb68 h) throws Exception{
		try{
			//办班类型，1：技能培训办班
			h.setChb103("1");
			String chb100 = h.getChb100();
			if(StringUtils.isNotEmpty(chb100)){//修改办班信息
				//校验该办班信息与单位信息是否匹配
				Map<String, Object> data = service.getById(chb100);
				validate(session, data.get("CHB320").toString());
				if(!data.get("CHB310").toString().equals("0")){//限制只能修改未提交的办班信息
					return this.error("抱歉，保存“"+chb100+"”的办班信息失败");
				}
				service.updateBaseInfo(h);
				return this.success("您好，修改“"+chb100+"”的办班信息成功", chb100);
			}else{
				if((h.getAca11a().equals("09") || h.getAca11a().equals("10")) && h.getChb526().equals("2")){
					return this.error("人员类型为用人单位职工的不能申报专项能力证");
				}
				SUser suser = SysUserUtil.getCurrentUser();
				//用户编号(机构编号、个人编号等)
				String AAB001 = suser.getUserid();
				//String AAB001 = suser.getMemberid();
				h.setAab001(AAB001);
				h.setAae011(suser.getUserid());
				h.setChb320(suser.getUserid());
				chb100 = service.saveBaseInfo(h);  //返回办班备案编号
				return this.success("您好，保存“"+chb100+"”的办班信息成功", chb100);
			}
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * 删除办班申报基础信息、报名信息、课程信息
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	public AjaxReturnMsg delete(HttpSession session, String CHB100) throws Exception{
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			if(!data.get("CHB310").toString().equals("0")){//限制只能删除未提交的办班信息
				return this.error("抱歉，删除“"+CHB100+"”的办班信息失败");
			}
			service.deleteClassById(CHB100);//删除办班申报基础信息
			service.deleteStudentById(CHB100);//删除报名信息
			service.deleteTempStuById(CHB100);
			service.deleteCourseById(CHB100);//删除课程信息
			service.deleteTempCouById(CHB100);
			return this.success("您好，删除“"+CHB100+"”的办班信息成功");
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * 上传学员信息
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "uploadStudent", method=RequestMethod.POST)
	public void impStudent(HttpSession session, HttpServletResponse response, MultipartFile file, 
			String CHB100) throws Exception {
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try{
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			if(!suffix.equalsIgnoreCase("xls") && !suffix.equalsIgnoreCase("xlsx")){
				out.print(JSONObject.fromObject(this.error("抱歉，请上传xls或xlsx后缀的Excel文件")));
        		return;
			}
			//通过导入的excel文件获取对应的信息
			ExcelUtil poi = new ExcelUtil(); 
			List<List<String>> list = poi.read(file.getInputStream(), file.getOriginalFilename());
			//检查是否是模板文件
			int n = 0;
			if(!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("姓名") || 
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("文化程度") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("身份证号码") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("家庭住址") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("联系电话") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("人员类别") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("户籍") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("符合的鉴定申报条件") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("性别") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("出生日期") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("毕业证(学生证)号") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("就业创业证号") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("社保编号") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("录用单位（或创办企业名称）") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("所在单位（学校）或毕业学校") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("参加工作日期") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("本工种工龄") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("已有技术等级") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("已有证书编号") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("所在岗位") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("是否应届毕业生") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("已有证书发证日期") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("国籍/地区") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("是否愿意加入高端人才技能库") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("期望薪资") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("意向工作地区") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("求职意向")){
				out.print(JSONObject.fromObject(this.error(("抱歉，上传的模板文件不正确，请下载最新模板"))));
	    		return;
	    	}else if(list.size() > 200){
	    		out.print(JSONObject.fromObject(this.error("抱歉，导入文件中学员信息过多")));
	    		return;
	    	}
			List<Hc60_Temp> student = new ArrayList<Hc60_Temp>();
	        for (int i=1; i<list.size(); i++) {
	        	List<String> sublist = list.get(i);
				Hc60_Temp info = new Hc60_Temp();
	        	int m = 0;
	        	//如果第一个单元格为空，进入下一循环
	            if(sublist.get(m).replaceAll("\r|\n", "").trim().equals("")){
	            	continue;
	        	}
	            info.setChb100(CHB100);
	            String AAC003 = sublist.get(m++).replaceAll("\r|\n", "").trim();//姓名
	        	info.setAac003(AAC003);
	        	String ACA011 = sublist.get(m++).replaceAll("\r|\n", "").trim();//学历
	        	info.setAac011(ACA011);
	        	String AAC002 = sublist.get(m++).replaceAll("\r|\n", "").trim();//身份证号码
	        	info.setAac002(AAC002);
	        	String AAE006 = sublist.get(m++).replaceAll("\r|\n", "").trim();//家庭住址
	        	info.setAae006(AAE006);
	        	String AAE005 = sublist.get(m++).replaceAll("\r|\n", "").trim();//联系电话
	        	info.setAae005(AAE005);
	        	String CHC002 = sublist.get(m++).replaceAll("\r|\n", "").trim();//人员类别
	        	info.setChc002(CHC002);
	        	String AAC026 = sublist.get(m++).replaceAll("\r|\n", "").trim();//户籍
	        	info.setAac026(AAC026);
	        	String CHC037 = sublist.get(m++).replaceAll("\r|\n", "").trim();//符合申报条件
	        	info.setChc037(CHC037);
	        	String AAC004 = sublist.get(m++).replaceAll("\r|\n", "").trim();//性别
	        	info.setAac004(AAC004);
	        	String AAC006 = sublist.get(m++).replaceAll("\r|\n", "").trim();//出生日期
	        	info.setAac006(AAC006);
	        	String AAE007 = sublist.get(m++).replaceAll("\r|\n", "").trim();//毕业证
	        	info.setAae007(AAE007);
	        	String CHC003 = sublist.get(m++).replaceAll("\r|\n", "").trim();//就业创业证
	        	info.setChc003(CHC003);
	        	String AAC008 = sublist.get(m++).replaceAll("\r|\n", "").trim();//社保编号
	        	info.setAac008(AAC008);
	        	String CHC007 = sublist.get(m++).replaceAll("\r|\n", "").trim();//录用单位（或创办企业名称）
	        	info.setChc007(CHC007);
	        	String AAB004 = sublist.get(m++).replaceAll("\r|\n", "").trim();//所在单位（学校）或毕业学校
	        	info.setAab004(AAB004);
	        	String AAC007 = sublist.get(m++).replaceAll("\r|\n", "").trim();//参加工作日期
	        	info.setAac007(AAC007);
	        	String CHC006 = sublist.get(m++).replaceAll("\r|\n", "").trim();//本工种工龄
	        	info.setChc006(CHC006);
	        	String AAC015 = sublist.get(m++).replaceAll("\r|\n", "").trim();//已有技术等级
	        	info.setAac015(AAC015);
	        	String CHC010 = sublist.get(m++).replaceAll("\r|\n", "").trim();//已有证书编号
	        	info.setChc010(CHC010);
	        	String CHC034 = sublist.get(m++).replaceAll("\r|\n", "").trim();//所在岗位
	        	info.setChc034(CHC034);
	        	String CHC035 = sublist.get(m++).replaceAll("\r|\n", "").trim();//是否是应届毕业生
	        	info.setChc035(CHC035);
	        	String CHC011 = sublist.get(m++).replaceAll("\r|\n", "").trim();//已有证书发证日期
	        	info.setChc011(CHC011);
	        	String CHC009 = sublist.get(m++).replaceAll("\r|\n", "").trim();//国籍
	        	info.setChc009(CHC009);
	        	String CHC049 = sublist.get(m++).replaceAll("\r|\n", "").trim();//是否加入高端技能人才库
	        	info.setChc049(CHC049);
	        	String CHC052 = sublist.get(m++).replaceAll("\r|\n", "").trim();//期望薪资
	        	info.setChc052(CHC052);
	        	String CHC053 = sublist.get(m++).replaceAll("\r|\n", "").trim();//意向职位地区
	        	info.setChc053(CHC053);
	        	String CHC054 = sublist.get(m++).replaceAll("\r|\n", "").trim();//求职意向
	        	info.setChc054(CHC054);
	        	if(CHC009.equals("") || CHC009.equals("中国大陆")){
	        		if(AAC002 != null){
	        			if(AAC002.length() == 15){
		        			try{
			        			int sex = Integer.valueOf(AAC002.substring(14));
			        			info.setAac004(sex % 2 == 1 ? "男" : "女");
			        			String birthday = AAC002.substring(6, 12);
			        			info.setAac006("19"+birthday.substring(0, 2)+"-"+birthday.substring(2, 4) + "-"
			        			    + birthday.substring(4,6));
		        			}catch (Exception e) {
								
							}
		        		}else{
		        			try{
			        			int sex = Integer.valueOf(AAC002.substring(16, 17));
			        			info.setAac004(sex % 2 == 1 ? "男" : "女");
			        			String birthday = AAC002.substring(6, 14);
			        			info.setAac006(birthday.substring(0, 4)+"-"+birthday.substring(4, 6) + "-"
			        			    + birthday.substring(6,8));
		        			}catch (Exception e) {
								
							}
		        		}
	        		}
	        	}
	        	info.setChb100(CHB100);
	        	info.setEae052("0");
	        	info.setChc001("SEQ_CHC001.NEXTVAL");
	        	student.add(info);
	        }
	        service.deleteTempStuById(CHB100);//根据办班备案编号删除临时学员信息
	        service.batchSaveStuTemp(student);  //批量保存到HC60_TEMP临时表
	        service.checkStudent(CHB100);  //调用存储过程校验
			out.print(JSONObject.fromObject(this.success(new String("您好，上传文件“"+fileName+"”成功"))));
		}catch(Exception e){
			e.printStackTrace();
			out.print(JSONObject.fromObject(this.error("抱歉，上传文件失败，" + e)));
		}finally{
			out.close();
		}
	}
	
	/**
	 * 上传课程信息
	 */
	@ResponseBody
	@RequestMapping(value = "uploadCourse", method=RequestMethod.POST)
	public void impCourse(HttpSession session, HttpServletResponse response, MultipartFile file, 
			String CHB100) throws Exception {
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		response.setContentType("text/html;chartset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//通过导入的excel文件获取对应的信息
		try {
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			if(!suffix.equalsIgnoreCase("xls") && !suffix.equalsIgnoreCase("xlsx")){
				out.print(JSONObject.fromObject(this.error("抱歉，请上传xls或xlsx后缀的Excel文件")));
        		return;
			}
    		ExcelUtil poi = new ExcelUtil(); 
    		List<List<String>> list = poi.read(file.getInputStream(), file.getOriginalFilename()); 
    		if(!list.get(0).get(0).replaceAll("\r|\n", "").trim().equals("上课内容") || 
        			!list.get(0).get(1).replaceAll("\r|\n", "").trim().equals("上课日期") ||
        			!list.get(0).get(2).replaceAll("\r|\n", "").trim().equals("上课时段") ||
        			!list.get(0).get(3).replaceAll("\r|\n", "").trim().equals("上课开始时间") ||
        			!list.get(0).get(4).replaceAll("\r|\n", "").trim().equals("上课结束时间") ||
        			!list.get(0).get(5).replaceAll("\r|\n", "").trim().equals("教师身份证号") ||
        			!list.get(0).get(6).replaceAll("\r|\n", "").trim().equals("教师姓名") ||
        			!list.get(0).get(7).replaceAll("\r|\n", "").trim().equals("上课地址")){
    			out.print(JSONObject.fromObject(this.error("抱歉，上传的模板文件不正确，请下载最新模板")));
        		return;
        	}else if(list.size() > 200){
	    		out.print(JSONObject.fromObject(this.error("抱歉，导入文件中课程信息过多")));
	    		return;
	    	}
    		List<Hb69> course = new ArrayList<Hb69>();
    		for (int i=1; i<list.size(); i++) {
            	List<String> sublist = list.get(i);
				Hb69 info = new Hb69();
            	int m = 0;
                if(sublist.get(0).replaceAll("\r|\n", "").trim().equals("")){
                	continue;
            	}
                String CHB158 = sublist.get(m++).replaceAll("\r|\n", "").trim();//上课内容
            	info.setChb158(CHB158);
            	String CHB160 = sublist.get(m++).replaceAll("\r|\n", "").trim();//上课日期
            	info.setChb160(CHB160);
            	String CHB171 = sublist.get(m++).replaceAll("\r|\n", "").trim();//上课时段
            	info.setChb171(CHB171);
            	String CHB167 = sublist.get(m++).replaceAll("\r|\n", "").trim();//上课开始时间
            	info.setChb167(CHB167);
            	String CHB186 = sublist.get(m++).replaceAll("\r|\n", "").trim();//上课结束时间
            	info.setChb186(CHB186);
            	String AAC002 = sublist.get(m++).replaceAll("\r|\n", "").trim();//教师身份证号
            	info.setAac002(AAC002);
            	String AAC003 = sublist.get(m++).replaceAll("\r|\n", "").trim();//教师姓名
            	info.setAac003(AAC003);
            	String CHB161 = sublist.get(m++).replaceAll("\r|\n", "").trim();//上课地址
            	info.setChb161(CHB161);
            	info.setEae052("0");
            	info.setChb100(CHB100);
            	info.setChb060("SEQ_CHB060.NEXTVAL");
            	course.add(info);
            }
    		service.deleteTempCouById(CHB100);//清空库缓存表中原记录
    		service.batchSaveCouTemp(course); //批量保存到hb69_temp
    		service.checkCourse(CHB100);
    		out.print(JSONObject.fromObject(this.success("您好，上传文件“"+fileName+"”成功")));
        } catch (Exception e) {
            e.printStackTrace();
            out.print(JSONObject.fromObject(this.error("抱歉，上传文件失败，"+e)));
        }finally{
			out.close();
		}
	}
	
	/**
	 * 将临时学员信息保存到HC60
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="saveStudent", method=RequestMethod.POST)
	public AjaxReturnMsg saveStudent(HttpSession session, String CHB100) throws Exception {
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			Map<String, Object> map = service.getStudentList(CHB100, 1);
			List<Map<String, String>> list = (List<Map<String, String>>)map.get("datas");
			if(list.size() == 0 || !list.get(0).get("PASS").toString().equals("0")){
				return this.success("抱歉，保存学员信息失败");
			}
			//调用存储过程，将临时表数据保存到ac01和hc60
			service.saveStudent(CHB100);
			return this.success("您好，保存学员信息成功");
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * 将临时课程信息保存到HB69
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "saveCourse", method=RequestMethod.POST)
	public AjaxReturnMsg saveCourse(HttpSession session, String CHB100) throws Exception {
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			
			Map<String, Object> map = service.getCourseList(CHB100, 1);
			List<Map<String, String>> list = (List<Map<String, String>>)map.get("datas");
			if(list.size() == 0 || !list.get(0).get("PASS").equals("0")){
				return this.error("抱歉，保存课程信息失败");
			}
			service.saveCourse(CHB100);//调用存储过程
			return this.success("您好，保存课程信息成功");
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * 提交申请
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="submitApply",method=RequestMethod.POST)
	public AjaxReturnMsg submitExamApply(HttpSession session,String CHB100) throws Exception{
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//提交时校验学员和课程信息是否为空
			List<Map<String, String>> student = (List<Map<String, String>>)
					service.getStudentList(CHB100, 2).get("datas");
			if(student.size() == 0){
				return this.error("抱歉，学员信息为空，无法提交");
			}
			List<Map<String, String>> course = (List<Map<String, String>>)
					service.getCourseList(CHB100, 2).get("datas");
			if(course.size() == 0){
				return this.error("抱歉，课程信息为空，无法提交");
			}
			//校验办班人数是否一致
			if(Integer.valueOf(data.get("CHB106").toString()) != student.size()){
				return this.error("抱歉，计划培训人数与学员人数不一致，无法提交");
			}
			SUser suser = (SUser)session.getAttribute("suser");
			String AAE011 = suser.getUsername();//登录用户名
			service.submitApply(CHB100, AAE011);
			//提交后删除缓存表的数据
			service.deleteTempStuById(CHB100);
			service.deleteTempCouById(CHB100);
			return this.success("您好，提交“"+CHB100+"”的办班信息成功");
		}catch (Exception e) {
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	
	/**
	 * 撤销申请
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="revokeApply",method=RequestMethod.POST)
	public AjaxReturnMsg revokeApply(HttpSession session,String CHB100) throws Exception{
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//校验是否已提交未审核
			if(data.get("CHB310").toString().equals("1") && data.get("CHB315").toString().equals("2")){
				service.revokeApply(CHB100);
				return this.success("撤销成功");
			}else{
				return this.success("只有已提交未审核的才能撤销");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	
	public String getNewFile(String path){
		File savePath = new File(path);
		File[] files = savePath.listFiles();
		if(files.length == 0){
			return "";
		}
		File file = null;
		for(int i=0; i<files.length; i++){
			if(i==0){
				file = files[i];
			}else if(files[i].lastModified() >= files[i-1].lastModified()){
				file = files[i];
			}
		}
		return file.getName();
	}
	
	/**
	 * 判断当前业务属于是否属于当前用户
	 */
	public void validate(HttpSession session, String CHB320) throws AppException{
		SUser suser = (SUser)session.getAttribute("suser");
		String username = suser.getUsername();//用户名
		if(!username.equals(CHB320)){
			throw new AppException("办班备案编号与用户不匹配");
		}
	}
	
	/**
	 * 进入补贴校验界面
	 */
	@AddToken
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/openSubCheck/{CHB100:\\d+}", method=RequestMethod.GET)
	public String openSubCheck(HttpSession session, ModelMap modelMap, @PathVariable String CHB100)  
			throws Exception{
		//校验该办班信息与单位信息是否匹配
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		modelMap.put("CHB100", CHB100);
		return PREFIX + "subcheck";
	}
	/**
	 * 学员补贴信息列表 
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getStuSubInfoList", method=RequestMethod.POST)
	public AjaxReturnMsg getStuSubInfoList(HttpSession session, String CHB100, Integer source) throws Exception{
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//source,1-查询临时表，2-查询正式表,null的话默认临时表
			Map<String, Object> map = service.getStudentList(CHB100,  2);
			return this.success(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * 补贴校验
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/checkStuSub",method=RequestMethod.POST)
	public AjaxReturnMsg checkStuSub(HttpSession session,String CHB100) throws Exception{
		try{
			//校验该办班信息与单位信息是否匹配
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			
			service.checkStuSub(CHB100);
			return this.success("校验成功");
		}catch (Exception e) {
			e.printStackTrace();
			return this.error(e);
		}
	}
}
