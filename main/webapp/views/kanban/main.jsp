<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main class='layout_width'>
	<div class='top_btn'>
		<button type="button" class='btn1 add_work'>작업등록</button>
	</div>
	
	<h1>작업 요약</h1>
	
	<div class='work_list'>
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
	</div>
	
</main>