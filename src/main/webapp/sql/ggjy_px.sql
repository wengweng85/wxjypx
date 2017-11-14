-----------------------------------------------
-- Export file for user GGJY_PX@ORCL158      --
-- Created by wengsh on 2017/11/14, 18:48:07 --
-----------------------------------------------

set define off
spool ggjy_px.log

prompt
prompt Creating table AA01
prompt ===================
prompt
create table AA01
(
  aaa001 VARCHAR2(50) not null,
  aaa002 VARCHAR2(50) not null,
  aaa005 VARCHAR2(500) not null,
  aaa105 VARCHAR2(100),
  aaa104 VARCHAR2(1) not null
)
;
alter table AA01
  add constraint PK_AA01 primary key (AAA001);

prompt
prompt Creating table AAA1
prompt ===================
prompt
create table AAA1
(
  aaa148 VARCHAR2(36),
  aab301 VARCHAR2(36)
)
;
comment on table AAA1
  is 'ϵͳ��֮����Ȩ�޼�����';
comment on column AAA1.aaa148
  is '�ϼ��������';
comment on column AAA1.aab301
  is '����Ͻ�¼��������';

prompt
prompt Creating table CODE_TYPE
prompt ========================
prompt
create table CODE_TYPE
(
  code_type        VARCHAR2(20) not null,
  type_name        VARCHAR2(200),
  code_group       VARCHAR2(200),
  code_description VARCHAR2(200),
  code_root_value  VARCHAR2(20)
)
;
comment on table CODE_TYPE
  is '[0078]ҵ������';
comment on column CODE_TYPE.code_type
  is '����������';
comment on column CODE_TYPE.type_name
  is '������������';
comment on column CODE_TYPE.code_group
  is '�������ķ���';
comment on column CODE_TYPE.code_description
  is '������������';
comment on column CODE_TYPE.code_root_value
  is '��������Ŀ¼ֵ�������';
alter table CODE_TYPE
  add constraint PK_CODE_TYPE primary key (CODE_TYPE);

prompt
prompt Creating table CODE_VALUE
prompt =========================
prompt
create table CODE_VALUE
(
  code_seq       NUMBER(6) not null,
  code_type      VARCHAR2(200) not null,
  code_level     VARCHAR2(10),
  code_value     VARCHAR2(100),
  par_code_value VARCHAR2(10),
  code_name      VARCHAR2(200),
  code_spelling  VARCHAR2(100),
  code_status    CHAR(1),
  code_describe  VARCHAR2(500),
  code_sort      VARCHAR2(100)
)
;
comment on table CODE_VALUE
  is '[0079]ҵ�������ϸ��';
comment on column CODE_VALUE.code_seq
  is '���������';
comment on column CODE_VALUE.code_type
  is '����������';
comment on column CODE_VALUE.code_level
  is '����ȼ�';
comment on column CODE_VALUE.code_value
  is '����ֵ';
comment on column CODE_VALUE.par_code_value
  is '������ֵ';
comment on column CODE_VALUE.code_name
  is '��������';
comment on column CODE_VALUE.code_spelling
  is '����ƴ��';
comment on column CODE_VALUE.code_status
  is '����״̬��0������ʾ��1����ʾ��';
comment on column CODE_VALUE.code_describe
  is '����ȫ��';
comment on column CODE_VALUE.code_sort
  is '�������';
create index CODE_VALUE_SEQ on CODE_VALUE (CODE_TYPE, PAR_CODE_VALUE);
alter table CODE_VALUE
  add primary key (CODE_SEQ);

prompt
prompt Creating table DEMO_AC01
prompt ========================
prompt
create table DEMO_AC01
(
  aac001   VARCHAR2(20) not null,
  aac003   VARCHAR2(50) not null,
  aac002   VARCHAR2(20),
  aac004   VARCHAR2(3) not null,
  aac005   VARCHAR2(3) not null,
  aac006   DATE not null,
  aac033   VARCHAR2(3),
  aac017   VARCHAR2(3),
  aac024   VARCHAR2(3),
  aac011   VARCHAR2(3),
  aae006   VARCHAR2(20),
  aac067   VARCHAR2(20),
  aae015   VARCHAR2(50),
  aac007   VARCHAR2(200),
  aac027   VARCHAR2(3),
  adc100   VARCHAR2(3),
  aaf011   VARCHAR2(100),
  aae011   VARCHAR2(32),
  aae036   DATE,
  aaz002   NUMBER(16),
  aae100   VARCHAR2(3),
  eae052   VARCHAR2(3),
  aae200   VARCHAR2(32),
  aae202   DATE,
  aae201   VARCHAR2(100),
  aae203   VARCHAR2(100),
  aaa148   VARCHAR2(50),
  adc300   VARCHAR2(20),
  aac127   VARCHAR2(3),
  aac032   VARCHAR2(3),
  aac034   NUMBER(5),
  aac035   NUMBER(5),
  aac036   VARCHAR2(5),
  aae013   VARCHAR2(200),
  aab301   VARCHAR2(12),
  aae009   VARCHAR2(100),
  aae010   VARCHAR2(80),
  aae198   VARCHAR2(100),
  aae199   VARCHAR2(80),
  aae132   VARCHAR2(100),
  aae133   VARCHAR2(80),
  aae134   VARCHAR2(32),
  aae135   VARCHAR2(100),
  aae102   DATE,
  id       VARCHAR2(32),
  aac128   VARCHAR2(3),
  aab800   VARCHAR2(50),
  aab801   VARCHAR2(50),
  aab802   VARCHAR2(50),
  bus_uuid VARCHAR2(36)
)
;
comment on table DEMO_AC01
  is '�Ͷ��߻�����������˻�����Ϣ��';
comment on column DEMO_AC01.aac001
  is '�Ͷ��߱��';
comment on column DEMO_AC01.aac003
  is '����';
comment on column DEMO_AC01.aac002
  is '���֤��';
comment on column DEMO_AC01.aac004
  is '�Ա�';
comment on column DEMO_AC01.aac005
  is '����';
comment on column DEMO_AC01.aac006
  is '��������';
comment on column DEMO_AC01.aac033
  is '����״��';
comment on column DEMO_AC01.aac017
  is '����״��';
comment on column DEMO_AC01.aac024
  is '������ò';
comment on column DEMO_AC01.aac011
  is 'ѧ��';
comment on column DEMO_AC01.aae006
  is '��ϵ�绰';
comment on column DEMO_AC01.aac067
  is '�ƶ��绰';
comment on column DEMO_AC01.aae015
  is '�����ʼ�';
comment on column DEMO_AC01.aac007
  is '��Ƭ·��';
comment on column DEMO_AC01.aac027
  is '��Ա���';
comment on column DEMO_AC01.adc100
  is '��ҵ״̬(��ҵʧҵ״̬)';
comment on column DEMO_AC01.aaf011
  is '�������(ID)';
comment on column DEMO_AC01.aae011
  is '������(ID)';
comment on column DEMO_AC01.aae036
  is '��������';
comment on column DEMO_AC01.aaz002
  is '��־ID';
comment on column DEMO_AC01.aae100
  is '�Ƿ���Ч';
comment on column DEMO_AC01.eae052
  is '���״̬';
comment on column DEMO_AC01.aae200
  is '�����(ID)';
comment on column DEMO_AC01.aae202
  is '���ʱ��';
comment on column DEMO_AC01.aae201
  is '��˻���(ID)';
comment on column DEMO_AC01.aae203
  is '���ԭ��';
comment on column DEMO_AC01.aaa148
  is '��ע��Ϣ��ͬͳ�������';
comment on column DEMO_AC01.adc300
  is '��ʧҵ״̬(�Ǽ�֤״̬)';
comment on column DEMO_AC01.aac127
  is '��Ա��� 1������Ա,2��У��ҵ��,3�Ͷ���';
comment on column DEMO_AC01.aac032
  is 'Ѫ��';
comment on column DEMO_AC01.aac034
  is '���(CM)';
comment on column DEMO_AC01.aac035
  is '����(KG)';
comment on column DEMO_AC01.aac036
  is '����';
comment on column DEMO_AC01.aae013
  is '��ע';
comment on column DEMO_AC01.aab301
  is 'ͳ�������(������������)';
comment on column DEMO_AC01.aae009
  is '���������';
comment on column DEMO_AC01.aae010
  is '����������';
comment on column DEMO_AC01.aae198
  is '��˻�����';
comment on column DEMO_AC01.aae199
  is '���������';
comment on column DEMO_AC01.aae132
  is '�޸Ļ�����';
comment on column DEMO_AC01.aae133
  is '�޸�������';
comment on column DEMO_AC01.aae134
  is '�޸���(ID)';
comment on column DEMO_AC01.aae135
  is '�޸Ļ���(ID)';
comment on column DEMO_AC01.aae102
  is '�޸�ʱ��';
comment on column DEMO_AC01.id
  is 'ƥ���˳�����';
comment on column DEMO_AC01.aac128
  is '��ʷ��Ա��� 1������Ա,2��У��ҵ��,3�Ͷ���';
comment on column DEMO_AC01.aab800
  is 'ʡ';
comment on column DEMO_AC01.aab801
  is '��';
comment on column DEMO_AC01.aab802
  is '��';
comment on column DEMO_AC01.bus_uuid
  is '�����ļ�ҵ���¼��';

prompt
prompt Creating table EF13
prompt ===================
prompt
create table EF13
(
  eef131 VARCHAR2(36) not null,
  eef121 VARCHAR2(36),
  aef140 VARCHAR2(20),
  aef141 VARCHAR2(20),
  aef142 VARCHAR2(200),
  aef143 VARCHAR2(200),
  aef144 VARCHAR2(500)
)
;
comment on table EF13
  is 'ҵ������ϸ��';
comment on column EF13.eef131
  is 'ҵ������ϸID';
comment on column EF13.eef121
  is 'ҵ�����¼�ID';
comment on column EF13.aef140
  is '����';
comment on column EF13.aef141
  is '�����Ŀ';
comment on column EF13.aef142
  is '���ǰ��Ϣ';
comment on column EF13.aef143
  is '�������Ϣ';
comment on column EF13.aef144
  is '��������ĺ���';
alter table EF13
  add constraint PK_EF13 primary key (EEF131);

prompt
prompt Creating table S_LOGININF
prompt =========================
prompt
create table S_LOGININF
(
  loginhash VARCHAR2(32),
  logintime DATE,
  ip        VARCHAR2(100),
  usergent  VARCHAR2(1000),
  sessionid VARCHAR2(100)
)
;
comment on table S_LOGININF
  is 'ϵͳ����֮��¼״̬��¼��';
comment on column S_LOGININF.loginhash
  is '��¼��Ϣhash��Ϣsessionid+ip+agent';
comment on column S_LOGININF.logintime
  is '��¼ʱ��';
comment on column S_LOGININF.ip
  is '�û�ip';
comment on column S_LOGININF.usergent
  is 'usergent';
comment on column S_LOGININF.sessionid
  is 'sessionid';

prompt
prompt Creating table S_BUS_FILE_RECORD
prompt ================================
prompt
create table S_BUS_FILE_RECORD
(
  bus_uuid      VARCHAR2(36) not null,
  file_uuid     VARCHAR2(36) not null,
  file_bus_id   VARCHAR2(36) not null,
  file_bus_type VARCHAR2(36) not null,
  bus_status    VARCHAR2(36),
  bus_addtime   DATE
)
;
comment on table S_BUS_FILE_RECORD
  is 'ϵͳ����֮ҵ���ļ���¼��';
comment on column S_BUS_FILE_RECORD.bus_uuid
  is 'ҵ���ļ���¼����';
comment on column S_BUS_FILE_RECORD.file_uuid
  is '�ļ��ϴ���¼����';
comment on column S_BUS_FILE_RECORD.file_bus_id
  is '�ļ�����ҵ����';
comment on column S_BUS_FILE_RECORD.file_bus_type
  is '�ļ�����ҵ��ҵ������';
comment on column S_BUS_FILE_RECORD.bus_status
  is '�ļ��ϴ�״̬';
comment on column S_BUS_FILE_RECORD.bus_addtime
  is 'ҵ����ʱ��';

prompt
prompt Creating table S_ERRORLOG
prompt =========================
prompt
create table S_ERRORLOG
(
  logid         VARCHAR2(36) not null,
  logtime       DATE,
  stackmsg      CLOB,
  message       VARCHAR2(2000),
  exceptiontype VARCHAR2(100),
  usergent      VARCHAR2(1000),
  ipaddr        VARCHAR2(200),
  referer       VARCHAR2(1000),
  url           VARCHAR2(1000),
  userid        VARCHAR2(32),
  cookie        VARCHAR2(1000)
)
;
comment on table S_ERRORLOG
  is 'ϵͳ����֮��վ�����쳣��־';
comment on column S_ERRORLOG.logid
  is '��־���(uuid)';
comment on column S_ERRORLOG.logtime
  is '����ʱ��';
comment on column S_ERRORLOG.stackmsg
  is '�쳣ջ��Ϣ';
comment on column S_ERRORLOG.message
  is '��־����';
comment on column S_ERRORLOG.exceptiontype
  is '�쳣����';
comment on column S_ERRORLOG.usergent
  is 'user-agent';
comment on column S_ERRORLOG.ipaddr
  is '�ͻ���ip��ַ';
comment on column S_ERRORLOG.referer
  is 'refer';
comment on column S_ERRORLOG.url
  is '����ĵ�ַ';
comment on column S_ERRORLOG.userid
  is '��ǰ������Աid';
comment on column S_ERRORLOG.cookie
  is 'cookie';

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
  add constraint PK_S_EXCEL_BATCH primary key (EXCEL_BATCH_ID);

prompt
prompt Creating table S_FILE_RECORD
prompt ============================
prompt
create table S_FILE_RECORD
(
  file_uuid     VARCHAR2(36) not null,
  file_name     VARCHAR2(200),
  file_length   VARCHAR2(32),
  file_addtime  DATE,
  file_path     VARCHAR2(200),
  file_status   VARCHAR2(32),
  file_md5      VARCHAR2(32),
  file_type     VARCHAR2(32),
  file_bus_id   VARCHAR2(32),
  file_bus_type VARCHAR2(32)
)
;
comment on table S_FILE_RECORD
  is 'ϵͳ����֮�ļ��ϴ���¼��';
comment on column S_FILE_RECORD.file_uuid
  is '�ļ��ϴ���¼����(uuid)';
comment on column S_FILE_RECORD.file_name
  is '�ļ�����';
comment on column S_FILE_RECORD.file_length
  is '�ļ���С��λ(byte)';
comment on column S_FILE_RECORD.file_addtime
  is '�ļ��ϴ�ʱ��';
comment on column S_FILE_RECORD.file_path
  is '�ļ��ϴ���ɫ·��';
comment on column S_FILE_RECORD.file_status
  is '�ļ�״̬(0��Ч 1��Ч���ͨ��)';
comment on column S_FILE_RECORD.file_md5
  is '�ļ�md5,�����ж��Ƿ��ظ��ϴ�';
comment on column S_FILE_RECORD.file_type
  is '�ļ�����';
comment on column S_FILE_RECORD.file_bus_id
  is '�ļ�����ҵ����';
comment on column S_FILE_RECORD.file_bus_type
  is '�ļ�����ҵ��ҵ������';
alter table S_FILE_RECORD
  add constraint PK_S_FILE_RECORD primary key (FILE_UUID);

prompt
prompt Creating table S_GROUP
prompt ======================
prompt
create table S_GROUP
(
  groupid      VARCHAR2(32) not null,
  description  VARCHAR2(500),
  parentid     VARCHAR2(32),
  org          VARCHAR2(8),
  districtcode VARCHAR2(32),
  license      VARCHAR2(20),
  name         VARCHAR2(100) not null,
  principal    VARCHAR2(32),
  shortname    VARCHAR2(50),
  address      VARCHAR2(150),
  tel          VARCHAR2(30),
  linkman      VARCHAR2(30),
  type         VARCHAR2(3),
  chargedept   VARCHAR2(50),
  otherinfo    VARCHAR2(2000),
  owner        VARCHAR2(32),
  status       CHAR(1),
  createdate   DATE,
  grouplevel   VARCHAR2(32)
)
;
comment on table S_GROUP
  is 'ϵͳ����֮������Ϣ��';
comment on column S_GROUP.groupid
  is '����ID';
comment on column S_GROUP.description
  is '�û�������';
comment on column S_GROUP.parentid
  is '�ϼ��ṹID';
comment on column S_GROUP.org
  is 'ϵͳ��������';
comment on column S_GROUP.districtcode
  is '��������';
comment on column S_GROUP.license
  is '������֯��������';
comment on column S_GROUP.name
  is '�û�������';
comment on column S_GROUP.principal
  is '����������';
comment on column S_GROUP.shortname
  is '���';
comment on column S_GROUP.address
  is '��ַ';
comment on column S_GROUP.tel
  is '�绰';
comment on column S_GROUP.linkman
  is '��ϵ��';
comment on column S_GROUP.type
  is '��������';
comment on column S_GROUP.chargedept
  is '���ܲ���';
comment on column S_GROUP.otherinfo
  is '������Ϣ';
comment on column S_GROUP.owner
  is '������';
comment on column S_GROUP.status
  is '״̬';
comment on column S_GROUP.createdate
  is '����ʱ��';
comment on column S_GROUP.grouplevel
  is '�����ȼ�(1ʡ��2�С�3���� 4��ֵ� 5������)';

prompt
prompt Creating table S_LOG
prompt ====================
prompt
create table S_LOG
(
  logid   VARCHAR2(32) not null,
  logtime DATE,
  content VARCHAR2(500)
)
;
comment on table S_LOG
  is 'ϵͳ����֮��Ŀ������־��¼��';
comment on column S_LOG.logid
  is '��־id(uuid)';
comment on column S_LOG.logtime
  is '��־����ʱ��';
comment on column S_LOG.content
  is '��־����';
alter table S_LOG
  add constraint PK_S_LOG primary key (LOGID);

prompt
prompt Creating table S_PERMISSION
prompt ===========================
prompt
create table S_PERMISSION
(
  permissionid VARCHAR2(32) not null,
  name         VARCHAR2(32),
  type         VARCHAR2(100),
  url          VARCHAR2(1000),
  parentid     VARCHAR2(32),
  enabled      VARCHAR2(3),
  sortnum      VARCHAR2(32),
  open         VARCHAR2(10),
  describe     VARCHAR2(200),
  code         VARCHAR2(200),
  updatetime   DATE,
  iconcss      VARCHAR2(100)
)
;
comment on table S_PERMISSION
  is 'ϵͳ����֮����ģ�����ñ�';
comment on column S_PERMISSION.permissionid
  is 'Ȩ�ޱ���ˮ�� uuid ';
comment on column S_PERMISSION.name
  is 'Ȩ������';
comment on column S_PERMISSION.type
  is 'Ȩ������(1 �ڵ� 2Ҷ�� 3 ��ť)';
comment on column S_PERMISSION.url
  is 'Ҷ�ӽ���Ӧ����url(��Ե�ַ)';
comment on column S_PERMISSION.parentid
  is '��Ȩ�ޱ��';
comment on column S_PERMISSION.enabled
  is '�Ƿ���Ч��־';
comment on column S_PERMISSION.sortnum
  is '����ҳ��';
comment on column S_PERMISSION.open
  is '��״̬';
comment on column S_PERMISSION.describe
  is 'Ȩ������';
comment on column S_PERMISSION.code
  is 'Ȩ�ޱ���';
comment on column S_PERMISSION.updatetime
  is 'Ȩ��������ʱ��';
comment on column S_PERMISSION.iconcss
  is 'ͼ����ʽ';
alter table S_PERMISSION
  add constraint PK_S_PERMISSION_K primary key (PERMISSIONID);

prompt
prompt Creating table S_ROLE
prompt =====================
prompt
create table S_ROLE
(
  roleid     VARCHAR2(32) not null,
  name       VARCHAR2(200),
  describe   VARCHAR2(200),
  enabled    VARCHAR2(20),
  code       VARCHAR2(200),
  updatetime DATE
)
;
comment on table S_ROLE
  is 'ϵͳ����֮�û���ɫ��Ϣ�� ';
comment on column S_ROLE.roleid
  is '��ɫid uuid';
comment on column S_ROLE.name
  is '��ɫ����';
comment on column S_ROLE.describe
  is '��ɫ����';
comment on column S_ROLE.enabled
  is '�Ƿ���Ч��־';
comment on column S_ROLE.code
  is '��ɫ����';
comment on column S_ROLE.updatetime
  is '������ʱ��';
alter table S_ROLE
  add constraint PK_S_ROLE_K primary key (ROLEID);

prompt
prompt Creating table S_ROLE_PERMISSION
prompt ================================
prompt
create table S_ROLE_PERMISSION
(
  id           VARCHAR2(32) not null,
  roleid       VARCHAR2(32),
  permissionid VARCHAR2(32)
)
;
comment on table S_ROLE_PERMISSION
  is 'ϵͳ����֮��ɫ��Ȩ�޹�����';
comment on column S_ROLE_PERMISSION.id
  is '��ˮ�� uuid ';
comment on column S_ROLE_PERMISSION.roleid
  is '��ɫid';
comment on column S_ROLE_PERMISSION.permissionid
  is 'Ȩ�ޱ��';
alter table S_ROLE_PERMISSION
  add constraint PK_S_ROLE_PERMISSION primary key (ID);

prompt
prompt Creating table S_USER
prompt =====================
prompt
create table S_USER
(
  userid     VARCHAR2(32) not null,
  password   VARCHAR2(64),
  username   VARCHAR2(50),
  enabled    VARCHAR2(1) not null,
  isleader   VARCHAR2(1),
  cnname     VARCHAR2(32),
  owner      VARCHAR2(32),
  macaddr    VARCHAR2(300),
  usertype   VARCHAR2(2),
  otherinfo  VARCHAR2(2000),
  createdate DATE
)
;
comment on table S_USER
  is 'ϵͳ����֮�����û���';
comment on column S_USER.userid
  is '�û�ID';
comment on column S_USER.password
  is '����';
comment on column S_USER.username
  is '��¼��';
comment on column S_USER.enabled
  is '�û��Ƿ���Ч 1��Ч��0��Ч';
comment on column S_USER.isleader
  is '�Ƿ��쵼 1�ǣ�0����';
comment on column S_USER.cnname
  is '�û�����';
comment on column S_USER.owner
  is '������';
comment on column S_USER.macaddr
  is '������ַ';
comment on column S_USER.usertype
  is '�û���� 0��������Ա��1����Ա��2��ͨ�û�';
comment on column S_USER.otherinfo
  is '������Ϣ';
comment on column S_USER.createdate
  is '����ʱ��';

prompt
prompt Creating table S_USERGROUPREF
prompt =============================
prompt
create table S_USERGROUPREF
(
  usergroupid VARCHAR2(32) not null,
  userid      VARCHAR2(32),
  groupid     VARCHAR2(32)
)
;
comment on table S_USERGROUPREF
  is 'ϵͳ����֮�û�����������';
comment on column S_USERGROUPREF.usergroupid
  is 'ID';
comment on column S_USERGROUPREF.userid
  is '�û�ID';
comment on column S_USERGROUPREF.groupid
  is '��ID';

prompt
prompt Creating table S_USER_PERMISSION
prompt ================================
prompt
create table S_USER_PERMISSION
(
  id           VARCHAR2(32) not null,
  userid       VARCHAR2(32) not null,
  permissionid VARCHAR2(32) not null
)
;
comment on table S_USER_PERMISSION
  is 'ϵͳ����֮�û���Ȩ�޹�����';
comment on column S_USER_PERMISSION.id
  is '�û���Ȩ�޹�������';
comment on column S_USER_PERMISSION.userid
  is '�û�����ˮ��';
comment on column S_USER_PERMISSION.permissionid
  is 'Ȩ�ޱ��';
alter table S_USER_PERMISSION
  add constraint PK_S_USER_PERMISSION primary key (ID);

prompt
prompt Creating table S_USER_ROLE
prompt ==========================
prompt
create table S_USER_ROLE
(
  id     VARCHAR2(32) not null,
  userid VARCHAR2(32) not null,
  roleid VARCHAR2(32) not null
)
;
comment on table S_USER_ROLE
  is 'ϵͳ����֮�û����ɫ������';
comment on column S_USER_ROLE.id
  is '��ˮ��';
comment on column S_USER_ROLE.userid
  is '�û�id';
comment on column S_USER_ROLE.roleid
  is '��ɫid';
alter table S_USER_ROLE
  add constraint PK_S_USER_ROLE primary key (ID);

prompt
prompt Creating sequence CODE_VALUE_SEQ
prompt ================================
prompt
create sequence CODE_VALUE_SEQ
minvalue 1
maxvalue 999999999
start with 965758
increment by 1
cache 20;

prompt
prompt Creating view AA26
prompt ==================
prompt
create or replace force view aa26 as
select
  GROUPID AAB301,
  DESCRIPTION AAA146,
  Case When (substr(GROUPID,3,4)='0000' And Length(GROUPID) = 6) Then '1'
   When (substr(GROUPID,5,2)='00' And Length(GROUPID) = 6) Then '2'
   When (substr(GROUPID,5,2)<>'00' And Length(GROUPID) = 6) Then '3'
   When (Length(GROUPID) > 6 And Length(GROUPID)<10) Then '4'
   When (Length(GROUPID) > 10) Then '5'
    End aaa147,
  PARENTID AAA148,
  Null EAE037

from s_group t;

prompt
prompt Creating view V_AA10
prompt ====================
prompt
create or replace force view v_aa10 as
select code_type aaa100, code_value aaa102, code_name aaa103, '1' aaa105 ,nvl(code_describe,code_name)  aaa106 from code_value t -- where t.code_type not in ('AAB301','AAC183_1','AAC200','AAC180')
UNION ALL
select 'AAB800' aaa100, code_value aaa102, code_name aaa103, PAR_CODE_VALUE aaa105, nvl(code_describe,code_name)  aaa106 from code_value t where t.code_type='AAB301' AND T.CODE_LEVEL='1'
union all
select 'AAB801' aaa100, code_value aaa102, code_name aaa103, PAR_CODE_VALUE aaa105, nvl(code_describe,code_name)  aaa106 from code_value t where t.code_type='AAB301' AND T.CODE_LEVEL='2'
AND t.par_code_value='610000'
union all
select 'AAB802' aaa100, code_value aaa102, code_name aaa103, PAR_CODE_VALUE aaa105, nvl(code_describe,code_name)  aaa106 from code_value t where t.code_type='AAB301' AND T.CODE_LEVEL='3'
union all
select 'AAS001',aab301,aaa146,aaa148,aaa146 aaa106 from AA26 WHERE aaa147='1'
union all
select 'AAS002',aab301,aaa146,aaa148,aaa146 aaa106 from AA26 WHERE aaa147='2'
union all
select 'AAS003',aab301,aaa146,aaa148,aaa146 aaa106 from AA26 WHERE aaa147='3'
union all
select 'AAS004',aab301,aaa146,aaa148,aaa146 aaa106 from AA26 WHERE aaa147='4'
union all
select 'AAS005',aab301,aaa146,aaa148,aaa146 aaa106 from AA26 WHERE aaa147='5'
union all
select 'AAC010',groupid,OTHERINFO,'','' aaa106 from S_GROUP T
;

prompt
prompt Creating view V_CODE_TYPE
prompt =========================
prompt
create or replace force view v_code_type as
select aaa100 from v_aa10 group by aaa100;

prompt
prompt Creating view V_SUGGEST_DATA
prompt ============================
prompt
create or replace force view v_suggest_data as
select aac001 id,aac002 key, aac003 name, aac003||'('||aac002||')' showname  ,'AC01' keytype  from demo_ac01;

prompt
prompt Creating view V_USER
prompt ====================
prompt
create or replace force view v_user as
select userid,username,password,cnname,enabled,USERTYPE from s_user;

prompt
prompt Creating view V_USER_LOGIN
prompt ==========================
prompt
create or replace force view v_user_login as
select
       a.userid,
       a.username,
       a.password,
       a.cnname,
       a.enabled,
       b.groupid,
       b.name groupname,
       b.parentid groupparentid,
       '' as aab998
     from s_user a,s_group b,s_usergroupref c
     where a.userid=c.userid and b.groupid=c.groupid;

prompt
prompt Creating function SPF_GENPARENTAREACASCADED
prompt ===========================================
prompt
CREATE OR REPLACE FUNCTION "SPF_GENPARENTAREACASCADED" (as_aab301 in varchar2) return varchar2 is
  ls_children varchar2(100);
  ls_parent varchar2(100);
begin
  begin
    select groupid into ls_parent from s_group where groupid=as_aab301;
  exception
    when NO_DATA_FOUND then
         return '������������!';
  end;

  --���Ȳ����Լ�
  --insert into AAA1(AAA148,AAB301) values (as_aab301,as_aab301);

  --ѭ���������и��ڵ�
  loop
    ls_children := ls_parent;
    begin
      select parentid into ls_parent from s_group where groupid = ls_children;
    exception
      when NO_DATA_FOUND then
        exit;
    end;
    if ls_parent=null or  ls_parent='' or ls_parent='G001'  then
        exit;
    end if;
    insert into AAA1(AAA148,AAB301) values(ls_children,as_aab301);
  end loop;
  return 'OK';
exception
  when others then
    return '�������ڵ�ʱ����:'||sqlerrm;
end SPF_GenParentAreaCascaded;
/

prompt
prompt Creating function SPF_GENALLPARENTAREACASCADED
prompt ==============================================
prompt
CREATE OR REPLACE FUNCTION "SPF_GENALLPARENTAREACASCADED" return varchar2 is
  errmsg varchar2(200);
  cursor cur_aa26 is select groupid from s_group t where t.type='1';
begin
  errmsg := 'OK';
  
  for c_aa26 in cur_aa26 loop
    --���ԭ������
    delete AAA1 where AAB301 = c_aa26.groupid;
    errmsg := SPF_GenParentAreaCascaded(c_aa26.groupid);
    if errmsg <> 'OK' then
       exit;
    end if;
    commit;
  end loop;
  return(errmsg);
end SPF_GenAllParentAreaCascaded;
/


spool off
