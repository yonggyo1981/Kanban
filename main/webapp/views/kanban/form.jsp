<%@ page contentType="text/html; charset=utf-8" %>
<form name="frmAdd" id="frmAdd" method="post" action="../kanban/add" target="ifrmHidden" autocomplete="off" enctype="multipart/form-data">
	<dl>
		<dt>작업구분</dt>
		<dd>
			<input type="radio" name="status" value="ready" id="status_ready" checked>
			<label for="status_ready">준비중</label>
			<input type="radio" name="status" value="progress" id="status_progress">
			<label for="status_progress">진행중</label>
			<input type="radio" name="status" value="done" id="status_done">
			<label for="status_done">완료</label>
		</dd>
	</dl>
	<dl>
		<dt>제목</dt>
		<dd>
			<input type="text" name="subject">
		</dd>
	</dl>
	<dl>
		<dt>작업내용</dt>
		<dd>
			<textarea name="content"></textarea>
		</dd>
	</dl>
	<dl>
		<dt>
			파일첨부
			
		</dt>
		<dd>
			<input type="file" name="file1">
		</dd>
	</dl>	
</form>



