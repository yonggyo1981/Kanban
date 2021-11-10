<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String status = request.getParameter("status");
%>
<c:set var="status" value="<%=status%>" />
<c:if test="${status != null}">
<style>
	.work_list .box { width: 100%; padding: 0; }
	.work_list li { float: left; width: calc(100% / 3 - 10px); margin-right: 10px; }
	.work_list ul:after { content: ''; display: block; clear: left; }
</style>
</c:if>
<main class='layout_width'>
	<div class='top_btn'>
		<button type="button" class='btn1 add_work'>작업등록</button>
	</div>
	
	<h1>
		작업 요약
		<c:if test="${status != null}">
		(
		<c:choose>
			<c:when test="${status == 'done'}">
				완료
			</c:when>
			<c:when test="${status == 'progress'}">
				진행중
			</c:when>
			<c:otherwise>
				준비중
			</c:otherwise>
		</c:choose>
		)
		</c:if>
	</h1>
	
	<div class='work_list'>
		<c:if test="${status == null || status == 'ready'}">
		<div class='box ready'>
			<h2>준비중</h2>
			<ul>
			<c:forEach var="item" items="${list}">
				<c:if test="${item.status == 'ready'}">
					<li class='show_work' data-idx='${item.idx}'>${item.subject}</li>
				</c:if>		
			</c:forEach>
			</ul>		
		</div>
		</c:if>
		<c:if test="${status == null || status == 'progress'}">
		<div class='box progress'>
			<h2>진행중</h2>
			<ul>
			<c:forEach var="item" items="${list}">
				<c:if test="${item.status == 'progress'}">
					<li class='show_work' data-idx='${item.idx}'>${item.subject}</li>
				</c:if>		
			</c:forEach>
			</ul>		
		</div>
		</c:if>
		<c:if test="${status == null || status == 'done' }">
		<div class='box done'>
			<h2>완료</h2>
			<ul>
			<c:forEach var="item" items="${list}">
				<c:if test="${item.status == 'done'}">
					<li class='show_work' data-idx='${item.idx}'>${item.subject}</li>
				</c:if>		
			</c:forEach>
			</ul>		
		</div>
		</c:if>
	</div>
</main>