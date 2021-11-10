<%@ page contentType="text/html; charset=utf-8" %>
<%@ page  import="com.models.member.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String rootURL = (String)request.getAttribute("rootURL");
	boolean isLogin = (Boolean)request.getAttribute("isLogin");
	Member member = (Member)request.getAttribute("member");
%>
<c:set var="rootURL" value="<%=rootURL%>" />
<c:set var="isLogin" value="<%=isLogin%>" />
<c:set var="member" value="<%=member%>" />
<header>
	<div class="main_top">
		<div class='inner layout_width'>
		<c:choose>
			<c:when test="${isLogin}">
				<a href="${rootURL}/member/logout">로그아웃</a>
				<a href="${rootURL}/member/info">회원정보수정</a>
			</c:when>
			<c:otherwise>
				<a href="${rootURL}">로그인</a>
				<a href="${rootURL}/member/join">회원가입</a>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
	<div class='main_logo'>작업 관리자</div>
</header>
<nav>
	<ul class='layout_width'>
		<li
			<c:if test="${menu == 'work'}"> class='on'</c:if>
		>
			<a href="${rootURL}/kanban/work">작업요약</a>
		</li>
		<li
			<c:if test="${menu == 'list_ready'}"> class='on'</c:if>
		>
			<a href="${rootURL}/kanban/list?status=ready">준비중</a>
		</li>
		<li
			<c:if test="${menu == 'list_progress'}"> class='on'</c:if>
		>
			<a href="${rootURL}/kanban/list?status=progress">진행중</a>
		</li>
		<li
			<c:if test="${menu == 'list_work'}"> class='on'</c:if>
		>
			<a href="${rootURL}/kanban/list?status=done">완료</a>
		</li>
	</ul>
</nav>



