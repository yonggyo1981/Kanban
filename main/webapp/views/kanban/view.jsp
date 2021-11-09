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
		<dd>
			
		</dd>
	</dl>
	<dl>
		<dt>작업내용</dt>
		<dd>
			
		</dd>
	</dl>
	<dl>
		<dt>첨부파일</dt>
		<dd >
			
		</dd>
	</dl>	
</div>