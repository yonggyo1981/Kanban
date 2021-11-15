<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class='popup_tit'>
	작업 내용 확인
</div>
<div class='work_view'>
	<dl>
		<dt>작업구분</dt>
		<dd class='status_type'>
			<c:choose>
				<c:when test="${data.status == 'progress'}">
					진행중
				</c:when>
				<c:when test="${data.status == 'done'}">
					완료
				</c:when>
				<c:otherwise>
					준비중
				</c:otherwise>
			</c:choose>
		</dd>
	</dl>
	<dl>
		<dt>제목</dt>
		<dd>${data.subject}</dd>
	</dl>
	<dl>
		<dt>작업내용</dt>
		<dd>
			${fn:replace(data.content,'\\n', '<br>')}
		</dd>
	</dl>
	<c:if test='${attachFiles != null}'>
	<dl>
		<dt>첨부파일</dt>
		<dd>
			<ul class='attach_files'>
			<c:forEach var="item" items="${attachFiles}" varStatus="status">
				<li class='file' data-idx='${item.idx}'>
					${status.count}.
					<a href='../file/download/${item.idx}'>${item.originalName}</a>
					<i class='xi-trash delete_file'></i>
				</li>
			</c:forEach>
			</ul>
		</dd>
	</dl>	
	</c:if>
	<div class='btns'>
		<a href='../kanban/remove?idx=${data.idx}' class='btn' onclick="return confirm('정말 삭제하시겠습니까?');" target='ifrmHidden'>삭제</a>
		<span class='btn update_work' data-idx='${data.idx}'>수정</span>
	</div>
</div>



