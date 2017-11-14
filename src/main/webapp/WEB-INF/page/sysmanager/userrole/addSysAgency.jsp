<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib uri="http://www.epsoft.com/rctag" prefix="rc"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>系统机构新增页面</title>
    <rc:csshead/>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content ">
        <form action="${contextpath}/sys/userrole/saveAddSysAgency" >
        <div id="input_content">
	        <!-- 代码信息开始 -->
	        <div class="ibox ">
	            <div class="ibox-content">
		            <div class="form-horizontal"  >
				       <rc:hidden property="parentid" value="${parentid }"/>
					   <div class="form-group">
							<rc:textedit property="name" label="机构名称"  cols="3,9"  required="true"   validate="{required:true,messages:{required:'机构名称不能为空'}}"/>
					   </div>
					   <div class="hr-line-dashed"></div>
					   <div class="form-group">
							<rc:textedit property="description"  label="机构描述"  cols="3,9" />
					   </div>
			       </div>
		       </div>
	        </div>
	        <!-- 代码信息结束 -->
	        <div class="form-group" style="text-align: right;">
	              <a class="btn btn-primary " onclick="demo_save_data()"><i class="fa fa-save"></i>&nbsp;保存</a>
	              <a class="btn btn-danger " onclick="select_closeframe()"><i class="fa fa-remove"></i>&nbsp;关闭</a>
	         </div>
         </div>
        </form>
    </div>
    <rc:jsfooter/>
    <script type="text/javascript">
    $(function() {
    	//验证 ajax
    	rc.validAndAjaxSubmit($("form"),demo_callback);
    })
    
    //保存页面配置信息
	function demo_save_data(){
	   $('form').submit();
	}
    
     //关闭
    function select_closeframe(){
    	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    	parent.layer.close(index); //再执行关闭   
    }
  
    function demo_callback(response){
    	if(response.success){
    		alert(response.message);
           	parent.refresh_agency_tree();
           	select_closeframe();
    	}
    	else{
    		alert(response.message);
    	}
    }
    
    </script>
</body>
</html>