<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.epsoft.com/rctag" prefix="rc"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<title>定点培训办班备案申报</title>
	<!-- css引入 -->
	<rc:csshead/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<!-- 查询条件开始 -->
	<div class="ibox float-e-margins">
		<div class="ibox-title">
			<h5>查询条件</h5>
			<div class="ibox-tools">
				<a class="collapse-link">
					<i class="fa fa-chevron-up"></i>
				</a>
			</div>
		</div>
		<div class="ibox-content">
			<form class="form-horizontal" id="query_form" >
				<div class="form-group">
					<rc:textedit property="Chb100" label="办班备案编号" />
					<div class="col-sm-3" align="right">
						<a class="btn btn-info" onclick="demo_query()"><i class="fa fa-search"></i>&nbsp;查询</a>
						<a class="btn btn-info" onclick="rc.clean($('query_form'))"><i class="fa fa-refresh"></i>&nbsp;重置</a>
						<a class="btn btn-info" href="<c:url value='/skill/apply'/>"><i class="fa fa-plus"></i>&nbsp;新增</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- 查询条件结束 -->
	<!-- 查询结果开始 -->
	<div class="ibox float-e-margins">
		<div class="ibox-title">
			<h5>查询结果列表</h5>
			<div class="ibox-tools">
			</div>
		</div>
		<!-- 模型 -->
		<script id="tpl" type="text/x-handlebars-template" >
			{{chb310}}
			{{#equals chb310 0}}
				{{#equals stu 1}}{{#equals cou 1}}
				<a class="link" class="btn_m" id="submit" href="javascript:dosubmit('{{chb100}}');"><i class="fa fa-upload"></i>&nbsp;提交</a>
				{{/equals}}{{/equals}}
				<a class="link" class="btn_m" href=""><i class="fa fa-edit"></i>&nbsp;修改</a>
				<a class="link" class="btn_m" href="javascript:doDel('{{chb100}}');"><i class="fa fa-remove"></i>&nbsp;删除</a>
			{{else}}
				{{#equals chb315 2}}
					<a class="link" class="btn_m" href="javascript:dorevoke('{{chb100}}');"><i class="fa fa-remove"></i>&nbsp;撤销</a>
				{{/equals}}
			{{/equals}}
			<a class="link" class="btn_m" href="javascript:openSubCheck('{{chb100}}');"><i class="fa fa-check-circle"></i>&nbsp;补贴校验</a>
		</script>
		<!-- 计划培训日期模型 -->
		<script id="planTraintpl" type="text/x-handlebars-template" >
			{{chb107}} 至 {{chb108}}
		</script>

		<div class="ibox-content">
			<table id="hb68table" data-url="<c:url value='/skill/getInfoList'/>"
				   data-click-to-select="false"
				   data-show-export="true"
				   data-pagination="true"
				   data-uniqueId="1"
				   data-page-size="10" >
				<thead>
				<tr>
					<th data-formatter="demo_indexFormatter">序号</th>
					<th data-field="chb100" >办班备案编号</th>
					<th data-field="aae001" >办班年月</th>
					<th data-field="aca111" >职业(工种)</th>
					<th data-field="aca11a" >等级</th>
					<th data-field="chb106" >人数</th>
					<th data-formatter="stuFormatter">学员</th>
					<th data-formatter="couFormatter">课程</th>
					<th data-field="chb113" >备案日期</th>
					<th data-formatter="planTrainFormatter" >计划培训日期</th>
					<th data-field="chb111" >业务状态</th>
					<th data-formatter="demo_jobnameFormatter">操作</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 查询结果结束 -->
</div>
<!-- javascript引入 -->
<rc:jsfooter/>
<script type="text/javascript">
    var $table=$('#hb68table');

    //基于xeditable表格编辑调用保存函数
    function onEditableSave(field, row, oldValue, $el) {
        console.log(field+" "+row[field]);
        var url=contextPath+'/demo/updateDataByXedit';
        var param={};
        param.chb100=row['chb100'];
        param[field]=row[field];
        rc.ajax(url, param,function (response) {
            layer.msg(response.message)
        });
    }

    var demo_options={
        formid:'query_form',
        onEditableSave:onEditableSave
    }
    //初始化
    $(function(){
        $('#hb68table').inittable(demo_options);
    });


    function stuFormatter(value, row, index) {
        if(row.stu=='1'){
            return '√';
        }else{
            return '×';
        }
    }
    function couFormatter(value, row, index) {
        if(row.cou=='1'){
            return '√';
        }else{
            return '×';
        }
    }
    //计划培训日期
    function planTrainFormatter(value, row, index) {
        var tpl = $("#planTraintpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        return template(row);
    }
    //操作编辑
    function demo_jobnameFormatter(value, row, index) {
        var tpl = $("#tpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        return template(row);
    }

    function demo_bus_uuidFormatter(value, row, index) {
        var tpl = $("#tplfile").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        return template(row);
    }

    function demo_indexFormatter(value, row, index) {
        return index+1;
    }


    //增加一行
    function addRow(insertIndex, rowObj){
        var insertRow = rowObj;
        $.each(insertRow, function(name, value){
            insertRow[name] = '';
        });

        var params = {index:insertIndex + 1, row:insertRow};
        $('#ac01table').bootstrapTable('insertRow', params);
    }


    //删除一行
    function removeRow(value){
        alert(value);
        var a=[];
        a.push(value);
        $table.bootstrapTable('remove', {
            field: 'aac001',
            values: a
        });
    }

    //查询
    function demo_query(){
        $('#hb68table').refreshtable();
        //$('.collapse-link').click();
    }

</script>
</body>
</html>