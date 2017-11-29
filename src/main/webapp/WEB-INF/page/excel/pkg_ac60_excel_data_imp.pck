create or replace package pkg_ac60_excel_data_imp AS

  -- �ɹ����ر�־
  RT_SUCCESS_CODE constant number  := '1';
  -- ���󷵻ر�־
  RT_ERROR_CODE   constant number  := '0';

  PROCEDURE ac60_temp_imp_update(in_excel_batch_number varchar2,rt_code out varchar2,rt_msg out varchar2);

end pkg_ac60_excel_data_imp;
/
create or replace package body pkg_ac60_excel_data_imp AS

   PROCEDURE ac60_temp_imp_update(in_excel_batch_number varchar2,rt_code out varchar2,rt_msg out varchar2) AS
   BEGIN
    
     --��������״̬0/8*40+80
     update s_excel_batch t set t.excel_batch_status='2',t.excel_batch_rt_msg='', t.excel_batch_data_status=round(0/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60) where t.excel_batch_number=in_excel_batch_number;  
    
     commit;
     
    --1 ���ݺϷ���У��
      
     update ac60_excel_temp t set t.excel_isvalid='0',t.excel_isop='1',t.excel_opdate=sysdate, t.excel_errormsg='��������Ϊ��' where  t.excel_batch_number =in_excel_batch_number and t.aac003 is null and excel_isvalid='1';
      
     update ac60_excel_temp t set t.excel_isvalid='0',t.excel_isop='1',t.excel_opdate=sysdate, t.excel_errormsg='���֤�Ų���Ϊ��' where  t.excel_batch_number =in_excel_batch_number and t.aac002 is null and excel_isvalid='1';
      
     update ac60_excel_temp t set t.excel_isvalid='0',t.excel_isop='1',t.excel_opdate=sysdate, t.excel_errormsg='���֤��ʽ������Ҫ��' where  t.excel_batch_number =in_excel_batch_number and length(t.aac002)<15  and excel_isvalid='1';
     
     --ҵ��У�� �Ƿ��Ѿ������
      
     --update ac60_excel_temp t set t.excel_isvalid='0',t.excel_isop='1',t.excel_opdate=sysdate, t.excel_errormsg='�Ѿ�����,�����ظ�����' where  t.excel_batch_number =in_excel_batch_number and exists (select 1 from ac60 c where c.aac002=t.aac002) and excel_isvalid='1';
     
    
     --�ظ�aac002����ɾ��
     update ac60_excel_temp t
     set t.excel_isvalid  = '0',
        t.excel_isop     = '1',
        t.excel_opdate   = sysdate,
        t.excel_errormsg = '����ͬ�������֤����'
     where rowid not in (select max(rowid)
        from ac60_excel_temp
        where excel_batch_number =in_excel_batch_number and excel_isvalid ='1'
        group by aac002 having count(*)>1
      )
      and aac002  in(
        select aac002 from ac60_excel_temp
        where excel_batch_number =in_excel_batch_number and excel_isvalid ='1'
        group by aac002 having count(*)>1
      );
          
    
     COMMIT;
     
     --ѭ����ѯ��ʱ��֮���� �жϸ�ʽ�Ƿ���ȷ
     for c in (select   
       excel_temp_id,
       adc005,--������ʱ��֮��ҵ���--��ҵʱ��
       adc012,--������ʱ��֮�����Ը�λ����ר�ڰ������-����ʱ��(���ڸ�ʽ)
       adc016 ,--������ʱ��֮��ѵ���-��ѵ��ʼʱ�� 
       adc017 ,--������ʱ��֮��ѵ���-��ѵ��ֹʱ�� 
       adc022 --������ʱ��֮��ҵ���-��ҵʱ�� 
       from ac60_excel_temp   where excel_batch_number =in_excel_batch_number and excel_isvalid='1')
     loop
       --��ҵʱ��
       begin 
          update ac60_excel_temp t set adc005_date= TO_DATE(substr(replace(replace(c.adc005,'-','/'),'.','/'),0,10),'yyyy-MM-dd') where excel_temp_id=c.excel_temp_id;
       exception
         when others then
         null;  
       end;  
       
       --����ʱ��(���ڸ�ʽ)
       begin 
          update ac60_excel_temp t set adc012_date= TO_DATE(substr(replace(replace(c.adc012,'-','/'),'.','/'),0,10),'yyyy-MM-dd') where excel_temp_id=c.excel_temp_id;
       exception
         when others then
         null;  
       end;   
       
       --��ѵ��ʼʱ�� 
       begin 
          update ac60_excel_temp t set adc016_date= TO_DATE(substr(replace(replace(c.adc016,'-','/'),'.','/'),0,10),'yyyy-MM-dd') where excel_temp_id=c.excel_temp_id;
       exception
         when others then
         null;  
       end;   
       
       --��ѵ��ֹʱ�� 
       begin 
          update ac60_excel_temp t set adc017_date= TO_DATE(substr(replace(replace(c.adc017,'-','/'),'.','/'),0,10),'yyyy-MM-dd') where excel_temp_id=c.excel_temp_id;
       exception
         when others then
         null;  
       end;   
       
       --��ҵʱ��
       begin 
          update ac60_excel_temp t set adc022_date= TO_DATE(substr(replace(replace(c.adc022,'-','/'),'.','/'),0,10),'yyyy-MM-dd') where excel_temp_id=c.excel_temp_id;
       exception
         when others then
         null;  
       end;   
     end loop;  

     commit;
    
     --ac60���ݲ���
     rt_msg:='������Ϣ����';
     
     insert into ac60
        (
          aac001   ,--      ��ƶϵͳ�����������������uuid��
          aac003   ,--      ����
          aac002   ,--      ���֤��
          aac010   ,--      �Ͷ��߻������ڵ�
          aab299   ,--      �Ͷ��߻������ڵ�������������
          aac004   ,--      �Ա����
          aac005   ,--      �������
          aac007   ,--      ����
          aac033   ,--      ����״������
          aac024   ,--      ������ò����
          aac011   ,--      ѧ�����루�Ļ��̶ȴ��룩
          aae006   ,--      ��ϵ�绰
          aae015   ,--      �Ͷ�����
          aac029   ,--      ��������
          aac030   ,--      �Ƿ���У��
          aac031   ,--      ��Ҫ��ƶԭ��
          aaf011   ,--      �����������
          aae009   ,--      ���������
          aae011   ,--      �����˱���
          aae010   ,--      ��������
          aae036   ,--      ��������
          eae052   ,--      ���״̬
          aab301   ,--      ҵ�񾭰���������
          aae100   ,--      �Ƿ���Ч(1��Ч0��Ч)
          aae013   ,--      ��ע,
          EXCEL_BATCH_NUMBER --������ʱ��֮�������κ�
        )
        select
          sys_guid()   ,--      ��ƶϵͳ�����������������uuid��
          aac003   ,--      ����
          aac002   ,--      ���֤��
          aac010   ,--      �Ͷ��߻������ڵ�
          null   ,--      �Ͷ��߻������ڵ�������������
          (select code_value from code_value t where t.code_type='AAC004' and CODE_NAME=aac004)   ,--      �Ա����
          (select code_value from code_value t where t.code_type='AAC005' and CODE_NAME=aac005)    ,--      �������
          aac007   ,--      ����
          (select code_value from code_value t where t.code_type='AAC033' and CODE_NAME=aac033)     ,--      ����״������
          (select code_value from code_value t where t.code_type='AAC024' and CODE_NAME=aac024)    ,--      ������ò����
          (select code_value from code_value t where t.code_type='AAC011' and CODE_NAME=aac011)    ,--      ѧ�����루�Ļ��̶ȴ��룩
          aae006   ,--      ��ϵ�绰
          (select code_value from code_value t where t.code_type='AAE015' and CODE_NAME=aae015)    ,--      �Ͷ�����
          aac029   ,--      ��������
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=aac030)    ,--      �Ƿ���У��
          aac031   ,--      ��Ҫ��ƶԭ��
          null     ,--      �����������
          null     ,--      ���������
          'sxft_data_imp'   ,--      �����˱���
          'excel���ݵ���'   ,--      ��������
          sysdate   ,--      ��������
          '1'   ,     --      ���״̬
          null   ,--      ҵ�񾭰���������
          '1'   ,--      �Ƿ���Ч(1��Ч0��Ч)
          '���ݵ���'   ,--      ��ע
          in_excel_batch_number
       from ac60_excel_temp t where t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1'
       and not exists (select 1 from ac60 a where a.aac002=t.aac002);
       
       
       COMMIT;
       
       --�Ͷ��߻������ڵ�������������
       update ac60 t set t.aab299=(select groupid from s_group s where s.otherinfo=t.aac010) where t.aab299 is null and t.EXCEL_BATCH_NUMBER=  in_excel_batch_number;
          

       --��������״̬1/8*70+30
       update s_excel_batch t set t.excel_batch_data_status=round(1/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60) where t.excel_batch_number=in_excel_batch_number;
       
       COMMIT;
       
       --AC61 ��ƶ��ҵ�����
       
       --�Ƚ����е��Ѿ����������ɾ��
       delete from AC61 a where exists (select 1 from ac60_excel_temp t,ac60 f where t.aac002=f.aac002 and  f.aac001=a.aac001 and t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1' );
       
       COMMIT;
       
       
       rt_msg:='��ƶ��ҵ�������';
       
       INSERT INTO AC61(
          eec001  ,--     ����(�������uuid)
          aac001  ,--     ��ƶϵͳ����������ac60�������uuid��
          adc001  ,--     ��ҵ��ʽ
          adc002  ,--     ��ҵ����
          adc003  ,--     ��ҵ��
          adc004  ,--     ��ҵ����
          adc005  ,--     ��ҵʱ�� ���ڸ�ʽ
          adc005_string  ,--     ��ҵʱ�� 
          adc006  ,--     �Ƿ�ǩ���Ͷ���ͬ��Э��
          adc007  ,--     �Ƿ�μ���ᱣ��
          adc008  ,--     �¾�����
          aae010  ,--     ����������
          aaf011  ,--     �����������
          aae009  ,--     ���������
          aae011  ,--     �����˱���
          aab301  ,--     ҵ�񾭰���������
          aae100  ,--     �Ƿ���Ч(1��Ч0��Ч)
          aae036  ,
          aae013  --      ��ע
       )
       SELECT 
          sys_guid()  ,--     ����(�������uuid)
          (select aac001 from ac60 t where t.aac002=a.aac002 )  ,--     ��ƶϵͳ����������ac60�������uuid��
          (select code_value from code_value t where t.code_type='ADC001' and CODE_NAME=adc001)   ,--     ��ҵ��ʽ
          (select code_value from code_value t where t.code_type='ADC002' and CODE_NAME=adc002)  ,--     ��ҵ����
          (select max(code_value) from code_value t where t.code_type='AAB301' and CODE_DESCRIBE=adc003) adc003 ,--     ��ҵ��
          (select code_value from code_value t where t.code_type='ACA111' and CODE_NAME=adc004) adc004  ,--     ��ҵ����
          adc005_date  ,--     ��ҵʱ�� ���ڸ�ʽ ,
          adc005  ,--     ��ҵʱ�� 
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc006) adc006  ,--     �Ƿ�ǩ���Ͷ���ͬ��Э��
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc007) adc007  ,--     �Ƿ�μ���ᱣ��
          decode(sign(length( REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc008,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]',''))-8),-1,REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc008,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','')) adc008 ,--     �¾�����
          'excel���ݵ���'  ,--     ����������
          null  ,--     �����������
          null  ,--     ���������
          'sxft_data_imp'  ,--     �����˱���
          null  ,--     ҵ�񾭰���������
          '1'  ,--     �Ƿ���Ч(1��Ч0��Ч)
          sysdate,
          in_excel_batch_number  --      ��ע
       FROM  ac60_excel_temp a where a.excel_batch_number=in_excel_batch_number and a.excel_isvalid='1';   
            
       --��������״̬2/8*40+80
       update s_excel_batch t set t.excel_batch_data_status=round(2/8*70+30) ,t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60) where t.excel_batch_number=in_excel_batch_number;
         
       COMMIT;
       
       
       
       
       
       --AC62  �����Ը�λ����ר�ڰ������
       
       
       rt_msg:='�����Ը�λ����ר�ڰ����������';
       
       --�Ƚ����е��Ѿ����������ɾ��
       delete from AC62 a where exists (select 1 from ac60_excel_temp t,ac60 f where t.aac002=f.aac002 and  f.aac001=a.aac001 and t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1' );
       
       COMMIT;
       
       
       INSERT INTO AC62 (
          eec001  ,--     ����(�������uuid)
          aac001   ,--     ��ƶϵͳ����������ac60�������uuid��
          adc009   ,--   ��λ����
          adc010   ,--  �����Ը�λ���õ�ַ
          adc011   ,--   �����Ը�λ���õ�λ����
          adc012   ,--   ����ʱ��
          adc012_string   ,--   ����ʱ��
          adc013   ,--   ��λ����
          aae010  ,--     ����������
          aaf011  ,--     �����������
          aae009  ,--     ���������
          aae011  ,--     �����˱���
          aab301  ,--     ҵ�񾭰���������
          aae100  ,--     �Ƿ���Ч(1��Ч0��Ч)
          aae036,
          aae013  --      ��ע
       )
       SELECT
          sys_guid()   ,--     ����(�������uuid)
          (select aac001 from ac60 t where t.aac002=a.aac002 )  ,--     ��ƶϵͳ����������ac60�������uuid��
          (select code_value from code_value t where t.code_type='ADC009' and CODE_NAME=adc009)  adc009  ,--   ��λ����
          (select max(code_value) from code_value t where t.code_type='AAB301' and CODE_DESCRIBE=adc010),--  �����Ը�λ���õ�ַ
          adc011   ,--   �����Ը�λ���õ�λ���� ,
          adc012_date,
          adc012 ,--   ����ʱ��,
          adc013   ,--   ��λ����
          'excel���ݵ���'  ,--     ����������
          null  ,--     �����������
          null  ,--     ���������
          'sxft_data_imp'  ,--     �����˱���
          null  ,--     ҵ�񾭰���������
          '1'  ,--     �Ƿ���Ч(1��Ч0��Ч)
          sysdate,
          in_excel_batch_number  --      ��ע
       FROM  ac60_excel_temp a where  a.excel_batch_number=in_excel_batch_number and a.excel_isvalid='1';  
      
       
       --��������״̬3/7*40+80
       update s_excel_batch t set t.excel_batch_data_status=round(3/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60)  where t.excel_batch_number=in_excel_batch_number;
       
       COMMIT;
       
       --AC63  ��ѵ���
       
       rt_msg:='��ѵ�������';
        --�Ƚ����е��Ѿ����������ɾ��
       delete from AC63 a where exists (select 1 from ac60_excel_temp t,ac60 f where t.aac002=f.aac002 and  f.aac001=a.aac001 and t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1' );
       
       COMMIT;
       

       INSERT INTO AC63 (
          eec001   ,--     ����(�������uuid)
          aac001   ,--     ��ƶϵͳ����������ac60�������uuid��
          adc014   ,--     �Ƿ�μӾ�ҵ��ѵ
          adc015   ,--     ��ѵ����
          adc016   ,--     ��ѵ��ʼʱ��
          adc016_string  ,--     ��ѵ��ʼʱ�� �ַ���ʽ
          adc017   ,--     ��ѵ��ֹʱ��
          adc017_string,   --��ѵ��ֹʱ�� �ַ���ʽ
          adc018   ,--     ��ѵ���
          adc019   ,--     ȡ��֤��
          adc020   ,--     ��ѵ������Ԫ��
          aae010   ,--     ����������
          aaf011   ,--     �����������
          aae009   ,--     ���������
          aae011   ,--     �����˱���
          aab301   ,--     ҵ�񾭰���������
          aae100   ,--     �Ƿ���Ч(1��Ч0��Ч)
          aae036,
          aae013  --       ��ע
       )
       SELECT
          sys_guid()   ,--     ����(�������uuid)
          (select aac001 from ac60 t where t.aac002=a.aac002 )  ,--     ��ƶϵͳ����������ac60�������uuid��
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc014)  adc014 ,--     �Ƿ�μӾ�ҵ��ѵ
          (select code_value from code_value t where t.code_type='ADC015' and CODE_NAME=adc015)   adc015,--     ��ѵ����
          adc016_date  ,--     ��ѵ��ʼʱ��  
          adc016,
          adc017_date  ,--     ��ѵ��ֹʱ��  ,
          adc017,
          (select code_value from code_value t where t.code_type='ADC018' and CODE_NAME=adc018)  adc018   ,--     ��ѵ���
          (select code_value from code_value t where t.code_type='ADC019' and CODE_NAME=adc019)   adc019 ,--     ȡ��֤��
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc020,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','') adc020 ,--    --     ��ѵ������Ԫ�� 
          'excel���ݵ���'  ,--     ����������
          null  ,--     �����������
          null  ,--     ���������
          'sxft_data_imp'  ,--     �����˱���
          null  ,--     ҵ�񾭰���������
          '1'  ,--     �Ƿ���Ч(1��Ч0��Ч)
          sysdate,
          in_excel_batch_number  --      ��ע
       FROM  ac60_excel_temp a where   a.excel_batch_number=in_excel_batch_number and a.excel_isvalid='1';  
       
       
       --��������״̬4/8*70+30
       update s_excel_batch t set t.excel_batch_data_status=round(4/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60)  where t.excel_batch_number=in_excel_batch_number;
       
       COMMIT;
       
       --AC64  ��ҵ���
       
       
       rt_msg:='��ҵ�������';
       
        --�Ƚ����е��Ѿ����������ɾ��
       delete from AC64 a where exists (select 1 from ac60_excel_temp t,ac60 f where t.aac002=f.aac002 and  f.aac001=a.aac001 and t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1' );
       
       COMMIT;
       
 
       INSERT INTO AC64 (
          eec001  ,--    ����(�������uuid)
          aac001   ,--   ��ƶϵͳ����������ac60�������uuid��
          adc022,--    ��ҵʱ��
          adc022_string  ,--    ��ҵʱ��
          adc023  ,--    �Ƿ�μӴ�ҵ��ѵ
          adc024  ,--    ��ҵ����
          adc025  ,--    ��ҵ��ʵ���ַ
          adc026  ,--    ��ҵ��ʵ������
          adc027  ,--    ��ҵ���
          adc028  ,--    �Ƿ�����С�������
          adc029  ,--    �������Ԫ��
          adc030  ,--    �����Ͷ�������
          adc031  ,--     �Ƿ��ڹ��̲���ע��Ǽ�
          aae010  ,--    ����������
          aaf011  ,--    �����������
          aae009  ,--    ���������
          aae011  ,--    �����˱���
          aab301  ,--    ҵ�񾭰���������
          aae100  ,--    �Ƿ���Ч(1��Ч0��Ч)
          aae036,
          aae013  --     ��ע
       )
       SELECT
          sys_guid()   ,--     ����(�������uuid)
          (select aac001 from ac60 t where t.aac002=a.aac002 )  ,--     ��ƶϵͳ����������ac60�������uuid��
          adc022_date,
          adc022  ,--     --   ��ҵʱ��
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc023)  adc023 ,--   �Ƿ�μӴ�ҵ��ѵ
          (select code_value from code_value t where t.code_type='ADC024' and CODE_NAME=adc024)  adc024 ,--   ��ҵ����
          adc025  ,--   ��ҵ��ʵ���ַ
          adc026  ,--   ��ҵ��ʵ������
          (select code_value from code_value t where t.code_type='ADC027' and CODE_NAME=adc027)  adc027 ,--   ��ҵ���
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc028)  adc028 ,--   �Ƿ�����С�������
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc029,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','')  adc029 ,--   �������Ԫ��
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc030,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','')  adc030 ,--   �����Ͷ�������  ��
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc031) adc031  ,--   �Ƿ��ڹ��̲���ע��Ǽ�
          'excel���ݵ���'  ,--     ����������
          null  ,--     �����������
          null  ,--     ���������
          'sxft_data_imp'  ,--     �����˱���
          null  ,--     ҵ�񾭰���������
          '1'  ,--     �Ƿ���Ч(1��Ч0��Ч)
          sysdate,
          in_excel_batch_number  --      ��ע
       FROM  ac60_excel_temp a where   a.excel_batch_number=in_excel_batch_number and a.excel_isvalid='1'; 
       
       
       --��������״̬5/8*70+30
       update s_excel_batch t set t.excel_batch_data_status=round(5/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60)  where t.excel_batch_number=in_excel_batch_number;
       
       COMMIT;
       
       --AC65  δ��ҵ���
       
       rt_msg:='δ��ҵ�������';
       
        --�Ƚ����е��Ѿ����������ɾ��
       delete from AC65 a where exists (select 1 from ac60_excel_temp t,ac60 f where t.aac002=f.aac002 and  f.aac001=a.aac001 and t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1' );
       
       COMMIT;

       INSERT INTO AC65 (
          eec001  ,--     ����(�������uuid)
          aac001  ,--     ��ƶϵͳ����������ac60�������uuid��
          adc032  ,--     �Ƿ��о�ҵԸ��
          adc033  ,--     ��ҵ�����
          adc034  ,--     ������н��Ԫ��
          adc035  ,--     ��ҵ��������
          aae010  ,--     ����������
          aaf011  ,--     �����������
          aae009  ,--     ���������
          aae011  ,--     �����˱���
          aab301  ,--     ҵ�񾭰���������
          aae100  ,--     �Ƿ���Ч(1��Ч0��Ч)
          aae036  ,
          aae013  --      ��ע
       )
       SELECT
          sys_guid()   ,--     ����(�������uuid)
          (select aac001 from ac60 t where t.aac002=a.aac002 )  ,--     ��ƶϵͳ����������ac60�������uuid��
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc032) adc032  ,--   �Ƿ��о�ҵԸ��
          (select code_value from code_value t where t.code_type='ADC033' and CODE_NAME=adc033) adc033  ,--   ��ҵ�����
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc034,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','')  adc034 ,--   ������н��Ԫ��
           (select code_value from code_value t where t.code_type='ADC035' and CODE_NAME=adc035)  adc035  ,--   ��ҵ��������
          'excel���ݵ���'  ,--     ����������
          null  ,--     �����������
          null  ,--     ���������
          'sxft_data_imp'  ,--     �����˱���
          null  ,--     ҵ�񾭰���������
          '1'  ,--     �Ƿ���Ч(1��Ч0��Ч)
          sysdate,
          in_excel_batch_number  --      ��ע
       FROM  ac60_excel_temp a where   a.excel_batch_number=in_excel_batch_number and a.excel_isvalid='1';
       
       
        --��������״̬6/8*70+30
       update s_excel_batch t set t.excel_batch_data_status=round(6/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60)  where t.excel_batch_number=in_excel_batch_number;

       COMMIT;
       
       --AC66  �ṩ��ҵ�������
       
       rt_msg:='�ṩ��ҵ�����������';
       
        --�Ƚ����е��Ѿ����������ɾ��
       delete from AC66 a where exists (select 1 from ac60_excel_temp t,ac60 f where t.aac002=f.aac002 and  f.aac001=a.aac001 and t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1' );
       
       COMMIT;

       INSERT INTO AC66 (
          eec001   ,--     ����(�������uuid)
          aac001   ,--     ��ƶϵͳ����������ac60�������uuid��
          adc036   ,--   �ṩ��ҵ������ѯ����(��)
          adc037   ,--   �ṩ��ҵ��Ϣ�������Σ�
          adc038   ,--   �ṩְҵָ������ܴ������Σ�
          adc039   ,--   �ṩ��ѵ�������Σ�
          adc040   ,--   �ṩ��ҵ�������(��)
          aae010  ,--     ����������
          aaf011  ,--     �����������
          aae009  ,--     ���������
          aae011  ,--     �����˱���
          aab301  ,--     ҵ�񾭰���������
          aae100  ,--     �Ƿ���Ч(1��Ч0��Ч)
          aae036,
          aae013  --      ��ע
       )
       SELECT
          sys_guid()   ,--     ����(�������uuid)
         (select aac001 from ac60 t where t.aac002=a.aac002 )  ,--     ��ƶϵͳ����������ac60�������uuid��
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc036,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','') adc036 ,--  �ṩ��ҵ������ѯ����(��)
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc037,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','') adc037 ,--  �ṩ��ҵ��Ϣ�������Σ�
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc038,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','') adc038 ,--  �ṩְҵָ������ܴ������Σ�
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc039,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','') adc039 ,--  �ṩ��ѵ�������Σ�
          REGEXP_REPLACE( REGEXP_REPLACE(REGEXP_REPLACE(adc040,'\-[0-9]+',''),'\.[0-9]+',''),'[^\x00-\xff]|[a-z|A-Z]','') adc040 ,--  �ṩ��ҵ�������(��)
          'excel���ݵ���'  ,--     ����������
          null  ,--     �����������
          null  ,--     ���������
          'sxft_data_imp'  ,--     �����˱���
          null  ,--     ҵ�񾭰���������
          '1'  ,--     �Ƿ���Ч(1��Ч0��Ч)
          sysdate,
          in_excel_batch_number  --      ��ע
       FROM  ac60_excel_temp a where   a.excel_batch_number=in_excel_batch_number and a.excel_isvalid='1'; 
       
       --��������״̬7/8*70+30
       update s_excel_batch t set t.excel_batch_data_status=round(7/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60)  where t.excel_batch_number=in_excel_batch_number;
       
       COMMIT; 
       
       --AC67  ������ʵ���
       
       rt_msg:='������ʵ�������';
       
        --�Ƚ����е��Ѿ����������ɾ��
       delete from AC67 a where exists (select 1 from ac60_excel_temp t,ac60 f where t.aac002=f.aac002 and  f.aac001=a.aac001 and t.excel_batch_number=in_excel_batch_number and t.excel_isvalid='1' );
       
       COMMIT;
       
       INSERT INTO AC67 (
          eec001  ,--     ����(�������uuid)
          aac001  ,--     ��ƶϵͳ����������ac60�������uuid��
          adc041  ,--   �Ƿ�����ְҵ���ܲ���
          adc042  ,--   �Ƿ�������ᱣ�ղ���
          adc043  ,--   �Ƿ����ܸ�λ����
          adc044  ,--  �Ƿ������������߷���
          aae010  ,--     ����������
          aaf011  ,--     �����������
          aae009  ,--     ���������
          aae011  ,--     �����˱���
          aab301  ,--     ҵ�񾭰���������
          aae100  ,--     �Ƿ���Ч(1��Ч0��Ч)
          aae036,
          aae013  --      ��ע
       )
       SELECT
          sys_guid()   ,--     ����(�������uuid)
          (select aac001 from ac60 t where t.aac002=a.aac002 )  ,--     ��ƶϵͳ����������ac60�������uuid��
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc041) adc041 ,--   �Ƿ�����ְҵ���ܲ���
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc042) adc042 ,--   �Ƿ�������ᱣ�ղ���
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc043) adc043 ,--   �Ƿ����ܸ�λ����
          (select code_value from code_value t where t.code_type='YESNO' and CODE_NAME=adc044) adc044 ,--  �Ƿ������������߷���
          'excel���ݵ���'  ,--     ����������
          null  ,--     �����������
          null  ,--     ���������
          'sxft_data_imp'  ,--     �����˱���
          null  ,--     ҵ�񾭰���������
          '1'  ,--     �Ƿ���Ч(1��Ч0��Ч)
          sysdate,
          in_excel_batch_number  --      ��ע
       FROM  ac60_excel_temp a where   a.excel_batch_number=in_excel_batch_number and a.excel_isvalid='1'; 
       
       COMMIT;
       
       --������ʱ����״̬ 
       UPDATE ac60_excel_temp T SET T.EXCEL_ERRORMSG='�ɹ�', T.EXCEL_OPDATE=SYSDATE,t.excel_isop='1' WHERE T.EXCEL_ISVALID='1' and t.excel_batch_number=in_excel_batch_number ;
       
       --�������α�����״̬
       UPDATE S_EXCEL_BATCH T SET  T.EXCEL_BATCH_STATUS='3',T.EXCEL_BATCH_ERROR_COUNT=(SELECT count(*) from ac60_excel_temp c where c.excel_batch_number=t.excel_batch_number and c.excel_isvalid='0' ),T.EXCEL_BATCH_TOTAL_COUNT=(SELECT count(*) from ac60_excel_temp c where c.excel_batch_number=t.excel_batch_number  ) where  t.excel_batch_number=in_excel_batch_number ;
       
       
       --��������״̬8/8*70+30
       update s_excel_batch t set t.excel_batch_data_status=round(8/8*70+30),t.excel_batch_end_time=sysdate, t.excel_batch_cost=round((t.excel_batch_end_time-t.excel_batch_begin_time)*24*60*60) where t.excel_batch_number=in_excel_batch_number;
       
       rt_code:=RT_SUCCESS_CODE;
       
       update S_EXCEL_BATCH t set  t.excel_batch_rt_code=RT_SUCCESS_CODE where excel_batch_number=in_excel_batch_number;
       
       COMMIT;

    Exception
       when others THEN
       rt_code:=RT_ERROR_CODE;
       rt_msg:=rt_msg||sqlerrm;
       update S_EXCEL_BATCH t set T.EXCEL_BATCH_STATUS='4', t.excel_batch_rt_code=rt_code ,t.excel_batch_rt_msg=rt_msg where excel_batch_number=in_excel_batch_number;
       COMMIT;
    END;
end pkg_ac60_excel_data_imp;
/
