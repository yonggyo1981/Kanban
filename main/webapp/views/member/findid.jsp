<%@ page contentType="text/html; charset=utf-8" %>
<main>
	<div class='findid_box login_box'>
		<div class='tit'>아이디 찾기</div>
		<form method="post" action="../member/findid" autocomplete="off">
			<input type="hidden" name="outline" value="print">
			<input type="text" name="memNm" placeholder="회원명">
			<input type="text" name="cellPhone" placeholder="휴대전화번호">
			<input type="submit" value="아이디 찾기">
		</form>
	</div>
</main>