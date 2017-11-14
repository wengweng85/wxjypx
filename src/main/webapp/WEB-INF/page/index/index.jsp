<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.epsoft.com/rctag" prefix="rc"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% 
	String path = request.getContextPath(); 
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>系统首页</title>
    <rc:csshead/>
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="<c:url value='/resource/hplus/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resource/hplus/css/font-awesome.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resource/hplus/css/animate.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resource/hplus/css/style.min.css'/>" rel="stylesheet">
    <!-- Morris -->
    <link href="<c:url value='/resource/hplus/css/plugins/morris/morris-0.4.3.min.css'/>" rel="stylesheet">
    <!-- Gritter -->
    <link href="<c:url value='/resource/hplus/js/plugins/gritter/jquery.gritter.css'/>" rel="stylesheet">
</head>
<body class="gray-bg"  >
<div class="wrapper wrapper-content">
    <div class="row">
                           各系统根据实际业务需求开发
   </div>
   </div>
<rc:jsfooter/>

    
</body>
</html>