<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id='frmAdd'>
	<dl>
		<dt>작업구분</dt>
		<dd class='status_type'>
			<c:choose>
				<c:when test="${status == 'progress'}">
					진행중
				</c:when>
				<c:when test="${status == 'done'}">
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
			<ul>
			<c:forEach var="item" items="${attachFiles}" varStatus="status">
				<li class='attach_file' data-idx='${item.idx}'>
					${status.count}.
					<a href='../file/download/${item.idx}'>${item.orignalName}</a>
					<i class='xi-trash delete_file'></i>
				</li>
			</c:forEach>
			</ul>
		</dd>
	</dl>	
	</c:if>
</div>