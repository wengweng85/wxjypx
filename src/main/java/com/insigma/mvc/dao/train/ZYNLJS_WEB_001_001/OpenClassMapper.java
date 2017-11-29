package com.insigma.mvc.dao.train.ZYNLJS_WEB_001_001;

import com.insigma.mvc.model.*;

import java.util.List;
import java.util.Map;


/**
 * ������ѵ��౸���걨DAO
 * @author xujiyu
 *
 */
public interface OpenClassMapper {
	
	/**
	 * ��ѯ������ѵ��౸���б�
	 */
	List<Hb68> getInfoList(Search search) throws Exception;
	
	
	String getId() throws Exception;
	
	/**
	 * ���ݰ���Ų�ѯ��Ϣ
	 */
	Map<String, Object> getById(String CHB100) throws Exception;
	
	/**
	 * ���漼����ѵ��౸��������Ϣ
	 */
	void saveBaseInfo(Hb68 hb68) throws Exception;
	
	/**
	 * ���ݰ����ɾ�������Ϣ
	 */
	void deleteClassById(String CHB100) throws Exception;
	
	/**
	 * ���ݰ����ɾ��ѧԱ��Ϣ
	 */
	void deleteStudentById(String CHB100) throws Exception;
	
	/**
	 * ���ݰ����ɾ���γ���Ϣ
	 */
	void deleteCourseById(String CHB100) throws Exception;
	
	/**
	 * ��ѯѧԱ��Ϣ�б�
	 */
	Map<String, Object> getStudentList(String CHB100, int source) throws Exception;
	
	/**
	 * �������֤�Ų�ѯ�ø�����Ϣ
	 */
	Map<String, Object> getByIdCard(String AAC002) throws Exception;
	
	/**
	 * ��Ӹ�����Ϣ
	 */
	String addPerson(Ac01 ac01) throws Exception;
	
	/**
	 * ���ѧԱ��Ϣ
	 */
	void addStudent(Ac01 ac01, String CHB100) throws Exception;
	
	/**
	 * ��ѯ�γ���Ϣ�б�
	 */
	Map<String, Object> getCourseList(String CHB100, int source) throws Exception;
	
	/**
	 * ����γ���Ϣ
	 */
	void addCourse(String CHB100) throws Exception;
	
	/**
	 * ���浽��ʱ��HC60_TEMP
	 */
	void batchSaveStuTemp(List<Hc60_Temp> list) throws Exception;
	
	/**
	 * ������ʱ��HC60_TEMP
	 */
	void updateStuTemp(String CHB100, String AAC002, String EAE052, String AAE013) throws Exception;
	
	/**
	 * ���ݰ����ɾ����ʱѧԱ��Ϣ
	 */
	void deleteTempStuById(String CHB100) throws Exception;
	
	/**
	 * ���ݰ����ɾ����ʱ�γ���Ϣ
	 */
	void deleteTempCouById(String CHB100) throws Exception;
	
	/**
	 * �������浽��ʱ��HB69_TEMP
	 */
	void batchSaveCouTemp(List<Hb69> list) throws Exception;
	
	/**
	 * ���¿γ���ʱ��
	 */
	void updateCouTemp(String CHB060, String EAE052, String CHB154)
			throws Exception;
	
	/**
	 * ���¼�����ѵ��౸��������Ϣ
	 */
	void updateBaseInfo(Hb68 h) throws Exception;
	
	/**
	 * ���ô洢���̣�У��ѧԱ��Ϣ
	 */
	void checkStudent(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�У�鴴ҵ��ѵѧԱ��Ϣ
	 */
	void checkBusinessStu(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�����ѧԱ��Ϣ
	 */
	void saveStudent(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣����洴ҵѧԱ��Ϣ
	 */
	void saveBusinessStu(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�У��γ���Ϣ
	 */
	void checkCourse(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�У�鴴ҵ�γ���Ϣ
	 */
	void checkBusinessCou(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�����γ���Ϣ
	 */
	void saveCourse(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣����洴ҵ�γ���Ϣ
	 */
	void saveBusinessCou(String CHB100) throws Exception;
	
	/**
	 * ����id���ѧԱ��Ϣ
	 */
	Map<String, Object> getStuById(String CHC001) throws Exception;
	
	/**
	 * ���ݹ��ֺ͵ȼ�����걨����
	 */
	Map<String, Object> getCondition(String ACA111, String ACA11A) throws Exception;
	
	/**
	 * ���ݹ��ֺ͵ȼ���ѯ����
	 */
	Map<String, Object> getByJobAndLevel(String ACA111, String ACA11A) throws Exception;
	/**
	 * �����ύ״̬
	 */
	void submit(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�У�������ѵѧԱ��Ϣ
	 */
	void validatePeopleStu(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�У��߼����˲���ѵѧԱ��Ϣ
	 */
	void validateTopStu(String CHB100) throws Exception;
	
	/**
	 * ���ô洢���̣�����߼����˲�ѧԱ��Ϣ
	 */
	void saveTopStudent(String CHB100) throws Exception;
	/**
	 * �߼����˲Ű���ύ
	 */
	void topsubmit(String CHB100) throws Exception ;
	/**
	 * ��ѯ���ز����б�
	 */
	List<Map<String, Object>> getQuXian() throws Exception;

	/**
	 * �޸��걨����
	 */
	void updateStuCount(String chb100) throws Exception;
	/**
	 * ��������
	 * @throws Exception; 
	 */
	void revokeApply(String chb100) throws Exception;
	/**
	 * ����У��
	 * @param chb100
	 */
	void checkStuSub(String chb100) throws Exception;
}
