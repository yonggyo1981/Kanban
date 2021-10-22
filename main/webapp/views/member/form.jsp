<%@ page contentType="text/html; charset=utf-8" %>
<main>
	<div class='join_box login_box'>
		<div class='tit'>회원가입</div>
		<form name='frmJoin' id='frmJoin' method="post" action="../member/join" target="ifrmHidden" autocomplete="off">
			<dl>
				<dt>아이디</dt>
				<dd>
					<input type="text" name="memId">
				</dd>
			</dl>
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
					<input type="text" name="memPwHint">
				</dd>
			</dl>
			<dl>
				<dt>회원명</dt>
				<dd>
					<input type="text" name="memNm">
				</dd>
			</dl>
			<dl>
				<dt>휴대전화</dt>
				<dd>
					<input type="text" name="cellPhone">
				</dd>
			</dl>
			<input type="reset" value="다시입력">
			<input type="submit" value="회원가입">
		</form>
	</div>
</main>