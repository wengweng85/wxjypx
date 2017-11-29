package com.insigma.mvc.dao.train.ZYNLJS_WEB_001_001;

import com.insigma.mvc.model.*;

import java.util.List;
import java.util.Map;


/**
 * 技能培训办班备案申报DAO
 * @author xujiyu
 *
 */
public interface OpenClassMapper {
	
	/**
	 * 查询技能培训办班备案列表
	 */
	List<Hb68> getInfoList(Search search) throws Exception;
	
	
	String getId() throws Exception;
	
	/**
	 * 根据办班编号查询信息
	 */
	Map<String, Object> getById(String CHB100) throws Exception;
	
	/**
	 * 保存技能培训办班备案基础信息
	 */
	void saveBaseInfo(Hb68 hb68) throws Exception;
	
	/**
	 * 根据办班编号删除办班信息
	 */
	void deleteClassById(String CHB100) throws Exception;
	
	/**
	 * 根据办班编号删除学员信息
	 */
	void deleteStudentById(String CHB100) throws Exception;
	
	/**
	 * 根据办班编号删除课程信息
	 */
	void deleteCourseById(String CHB100) throws Exception;
	
	/**
	 * 查询学员信息列表
	 */
	Map<String, Object> getStudentList(String CHB100, int source) throws Exception;
	
	/**
	 * 根据身份证号查询该个人信息
	 */
	Map<String, Object> getByIdCard(String AAC002) throws Exception;
	
	/**
	 * 添加个人信息
	 */
	String addPerson(Ac01 ac01) throws Exception;
	
	/**
	 * 添加学员信息
	 */
	void addStudent(Ac01 ac01, String CHB100) throws Exception;
	
	/**
	 * 查询课程信息列表
	 */
	Map<String, Object> getCourseList(String CHB100, int source) throws Exception;
	
	/**
	 * 保存课程信息
	 */
	void addCourse(String CHB100) throws Exception;
	
	/**
	 * 保存到临时表HC60_TEMP
	 */
	void batchSaveStuTemp(List<Hc60_Temp> list) throws Exception;
	
	/**
	 * 更新临时表HC60_TEMP
	 */
	void updateStuTemp(String CHB100, String AAC002, String EAE052, String AAE013) throws Exception;
	
	/**
	 * 根据办班编号删除临时学员信息
	 */
	void deleteTempStuById(String CHB100) throws Exception;
	
	/**
	 * 根据办班编号删除临时课程信息
	 */
	void deleteTempCouById(String CHB100) throws Exception;
	
	/**
	 * 批量保存到临时表HB69_TEMP
	 */
	void batchSaveCouTemp(List<Hb69> list) throws Exception;
	
	/**
	 * 更新课程临时表
	 */
	void updateCouTemp(String CHB060, String EAE052, String CHB154)
			throws Exception;
	
	/**
	 * 更新技能培训办班备案基础信息
	 */
	void updateBaseInfo(Hb68 h) throws Exception;
	
	/**
	 * 调用存储过程，校验学员信息
	 */
	void checkStudent(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，校验创业培训学员信息
	 */
	void checkBusinessStu(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，保存学员信息
	 */
	void saveStudent(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，保存创业学员信息
	 */
	void saveBusinessStu(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，校验课程信息
	 */
	void checkCourse(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，校验创业课程信息
	 */
	void checkBusinessCou(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，保存课程信息
	 */
	void saveCourse(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，保存创业课程信息
	 */
	void saveBusinessCou(String CHB100) throws Exception;
	
	/**
	 * 根据id获得学员信息
	 */
	Map<String, Object> getStuById(String CHC001) throws Exception;
	
	/**
	 * 根据工种和等级获得申报条件
	 */
	Map<String, Object> getCondition(String ACA111, String ACA11A) throws Exception;
	
	/**
	 * 根据工种和等级查询工种
	 */
	Map<String, Object> getByJobAndLevel(String ACA111, String ACA11A) throws Exception;
	/**
	 * 更新提交状态
	 */
	void submit(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，校验民办培训学员信息
	 */
	void validatePeopleStu(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，校验高技能人才培训学员信息
	 */
	void validateTopStu(String CHB100) throws Exception;
	
	/**
	 * 调用存储过程，保存高技能人才学员信息
	 */
	void saveTopStudent(String CHB100) throws Exception;
	/**
	 * 高技能人才办班提交
	 */
	void topsubmit(String CHB100) throws Exception ;
	/**
	 * 查询区县部门列表
	 */
	List<Map<String, Object>> getQuXian() throws Exception;

	/**
	 * 修改申报人数
	 */
	void updateStuCount(String chb100) throws Exception;
	/**
	 * 撤销申请
	 * @throws Exception; 
	 */
	void revokeApply(String chb100) throws Exception;
	/**
	 * 补贴校验
	 * @param chb100
	 */
	void checkStuSub(String chb100) throws Exception;
}
