-----------------------------------------------
-- Export file for user SXJYFP@ORCL          --
-- Created by wengsh on 2017/11/29, 15:49:55 --
-----------------------------------------------

set define off
spool excel_import_about.log

prompt
prompt Creating table AC60
prompt ===================
prompt
create table AC60
(
  aac001             VARCHAR2(36) not null,
  aac003             VARCHAR2(50) not null,
  aac002             VARCHAR2(20) not null,
  aac010             VARCHAR2(100) not null,
  aab299             VARCHAR2(20),
  aac004             VARCHAR2(20) not null,
  aac005             VARCHAR2(20),
  aac006             DATE,
  aac007             NUMBER(3),
  aac033             VARCHAR2(20),
  aac017             VARCHAR2(20),
  aac024             VARCHAR2(20),
  aac011             VARCHAR2(20),
  aae006             VARCHAR2(20),
  aac067             VARCHAR2(20),
  aae015             VARCHAR2(50),
  aac029             VARCHAR2(100),
  aac030             VARCHAR2(20),
  aac031             VARCHAR2(20),
  aaf011             VARCHAR2(100),
  aae009             VARCHAR2(100),
  aae011             VARCHAR2(50),
  aae010             VARCHAR2(80),
  aae036             DATE,
  eae052             VARCHAR2(20),
  aae200             VARCHAR2(50),
  aae199             VARCHAR2(80),
  aae202             DATE,
  aae201             VARCHAR2(100),
  aae198             VARCHAR2(100),
  aae203             VARCHAR2(100),
  aae132             VARCHAR2(100),
  aae133             VARCHAR2(80),
  aae134             VARCHAR2(50),
  aae135             VARCHAR2(100),
  aae102             DATE,
  aab301             VARCHAR2(12),
  aae100             VARCHAR2(20) not null,
  aae013             VARCHAR2(200),
  excel_batch_number VARCHAR2(36)
)
;
comment on table AC60
  is '������ƶ��Ϣ����ϵͳ����';
comment on column AC60.aac001
  is '��ƶϵͳ�����������������UUID��';
comment on column AC60.aac003
  is '����';
comment on column AC60.aac002
  is '���֤��';
comment on column AC60.aac010
  is '�Ͷ��߻������ڵ�';
comment on column AC60.aab299
  is '�Ͷ��߻������ڵ�������������';
comment on column AC60.aac004
  is '�Ա����';
comment on column AC60.aac005
  is '�������';
comment on column AC60.aac006
  is '��������';
comment on column AC60.aac007
  is '����';
comment on column AC60.aac033
  is '����״������';
comment on column AC60.aac017
  is '����״������';
comment on column AC60.aac024
  is '������ò����';
comment on column AC60.aac011
  is 'ѧ�����루�Ļ��̶ȴ��룩';
comment on column AC60.aae006
  is '��ϵ�绰';
comment on column AC60.aac067
  is '�ƶ��绰';
comment on column AC60.aae015
  is '�Ͷ�����';
comment on column AC60.aac029
  is '��������';
comment on column AC60.aac030
  is '�Ƿ���У��';
comment on column AC60.aac031
  is '��Ҫ��ƶԭ��';
comment on column AC60.aaf011
  is '�����������';
comment on column AC60.aae009
  is '���������';
comment on column AC60.aae011
  is '�����˱���';
comment on column AC60.aae010
  is '����������';
comment on column AC60.aae036
  is '��������';
comment on column AC60.eae052
  is '���״̬';
comment on column AC60.aae200
  is '����˱���';
comment on column AC60.aae199
  is '���������';
comment on column AC60.aae202
  is '���ʱ��';
comment on column AC60.aae201
  is '��˻�������';
comment on column AC60.aae198
  is '��˻�����';
comment on column AC60.aae203
  is '���ԭ��';
comment on column AC60.aae132
  is '�޸Ļ�����';
comment on column AC60.aae133
  is '�޸�������';
comment on column AC60.aae134
  is '�޸��˱���';
comment on column AC60.aae135
  is '�޸Ļ�������';
comment on column AC60.aae102
  is '�޸�ʱ��';
comment on column AC60.aab301
  is 'ҵ�񾭰���������';
comment on column AC60.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC60.aae013
  is '��ע';
comment on column AC60.excel_batch_number
  is '������ʱ��֮�������κ�';
create index IDX_AC60_AAC002 on AC60 (AAC002);
create index IDX_AC60_BATCH_NUMBER on AC60 (EXCEL_BATCH_NUMBER);
alter table AC60
  add constraint PK_AC60 primary key (AAC001);

prompt
prompt Creating table AC60_EXCEL_TEMP
prompt ==============================
prompt
create table AC60_EXCEL_TEMP
(
  excel_temp_id      VARCHAR2(36) not null,
  excel_batch_number VARCHAR2(36) not null,
  aac003             VARCHAR2(50) not null,
  aac002             VARCHAR2(20) not null,
  aac010             VARCHAR2(200),
  aac004             VARCHAR2(20),
  aac005             VARCHAR2(20),
  aac007             VARCHAR2(100),
  aac024             VARCHAR2(100),
  aac011             VARCHAR2(100),
  aac033             VARCHAR2(100),
  aae015             VARCHAR2(100),
  aac029             VARCHAR2(100),
  adc001             VARCHAR2(100),
  aae006             VARCHAR2(100),
  aac030             VARCHAR2(100),
  aac031             VARCHAR2(100),
  adc002             VARCHAR2(100),
  adc003             VARCHAR2(100),
  adc004             VARCHAR2(100),
  adc005             VARCHAR2(200),
  adc006             VARCHAR2(100),
  adc007             VARCHAR2(100),
  adc008             VARCHAR2(100),
  adc009             VARCHAR2(100),
  adc010             VARCHAR2(100),
  adc011             VARCHAR2(200),
  adc012             VARCHAR2(100),
  adc013             VARCHAR2(100),
  adc014             VARCHAR2(100),
  adc015             VARCHAR2(100),
  adc016             VARCHAR2(100),
  adc017             VARCHAR2(100),
  adc018             VARCHAR2(100),
  adc019             VARCHAR2(100),
  adc020             VARCHAR2(100),
  adc021             VARCHAR2(100),
  adc022             VARCHAR2(100),
  adc023             VARCHAR2(100),
  adc024             VARCHAR2(100),
  adc025             VARCHAR2(100),
  adc026             VARCHAR2(100),
  adc027             VARCHAR2(100),
  adc028             VARCHAR2(100),
  adc029             VARCHAR2(100),
  adc030             VARCHAR2(100),
  adc031             VARCHAR2(100),
  adc032             VARCHAR2(100),
  adc033             VARCHAR2(100),
  adc034             VARCHAR2(100),
  adc035             VARCHAR2(100),
  adc036             VARCHAR2(100),
  adc037             VARCHAR2(100),
  adc038             VARCHAR2(100),
  adc039             VARCHAR2(100),
  adc040             VARCHAR2(100),
  adc041             VARCHAR2(100),
  adc042             VARCHAR2(100),
  adc043             VARCHAR2(100),
  adc044             VARCHAR2(100),
  excel_isvalid      VARCHAR2(36) not null,
  excel_rownum       NUMBER(10) not null,
  excel_impdate      DATE not null,
  excel_errormsg     VARCHAR2(36),
  excel_isop         VARCHAR2(36),
  excel_opdate       DATE,
  adc005_date        DATE,
  adc016_date        DATE,
  adc017_date        DATE,
  adc022_date        DATE,
  adc012_date        DATE
)
;
comment on table AC60_EXCEL_TEMP
  is '������ƶ��Ϣ�������ݵ�����ʱ��';
comment on column AC60_EXCEL_TEMP.excel_temp_id
  is '������ʱ��֮��ʱ������';
comment on column AC60_EXCEL_TEMP.excel_batch_number
  is '������ʱ��֮�������κ�';
comment on column AC60_EXCEL_TEMP.aac003
  is '������ʱ��֮������Ϣ-����';
comment on column AC60_EXCEL_TEMP.aac002
  is '������ʱ��֮������Ϣ-���֤��';
comment on column AC60_EXCEL_TEMP.aac010
  is '������ʱ��֮������Ϣ-�Ͷ��߻������ڵ�';
comment on column AC60_EXCEL_TEMP.aac004
  is '������ʱ��֮������Ϣ-�Ա�';
comment on column AC60_EXCEL_TEMP.aac005
  is '������ʱ��֮������Ϣ-����';
comment on column AC60_EXCEL_TEMP.aac007
  is '������ʱ��֮������Ϣ-����';
comment on column AC60_EXCEL_TEMP.aac024
  is '������ʱ��֮������Ϣ-������ò';
comment on column AC60_EXCEL_TEMP.aac011
  is '������ʱ��֮������Ϣ-ѧ������';
comment on column AC60_EXCEL_TEMP.aac033
  is '������ʱ��֮������Ϣ-����״��';
comment on column AC60_EXCEL_TEMP.aae015
  is '������ʱ��֮������Ϣ-�Ͷ�����';
comment on column AC60_EXCEL_TEMP.aac029
  is '������ʱ��֮������Ϣ-��������';
comment on column AC60_EXCEL_TEMP.adc001
  is '������ʱ��֮��ҵ���-��ҵ��ʽ';
comment on column AC60_EXCEL_TEMP.aae006
  is '������ʱ��֮������Ϣ-��ϵ�绰';
comment on column AC60_EXCEL_TEMP.aac030
  is '������ʱ��֮������Ϣ-�Ƿ���У��';
comment on column AC60_EXCEL_TEMP.aac031
  is '������ʱ��֮������Ϣ-��Ҫ��ƶԭ��';
comment on column AC60_EXCEL_TEMP.adc002
  is '������ʱ��֮��ҵ���-��ҵ����';
comment on column AC60_EXCEL_TEMP.adc003
  is '������ʱ��֮��ҵ���-��ҵ��';
comment on column AC60_EXCEL_TEMP.adc004
  is '������ʱ��֮��ҵ���-��ҵ����';
comment on column AC60_EXCEL_TEMP.adc005
  is '������ʱ��֮��ҵ���--��ҵʱ��';
comment on column AC60_EXCEL_TEMP.adc006
  is '������ʱ��֮��ҵ���-�Ƿ�ǩ���Ͷ���ͬ��Э��';
comment on column AC60_EXCEL_TEMP.adc007
  is '������ʱ��֮��ҵ���-�Ƿ�μ���ᱣ��';
comment on column AC60_EXCEL_TEMP.adc008
  is '������ʱ��֮��ҵ���-�¾�����';
comment on column AC60_EXCEL_TEMP.adc009
  is '������ʱ��֮�����Ը�λ����ר�ڰ������-��λ����';
comment on column AC60_EXCEL_TEMP.adc010
  is '������ʱ��֮�����Ը�λ����ר�ڰ������-�����Ը�λ���õ�ַ';
comment on column AC60_EXCEL_TEMP.adc011
  is '������ʱ��֮�����Ը�λ����ר�ڰ������-�����Ը�λ���õ�λ����';
comment on column AC60_EXCEL_TEMP.adc012
  is '������ʱ��֮�����Ը�λ����ר�ڰ������-����ʱ��';
comment on column AC60_EXCEL_TEMP.adc013
  is '������ʱ��֮�����Ը�λ����ר�ڰ������-��λ����';
comment on column AC60_EXCEL_TEMP.adc014
  is '������ʱ��֮��ѵ���-�Ƿ�μӾ�ҵ��ѵ';
comment on column AC60_EXCEL_TEMP.adc015
  is '������ʱ��֮��ѵ���-��ѵ����';
comment on column AC60_EXCEL_TEMP.adc016
  is '������ʱ��֮��ѵ���-��ѵ��ʼʱ��';
comment on column AC60_EXCEL_TEMP.adc017
  is '������ʱ��֮��ѵ���-��ѵ��ֹʱ��';
comment on column AC60_EXCEL_TEMP.adc018
  is '������ʱ��֮��ѵ���-��ѵ���';
comment on column AC60_EXCEL_TEMP.adc019
  is '������ʱ��֮��ѵ���-ȡ��֤��';
comment on column AC60_EXCEL_TEMP.adc020
  is '������ʱ��֮��ѵ���-��ѵ������Ԫ��';
comment on column AC60_EXCEL_TEMP.adc021
  is '������ʱ��֮��ҵ���-�Ƿ��ҵ';
comment on column AC60_EXCEL_TEMP.adc022
  is '������ʱ��֮��ҵ���-��ҵʱ��';
comment on column AC60_EXCEL_TEMP.adc023
  is '������ʱ��֮��ҵ���-�Ƿ�μӴ�ҵ��ѵ';
comment on column AC60_EXCEL_TEMP.adc024
  is '������ʱ��֮��ҵ���-��ҵ����';
comment on column AC60_EXCEL_TEMP.adc025
  is '������ʱ��֮��ҵ���-��ҵ��ʵ���ַ';
comment on column AC60_EXCEL_TEMP.adc026
  is '������ʱ��֮��ҵ���-��ҵ��ʵ������';
comment on column AC60_EXCEL_TEMP.adc027
  is '������ʱ��֮��ҵ���-��ҵ���';
comment on column AC60_EXCEL_TEMP.adc028
  is '������ʱ��֮��ҵ���-�Ƿ�����С�������';
comment on column AC60_EXCEL_TEMP.adc029
  is '������ʱ��֮��ҵ���-�������Ԫ��';
comment on column AC60_EXCEL_TEMP.adc030
  is '������ʱ��֮��ҵ���-�����Ͷ�������';
comment on column AC60_EXCEL_TEMP.adc031
  is '������ʱ��֮��ҵ���-�Ƿ��ڹ��̲���ע��Ǽ�';
comment on column AC60_EXCEL_TEMP.adc032
  is '������ʱ��֮δ��ҵ���-�Ƿ��о�ҵԸ��';
comment on column AC60_EXCEL_TEMP.adc033
  is '������ʱ��֮δ��ҵ���-��ҵ�����';
comment on column AC60_EXCEL_TEMP.adc034
  is '������ʱ��֮δ��ҵ���-������н��Ԫ��';
comment on column AC60_EXCEL_TEMP.adc035
  is '������ʱ��֮δ��ҵ���-��ҵ��������';
comment on column AC60_EXCEL_TEMP.adc036
  is '������ʱ��֮�ṩ��ҵ�������-�ṩ��ҵ������ѯ����(��)';
comment on column AC60_EXCEL_TEMP.adc037
  is '������ʱ��֮�ṩ��ҵ�������-�ṩ��ҵ��Ϣ�������Σ�';
comment on column AC60_EXCEL_TEMP.adc038
  is '������ʱ��֮�ṩ��ҵ�������-�ṩְҵָ������ܴ������Σ�';
comment on column AC60_EXCEL_TEMP.adc039
  is '������ʱ��֮�ṩ��ҵ�������-�ṩ��ѵ�������Σ�';
comment on column AC60_EXCEL_TEMP.adc040
  is '������ʱ��֮�ṩ��ҵ�������-�ṩ��ҵ�������(��)';
comment on column AC60_EXCEL_TEMP.adc041
  is '������ʱ��֮������ʵ���-�Ƿ�����ְҵ���ܲ���';
comment on column AC60_EXCEL_TEMP.adc042
  is '������ʱ��֮������ʵ���-�Ƿ�������ᱣ�ղ���';
comment on column AC60_EXCEL_TEMP.adc043
  is '������ʱ��֮������ʵ���-�Ƿ����ܸ�λ����';
comment on column AC60_EXCEL_TEMP.adc044
  is '������ʱ��֮������ʵ���-�Ƿ������������߷���';
comment on column AC60_EXCEL_TEMP.excel_isvalid
  is '������ʱ��֮���������Ƿ���ȷ';
comment on column AC60_EXCEL_TEMP.excel_rownum
  is '������ʱ��֮����';
comment on column AC60_EXCEL_TEMP.excel_impdate
  is '������ʱ��֮��������';
comment on column AC60_EXCEL_TEMP.excel_errormsg
  is '������ʱ��֮��������ԭ��';
comment on column AC60_EXCEL_TEMP.excel_isop
  is '������ʱ��֮�Ƿ��Ѿ�����';
comment on column AC60_EXCEL_TEMP.excel_opdate
  is '������ʱ��֮��������';
comment on column AC60_EXCEL_TEMP.adc005_date
  is '������ʱ��֮��ҵ���--��ҵʱ��(���ڸ�ʽ)';
comment on column AC60_EXCEL_TEMP.adc016_date
  is '������ʱ��֮��ѵ���-��ѵ��ʼʱ��(���ڸ�ʽ)';
comment on column AC60_EXCEL_TEMP.adc017_date
  is '������ʱ��֮��ѵ���-��ѵ��ֹʱ��(���ڸ�ʽ)';
comment on column AC60_EXCEL_TEMP.adc022_date
  is '������ʱ��֮��ҵ���-��ҵʱ��(���ڸ�ʽ)';
comment on column AC60_EXCEL_TEMP.adc012_date
  is '������ʱ��֮�����Ը�λ����ר�ڰ������-����ʱ��(���ڸ�ʽ)';
create index IDX_AC60_EXCEL_TEMP on AC60_EXCEL_TEMP (EXCEL_BATCH_NUMBER);
create index IDX_AC60_EXCEL_TEMP_AAC002 on AC60_EXCEL_TEMP (AAC002);
alter table AC60_EXCEL_TEMP
  add constraint PK_AC60_EXCEL_TEMP primary key (EXCEL_TEMP_ID);

prompt
prompt Creating table AC61
prompt ===================
prompt
create table AC61
(
  eec001        VARCHAR2(36) not null,
  aac001        VARCHAR2(36) not null,
  adc001        VARCHAR2(20),
  adc002        VARCHAR2(20),
  adc003        VARCHAR2(100),
  adc004        VARCHAR2(100),
  adc005        DATE,
  adc006        VARCHAR2(50),
  adc007        VARCHAR2(20),
  adc008        NUMBER(10,2),
  aaf011        VARCHAR2(100),
  aae009        VARCHAR2(100),
  aae011        VARCHAR2(50) not null,
  aae010        VARCHAR2(80) not null,
  aae036        DATE not null,
  aae132        VARCHAR2(100),
  aae133        VARCHAR2(80),
  aae134        VARCHAR2(50),
  aae135        VARCHAR2(100),
  aae102        DATE,
  aab301        VARCHAR2(12),
  aae100        VARCHAR2(20) not null,
  aae013        VARCHAR2(200),
  adc005_string VARCHAR2(200)
)
;
comment on table AC61
  is '��ƶ��ҵ�����';
comment on column AC61.eec001
  is '����(�������UUID)';
comment on column AC61.aac001
  is '��ƶϵͳ����������AC60�������UUID��';
comment on column AC61.adc001
  is '��ҵ��ʽ';
comment on column AC61.adc002
  is '��ҵ����';
comment on column AC61.adc003
  is '��ҵ��';
comment on column AC61.adc004
  is '��ҵ����';
comment on column AC61.adc005
  is '��ҵʱ��';
comment on column AC61.adc006
  is '�Ƿ�ǩ���Ͷ���ͬ��Э��';
comment on column AC61.adc007
  is '�Ƿ�μ���ᱣ��';
comment on column AC61.adc008
  is '�¾�����';
comment on column AC61.aaf011
  is '�����������';
comment on column AC61.aae009
  is '���������';
comment on column AC61.aae011
  is '�����˱���';
comment on column AC61.aae010
  is '����������';
comment on column AC61.aae036
  is '��������';
comment on column AC61.aae132
  is '�޸Ļ�����';
comment on column AC61.aae133
  is '�޸�������';
comment on column AC61.aae134
  is '�޸��˱���';
comment on column AC61.aae135
  is '�޸Ļ�������';
comment on column AC61.aae102
  is '�޸�ʱ��';
comment on column AC61.aab301
  is 'ҵ�񾭰���������';
comment on column AC61.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC61.aae013
  is '��ע';
comment on column AC61.adc005_string
  is '��ҵʱ����ʱ�ֶ�';
create index IDX_AC61_AAC001 on AC61 (AAC001);
alter table AC61
  add constraint PK_AC61 primary key (EEC001);

prompt
prompt Creating table AC62
prompt ===================
prompt
create table AC62
(
  eec001        VARCHAR2(36) not null,
  aac001        VARCHAR2(36) not null,
  adc009        VARCHAR2(20),
  adc010        VARCHAR2(100),
  adc011        VARCHAR2(200),
  adc012        DATE,
  adc013        VARCHAR2(100),
  aaf011        VARCHAR2(100),
  aae009        VARCHAR2(100),
  aae011        VARCHAR2(50) not null,
  aae010        VARCHAR2(80) not null,
  aae036        DATE not null,
  aae132        VARCHAR2(100),
  aae133        VARCHAR2(80),
  aae134        VARCHAR2(50),
  aae135        VARCHAR2(100),
  aae102        DATE,
  aab301        VARCHAR2(12),
  aae100        VARCHAR2(20) not null,
  aae013        VARCHAR2(200),
  adc012_string VARCHAR2(100)
)
;
comment on table AC62
  is '�����Ը�λ����ר�ڰ������';
comment on column AC62.eec001
  is '����(�������UUID)';
comment on column AC62.aac001
  is '��ƶϵͳ����������AC60�������UUID��';
comment on column AC62.adc009
  is '��λ����';
comment on column AC62.adc010
  is '�����Ը�λ���õ�ַ';
comment on column AC62.adc011
  is '�����Ը�λ���õ�λ����';
comment on column AC62.adc012
  is '����ʱ��';
comment on column AC62.adc013
  is '��λ����';
comment on column AC62.aaf011
  is '�����������';
comment on column AC62.aae009
  is '���������';
comment on column AC62.aae011
  is '�����˱���';
comment on column AC62.aae010
  is '����������';
comment on column AC62.aae036
  is '��������';
comment on column AC62.aae132
  is '�޸Ļ�����';
comment on column AC62.aae133
  is '�޸�������';
comment on column AC62.aae134
  is '�޸��˱���';
comment on column AC62.aae135
  is '�޸Ļ�������';
comment on column AC62.aae102
  is '�޸�ʱ��';
comment on column AC62.aab301
  is 'ҵ�񾭰���������';
comment on column AC62.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC62.aae013
  is '��ע';
comment on column AC62.adc012_string
  is '����ʱ����ʱ�ֶ�';
create index IDX_AC62_AAC001 on AC62 (AAC001);
alter table AC62
  add constraint PK_AC62 primary key (EEC001);

prompt
prompt Creating table AC63
prompt ===================
prompt
create table AC63
(
  eec001        VARCHAR2(36) not null,
  aac001        VARCHAR2(36) not null,
  adc014        VARCHAR2(20),
  adc015        VARCHAR2(20),
  adc016        DATE,
  adc017        DATE,
  adc018        VARCHAR2(20),
  adc019        VARCHAR2(20),
  adc020        NUMBER(10,2),
  adc021        VARCHAR2(20),
  aaf011        VARCHAR2(100),
  aae009        VARCHAR2(100),
  aae011        VARCHAR2(50) not null,
  aae010        VARCHAR2(80) not null,
  aae036        DATE not null,
  aae132        VARCHAR2(100),
  aae133        VARCHAR2(80),
  aae134        VARCHAR2(50),
  aae135        VARCHAR2(100),
  aae102        DATE,
  aab301        VARCHAR2(12),
  aae100        VARCHAR2(20) not null,
  aae013        VARCHAR2(200),
  adc016_string VARCHAR2(100),
  adc017_string VARCHAR2(100)
)
;
comment on table AC63
  is ' ��ѵ���';
comment on column AC63.eec001
  is '����(�������UUID)';
comment on column AC63.aac001
  is '��ƶϵͳ����������AC60�������UUID��';
comment on column AC63.adc014
  is '�Ƿ�μӾ�ҵ��ѵ';
comment on column AC63.adc015
  is '��ѵ����';
comment on column AC63.adc016
  is '��ѵ��ʼʱ��';
comment on column AC63.adc017
  is '��ѵ��ֹʱ��';
comment on column AC63.adc018
  is '��ѵ���';
comment on column AC63.adc019
  is 'ȡ��֤��';
comment on column AC63.adc020
  is '��ѵ������Ԫ��';
comment on column AC63.adc021
  is '�Ƿ��ҵ';
comment on column AC63.aaf011
  is '�����������';
comment on column AC63.aae009
  is '���������';
comment on column AC63.aae011
  is '�����˱���';
comment on column AC63.aae010
  is '����������';
comment on column AC63.aae036
  is '��������';
comment on column AC63.aae132
  is '�޸Ļ�����';
comment on column AC63.aae133
  is '�޸�������';
comment on column AC63.aae134
  is '�޸��˱���';
comment on column AC63.aae135
  is '�޸Ļ�������';
comment on column AC63.aae102
  is '�޸�ʱ��';
comment on column AC63.aab301
  is 'ҵ�񾭰���������';
comment on column AC63.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC63.aae013
  is '��ע';
comment on column AC63.adc016_string
  is '��ѵ��ʼʱ����ʱ�ֶ�';
comment on column AC63.adc017_string
  is '��ѵ��ֹʱ����ʱ�ֶ�';
create index IDX_AC63_AAC001 on AC63 (AAC001);
alter table AC63
  add constraint PK_AC63 primary key (EEC001);

prompt
prompt Creating table AC64
prompt ===================
prompt
create table AC64
(
  eec001        VARCHAR2(36) not null,
  aac001        VARCHAR2(36) not null,
  adc022        DATE,
  adc023        VARCHAR2(20),
  adc024        VARCHAR2(20),
  adc025        VARCHAR2(100),
  adc026        VARCHAR2(100),
  adc027        VARCHAR2(20),
  adc028        VARCHAR2(20),
  adc029        NUMBER(10,2),
  adc030        NUMBER(10),
  adc031        VARCHAR2(100),
  aaf011        VARCHAR2(100),
  aae009        VARCHAR2(100),
  aae011        VARCHAR2(50) not null,
  aae010        VARCHAR2(80) not null,
  aae036        DATE not null,
  aae132        VARCHAR2(100),
  aae133        VARCHAR2(80),
  aae134        VARCHAR2(50),
  aae135        VARCHAR2(100),
  aae102        DATE,
  aab301        VARCHAR2(12),
  aae100        VARCHAR2(20) not null,
  aae013        VARCHAR2(200),
  adc022_string VARCHAR2(100)
)
;
comment on table AC64
  is '��ҵ���';
comment on column AC64.eec001
  is '����(�������UUID)';
comment on column AC64.aac001
  is '��ƶϵͳ����������AC60�������UUID��';
comment on column AC64.adc022
  is '��ҵʱ��';
comment on column AC64.adc023
  is '�Ƿ�μӴ�ҵ��ѵ';
comment on column AC64.adc024
  is '��ҵ����';
comment on column AC64.adc025
  is '��ҵ��ʵ���ַ';
comment on column AC64.adc026
  is '��ҵ��ʵ������';
comment on column AC64.adc027
  is '��ҵ���';
comment on column AC64.adc028
  is '�Ƿ�����С�������';
comment on column AC64.adc029
  is '�������Ԫ��';
comment on column AC64.adc030
  is '�����Ͷ�������';
comment on column AC64.adc031
  is '�Ƿ��ڹ��̲���ע��Ǽ�';
comment on column AC64.aaf011
  is '�����������';
comment on column AC64.aae009
  is '���������';
comment on column AC64.aae011
  is '�����˱���';
comment on column AC64.aae010
  is '����������';
comment on column AC64.aae036
  is '��������';
comment on column AC64.aae132
  is '�޸Ļ�����';
comment on column AC64.aae133
  is '�޸�������';
comment on column AC64.aae134
  is '�޸��˱���';
comment on column AC64.aae135
  is '�޸Ļ�������';
comment on column AC64.aae102
  is '�޸�ʱ��';
comment on column AC64.aab301
  is 'ҵ�񾭰���������';
comment on column AC64.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC64.aae013
  is '��ע';
comment on column AC64.adc022_string
  is '��ҵʱ����ʱ�ֶ�';
create index IDX_AC64_AAC001 on AC64 (AAC001);
alter table AC64
  add constraint PK_AC64 primary key (EEC001);

prompt
prompt Creating table AC65
prompt ===================
prompt
create table AC65
(
  eec001 VARCHAR2(36) not null,
  aac001 VARCHAR2(36) not null,
  adc032 VARCHAR2(20),
  adc033 VARCHAR2(20),
  adc034 NUMBER(10,2),
  adc035 VARCHAR2(20),
  aaf011 VARCHAR2(100),
  aae009 VARCHAR2(100),
  aae011 VARCHAR2(50) not null,
  aae010 VARCHAR2(80) not null,
  aae036 DATE not null,
  aae132 VARCHAR2(100),
  aae133 VARCHAR2(80),
  aae134 VARCHAR2(50),
  aae135 VARCHAR2(100),
  aae102 DATE,
  aab301 VARCHAR2(12),
  aae100 VARCHAR2(20) not null,
  aae013 VARCHAR2(200)
)
;
comment on table AC65
  is 'δ��ҵ���';
comment on column AC65.eec001
  is '����(�������UUID)';
comment on column AC65.aac001
  is '��ƶϵͳ����������AC60�������UUID��';
comment on column AC65.adc032
  is '�Ƿ��о�ҵԸ��';
comment on column AC65.adc033
  is '��ҵ�����';
comment on column AC65.adc034
  is '������н��Ԫ��';
comment on column AC65.adc035
  is '��ҵ��������';
comment on column AC65.aaf011
  is '�����������';
comment on column AC65.aae009
  is '���������';
comment on column AC65.aae011
  is '�����˱���';
comment on column AC65.aae010
  is '����������';
comment on column AC65.aae036
  is '��������';
comment on column AC65.aae132
  is '�޸Ļ�����';
comment on column AC65.aae133
  is '�޸�������';
comment on column AC65.aae134
  is '�޸��˱���';
comment on column AC65.aae135
  is '�޸Ļ�������';
comment on column AC65.aae102
  is '�޸�ʱ��';
comment on column AC65.aab301
  is 'ҵ�񾭰���������';
comment on column AC65.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC65.aae013
  is '��ע';
create index IDX_AC65_AAC001 on AC65 (AAC001);
alter table AC65
  add constraint PK_AC65 primary key (EEC001);

prompt
prompt Creating table AC66
prompt ===================
prompt
create table AC66
(
  eec001 VARCHAR2(36) not null,
  aac001 VARCHAR2(36) not null,
  adc036 NUMBER(5),
  adc037 NUMBER(5),
  adc038 NUMBER(5),
  adc039 NUMBER(5),
  adc040 NUMBER(5),
  aaf011 VARCHAR2(100),
  aae009 VARCHAR2(100),
  aae011 VARCHAR2(50) not null,
  aae010 VARCHAR2(80) not null,
  aae036 DATE not null,
  aae132 VARCHAR2(100),
  aae133 VARCHAR2(80),
  aae134 VARCHAR2(50),
  aae135 VARCHAR2(100),
  aae102 DATE,
  aab301 VARCHAR2(12),
  aae100 VARCHAR2(20) not null,
  aae013 VARCHAR2(200)
)
;
comment on table AC66
  is '�ṩ��ҵ�������';
comment on column AC66.eec001
  is '����(�������UUID)';
comment on column AC66.aac001
  is '��ƶϵͳ����������AC60�������UUID��';
comment on column AC66.adc036
  is '�ṩ��ҵ������ѯ����(��)';
comment on column AC66.adc037
  is '�ṩ��ҵ��Ϣ�������Σ�';
comment on column AC66.adc038
  is '�ṩְҵָ������ܴ������Σ�';
comment on column AC66.adc039
  is '�ṩ��ѵ�������Σ�';
comment on column AC66.adc040
  is '�ṩ��ҵ�������(��)';
comment on column AC66.aaf011
  is '�����������';
comment on column AC66.aae009
  is '���������';
comment on column AC66.aae011
  is '�����˱���';
comment on column AC66.aae010
  is '����������';
comment on column AC66.aae036
  is '��������';
comment on column AC66.aae132
  is '�޸Ļ�����';
comment on column AC66.aae133
  is '�޸�������';
comment on column AC66.aae134
  is '�޸��˱���';
comment on column AC66.aae135
  is '�޸Ļ�������';
comment on column AC66.aae102
  is '�޸�ʱ��';
comment on column AC66.aab301
  is 'ҵ�񾭰���������';
comment on column AC66.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC66.aae013
  is '��ע';
create index IDX_AC66_AAC001 on AC66 (AAC001);
alter table AC66
  add constraint PK_AC66 primary key (EEC001);

prompt
prompt Creating table AC67
prompt ===================
prompt
create table AC67
(
  eec001 VARCHAR2(36) not null,
  aac001 VARCHAR2(36) not null,
  adc041 VARCHAR2(20),
  adc042 VARCHAR2(20),
  adc043 VARCHAR2(20),
  adc044 VARCHAR2(20),
  aaf011 VARCHAR2(100),
  aae009 VARCHAR2(100),
  aae011 VARCHAR2(50) not null,
  aae010 VARCHAR2(80) not null,
  aae036 DATE not null,
  aae132 VARCHAR2(100),
  aae133 VARCHAR2(80),
  aae134 VARCHAR2(50),
  aae135 VARCHAR2(100),
  aae102 DATE,
  aab301 VARCHAR2(12),
  aae100 VARCHAR2(20) not null,
  aae013 VARCHAR2(200)
)
;
comment on table AC67
  is '������ʵ���';
comment on column AC67.eec001
  is '����(�������UUID)';
comment on column AC67.aac001
  is '��ƶϵͳ����������AC60�������UUID��';
comment on column AC67.adc041
  is '�Ƿ�����ְҵ���ܲ���';
comment on column AC67.adc042
  is '�Ƿ�������ᱣ�ղ���';
comment on column AC67.adc043
  is '�Ƿ����ܸ�λ����';
comment on column AC67.adc044
  is '�Ƿ������������߷���';
comment on column AC67.aaf011
  is '�����������';
comment on column AC67.aae009
  is '���������';
comment on column AC67.aae011
  is '�����˱���';
comment on column AC67.aae010
  is '����������';
comment on column AC67.aae036
  is '��������';
comment on column AC67.aae132
  is '�޸Ļ�����';
comment on column AC67.aae133
  is '�޸�������';
comment on column AC67.aae134
  is '�޸��˱���';
comment on column AC67.aae135
  is '�޸Ļ�������';
comment on column AC67.aae102
  is '�޸�ʱ��';
comment on column AC67.aab301
  is 'ҵ�񾭰���������';
comment on column AC67.aae100
  is '�Ƿ���Ч(1��Ч0��Ч)';
comment on column AC67.aae013
  is '��ע';

prompt
prompt Creating table S_EXCEL_BATCH
prompt ============================
prompt
create table S_EXCEL_BATCH
(
  excel_batch_id            VARCHAR2(36) not null,
  excel_batch_number        VARCHAR2(36),
  excel_batch_total_count   NUMBER(10),
  excel_batch_error_count   NUMBER(10),
  excel_batch_begin_time    DATE,
  excel_batch_end_time      DATE,
  excel_batch_cost          NUMBER(10),
  excel_batch_file_path     VARCHAR2(500),
  excel_batch_file_length   NUMBER(10),
  excel_batch_excel_type    VARCHAR2(20),
  excel_batch_aae011        VARCHAR2(20),
  excel_batch_status        VARCHAR2(3),
  excel_batch_data_status   NUMBER(3),
  excel_batch_rt_code       VARCHAR2(20),
  excel_batch_rt_msg        VARCHAR2(200),
  excel_batch_file_name     VARCHAR2(200),
  excel_error_file_path     VARCHAR2(200),
  excel_error_file_download VARCHAR2(200)
)
;
comment on table S_EXCEL_BATCH
  is 'ϵͳ����֮excel���ݵ���״̬��';
comment on column S_EXCEL_BATCH.excel_batch_id
  is 'excel����״̬����(uuid)';
comment on column S_EXCEL_BATCH.excel_batch_number
  is 'excel�������κš����ɹ���������ʱ�������';
comment on column S_EXCEL_BATCH.excel_batch_total_count
  is 'excel������������';
comment on column S_EXCEL_BATCH.excel_batch_error_count
  is 'excel�������ݴ�������';
comment on column S_EXCEL_BATCH.excel_batch_begin_time
  is 'excel�������ݿ�ʼʱ��';
comment on column S_EXCEL_BATCH.excel_batch_end_time
  is 'excel�������ݽ���ʱ��';
comment on column S_EXCEL_BATCH.excel_batch_cost
  is 'excel�������ݺķ�ʱ��(�룩';
comment on column S_EXCEL_BATCH.excel_batch_file_path
  is 'excel�����ļ�·��';
comment on column S_EXCEL_BATCH.excel_batch_file_length
  is 'excel�����ļ���С';
comment on column S_EXCEL_BATCH.excel_batch_excel_type
  is 'excel��������';
comment on column S_EXCEL_BATCH.excel_batch_aae011
  is 'excel���뾭����';
comment on column S_EXCEL_BATCH.excel_batch_status
  is 'excel����״̬���벽��(-1Ĭ��״̬ 0ת��xlsx 1������ʱ�� 2������ʱ�� 3������� 4����ʧ��)';
comment on column S_EXCEL_BATCH.excel_batch_data_status
  is 'excel��������״̬(0-100)';
comment on column S_EXCEL_BATCH.excel_batch_rt_code
  is 'excel��������У���Ƿ�ɹ�';
comment on column S_EXCEL_BATCH.excel_batch_rt_msg
  is 'excel��������У�����ԭ��';
comment on column S_EXCEL_BATCH.excel_batch_file_name
  is 'excel�����ļ�ԭ�ļ���';
comment on column S_EXCEL_BATCH.excel_error_file_path
  is 'excel�����ļ��ļ�·��';
comment on column S_EXCEL_BATCH.excel_error_file_download
  is 'excel�����ļ��ļ�����״��0�������� 1���ɳɹ������� 2 ����ʧ�ܣ�';
alter table S_EXCEL_BATCH
  add constraint PK_EXCEL_BATCH_ID primary key (EXCEL_BATCH_ID);

prompt
prompt Creating package PKG_AC60_EXCEL_DATA_IMP
prompt ========================================
prompt
create or replace package pkg_ac60_excel_data_imp AS

  -- �ɹ����ر�־
  RT_SUCCESS_CODE constant number  := '1';
  -- ���󷵻ر�־
  RT_ERROR_CODE   constant number  := '0';

  PROCEDURE ac60_temp_imp_update(in_excel_batch_number varchar2,rt_code out varchar2,rt_msg out varchar2);

end pkg_ac60_excel_data_imp;
/


spool off
