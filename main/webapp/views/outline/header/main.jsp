<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.HashSet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String rootURL = (String)request.getAttribute("rootURL");
	HashSet<String> addCss = (HashSet<String>)request.getAttribute("addCss");	
	HashSet<String> addScripts = (HashSet<String>)request.getAttribute("addScripts");
	
	String pageTitle = (String)request.getAttribute("pageTitle"); // 사이트 기본 제목
	String cssJsVersion = (String)request.getAttribute("cssJsVersion"); 
	
	String bodyClass = (String)request.getAttribute("bodyClass");
%>
<c:set var="rootURL" value="<%=rootURL%>" />
<c:set var="addCss" value="<%=addCss%>" />
<c:set var="addScripts" value="<%=addScripts%>" />
<c:set var="pageTitle" value="<%=pageTitle%>" />
<c:set var="version" value="<%=cssJsVersion%>" />
<c:set var="bodyClass" value="<%=bodyClass%>" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset='utf-8'>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
		<link rel="stylesheet" type="text/css" href="${rootURL}/resources/css/style.css${version}" />
		<c:forEach var="css" items="${addCss}">
		<link rel="stylesheet" type="text/css" href="${rootURL}/resources/css/${css}.css${version}" />
		</c:forEach>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
		<script type="text/javascript" src="${rootURL}/resources/js/layer.js${version}"></script>
		<script type="text/javascript" src="${rootURL}/resources/js/common.js${version}"></script>
		<c:forEach var="script" items="${addScripts}">
		<script type="text/javascript" src="${rootURL}/resources/js/${script}.js${version}"></script>
		</c:forEach>
		<title><c:out value="${pageTitle}" /></title>
	</head>
	<body class="${bodyClass}">