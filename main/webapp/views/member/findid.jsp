<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String memId = (String)request.getAttribute("memId");
%>
<c:set var="memId" value="<%=memId%>" />
<main>
	<div class='findid_box login_box'>
		<div class='tit'>아이디 찾기</div>
		<c:choose>
			<c:when test="${memId == null}">
				<form method="post" action="../member/findid" autocomplete="off">
					<input type="hidden" name="outline" value="print">
					<input type="text" name="memNm" placeholder="회원명">
					<input type="text" name="cellPhone" placeholder="휴대전화번호">
					<input type="submit" value="아이디 찾기">
				</form>
			</c:when>
			<c:otherwise>
				<h3>아이디 : <c:out value="${memId}" /></h3>
				<div class='links'>
					<div class='left'>
						<a href='../member/findid'>다시 찾기</a> 
						<a href='../member/findpw'>비밀번호 찾기</a>
					</div>
					<div class='right'>
						<a href='../index.jsp'>로그인 하기</a>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</main>