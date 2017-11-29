package com.insigma.mvc.serviceimp.train.ZYNLJS_WEB_001_001;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.train.ZYNLJS_WEB_001_001.OpenClassMapper;
import com.insigma.mvc.model.*;
import com.insigma.mvc.service.train.ZYNLJS_WEB_001_001.OpenClassService;
import com.insigma.shiro.realm.SysUserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;



@Service
public class OpenClassServiceImpl extends MvcHelper implements OpenClassService {
	
	@Resource
	private OpenClassMapper dao;
	
	/*@Resource
	private AuditRuntimeDao auditdao;*/

	@Override
	public HashMap<String, Object> getInfoList(Search search) throws Exception {
		SUser suser = SysUserUtil.getCurrentUser();
		String AAB001 = suser.getMemberid();//用户编号(机构编号、个人编号等)
		search.setAab001(AAB001);//单位编号
		search.setChb320(suser.getUserid());
		PageHelper.offsetPage(search.getOffset(), search.getLimit());
		List<Hb68> list=dao.getInfoList(search);
		PageInfo<Hb68> pageinfo = new PageInfo<Hb68>(list);
		return this.success_hashmap_response(pageinfo);
	}

	@Override
	public String saveBaseInfo(Hb68 h) throws Exception {
		dao.saveBaseInfo(h);
		return h.getChb100();
		
	}

	@Override
	public void deleteStudentById(String CHB100) throws Exception {
		dao.deleteStudentById(CHB100);
	}

	@Override
	public Map<String, Object> getStudentList(String CHB100, int source) throws Exception {
		return dao.getStudentList(CHB100, source);
	}

	@Override
	public Map<String, Object> getById(String CHB100) throws Exception {
		if(StringUtils.isEmpty(CHB100)){
			throw new Exception("办班备案编号为空");
		}
		return dao.getById(CHB100);
	}

	@Override
	public Map<String, Object> getByIdCard(String AAC002) throws Exception {
		return dao.getByIdCard(AAC002);
	}

	@Override
	public String addPerson(Ac01 ac01) throws Exception {
		return dao.addPerson(ac01);
	}

	@Override
	public void addStudent(Ac01 ac01, String CHB100) throws Exception {
		dao.addStudent(ac01, CHB100);
	}

	@Override
	public void deleteClassById(String CHB100) throws Exception {
		dao.deleteClassById(CHB100);
	}

	@Override
	public void deleteCourseById(String CHB100) throws Exception {
		dao.deleteCourseById(CHB100);	
	}

	@Override
	public Map<String, Object> getCourseList(String CHB100, int source) throws Exception {
		return dao.getCourseList(CHB100, source);
	}

	@Override
	public void addCourse(String CHB100) throws Exception {
		dao.addCourse(CHB100);;
	}

	@Override
	public void submitApply(String CHB100, String AAE011) throws Exception {
		//auditdao.auditTask("F001", CHB100, "01", AAE011, "1", "");
		dao.submit(CHB100);
	}

	@Override
	public void batchSaveStuTemp(List<Hc60_Temp> list) throws Exception {
		dao.batchSaveStuTemp(list);
	}

	@Override
	public void updateStuTemp(String CHB100, String AAC002, String EAE052, String AAE013) throws Exception {
		dao.updateStuTemp(CHB100, AAC002, EAE052, AAE013);
	}

	@Override
	public void deleteTempStuById(String CHB100) throws Exception {
		dao.deleteTempStuById(CHB100);
		
	}

	@Override
	public void deleteTempCouById(String CHB100) throws Exception {
		dao.deleteTempCouById(CHB100);
		
	}

	@Override
	public void batchSaveCouTemp(List<Hb69> list) throws Exception {
		dao.batchSaveCouTemp(list);
		
	}

	@Override
	public void updateCouTemp(String CHB060, String EAE052, String CHB154) throws Exception {
		dao.updateCouTemp(CHB060, EAE052, CHB154);
	}

	@Override
	public void updateBaseInfo(Hb68 h) throws Exception {
		dao.updateBaseInfo(h);
	}

	@Override
	public void checkStudent(String CHB100) throws Exception {
		dao.checkStudent(CHB100);
	}
	
	@Override
	public void checkBusinessStu(String CHB100) throws Exception {
		dao.checkBusinessStu(CHB100);
	}

	@Override
	public void saveStudent(String CHB100) throws Exception {
		dao.saveStudent(CHB100);
	}
	
	@Override
	public void saveBusinessStu(String CHB100) throws Exception {
		dao.saveBusinessStu(CHB100);
	}

	@Override
	public void checkCourse(String CHB100) throws Exception {
		dao.checkCourse(CHB100);
	}
	
	@Override
	public void saveCourse(String CHB100) throws Exception {
		dao.saveCourse(CHB100);
	}

	@Override
	public Map<String, Object> getStuById(String CHC001) throws Exception {
		return dao.getStuById(CHC001);
	}

	@Override
	public Map<String, Object> getCondition(String ACA111, String ACA11A) throws Exception {
		return dao.getCondition(ACA111, ACA11A);
	}

	@Override
	public Map<String, Object> getByJobAndLevel(String ACA111, String ACA11A) throws Exception {
		return dao.getByJobAndLevel(ACA111, ACA11A);
	}

	@Override
	public void checkBusinessCou(String CHB100) throws Exception {
		dao.checkBusinessCou(CHB100);
		
	}

	@Override
	public void saveBusinessCou(String CHB100) throws Exception {
		dao.saveBusinessCou(CHB100);
	}

	@Override
	public void validatePeopleStu(String CHB100) throws Exception {
		dao.validatePeopleStu(CHB100);
	}

	@Override
	public void validateTopStu(String CHB100) throws Exception {
		dao.validateTopStu(CHB100);
	}

	@Override
	public void saveTopStudent(String CHB100) throws Exception {
		dao.saveTopStudent(CHB100);
		dao.updateStuCount(CHB100);
	}
	@Override
	public void submitTopApply(String CHB100, String AAE011) throws Exception{
		//auditdao.auditTask("F001", CHB100, "01", AAE011, "1", "");
		dao.topsubmit(CHB100);
	}
	/**
	 * 查询区县部门列表
	 */
	@Override
	public List<Map<String, Object>> getQuXian() throws Exception{
		return dao.getQuXian();
	}
	/**
	 * 撤销申请
	 * @throws Exception 
	 */
	@Override
	public void revokeApply(String chb100) throws Exception{
		dao.revokeApply(chb100);
	}
	/**
	 * 补贴校验
	 * @param chb100
	 */
	@Override
	public void checkStuSub(String chb100) throws Exception{
		dao.checkStuSub(chb100);
	}
}
