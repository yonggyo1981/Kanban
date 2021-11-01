<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String rootURL = (String)request.getAttribute("rootURL");
	String naverCodeURL = (String)request.getAttribute("naverCodeURL");
%>
<c:set var="rootURL" value="<%=rootURL%>" />
<c:set var="naverCodeURL" value="<%=naverCodeURL%>" />
<main>
	<div class="login_box">
		<div class='tit'>로그인</div>
		<form name='frmLogin' id='frmLogin' method="post" action="${rootURL}/member/login" target="ifrmHidden" autocomplete="off">
			<input type="text" name="memId" placeholder="아이디">
			<input type="password" name="memPw" placeholder="비밀번호">
			
			<div class='links'>
				<div class='left'>
					<a href="${rootURL}/member/findid">아이디 찾기</a>
					<a href="${rootURL}/member/findpw">비밀번호 찾기</a>
				</div>
				<div class='right'>
					<a href="${rootURL}/member/join">회원 가입</a>
				</div>
			</div>
			
			<input type="submit" value="로그인">
			<a href="${naverCodeURL}">
			<img src="${rootURL}/resources/image/naverlogin_btn.png" width="340">
			</a>
		</form>
	</div>
</main>