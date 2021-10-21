<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String rootURL = (String)request.getAttribute("rootURL");
%>
<c:set var="rootURL" value="<%=rootURL%>" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset='utf-8'>
		<link rel="stylesheet" type="text/css" href="${rootURL}/resources/css/style.css" />
		<script type="text/javascript" src=" https://unpkg.com/@babel/standalone/babel.min.js"></script>
		<script type="text/babel" src="${rootURL}/resources/js/common.js"></script>
		<title>작업 관리자</title>
	</head>
	<body>
	