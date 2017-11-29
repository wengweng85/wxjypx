<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.epsoft.com/rctag" prefix="rc"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>技能培训办班</title>
	<rc:csshead/>
</head>
<body class="gray-bg"  style="margin-top:40px" >
<!-- 固定浮动框  navbar-fixed-top 顶部、navbar-fixed-bottom 底部-->
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<!--fluid 是偏移一部分-->
		<div class="container-fluid " style="padding: 0px 20px;">
			<div class="form-group" style="text-align: right;">
				<a class="btn btn-primary " href="javascript:history.back();"><i class="fa fa-backward"></i>&nbsp;返回</a>
				<a class="btn btn-primary " onclick="$('form').submit();"><i class="fa fa-save"></i>&nbsp;保存</a>
			</div>
		</div>
	</div>
</div>
<!-- 浮动框结束 -->
<div class="wrapper wrapper-content ">
	<form action="${contextpath}/skill/saveBaseInfo" >
		<div >
			<div class="ibox ">
				<div class="ibox-title">
					<h5>基本信息</h5>
				</div>
				<div class="ibox-content">
					<div class="form-horizontal"  >
						<input type="hidden" id="CHB100" value="${base.CHB100}" name="CHB100">
						<div class="form-group">
							<rc:textedit  property="aca111" required="true" label="职业(工种)：" validate="{required:true,messages:{required:'请选择职业(工种)'}}"  />
							<rc:select property="aca11a" required="true"  label="等级"   codetype="AAC004"  validate="{required:true,messages:{required:'请选择等级'}}"/>
							<rc:select property="chb526" required="true"  label="人员类别"   codetype="AAC005"  validate="{required:true,messages:{required:'请选择人员类别'}}"/>
							<rc:select property="chb104" required="true"  label="是否鉴定指导中心鉴定"   codetype="AAC005"  validate="{required:true,messages:{required:'请选择是否需要鉴定'}}"/>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<rc:date property="chb107" required="true"   label="培训开始日期"  readonly="false"  validate="{dateCH:true,required:true,messages:{dateCH:'请输入正确的日期格式',required:'请输入培训开始日期'}}"/>
							<rc:date property="chb108" required="true"   label="培训结束日期"  readonly="false"  validate="{required:true,compareDate:'#chb107',messages:{required:'请输入培训结束日期',compareDate:'培训结束日期必须大于培训开始日期'}}"/>
							<rc:date property="chb135" required="true"   label="理论考试日期"  readonly="false"  validate="{required:true,compareDate:'#chb107',messages:{required:'请输入理论考试日期',compareDate:'理论考试日期必须大于培训开始日期'}}"/>
							<rc:date property="chb136" required="true"   label="技能考核日期"  readonly="false"  validate="{required:true,compareDate:'#chb107',messages:{required:'请输入技能考核日期',compareDate:'技能考核日期必须大于培训开始日期'}}"/>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<rc:textedit property="aae004" required="true" label="联系人"  validate="{required:true,maxlength:20,messages:{required:'请输入联系人',maxlength:'联系人不能超过10个汉字'}}"/>
							<rc:textedit property="acb502" required="true" label="联系电话(手机)"  validate="{required:true,mobile:true,messages:{required:'请输入联系电话(手机)'}}"/>
							<rc:textedit property="aae005" required="true" datamask="'mask': '99-9999999'" label="联系电话(固定)"  validate="{required:true,phone:true,messages:{required:'联系电话不能为空'}}"/>
							<rc:textedit property="chb106" required="true" label="计划培训人数" maxlength="2" validate="{plus_digits:true,range:[1,50],required:true,messages:{plus_digits:'请输入正确的整数格式',range:'申报人数不能超过50人',required:'请输入计划培训人数'}}"/>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<rc:textedit property="chb105" cols="1,11" required="true" label="计划培训地点"  validate="{required:true,maxlength:40,messages:{required:'必须填写地址',maxlength:'计划培训地点长度不能超过20个汉字'}}"/>
						</div>
					</div>
				</div>
			</div>

		</div>
	</form>
</div>
<rc:jsfooter/>
<script type="text/javascript">
    $(function() {
        //验证 ajax
        rc.validAndAjaxSubmit($("form"),callback);
    })

    function callback(response){
        if(response.success){
            alert(response.message);
            window.localhost.href=<c:url value='/skill/main'/>
        }
        else{
            alert(response.message);
        }
    }

</script>
</body>
</html>