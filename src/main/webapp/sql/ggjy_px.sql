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
  is '系统表之机构权限级连表';
comment on column AAA1.aaa148
  is '上级机构编号';
comment on column AAA1.aab301
  is '所管辖下级机构编号';

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
  is '[0078]业务代码表';
comment on column CODE_TYPE.code_type
  is '代码类别编码';
comment on column CODE_TYPE.type_name
  is '代码类别的名称';
comment on column CODE_TYPE.code_group
  is '代码类别的分组';
comment on column CODE_TYPE.code_description
  is '代码类别的描述';
comment on column CODE_TYPE.code_root_value
  is '代码类别根目录值如果存在';
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
  is '[0079]业务代码明细表';
comment on column CODE_VALUE.code_seq
  is '代码项序号';
comment on column CODE_VALUE.code_type
  is '代码类别编码';
comment on column CODE_VALUE.code_level
  is '代码等级';
comment on column CODE_VALUE.code_value
  is '代码值';
comment on column CODE_VALUE.par_code_value
  is '父代码值';
comment on column CODE_VALUE.code_name
  is '代码名称';
comment on column CODE_VALUE.code_spelling
  is '代码拼音';
comment on column CODE_VALUE.code_status
  is '代码状态（0：不显示，1：显示）';
comment on column CODE_VALUE.code_describe
  is '代码全称';
comment on column CODE_VALUE.code_sort
  is '代码分类';
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
  is '劳动者基本情况表（个人基本信息）';
comment on column DEMO_AC01.aac001
  is '劳动者编号';
comment on column DEMO_AC01.aac003
  is '姓名';
comment on column DEMO_AC01.aac002
  is '身份证号';
comment on column DEMO_AC01.aac004
  is '性别';
comment on column DEMO_AC01.aac005
  is '民族';
comment on column DEMO_AC01.aac006
  is '出生日期';
comment on column DEMO_AC01.aac033
  is '健康状况';
comment on column DEMO_AC01.aac017
  is '婚姻状况';
comment on column DEMO_AC01.aac024
  is '政治面貌';
comment on column DEMO_AC01.aac011
  is '学历';
comment on column DEMO_AC01.aae006
  is '联系电话';
comment on column DEMO_AC01.aac067
  is '移动电话';
comment on column DEMO_AC01.aae015
  is '电子邮件';
comment on column DEMO_AC01.aac007
  is '照片路径';
comment on column DEMO_AC01.aac027
  is '人员身份';
comment on column DEMO_AC01.adc100
  is '就业状态(就业失业状态)';
comment on column DEMO_AC01.aaf011
  is '经办机构(ID)';
comment on column DEMO_AC01.aae011
  is '经办人(ID)';
comment on column DEMO_AC01.aae036
  is '经办日期';
comment on column DEMO_AC01.aaz002
  is '日志ID';
comment on column DEMO_AC01.aae100
  is '是否有效';
comment on column DEMO_AC01.eae052
  is '审核状态';
comment on column DEMO_AC01.aae200
  is '审核人(ID)';
comment on column DEMO_AC01.aae202
  is '审核时间';
comment on column DEMO_AC01.aae201
  is '审核机构(ID)';
comment on column DEMO_AC01.aae203
  is '审核原因';
comment on column DEMO_AC01.aaa148
  is '备注信息，同统筹区编号';
comment on column DEMO_AC01.adc300
  is '就失业状态(登记证状态)';
comment on column DEMO_AC01.aac127
  is '人员类别 1正常人员,2高校毕业生,3劳动力';
comment on column DEMO_AC01.aac032
  is '血型';
comment on column DEMO_AC01.aac034
  is '身高(CM)';
comment on column DEMO_AC01.aac035
  is '体重(KG)';
comment on column DEMO_AC01.aac036
  is '视力';
comment on column DEMO_AC01.aae013
  is '备注';
comment on column DEMO_AC01.aab301
  is '统筹区编号(数据行政区划)';
comment on column DEMO_AC01.aae009
  is '经办机构名';
comment on column DEMO_AC01.aae010
  is '经办人姓名';
comment on column DEMO_AC01.aae198
  is '审核机构名';
comment on column DEMO_AC01.aae199
  is '审核人姓名';
comment on column DEMO_AC01.aae132
  is '修改机构名';
comment on column DEMO_AC01.aae133
  is '修改人姓名';
comment on column DEMO_AC01.aae134
  is '修改人(ID)';
comment on column DEMO_AC01.aae135
  is '修改机构(ID)';
comment on column DEMO_AC01.aae102
  is '修改时间';
comment on column DEMO_AC01.id
  is '匹配浪潮数据';
comment on column DEMO_AC01.aac128
  is '历史人员类别 1正常人员,2高校毕业生,3劳动力';
comment on column DEMO_AC01.aab800
  is '省';
comment on column DEMO_AC01.aab801
  is '市';
comment on column DEMO_AC01.aab802
  is '县';
comment on column DEMO_AC01.bus_uuid
  is '关联文件业务记录表';

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
  is '业务变更明细表';
comment on column EF13.eef131
  is '业务变更明细ID';
comment on column EF13.eef121
  is '业务变更事件ID';
comment on column EF13.aef140
  is '表名';
comment on column EF13.aef141
  is '变更项目';
comment on column EF13.aef142
  is '变更前信息';
comment on column EF13.aef143
  is '变更后信息';
comment on column EF13.aef144
  is '变更项中文含义';
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
  is '系统管理之登录状态记录表';
comment on column S_LOGININF.loginhash
  is '登录信息hash信息sessionid+ip+agent';
comment on column S_LOGININF.logintime
  is '登录时间';
comment on column S_LOGININF.ip
  is '用户ip';
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
  is '系统管理之业务文件记录表';
comment on column S_BUS_FILE_RECORD.bus_uuid
  is '业务文件记录表编号';
comment on column S_BUS_FILE_RECORD.file_uuid
  is '文件上传记录表编号';
comment on column S_BUS_FILE_RECORD.file_bus_id
  is '文件所属业务编号';
comment on column S_BUS_FILE_RECORD.file_bus_type
  is '文件所属业务业务类型';
comment on column S_BUS_FILE_RECORD.bus_status
  is '文件上传状态';
comment on column S_BUS_FILE_RECORD.bus_addtime
  is '业务发生时间';

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
  is '系统管理之网站运行异常日志';
comment on column S_ERRORLOG.logid
  is '日志编号(uuid)';
comment on column S_ERRORLOG.logtime
  is '发生时间';
comment on column S_ERRORLOG.stackmsg
  is '异常栈信息';
comment on column S_ERRORLOG.message
  is '日志标题';
comment on column S_ERRORLOG.exceptiontype
  is '异常类型';
comment on column S_ERRORLOG.usergent
  is 'user-agent';
comment on column S_ERRORLOG.ipaddr
  is '客户端ip地址';
comment on column S_ERRORLOG.referer
  is 'refer';
comment on column S_ERRORLOG.url
  is '请求的地址';
comment on column S_ERRORLOG.userid
  is '当前操作人员id';
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
  is '系统管理之excel数据导入状态表';
comment on column S_EXCEL_BATCH.excel_batch_id
  is 'excel导入状态表编号(uuid)';
comment on column S_EXCEL_BATCH.excel_batch_number
  is 'excel导入批次号。生成规则年月日时分秒毫秒';
comment on column S_EXCEL_BATCH.excel_batch_total_count
  is 'excel导入数据总量';
comment on column S_EXCEL_BATCH.excel_batch_error_count
  is 'excel导入数据错误数量';
comment on column S_EXCEL_BATCH.excel_batch_begin_time
  is 'excel导入数据开始时间';
comment on column S_EXCEL_BATCH.excel_batch_end_time
  is 'excel导入数据结束时间';
comment on column S_EXCEL_BATCH.excel_batch_cost
  is 'excel导入数据耗费时间(秒）';
comment on column S_EXCEL_BATCH.excel_batch_file_path
  is 'excel导入文件路径';
comment on column S_EXCEL_BATCH.excel_batch_file_length
  is 'excel导入文件大小';
comment on column S_EXCEL_BATCH.excel_batch_excel_type
  is 'excel导入类型';
comment on column S_EXCEL_BATCH.excel_batch_aae011
  is 'excel导入经办人';
comment on column S_EXCEL_BATCH.excel_batch_status
  is 'excel导入状态导入步骤(-1默认状态 0转换xlsx 1导入临时表 2解析临时表 3导入完成 4导入失败)';
comment on column S_EXCEL_BATCH.excel_batch_data_status
  is 'excel导入数据状态(0-100)';
comment on column S_EXCEL_BATCH.excel_batch_rt_code
  is 'excel导入数据校验是否成功';
comment on column S_EXCEL_BATCH.excel_batch_rt_msg
  is 'excel导入数据校验错误原因';
comment on column S_EXCEL_BATCH.excel_batch_file_name
  is 'excel导入文件原文件名';
comment on column S_EXCEL_BATCH.excel_error_file_path
  is 'excel导出文件文件路径';
comment on column S_EXCEL_BATCH.excel_error_file_download
  is 'excel导出文件文件生成状（0正在生成 1生成成功可下载 2 生成失败）';
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
  is '系统管理之文件上传记录表';
comment on column S_FILE_RECORD.file_uuid
  is '文件上传记录表编号(uuid)';
comment on column S_FILE_RECORD.file_name
  is '文件名称';
comment on column S_FILE_RECORD.file_length
  is '文件大小单位(byte)';
comment on column S_FILE_RECORD.file_addtime
  is '文件上传时间';
comment on column S_FILE_RECORD.file_path
  is '文件上传角色路径';
comment on column S_FILE_RECORD.file_status
  is '文件状态(0无效 1有效审核通过)';
comment on column S_FILE_RECORD.file_md5
  is '文件md5,用于判断是否重复上传';
comment on column S_FILE_RECORD.file_type
  is '文件类型';
comment on column S_FILE_RECORD.file_bus_id
  is '文件所属业务编号';
comment on column S_FILE_RECORD.file_bus_type
  is '文件所属业务业务类型';
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
  is '系统管理之机构信息表';
comment on column S_GROUP.groupid
  is '机构ID';
comment on column S_GROUP.description
  is '用户组描述';
comment on column S_GROUP.parentid
  is '上级结构ID';
comment on column S_GROUP.org
  is '系统机构编码';
comment on column S_GROUP.districtcode
  is '地区代码';
comment on column S_GROUP.license
  is '机构组织机构编码';
comment on column S_GROUP.name
  is '用户组名称';
comment on column S_GROUP.principal
  is '机构负责人';
comment on column S_GROUP.shortname
  is '简称';
comment on column S_GROUP.address
  is '地址';
comment on column S_GROUP.tel
  is '电话';
comment on column S_GROUP.linkman
  is '联系人';
comment on column S_GROUP.type
  is '机构类型';
comment on column S_GROUP.chargedept
  is '主管部门';
comment on column S_GROUP.otherinfo
  is '其他信息';
comment on column S_GROUP.owner
  is '创建者';
comment on column S_GROUP.status
  is '状态';
comment on column S_GROUP.createdate
  is '创建时间';
comment on column S_GROUP.grouplevel
  is '机构等级(1省、2市、3区县 4镇街道 5村社区)';

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
  is '系统管理之项目运行日志记录表';
comment on column S_LOG.logid
  is '日志id(uuid)';
comment on column S_LOG.logtime
  is '日志发生时间';
comment on column S_LOG.content
  is '日志内容';
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
  is '系统管理之功能模块配置表';
comment on column S_PERMISSION.permissionid
  is '权限表流水号 uuid ';
comment on column S_PERMISSION.name
  is '权限名称';
comment on column S_PERMISSION.type
  is '权限类型(1 节点 2叶子 3 按钮)';
comment on column S_PERMISSION.url
  is '叶子结点对应功能url(相对地址)';
comment on column S_PERMISSION.parentid
  is '父权限编号';
comment on column S_PERMISSION.enabled
  is '是否有效标志';
comment on column S_PERMISSION.sortnum
  is '排序页面';
comment on column S_PERMISSION.open
  is '打开状态';
comment on column S_PERMISSION.describe
  is '权限描述';
comment on column S_PERMISSION.code
  is '权限编码';
comment on column S_PERMISSION.updatetime
  is '权限最后更新时间';
comment on column S_PERMISSION.iconcss
  is '图标样式';
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
  is '系统管理之用户角色信息表 ';
comment on column S_ROLE.roleid
  is '角色id uuid';
comment on column S_ROLE.name
  is '角色名称';
comment on column S_ROLE.describe
  is '角色描述';
comment on column S_ROLE.enabled
  is '是否有效标志';
comment on column S_ROLE.code
  is '角色编码';
comment on column S_ROLE.updatetime
  is '最后更新时间';
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
  is '系统管理之角色与权限关联表';
comment on column S_ROLE_PERMISSION.id
  is '流水号 uuid ';
comment on column S_ROLE_PERMISSION.roleid
  is '角色id';
comment on column S_ROLE_PERMISSION.permissionid
  is '权限编号';
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
  is '系统管理之操作用户表';
comment on column S_USER.userid
  is '用户ID';
comment on column S_USER.password
  is '密码';
comment on column S_USER.username
  is '登录名';
comment on column S_USER.enabled
  is '用户是否有效 1有效，0无效';
comment on column S_USER.isleader
  is '是否领导 1是，0不是';
comment on column S_USER.cnname
  is '用户名称';
comment on column S_USER.owner
  is '创建者';
comment on column S_USER.macaddr
  is '网卡地址';
comment on column S_USER.usertype
  is '用户类别 0超级管理员，1管理员，2普通用户';
comment on column S_USER.otherinfo
  is '其它信息';
comment on column S_USER.createdate
  is '创建时间';

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
  is '系统管理之用户机构关联表';
comment on column S_USERGROUPREF.usergroupid
  is 'ID';
comment on column S_USERGROUPREF.userid
  is '用户ID';
comment on column S_USERGROUPREF.groupid
  is '组ID';

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
  is '系统管理之用户与权限关联表';
comment on column S_USER_PERMISSION.id
  is '用户与权限关联表编号';
comment on column S_USER_PERMISSION.userid
  is '用户表流水号';
comment on column S_USER_PERMISSION.permissionid
  is '权限编号';
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
  is '系统管理之用户与角色关联表';
comment on column S_USER_ROLE.id
  is '流水号';
comment on column S_USER_ROLE.userid
  is '用户id';
comment on column S_USER_ROLE.roleid
  is '角色id';
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
         return '行政区不存在!';
  end;

  --首先插入自己
  --insert into AAA1(AAA148,AAB301) values (as_aab301,as_aab301);

  --循环插入所有父节点
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
    return '创建父节点时出错:'||sqlerrm;
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
    --清空原有数据
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
