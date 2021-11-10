<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class='popup_tit'>
	<c:choose>
		<c:when test="${data == null}">
		작업 등록
		</c:when>
		<c:otherwise>
		작업 수정
		</c:otherwise>
	</c:choose>
	
</div>
<form name="frmAdd" id="frmAdd" method="post" action="../kanban/${mode}" target="ifrmHidden" autocomplete="off" enctype="multipart/form-data">
	<c:choose>
		<c:when test="${data == null}">
			<input type="hidden" name="gid" value="${gid}" />
		</c:when>
		<c:otherwise>
			<input type="hidden" name="idx" value="${data.idx}" />
			<input type="hidden" name="gid" value="${data.gid}" />
		</c:otherwise>
	</c:choose>
	<dl>
		<dt>작업구분</dt>
		<dd class='status_type'>
			<input type="radio" name="status" value="ready" id="status_ready" 
				<c:if test="${data == null || data.status == 'ready'}"> checked</c:if>
			>
			<label for="status_ready">준비중</label>
			<input type="radio" name="status" value="progress" id="status_progress"
				<c:if test="${data.status == 'progress'}"> checked</c:if>
			>
			<label for="status_progress">진행중</label>
			<input type="radio" name="status" value="done" id="status_done"
				<c:if test="${data.status == 'done'}"> checked</c:if>
			>
			<label for="status_done">완료</label>
		</dd>
	</dl>
	<dl>
		<dt>제목</dt>
		<dd>
			<input type="text" name="subject" value="${data.subject}">
		</dd>
	</dl>
	<dl>
		<dt>작업내용</dt>
		<dd>
			<textarea name="content">${data.content}</textarea>
		</dd>
	</dl>
	<c:if test="${attachFiles != null}">
		<dl>
			<dt>첨부파일목록</dt>
			<dd>
				<ul class="attach_files">
					<c:forEach var="item" items="${attachFiles}" varStatus="status">
						<li data-idx='${item.idx}'>
							${status.count}.
							<a href='../file/download/${item.idx}'>${item.originalName}</a>
							<i class='xi-trash delete_file'></i>
						</li>
					</c:forEach>
				</ul>
			</dd>
		</dl>
	</c:if>
	<dl>
		<dt>
			파일첨부<br>
			<span class="del_file btn"><i class='xi-minus'></i>삭제</span>
			<span class='add_file btn'><i class='xi-plus'></i> 추가</span>
		</dt>
		<dd id='file_upload'>
			<div class='rows'><input type="file" name="file1"></div>
		</dd>
	</dl>	
	<c:choose> 
		<c:when test="${data == null}">
			<input type="submit" value="작업등록">
		</c:when>
		<c:otherwise>
			<input type="submit" value="작업수정">
		</c:otherwise>
	</c:choose>
</form>



