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
 * ������ѵ��౸���걨
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
	 * �������걨�б�ҳ��
	 */
	@AddToken
	@RequestMapping(value="main", method=RequestMethod.GET)
	//@RequiresPermissions("skill:main")
	public ModelAndView draglist(HttpServletRequest request, Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("train/ZYNLJS_WEB_001_001/main");
		return modelAndView;
	}

	/**
	 * �鿴�����Ϣ
	 */
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/look/{CHB100:\\d+}", method=RequestMethod.GET)
	public String look(HttpSession session, ModelMap map, @PathVariable String CHB100) 
			throws Exception{
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		map.put("base", data);
		return PREFIX + "look";
	}
	
	/**
	 * �������걨������Ϣ
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
	 * �������걨������Ϣ
	 */
	@AddToken
	//@RequiresPermissions("skill:main")
	@RequestMapping(value="/apply", method=RequestMethod.GET)
	public String toAddClass(){
		return "train/ZYNLJS_WEB_001_001/apply";
	}
	
	/**
	 * ��ѵ����
	 */
	@AddToken
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/register/{CHB100:\\d+}",method=RequestMethod.GET)
	public String toRegister(HttpSession session, ModelMap map, @PathVariable String CHB100) 
			throws Exception{
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		map.put("CHB100", CHB100);
		return PREFIX + "student";
	}
	
	/**
	 * ����γ���Ϣ����
	 */
	@AddToken
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/toCourse/{CHB100:\\d+}", method=RequestMethod.GET)
	public String toCourse(HttpSession session, ModelMap modelMap, @PathVariable String CHB100)  
			throws Exception{
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		modelMap.put("CHB100", CHB100);
		return PREFIX + "course";
	}
	
	/**
	 * ��౸��������Ϣ�б� 
	 */
	@ResponseBody
	//@RequiresPermissions("skilltrain")
	@RequestMapping(value="/getInfoList", method=RequestMethod.POST)
	public HashMap<String,Object> getInfoList(Search search) throws Exception{
		search.setChb103("1");//��ѵ����(1-������ѵ�� 5-��ҵ��ѵ��9-��ҵ�����˲�����)
		return service.getInfoList(search);
	}
	
	/**
	 * ѧԱ��Ϣ�б� 
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getStudentList", method=RequestMethod.POST)
	public AjaxReturnMsg getStudentList(HttpSession session, String CHB100, Integer source) 
			throws Exception{
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//source,1-��ѯ��ʱ��2-��ѯ��ʽ��,null�Ļ�Ĭ����ʱ��
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
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
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
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		Map<String, Object> list = service.getCondition(data.get("ACA111X").toString(), 
				data.get("ACA11AX").toString());
		map.put("list", list.get("datas"));
		return PREFIX + "condition";
	}
	
	/**
	 * �γ���Ϣ�б� 
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getCourseList", method=RequestMethod.POST)
	public AjaxReturnMsg getCourseList(HttpSession session, String CHB100, Integer source) 
			throws Exception{
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//source,1-��ѯ��ʱ��2-��ѯ��ʽ��null�Ļ�Ĭ����ʱ��
			source = source == null ? 1 : source;
			Map<String, Object> map = service.getCourseList(CHB100, source);
			return this.success(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * �������걨������Ϣ
	 */
	@ValidateToken
	@ResponseBody
	//@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/saveBaseInfo", method=RequestMethod.POST)
	public AjaxReturnMsg saveBaseInfo(HttpSession session, Hb68 h) throws Exception{
		try{
			//������ͣ�1��������ѵ���
			h.setChb103("1");
			String chb100 = h.getChb100();
			if(StringUtils.isNotEmpty(chb100)){//�޸İ����Ϣ
				//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
				Map<String, Object> data = service.getById(chb100);
				validate(session, data.get("CHB320").toString());
				if(!data.get("CHB310").toString().equals("0")){//����ֻ���޸�δ�ύ�İ����Ϣ
					return this.error("��Ǹ�����桰"+chb100+"���İ����Ϣʧ��");
				}
				service.updateBaseInfo(h);
				return this.success("���ã��޸ġ�"+chb100+"���İ����Ϣ�ɹ�", chb100);
			}else{
				if((h.getAca11a().equals("09") || h.getAca11a().equals("10")) && h.getChb526().equals("2")){
					return this.error("��Ա����Ϊ���˵�λְ���Ĳ����걨ר������֤");
				}
				SUser suser = SysUserUtil.getCurrentUser();
				//�û����(������š����˱�ŵ�)
				String AAB001 = suser.getUserid();
				//String AAB001 = suser.getMemberid();
				h.setAab001(AAB001);
				h.setAae011(suser.getUserid());
				h.setChb320(suser.getUserid());
				chb100 = service.saveBaseInfo(h);  //���ذ�౸�����
				return this.success("���ã����桰"+chb100+"���İ����Ϣ�ɹ�", chb100);
			}
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * ɾ������걨������Ϣ��������Ϣ���γ���Ϣ
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	public AjaxReturnMsg delete(HttpSession session, String CHB100) throws Exception{
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			if(!data.get("CHB310").toString().equals("0")){//����ֻ��ɾ��δ�ύ�İ����Ϣ
				return this.error("��Ǹ��ɾ����"+CHB100+"���İ����Ϣʧ��");
			}
			service.deleteClassById(CHB100);//ɾ������걨������Ϣ
			service.deleteStudentById(CHB100);//ɾ��������Ϣ
			service.deleteTempStuById(CHB100);
			service.deleteCourseById(CHB100);//ɾ���γ���Ϣ
			service.deleteTempCouById(CHB100);
			return this.success("���ã�ɾ����"+CHB100+"���İ����Ϣ�ɹ�");
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * �ϴ�ѧԱ��Ϣ
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "uploadStudent", method=RequestMethod.POST)
	public void impStudent(HttpSession session, HttpServletResponse response, MultipartFile file, 
			String CHB100) throws Exception {
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try{
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			if(!suffix.equalsIgnoreCase("xls") && !suffix.equalsIgnoreCase("xlsx")){
				out.print(JSONObject.fromObject(this.error("��Ǹ�����ϴ�xls��xlsx��׺��Excel�ļ�")));
        		return;
			}
			//ͨ�������excel�ļ���ȡ��Ӧ����Ϣ
			ExcelUtil poi = new ExcelUtil(); 
			List<List<String>> list = poi.read(file.getInputStream(), file.getOriginalFilename());
			//����Ƿ���ģ���ļ�
			int n = 0;
			if(!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("����") || 
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("�Ļ��̶�") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("���֤����") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("��ͥסַ") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("��ϵ�绰") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("��Ա���") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("����") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("���ϵļ����걨����") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("�Ա�") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("��������") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("��ҵ֤(ѧ��֤)��") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("��ҵ��ҵ֤��") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("�籣���") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("¼�õ�λ���򴴰���ҵ���ƣ�") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("���ڵ�λ��ѧУ�����ҵѧУ") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("�μӹ�������") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("�����ֹ���") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("���м����ȼ�") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("����֤����") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("���ڸ�λ") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("�Ƿ�Ӧ���ҵ��") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("����֤�鷢֤����") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("����/����") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("�Ƿ�Ը�����߶��˲ż��ܿ�") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("����н��") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("����������") ||
	    			!list.get(0).get(n++).replaceAll("\r|\n", "").trim().equals("��ְ����")){
				out.print(JSONObject.fromObject(this.error(("��Ǹ���ϴ���ģ���ļ�����ȷ������������ģ��"))));
	    		return;
	    	}else if(list.size() > 200){
	    		out.print(JSONObject.fromObject(this.error("��Ǹ�������ļ���ѧԱ��Ϣ����")));
	    		return;
	    	}
			List<Hc60_Temp> student = new ArrayList<Hc60_Temp>();
	        for (int i=1; i<list.size(); i++) {
	        	List<String> sublist = list.get(i);
				Hc60_Temp info = new Hc60_Temp();
	        	int m = 0;
	        	//�����һ����Ԫ��Ϊ�գ�������һѭ��
	            if(sublist.get(m).replaceAll("\r|\n", "").trim().equals("")){
	            	continue;
	        	}
	            info.setChb100(CHB100);
	            String AAC003 = sublist.get(m++).replaceAll("\r|\n", "").trim();//����
	        	info.setAac003(AAC003);
	        	String ACA011 = sublist.get(m++).replaceAll("\r|\n", "").trim();//ѧ��
	        	info.setAac011(ACA011);
	        	String AAC002 = sublist.get(m++).replaceAll("\r|\n", "").trim();//���֤����
	        	info.setAac002(AAC002);
	        	String AAE006 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��ͥסַ
	        	info.setAae006(AAE006);
	        	String AAE005 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��ϵ�绰
	        	info.setAae005(AAE005);
	        	String CHC002 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��Ա���
	        	info.setChc002(CHC002);
	        	String AAC026 = sublist.get(m++).replaceAll("\r|\n", "").trim();//����
	        	info.setAac026(AAC026);
	        	String CHC037 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�����걨����
	        	info.setChc037(CHC037);
	        	String AAC004 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ա�
	        	info.setAac004(AAC004);
	        	String AAC006 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��������
	        	info.setAac006(AAC006);
	        	String AAE007 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��ҵ֤
	        	info.setAae007(AAE007);
	        	String CHC003 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��ҵ��ҵ֤
	        	info.setChc003(CHC003);
	        	String AAC008 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�籣���
	        	info.setAac008(AAC008);
	        	String CHC007 = sublist.get(m++).replaceAll("\r|\n", "").trim();//¼�õ�λ���򴴰���ҵ���ƣ�
	        	info.setChc007(CHC007);
	        	String AAB004 = sublist.get(m++).replaceAll("\r|\n", "").trim();//���ڵ�λ��ѧУ�����ҵѧУ
	        	info.setAab004(AAB004);
	        	String AAC007 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�μӹ�������
	        	info.setAac007(AAC007);
	        	String CHC006 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�����ֹ���
	        	info.setChc006(CHC006);
	        	String AAC015 = sublist.get(m++).replaceAll("\r|\n", "").trim();//���м����ȼ�
	        	info.setAac015(AAC015);
	        	String CHC010 = sublist.get(m++).replaceAll("\r|\n", "").trim();//����֤����
	        	info.setChc010(CHC010);
	        	String CHC034 = sublist.get(m++).replaceAll("\r|\n", "").trim();//���ڸ�λ
	        	info.setChc034(CHC034);
	        	String CHC035 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ƿ���Ӧ���ҵ��
	        	info.setChc035(CHC035);
	        	String CHC011 = sublist.get(m++).replaceAll("\r|\n", "").trim();//����֤�鷢֤����
	        	info.setChc011(CHC011);
	        	String CHC009 = sublist.get(m++).replaceAll("\r|\n", "").trim();//����
	        	info.setChc009(CHC009);
	        	String CHC049 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ƿ����߶˼����˲ſ�
	        	info.setChc049(CHC049);
	        	String CHC052 = sublist.get(m++).replaceAll("\r|\n", "").trim();//����н��
	        	info.setChc052(CHC052);
	        	String CHC053 = sublist.get(m++).replaceAll("\r|\n", "").trim();//����ְλ����
	        	info.setChc053(CHC053);
	        	String CHC054 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��ְ����
	        	info.setChc054(CHC054);
	        	if(CHC009.equals("") || CHC009.equals("�й���½")){
	        		if(AAC002 != null){
	        			if(AAC002.length() == 15){
		        			try{
			        			int sex = Integer.valueOf(AAC002.substring(14));
			        			info.setAac004(sex % 2 == 1 ? "��" : "Ů");
			        			String birthday = AAC002.substring(6, 12);
			        			info.setAac006("19"+birthday.substring(0, 2)+"-"+birthday.substring(2, 4) + "-"
			        			    + birthday.substring(4,6));
		        			}catch (Exception e) {
								
							}
		        		}else{
		        			try{
			        			int sex = Integer.valueOf(AAC002.substring(16, 17));
			        			info.setAac004(sex % 2 == 1 ? "��" : "Ů");
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
	        service.deleteTempStuById(CHB100);//���ݰ�౸�����ɾ����ʱѧԱ��Ϣ
	        service.batchSaveStuTemp(student);  //�������浽HC60_TEMP��ʱ��
	        service.checkStudent(CHB100);  //���ô洢����У��
			out.print(JSONObject.fromObject(this.success(new String("���ã��ϴ��ļ���"+fileName+"���ɹ�"))));
		}catch(Exception e){
			e.printStackTrace();
			out.print(JSONObject.fromObject(this.error("��Ǹ���ϴ��ļ�ʧ�ܣ�" + e)));
		}finally{
			out.close();
		}
	}
	
	/**
	 * �ϴ��γ���Ϣ
	 */
	@ResponseBody
	@RequestMapping(value = "uploadCourse", method=RequestMethod.POST)
	public void impCourse(HttpSession session, HttpServletResponse response, MultipartFile file, 
			String CHB100) throws Exception {
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		response.setContentType("text/html;chartset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//ͨ�������excel�ļ���ȡ��Ӧ����Ϣ
		try {
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			if(!suffix.equalsIgnoreCase("xls") && !suffix.equalsIgnoreCase("xlsx")){
				out.print(JSONObject.fromObject(this.error("��Ǹ�����ϴ�xls��xlsx��׺��Excel�ļ�")));
        		return;
			}
    		ExcelUtil poi = new ExcelUtil(); 
    		List<List<String>> list = poi.read(file.getInputStream(), file.getOriginalFilename()); 
    		if(!list.get(0).get(0).replaceAll("\r|\n", "").trim().equals("�Ͽ�����") || 
        			!list.get(0).get(1).replaceAll("\r|\n", "").trim().equals("�Ͽ�����") ||
        			!list.get(0).get(2).replaceAll("\r|\n", "").trim().equals("�Ͽ�ʱ��") ||
        			!list.get(0).get(3).replaceAll("\r|\n", "").trim().equals("�Ͽο�ʼʱ��") ||
        			!list.get(0).get(4).replaceAll("\r|\n", "").trim().equals("�Ͽν���ʱ��") ||
        			!list.get(0).get(5).replaceAll("\r|\n", "").trim().equals("��ʦ���֤��") ||
        			!list.get(0).get(6).replaceAll("\r|\n", "").trim().equals("��ʦ����") ||
        			!list.get(0).get(7).replaceAll("\r|\n", "").trim().equals("�Ͽε�ַ")){
    			out.print(JSONObject.fromObject(this.error("��Ǹ���ϴ���ģ���ļ�����ȷ������������ģ��")));
        		return;
        	}else if(list.size() > 200){
	    		out.print(JSONObject.fromObject(this.error("��Ǹ�������ļ��пγ���Ϣ����")));
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
                String CHB158 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ͽ�����
            	info.setChb158(CHB158);
            	String CHB160 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ͽ�����
            	info.setChb160(CHB160);
            	String CHB171 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ͽ�ʱ��
            	info.setChb171(CHB171);
            	String CHB167 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ͽο�ʼʱ��
            	info.setChb167(CHB167);
            	String CHB186 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ͽν���ʱ��
            	info.setChb186(CHB186);
            	String AAC002 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��ʦ���֤��
            	info.setAac002(AAC002);
            	String AAC003 = sublist.get(m++).replaceAll("\r|\n", "").trim();//��ʦ����
            	info.setAac003(AAC003);
            	String CHB161 = sublist.get(m++).replaceAll("\r|\n", "").trim();//�Ͽε�ַ
            	info.setChb161(CHB161);
            	info.setEae052("0");
            	info.setChb100(CHB100);
            	info.setChb060("SEQ_CHB060.NEXTVAL");
            	course.add(info);
            }
    		service.deleteTempCouById(CHB100);//��տ⻺�����ԭ��¼
    		service.batchSaveCouTemp(course); //�������浽hb69_temp
    		service.checkCourse(CHB100);
    		out.print(JSONObject.fromObject(this.success("���ã��ϴ��ļ���"+fileName+"���ɹ�")));
        } catch (Exception e) {
            e.printStackTrace();
            out.print(JSONObject.fromObject(this.error("��Ǹ���ϴ��ļ�ʧ�ܣ�"+e)));
        }finally{
			out.close();
		}
	}
	
	/**
	 * ����ʱѧԱ��Ϣ���浽HC60
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="saveStudent", method=RequestMethod.POST)
	public AjaxReturnMsg saveStudent(HttpSession session, String CHB100) throws Exception {
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			Map<String, Object> map = service.getStudentList(CHB100, 1);
			List<Map<String, String>> list = (List<Map<String, String>>)map.get("datas");
			if(list.size() == 0 || !list.get(0).get("PASS").toString().equals("0")){
				return this.success("��Ǹ������ѧԱ��Ϣʧ��");
			}
			//���ô洢���̣�����ʱ�����ݱ��浽ac01��hc60
			service.saveStudent(CHB100);
			return this.success("���ã�����ѧԱ��Ϣ�ɹ�");
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * ����ʱ�γ���Ϣ���浽HB69
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "saveCourse", method=RequestMethod.POST)
	public AjaxReturnMsg saveCourse(HttpSession session, String CHB100) throws Exception {
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			
			Map<String, Object> map = service.getCourseList(CHB100, 1);
			List<Map<String, String>> list = (List<Map<String, String>>)map.get("datas");
			if(list.size() == 0 || !list.get(0).get("PASS").equals("0")){
				return this.error("��Ǹ������γ���Ϣʧ��");
			}
			service.saveCourse(CHB100);//���ô洢����
			return this.success("���ã�����γ���Ϣ�ɹ�");
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * �ύ����
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="submitApply",method=RequestMethod.POST)
	public AjaxReturnMsg submitExamApply(HttpSession session,String CHB100) throws Exception{
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//�ύʱУ��ѧԱ�Ϳγ���Ϣ�Ƿ�Ϊ��
			List<Map<String, String>> student = (List<Map<String, String>>)
					service.getStudentList(CHB100, 2).get("datas");
			if(student.size() == 0){
				return this.error("��Ǹ��ѧԱ��ϢΪ�գ��޷��ύ");
			}
			List<Map<String, String>> course = (List<Map<String, String>>)
					service.getCourseList(CHB100, 2).get("datas");
			if(course.size() == 0){
				return this.error("��Ǹ���γ���ϢΪ�գ��޷��ύ");
			}
			//У���������Ƿ�һ��
			if(Integer.valueOf(data.get("CHB106").toString()) != student.size()){
				return this.error("��Ǹ���ƻ���ѵ������ѧԱ������һ�£��޷��ύ");
			}
			SUser suser = (SUser)session.getAttribute("suser");
			String AAE011 = suser.getUsername();//��¼�û���
			service.submitApply(CHB100, AAE011);
			//�ύ��ɾ������������
			service.deleteTempStuById(CHB100);
			service.deleteTempCouById(CHB100);
			return this.success("���ã��ύ��"+CHB100+"���İ����Ϣ�ɹ�");
		}catch (Exception e) {
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	
	/**
	 * ��������
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="revokeApply",method=RequestMethod.POST)
	public AjaxReturnMsg revokeApply(HttpSession session,String CHB100) throws Exception{
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//У���Ƿ����ύδ���
			if(data.get("CHB310").toString().equals("1") && data.get("CHB315").toString().equals("2")){
				service.revokeApply(CHB100);
				return this.success("�����ɹ�");
			}else{
				return this.success("ֻ�����ύδ��˵Ĳ��ܳ���");
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
	 * �жϵ�ǰҵ�������Ƿ����ڵ�ǰ�û�
	 */
	public void validate(HttpSession session, String CHB320) throws AppException{
		SUser suser = (SUser)session.getAttribute("suser");
		String username = suser.getUsername();//�û���
		if(!username.equals(CHB320)){
			throw new AppException("��౸��������û���ƥ��");
		}
	}
	
	/**
	 * ���벹��У�����
	 */
	@AddToken
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/openSubCheck/{CHB100:\\d+}", method=RequestMethod.GET)
	public String openSubCheck(HttpSession session, ModelMap modelMap, @PathVariable String CHB100)  
			throws Exception{
		//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
		Map<String, Object> data = service.getById(CHB100);
		validate(session, data.get("CHB320").toString());
		modelMap.put("CHB100", CHB100);
		return PREFIX + "subcheck";
	}
	/**
	 * ѧԱ������Ϣ�б� 
	 */
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value = "/getStuSubInfoList", method=RequestMethod.POST)
	public AjaxReturnMsg getStuSubInfoList(HttpSession session, String CHB100, Integer source) throws Exception{
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			//source,1-��ѯ��ʱ��2-��ѯ��ʽ��,null�Ļ�Ĭ����ʱ��
			Map<String, Object> map = service.getStudentList(CHB100,  2);
			return this.success(map);
		}catch(Exception e){
			e.printStackTrace();
			return this.error(e);
		}
	}
	
	/**
	 * ����У��
	 */
	@SuppressWarnings("unchecked")
	@ValidateToken
	@ResponseBody
	@RequiresPermissions("skilltrain")
	@RequestMapping(value="/checkStuSub",method=RequestMethod.POST)
	public AjaxReturnMsg checkStuSub(HttpSession session,String CHB100) throws Exception{
		try{
			//У��ð����Ϣ�뵥λ��Ϣ�Ƿ�ƥ��
			Map<String, Object> data = service.getById(CHB100);
			validate(session, data.get("CHB320").toString());
			
			service.checkStuSub(CHB100);
			return this.success("У��ɹ�");
		}catch (Exception e) {
			e.printStackTrace();
			return this.error(e);
		}
	}
}
