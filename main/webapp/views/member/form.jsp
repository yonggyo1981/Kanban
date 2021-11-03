<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.models.member.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
Member member = null;
if (request.getAttribute("member") != null) {
	member = (Member)request.getAttribute("member");
}

String action = (String)request.getAttribute("action");
String socialType = (String)request.getAttribute("socialType");
Member socialMember = null;
if (request.getAttribute("socialMember") != null) {
	socialMember = (Member)request.getAttribute("socialMember");
}
%>
<c:set var="member" value="<%=member%>" />
<c:set var="action" value="<%=action%>" />
<c:set var="socialType" value="<%=socialType%>" />
<c:set var="socialMember" value="<%=socialMember%>" />
<main>
	<div class='join_box login_box'>
		<div class='tit'>
			<c:if test="${socialType != 'none'}">
				<c:choose>
					<c:when test="${socialType == 'naver'}">
						네이버
					</c:when>
					<c:when test="${socialType == 'kakao' }">
						카카오
					</c:when>
				</c:choose>
				아이디로 
			</c:if>
			<c:choose>
				<c:when test="${member == null}">
				회원가입
				</c:when>
				<c:otherwise>
				회원정보 수정
				</c:otherwise>
			</c:choose>
		</div>
		<form name='frmJoin' id='frmJoin' method="post" action="${action}" target="ifrmHidden" autocomplete="off">
			<dl>
				<dt>아이디</dt>
				<dd>
					<c:choose>
						<c:when test="${member == null}">
							<input type="text" name="memId" value="${socialMember.memId}">
						</c:when>
						<c:otherwise>
							<c:out value="${member.memId}" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<c:if test="${socialType == 'none'}">
			<dl>
				<dt>비밀번호</dt>
				<dd>
					<input type="password" name="memPw">
				</dd>
			</dl>
			<dl>
				<dt>비밀번호 확인</dt>
				<dd>
					<input type="password" name="memPwRe">
				</dd>
			</dl>
			<dl>
				<dt>비밀번호 힌트</dt>
				<dd>
					<input type="text" name="memPwHint" value="${member.memPwHint}">
				</dd>
			</dl>
			</c:if>
			<dl>
				<dt>회원명</dt>
				<dd>
					<c:choose>
						<c:when test="${member == null }">
							<input type="text" name="memNm" value="${socialMember.memNm}">
						</c:when>
						<c:otherwise>
							<input type="text" name="memNm" value="${member.memNm}">
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>휴대전화</dt>
				<dd>
					<input type="text" name="cellPhone" value="${member.cellPhone}">
				</dd>
			</dl>
			<input type="reset" value="다시입력">
			<c:choose>
				<c:when test="${member == null}">
					<input type="submit" value="회원가입">
				</c:when>
				<c:otherwise>
					<input type="submit" value="정보수정">
				</c:otherwise>
			</c:choose>
		</form>
	</div>
</main>