<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.epsoft.com/rctag" prefix="rc"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>用户角色授权管理</title>
<!-- css头文件  -->
<rc:csshead />
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-4">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>机构用户树 <span style="color:blue">蓝色代表机构数</span> <span style="color:red">红色代表用户数</span> </h5>
					<div class="ibox-tools">
						 <a  class="btn btn-primary btn-xs" onclick="addSysAgency()" ><i class="fa fa-plus"></i>&nbsp;新增机构</a>
						 <a  class="btn btn-danger btn-xs" onclick="sysgroup_delete()" ><i class="fa  fa-trash-o"></i>&nbsp;删除</a>
			       </div>
				</div>
				<div class="ibox-content">
					<div id="tree-div" class="ztree" style="overflow: auto; height: 750px; "></div>
				</div>
			</div>
		</div>
		<div class="col-sm-4">
			<div class="row">
			    <div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>机构信息</h5>
						</div>
						<div class="ibox-content" >
							<form class="form-horizontal" id="group_form">
								<!-- 隐藏区域 -->
								<rc:hidden property="groupid"/>
								<div class="form-group">
									<rc:textedit property="name" label="机构名称"  cols="3,9"  readonly="true" />
								</div>
								<div class="hr-line-dashed"></div>
								<div class="form-group">
									<rc:textedit property="description"  label="机构描述"  cols="3,9"  readonly="true" />
								</div>
							</form>
						</div>
					</div>
				</div>
				
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>用户列表</h5>
							<div class="ibox-tools">
						         <a  class="btn btn-primary btn-xs" onclick="addSysUser()" ><i class="fa fa-plus"></i>&nbsp;创建用户</a>
				                 <!-- <a  class="btn btn-danger btn-xs" onclick="sysuser_bat_delete()" ><i class="fa  fa-trash-o"></i>&nbsp;删除</a> -->
					        </div>
						</div>
						<!-- 模型 -->
			            <script id="tpl" type="text/x-handlebars-template" >
                            {{#equals isgrant 'false' }}			                
				                 <button class="btn btn-outline btn-danger">未授权</button>
			                {{/equals}} 
			                {{#equals isgrant 'true'}}
			                     <button class="btn btn-outline  btn-info">已授权</button>
			                {{/equals}} 
			            </script>
			            
			            <script id="tpl_op" type="text/x-handlebars-template" >
				             <a class="btn btn-info" onclick="sys_user_role_grant('{{userid}}')">角色授权</a>
			            </script>
						<div class="ibox-content" >
							<table id="usertable" 
							data-url="<c:url value='/sys/userrole/getUserListDataByid'/>"
							data-click-to-select="true"
                            data-single-select="true"
                            data-page-size="5"
							>
								<thead>
								    <tr>
								        <th data-checkbox="true" ></th>
								        <th data-formatter="sys_user_role_indexFormatter">序号</th>
					                    <th data-field="username">用户名</th>
					                    <th data-field="cnname">姓名</th>
					                    <th data-formatter="sys_user_role_grantStatusFormatter">是否已授权</th>
					                    <!--  
					                    <th data-formatter="sys_user_role_opFormatter">授权</th>
					                    -->
								    </tr>
						        </thead>
							</table>
						</div>
					</div>
				</div>
			</div>
			</div>
			<div class="col-sm-4">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>用户信息</h5>
						<div class="ibox-tools">
						    <a  class="btn btn-success btn-xs J_menuItem " href="<c:url value='sys/role/index'/>" ><i class="fa fa-share"></i>&nbsp;角色管理</a>
						    <a class="btn btn-primary btn-xs" onclick="sys_user_role_batchAddUserRole()"><i class="fa fa-save"></i>&nbsp;保存</a>
					    </div>
					</div>
					<div class="ibox-content" >
						<form class="form-horizontal" id="user_form">
							<!-- 隐藏区域 -->
							<rc:hidden property="userid"/>
							<div class="form-group">
								<rc:textedit property="username" label="用户名"  cols="3,9"  readonly="true" />
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<rc:textedit property="cnname" label="姓名" cols="3,9"  readonly="true" />
							</div>
						</form>
					</div>
				</div>
			</div>
			
			<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>角色列表</h5>
				</div>
				<div class="ibox-content" >
					<table id="roletable" data-url="<c:url value='/sys/userrole/getRoleByUserId'/>"
					data-page-size="20"
					>
						<thead>
						    <tr>
						        <th data-checkbox="true"  data-formatter="sys_user_role_checkedFormatter"></th>
						        <th data-formatter="sys_user_role_indexFormatter">序号</th>
			                    <th data-field="name">角色名称</th>
			                    <th data-field="code">角色描述</th>
						    </tr>
				        </thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<rc:jsfooter />
<script type="text/javascript">
$(function() {
	 $.fn.zTree.init($("#tree-div"), sys_user_role_setting);
	 //树默认展开第一级节点
   	 var treeObj = $.fn.zTree.getZTreeObj("tree-div");
     treeObj.expandAll(true);
	 $('#usertable').inittable();
	 $('#roletable').inittable();
})
    
//用户表格监听 
$('#usertable').on('click-row.bs.table', function (e, row, $element) {
   	rc.evaluation(row,$('#user_form'));
   	sys_user_role_grant(row.userid);
});   
//树配置
var sys_user_role_setting = {
	  view: {
          dblClickExpand: false,
          showLine: true,
          selectedMulti: false,
          nameIsHTML: true
	  },	
	  check: {
		enable: false
	  },
	  data: {
		simpleData: {
			enable: true
		}
	  },
	  callback: {
			onClick: onClick
		},
	  async: {
		 enable: true,
		 url: "<c:url value='/sys/userrole/treedata'/>",
		 autoParam:["id"]
	  }
};
//format
//状态
function sys_user_role_grantStatusFormatter(value, row, index) {
	var tpl = $("#tpl").html();  
  	//预编译模板  
  	var template = Handlebars.compile(tpl);  
  	return template(row);
}
//区域
function sys_user_role_opFormatter(value, row, index) {
	var tpl = $("#tpl_op").html();  
  	//预编译模板  
  	var template = Handlebars.compile(tpl);  
  	return template(row);
}

//是否选中
function sys_user_role_checkedFormatter(value,row,index){
   if (row.checked == 'true')
       return {
           checked : true//设置选中
       };
   return value;
}
function sys_user_role_indexFormatter(value, row, index) {
    return index+1;
}

//点击事件
function onClick(event, treeId, treeNode, clickFlag) {
	//机构信息查询
	rc.ajaxQuery("<c:url value='/sys/userrole/getgroupdatabyid/'/>"+treeNode.id);
	sys_user_role_queryuser(treeNode.id);
}

//查询
function sys_user_role_queryuser(groupid){
	var url="<c:url value='/sys/userrole/getUserListDataByid'/>"+'?groupid='+groupid;
	$('#usertable').refreshtable(url);
}

//刷新用户列表
function query_user_list(){
	$('#usertable').refreshtable();
}
//人员选中加载角色
function sys_user_role_grant(userid){
	$('#userid').val(userid);
	var url="<c:url value='/sys/userrole/getRoleByUserId'/>"+'?userid='+userid;
	$('#roletable').refreshtable(url);
}


//批量增加用户角色
function sys_user_role_batchAddUserRole(){
	var userid=$('#userid').val();
	if(userid){
		var selections=$('#roletable').getAllTableSelections();
	    //选中的值
	    var selectnodes='';
	    if(selections.length>0){
			   for(i=0;i<selections.length;i++){
	     	   var item=selections[i];
	     	   selectnodes+=item.roleid+',';
	       }
		   rc.ajax("<c:url value='/sys/userrole/saveUserRole'/>", {userid:userid,selectnodes:selectnodes},function (response) {
		    	alert(response.message);
		    	$('#usertable').refreshtable();
		   }); 
	    }else{
	 	   layer.alert("请至少选中一条记录");                
			   return;
	    }
	}else{
		layer.alert('请先选择要增加角色的用户');
	}
}	 
//创建系统用户
function addSysUser(){
	var groupid=$('#groupid').val();
	if(groupid){
		layer.open({
			  type: 2,
			  title: '创建系统用户',
			  shadeClose: false,
			  maxmin:true,
			  shade: 0.8,
			  area: ['45%', '40%'],
			  content:"<c:url value='/sys/userrole/addSysUser/'/>"+groupid     //iframe的url
			});
	}else {
		layer.alert("请先选择机构");
	}
}
//创建系统机构
function addSysAgency(){
	var groupid=$('#groupid').val();
	if(groupid){
		layer.open({
			  type: 2,
			  title: '创建系统机构',
			  shadeClose: false,
			  maxmin:true,
			  shade: 0.8,
			  area: ['45%', '40%'],
			  content:"<c:url value='/sys/userrole/addSysAgency/'/>"+groupid     //iframe的url
			});
	}else {
		layer.alert("请选择新增机构的父节点");
	}
}
function refresh_agency_tree(){
	 //树默认展开第一级节点
  	var treeObj = $.fn.zTree.getZTreeObj("tree-div");
    treeObj.expandAll(true);
    var nodes = treeObj.getSelectedNodes();
    if (nodes.length>0) {
    	treeObj.reAsyncChildNodes(nodes[0], "refresh");
    }
}
function remove_agency_tree(){
	var treeObj = $.fn.zTree.getZTreeObj("tree-div");
	treeObj.expandAll(true);
	var nodes = treeObj.getSelectedNodes();
	for (var i=0, l=nodes.length; i < l; i++) {
		treeObj.removeNode(nodes[i]);
	}
}
//删除数据
function sysgroup_delete(){
	  var groupid=$('#groupid').val();
	  if(groupid){
		layer.confirm('确定要删除该机构吗？', function(index){
			var url= "<c:url value='/sys/userrole/deleteSysAgency/'/>"+groupid ;
			rc.ajax(url, null,function (response) {
				if(response.success){
					alert(response.message);
					remove_agency_tree();
				}else{
					alert(response.message);
				}
			});
		});
	  }else{
		layer.alert('请先选择你要删除的机构');
	  }
}
</script>
</body>
</html>