package com.insigma.mvc.serviceimp.excel.EXCEL_IMPORT_001_001;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.common.excel.ExcelExport;
import com.insigma.common.util.JvmMemoryUtil;
import com.insigma.common.util.UUIDGenerator;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.common.fileupload.FileLoadMapper;
import com.insigma.mvc.dao.excel.EXCEL_IMPORT_001_001.ExcelImportMapper;
import com.insigma.mvc.model.Ac60ExcelTemp;
import com.insigma.mvc.model.ExcelExportModel;
import com.insigma.mvc.model.SExcelBatch;
import com.insigma.mvc.service.excel.EXCEL_IMPORT_001_001.ExcelImportService;
import com.insigma.resolver.AppException;

/**
 * ��ƶ���ݵ��뵼��
 * 
 * @author wengsh
 *
 */

@Service
public class ExcelImportServiceImpl extends MvcHelper implements ExcelImportService {

	private static Log log=LogFactory.getLog(ExcelImportServiceImpl.class);
	
	@Resource
	private FileLoadMapper fileLoadMapper;

	 @Autowired 
	private SqlSessionTemplate sqlSessionTemplate;
	 
	 @Resource
	private ExcelImportMapper excelImportMapper;
	 
	 @Resource(name = "taskExecutor")  
	private TaskExecutor taskExecutor; 

	// excel��׼����
	private static String[] STANDARD_TITLES = new String[] {"","����*,20,String","���֤��*,18,String" };
	//[���, ����, ���֤����, �������ڵ�, �Ա�, ����, ����, ������ò, �Ļ��̶�, ����״��, �Ͷ�����, ��������, ��Ҫ��ƶԭ��, ��ϵ�绰, �Ƿ�����У��, ��ҵ���, null, null, null, null, null, null, null, �����Ը�λ����ר�ڰ������, null, null, null, null, ��ѵ���, null, null, null, null, null, null, null, ��ҵ���, null, null, null, null, null, null, null, null, null, δ��ҵ���, null, null, null, �ṩ��ҵ�������, null, null, null, null, ������ʵ���, null, null, null, null]
	/**
	 * ��ҳ���ѯ
	 */
	@Override
	public HashMap<String, Object> getList(SExcelBatch sExcelBatch) {
		PageHelper.offsetPage(sExcelBatch.getOffset(), sExcelBatch.getLimit());
		List<SExcelBatch> list = fileLoadMapper.getExcelBatchList(sExcelBatch);
		PageInfo<SExcelBatch> pageinfo = new PageInfo<SExcelBatch>(list);
		return this.success_hashmap_response(pageinfo);
	}

	/**
	 * ���ݴ��� ���ӵ���ʱ��
	 */
	@Override
	public void executeListToTemp(List<String[]> list, SExcelBatch sexcelbatch) throws AppException {
		JvmMemoryUtil.showJvmMemory();
		/**2 �����ݵ��뵽��ʱ����*/
		if(list.size()>1){
			// ���ݵ�һ���Ƿ����׼��ʽƥ�䣬�жϵ����excel��ʽ�Ƿ���ȷ
			String[] cells = list.get(0);
			// �Ƿ�ƥ��
			boolean eq = true;
			// excel��ѭ��
			for (int j = 0; j < cells.length; j++) {
				if (!cells[j].equals(STANDARD_TITLES[j+1].split(",")[0])) {
					eq = false;
					break;
				}
			}
			// �����ƥ��
			if (!eq) {
				//throw new AppException("���õ�excel����ģ���ʽ����,���ܵ���,��ȷ�ϸ�ʽ�Ƿ���ȷ");
			}
		}else{
			sexcelbatch.setExcel_batch_status("4");//�����쳣
			sexcelbatch.setExcel_batch_rt_msg("���õ�excelû��ҵ������,��ȷ�ϸ�ʽ�Ƿ���ȷ");
			fileLoadMapper.updateExelBatch(sexcelbatch);
			throw new AppException("���õ�excelû��ҵ������,��ȷ�ϸ�ʽ�Ƿ���ȷ");
		}
		// �ӵڶ��п�ʼȡ����

		// �»�ȡһ��ģʽΪBATCH���Զ��ύΪfalse��session
		// ����Զ��ύ����Ϊtrue,���޷������ύ����������Ϊ���ͳһ�ύ�����ܵ����ڴ����
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		// ͨ���µ�session��ȡmapper
		ExcelImportMapper povertyMapper = session.getMapper(ExcelImportMapper.class);
		try {
			// excel��ѭ��
			for (int i = 1; i < list.size(); i++) {
				String[] tempstr = list.get(i);
				Ac60ExcelTemp ac60temp = new Ac60ExcelTemp();
				ac60temp.setExcel_batch_number(sexcelbatch.getExcel_batch_number());//������ʱ��֮�������κ�
				ac60temp.setExcel_rownum(new Long(i));
				ac60temp.setExcel_temp_id(UUIDGenerator.generate());
				//������Ϣ 14
				ac60temp.setAac003(tempstr[1]); // ������ʱ��֮����
				if(null!=tempstr[2]){
				     ac60temp.setAac002(tempstr[2].replace("'", "")); // ������ʱ��֮���֤��
				}
				ac60temp.setAac010(tempstr[3]);	// ������ʱ��֮�Ͷ��߻������ڵ�
				ac60temp.setAac004(tempstr[4]);	//	������ʱ��֮�Ա�
				ac60temp.setAac007(tempstr[5]);	//	������ʱ��֮����
				ac60temp.setAac005(tempstr[6]);	//	������ʱ��֮����
				ac60temp.setAac024(tempstr[7]);	//	������ʱ��֮������ò
				ac60temp.setAac011(tempstr[8]);	//	������ʱ��֮ѧ������
				ac60temp.setAac033(tempstr[9]);	//	������ʱ��֮����״��
				ac60temp.setAae015(tempstr[10]);	//	������ʱ��֮�Ͷ�����
				ac60temp.setAac029(tempstr[11]);	//	������ʱ��֮��������
				ac60temp.setAac031(tempstr[12]);	//	������ʱ��֮��Ҫ��ƶԭ��
				if(null!=tempstr[13]){
					ac60temp.setAae006(tempstr[13].replace("'", ""));	//	������ʱ��֮��ϵ�绰
				}
				ac60temp.setAac030(tempstr[14]);	//	������ʱ��֮�Ƿ���У��
				//��ҵ��� 8
				ac60temp.setAdc001(tempstr[15]);	//	������ʱ��֮��ҵ��ʽ
				ac60temp.setAdc002(tempstr[16]);	//	������ʱ��֮��ҵ����
				ac60temp.setAdc003(tempstr[17]);	//	������ʱ��֮��ҵ��
				ac60temp.setAdc004(tempstr[18]);	//	������ʱ��֮��ҵ����
				ac60temp.setAdc005(tempstr[19]);	//	������ʱ��֮��ҵʱ��
				ac60temp.setAdc006(tempstr[20]);	//	������ʱ��֮�Ƿ�ǩ���Ͷ���ͬ��Э��
				ac60temp.setAdc007(tempstr[21]);	//	������ʱ��֮�Ƿ�μ���ᱣ��
				ac60temp.setAdc008(tempstr[22]);	//	������ʱ��֮�¾�����
				//�����Ը�λ����ר�ڰ������ 5
				ac60temp.setAdc009(tempstr[23]);	//	������ʱ��֮��λ����
				ac60temp.setAdc010(tempstr[24]);	//	������ʱ��֮�����Ը�λ���õ�ַ
				ac60temp.setAdc011(tempstr[25]);	//	������ʱ��֮�����Ը�λ���õ�λ����
				ac60temp.setAdc012(tempstr[26]);	//	������ʱ��֮����ʱ��
				ac60temp.setAdc013(tempstr[27]);	//	������ʱ��֮��λ����
				//��ѵ��� 7
				ac60temp.setAdc014(tempstr[28]);	//	������ʱ��֮�Ƿ�μӾ�ҵ��ѵ
				ac60temp.setAdc015(tempstr[29]);	//	������ʱ��֮��ѵ����
				ac60temp.setAdc016(tempstr[30]);   //	������ʱ��֮��ѵ��ʼʱ��
				ac60temp.setAdc017(tempstr[31]);	//	������ʱ��֮��ѵ��ֹʱ��
				ac60temp.setAdc018(tempstr[32]);	//	������ʱ��֮��ѵ���
				ac60temp.setAdc019(tempstr[33]);   //	������ʱ��֮ȡ��֤��
				ac60temp.setAdc020(tempstr[34]);	//	������ʱ��֮��ѵ������Ԫ��
				//��ҵ��� 11
				ac60temp.setAdc021(tempstr[35]);	//	������ʱ��֮�Ƿ��ҵ
				ac60temp.setAdc022(tempstr[36]);	//	������ʱ��֮��ҵʱ��
				ac60temp.setAdc023(tempstr[37]);	//	������ʱ��֮�Ƿ�μӴ�ҵ��ѵ
				ac60temp.setAdc024(tempstr[38]);	//	������ʱ��֮��ҵ����
				ac60temp.setAdc025(tempstr[39]);	//	������ʱ��֮��ҵ��ʵ���ַ
				ac60temp.setAdc026(tempstr[40]);	//	������ʱ��֮��ҵ��ʵ������
				ac60temp.setAdc027(tempstr[41]);	//	������ʱ��֮��ҵ���
				ac60temp.setAdc028(tempstr[42]);	//	������ʱ��֮�Ƿ�����С�������
				ac60temp.setAdc029(tempstr[43]);	//	������ʱ��֮�������Ԫ��
				ac60temp.setAdc030(tempstr[44]);	//	������ʱ��֮�����Ͷ�������
				ac60temp.setAdc031(tempstr[45]);	//	������ʱ��֮�Ƿ��ڹ��̲���ע��Ǽ�
				//δ��ҵ��� 4
				ac60temp.setAdc032(tempstr[46]);	//	������ʱ��֮�Ƿ��о�ҵԸ��
				ac60temp.setAdc033(tempstr[47]);	//	������ʱ��֮��ҵ�����
				ac60temp.setAdc034(tempstr[48]);	//	������ʱ��֮������н��Ԫ��
				ac60temp.setAdc035(tempstr[49]);	//	������ʱ��֮��ҵ��������
				
				//�ṩ��ҵ������� 5
				ac60temp.setAdc036(tempstr[50]);	//	������ʱ��֮�ṩ��ҵ������ѯ����(��)
				ac60temp.setAdc037(tempstr[51]);	//	������ʱ��֮�ṩ��ҵ��Ϣ�������Σ�
				ac60temp.setAdc038(tempstr[52]);	//	������ʱ��֮�ṩְҵָ������ܴ������Σ�
				ac60temp.setAdc039(tempstr[53]);	//	������ʱ��֮�ṩ��ѵ�������Σ�
				ac60temp.setAdc040(tempstr[54]);	//	������ʱ��֮�ṩ��ҵ�������(��)
				//������ʵ��� 4
				ac60temp.setAdc041(tempstr[55]);	//	������ʱ��֮�Ƿ�����ְҵ���ܲ���
				ac60temp.setAdc042(tempstr[56]);	//	������ʱ��֮�Ƿ�������ᱣ�ղ���
				ac60temp.setAdc043(tempstr[57]);	//	������ʱ��֮�Ƿ����ܸ�λ����
				ac60temp.setAdc044(tempstr[58]);	//	������ʱ��֮�Ƿ������������߷���
				
				povertyMapper.insertExcelTempData(ac60temp);
				if (i % 5000 == 0 || i == list.size() - 1) {
					// �ֶ�ÿ1000��һ�ύ���ύ���޷��ع�
					session.commit();
					// �����棬��ֹ���
					session.clearCache();
					JvmMemoryUtil.showJvmMemory();
					//���µ�ǰ��������״̬ 10%+20%�İٷݱ� ������ʱ���30%�ķֶα���
					int statusnumber=new Float((i+0f)/(list.size()+0f)*20).intValue()+10;
					sexcelbatch.setExcel_batch_data_status(statusnumber);
					fileLoadMapper.updateExelBatch(sexcelbatch);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// û���ύ�����ݿ��Իع�
			session.rollback();
		} finally {
			session.close();
		}
		//������
		sexcelbatch.setExcel_batch_total_count(new Long(list.size()));
		sexcelbatch.setExcel_batch_status("2");//������ʱ��
		//�����ļ���¼
		fileLoadMapper.updateExelBatch(sexcelbatch);
		
	}
	/**
	 * ִ�����ݴ������
	 * @param executeProc
	 */
	@Override
	public void executeProc(final SExcelBatch  sexcelbatch) throws  AppException{
		/**3 ���ù��̴�������*/
		//��ȡ���κ�
		final SExcelBatch newsexcelbatch=fileLoadMapper.getExcelBatchById(sexcelbatch.getExcel_batch_id());
		//�����߳�ִ��
		taskExecutor.execute(new Runnable() {  
		    @Override  
		    public void run() {  
		        // TODO Auto-generated method stub  
		        try {  
		    		    SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		    		   // ͨ���µ�session��ȡmapper
		    		   ExcelImportMapper povertyMapper = session.getMapper(ExcelImportMapper.class);
		        	    Date start=new Date();
						log.info("���ù��̿�ʼʱ��"+new Date().toLocaleString());
						//���ù��̴�������
						povertyMapper.executePovertyData(newsexcelbatch);
						Date end=new Date();
						Long cost=end.getTime()-start.getTime();
						log.info("���ù��̽���ʱ��"+new Date().toLocaleString()+"����"+cost/1000+"s");
						//ִ���Ƿ�ɹ�
						if(newsexcelbatch.getExcel_batch_rt_code() .equals("1")){
							
						}else{
							log.info("��������ʧ��,ʧ��ԭ��"+newsexcelbatch.getExcel_batch_rt_msg());
						}
		        } catch (Exception e) {  
		            // TODO Auto-generated catch block  
		            e.printStackTrace();  
		        }  
		    }  
		});  
	}

	/**
	 * ͨ�����κŲ�ѯ��������
	 */
	@Override
	public HashMap<String, Object> queryPovertyDataTotalByexcelBatchNumber(Ac60ExcelTemp ac60ExcelTemp) {
		List<Ac60ExcelTemp> list = excelImportMapper.queryPovertyDataTotalByexcelBatchNumber(ac60ExcelTemp);;
		PageInfo<Ac60ExcelTemp> pageinfo = new PageInfo<Ac60ExcelTemp>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	/**
	 * ͨ�����κŲ�ѯ��������
	 */
	@Override
	public HashMap<String, Object> getPovertyImprtDataList(Ac60ExcelTemp ac60ExcelTemp) {
		PageHelper.offsetPage(ac60ExcelTemp.getOffset(), ac60ExcelTemp.getLimit());
		List<Ac60ExcelTemp> list = excelImportMapper.queryPovertyDataByexcelBatchNumber(ac60ExcelTemp);;
		PageInfo<Ac60ExcelTemp> pageinfo = new PageInfo<Ac60ExcelTemp>(list);
		return this.success_hashmap_response(pageinfo);
	}

	
	/**
	 * ɾ�����μ������Ϣ
	 */
	@Override
	@Transactional
	public AjaxReturnMsg<String> deleteTempDataByNumber(String number) {
		// TODO Auto-generated method stub
		int result=fileLoadMapper.deleteExcelBatchByNumber(number);
		if(result==1){
			excelImportMapper.deleteTempDataByNumber(number);
		}
		if(result==1){
			return this.success("ɾ���ɹ�");
		}else{
			return this.error("ɾ��ʧ��");
		}
	}

	/**
	 * ��ѯexcel��������
	 */
	@Override
	public List<ExcelExportModel> queryExportDataByNumber(String number) {
		return excelImportMapper.queryExportDataByNumber(number);
	}

	/**
	 * ������������
	 */
	@Override
	public AjaxReturnMsg<String>  exportData(final String excel_batch_number) {
		// TODO Auto-generated method stub
		final SExcelBatch  sExcelBatch = fileLoadMapper.getExcelBatchByNumber(excel_batch_number);
    	if(null!=sExcelBatch.getExcel_error_file_download()&&sExcelBatch.getExcel_error_file_download().equals("0")){
    		 return this.success("��������.....,�����ɳɹ��������б�������,����Ƶ������");
    	}
    	//�����߳�ִ��
		taskExecutor.execute(new Runnable() {  
		    @Override  
		    public void run() {  
	            sExcelBatch.setExcel_error_file_download("0");
	            fileLoadMapper.updateExelBatchErrorFilePath(sExcelBatch);
		        // TODO Auto-generated method stub  
		        try {  
		        	//��ѯexcel�������ݿ�ʼ
					log.info("��ѯexcel�������ݿ�ʼ"+new Date().toLocaleString());
					Date start=new Date();
		        	List<ExcelExportModel> list=	excelImportMapper.queryExportDataByNumber(excel_batch_number);
		        	Date end=new Date();
					Long cost=end.getTime()-start.getTime();
					log.info("��ѯexcel�������ݿ�ʼ"+new Date().toLocaleString()+"����="+cost+"������="+list.size());
		        	ExcelExport<ExcelExportModel> ee= new ExcelExport<ExcelExportModel>();  
		            String[] headers =
		            {
		            	"ԭ��������"
		            	,"����"
		            	,"���֤��"
		            	,"�Ͷ��߻������ڵ�"
		            	,"�Ա�"
		            	,"����"
		            	,"����"
		            	,"����״��"
		            	,"������ò"
		            	,"ѧ������"
		            	,"��ϵ�绰"
		            	,"�Ͷ�����"
		            	,"��������"
		            	,"�Ƿ���У��"
		            	,"��Ҫ��ƶԭ��"
		            	,"��ҵ��ʽ"
		            	,"��ҵ����"
		            	,"��ҵ��"
		            	,"��ҵ����"
		            	,"��ҵʱ��"
		            	,"�Ƿ�ǩ���Ͷ���ͬ��Э��"
		            	,"�Ƿ�μ���ᱣ��"
		            	,"�¾�����"
		            	,"��λ����"
		            	,"�����Ը�λ���õ�ַ"
		                ,"�����Ը�λ���õ�λ����"
		            	,"����ʱ��"
		                ,"��λ����"
		            	,"�Ƿ�μӾ�ҵ��ѵ"
		            	,"��ѵ����"
		            	,"��ѵ��ʼʱ��"
		            	,"��ѵ��ֹʱ��"
		            	,"��ѵ���"
		            	,"ȡ��֤��"
		            	,"��ѵ������Ԫ��"
		            	,"�Ƿ��ҵ"
		            	,"��ҵʱ��"
		            	,"�Ƿ�μӴ�ҵ��ѵ"
		            	,"��ҵ����"
		            	,"��ҵ��ʵ���ַ"
		            	,"��ҵ��ʵ������"
		            	,"��ҵ���"
		            	,"�Ƿ�����С�������"
		            	,"�������Ԫ��"
		            	,"�����Ͷ�������"
		            	,"�Ƿ��ڹ��̲���ע��Ǽ�"
		            	,"�Ƿ��о�ҵԸ��"
		                ,"��ҵ�����"
		            	,"������н��Ԫ��"
		            	,"��ҵ��������"
		            	,"�ṩ��ҵ������ѯ����(��)"
		            	,"֮�ṩ��ҵ��Ϣ�������Σ�"
		            	,"�ṩְҵָ������ܴ������Σ�"
		            	,"�ṩ��ѵ�������Σ�"
		            	,"�ṩ��ҵ�������(��)"
		            	,"�Ƿ�����ְҵ���ܲ���"
		            	,"�Ƿ�������ᱣ�ղ���"
		            	,"�Ƿ����ܸ�λ����"
		            	,"������ʱ��֮�Ƿ������������߷���"
		            	,"�Ƿ���ȷ����"
		            };
		            String filepath=ee.exportCvs(headers,list,sExcelBatch.getExcel_batch_file_name());  
		            sExcelBatch.setExcel_error_file_path(filepath);
		            sExcelBatch.setExcel_error_file_download("1");
		            fileLoadMapper.updateExelBatchErrorFilePath(sExcelBatch);
		        } catch (Exception e) {  
		            // TODO Auto-generated catch block  
		            e.printStackTrace();  
		        }  
		    }  
		});  
		return this.success("��������.....,�����ɳɹ��������б�������,����Ƶ������");
	}

	@Override
	public Ac60ExcelTemp queryImpDataById(String aac002) {
		return excelImportMapper.queryImpDataById(aac002);
	}
}