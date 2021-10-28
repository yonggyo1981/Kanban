<%@ page contentType="text/html; charset=utf-8" %>
<main>
	<div class="findpw_box login_box">
		<div class='tit'>비밀번호 찾기</div>
		<form method="post" action="../member/findpw" autocomplete="off" target="ifrmHidden">
			<input type="text" name="memId" placeholder="아이디">
			<input type="text" name="memNm" placeholder="회원명">
			<input type="text" name="memPwHint" placeholder="비밀번호 찾기 힌트">
			
			<input type="submit" value="비밀번호 찾기">
		</form>
	</div>
</main>