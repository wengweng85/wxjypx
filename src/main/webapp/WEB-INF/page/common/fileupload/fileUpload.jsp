<%@ page language="java" contentType="text/html; charset=gbk"  pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--360�����������webkit�ں˽���-->
     <title>�ļ��ϴ�</title>
    <link href="<c:url value='/resource/hplus/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resource/hplus/css/font-awesome.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resource/hplus/css/animate.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resource/hplus/css/plugins/webuploader/webuploader.css'/>" rel="stylesheet">
    <link href="<c:url value='/resource/hplus/css/demo/webuploader-demo.min.css'/>" rel="stylesheet" >
</head>
<body >
    <body >
    <div class="col-sm-12">
	    <div class="row">
            <div id="uploader" class="wu-example">
		    <!--��������ļ���Ϣ-->
		    <div id="thelist" class="uploader-list"></div>
		    <div class="btns ">
		        <div id="picker" >ѡ���ļ�</div>
		        <button id="ctlBtn" class="btn btn-default">��ʼ�ϴ�</button>
		    </div>
		    <input type="hidden" id="file_bus_id" value="${filerecord.file_bus_id}">
            <input type="hidden" id="file_bus_type" value="${filerecord.file_bus_type}">
            <input type="hidden" id="upload_callback" value="${filerecord.upload_callback}">
		</div>	 
	</div>   
    <script src="<c:url value='/resource/hplus/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/resource/hplus/js/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/resource/hplus/js/plugins/layer/layer.min.js'/>"></script>
    <script type="text/javascript">
        var BASE_URL = 'js/plugins/webuploader';
        var contextPath='<c:url value="/"/>';
        var upload_callback=function(file_uuid){
        	parent.${filerecord.upload_callback}('${filerecord.file_bus_id}',file_uuid)
        }
    </script>
    <script src="<c:url value='/resource/hplus/js/plugins/webuploader/webuploader.min.js'/>"></script>
    <script src="<c:url value='/resource/hplus/js/json2.js'/>"></script>
    <script src="<c:url value='/resource/hplus/js/rc.webuploader.file.js'/>"></script>
</body>
</html>
