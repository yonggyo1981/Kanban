<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.HashSet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String rootURL = (String)request.getAttribute("rootURL");
	HashSet<String> addCss = (HashSet<String>)request.getAttribute("addCss");	
	HashSet<String> addScripts = (HashSet<String>)request.getAttribute("addScripts");
%>
<c:set var="rootURL" value="<%=rootURL%>" />
<c:set var="addCss" value="<%=addCss%>" />
<c:set var="addScripts" value="<%=addScripts%>" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset='utf-8'>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
		<link rel="stylesheet" type="text/css" href="${rootURL}/resources/css/style.css" />
		<c:forEach var="css" items="${addCss}">
		<link rel="stylesheet" type="text/css" href="${rootURL}/resources/css/${css}.css" />
		</c:forEach>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.26.0/babel.min.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<script type="text/babel" src="${rootURL}/resources/js/common.js"></script>
		<c:forEach var="script" items="${addScripts}">
		<script type="text/babel" src="${rootURL}/resources/js/${script}.js"></script>
		</c:forEach>
		
		<title>작업 관리자</title>
	</head>
	<body>
	