<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String rootURL = (String)request.getAttribute("rootURL");
%>
<c:set var="rootURL" value="<%=rootURL%>" />
<header>
	<div class="main_top">
		<div class='inner layout_width'>
			<a href="${rootURL}">로그인</a>
			<a href="${rootURL}/member/join">회원가입</a>
		</div>
	</div>
	<div class='main_logo'>작업 관리자</div>
</header>
<nav>
	<ul class='layout_width'>
		<li>
			<a href="${rootURL}/kanban/work">작업요약</a>
		</li>
		<li>
			<a href="${rootURL}/kanban/list?status=ready">준비중</a>
		</li>
		<li>
			<a href="${rootURL}/kanban/list?status=progress">진행중</a>
		</li>
		<li>
			<a href="${rootURL}/kanban/list?status=done">완료</a>
		</li>
	</ul>
</nav>



