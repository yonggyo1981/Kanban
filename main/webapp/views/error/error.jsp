<%@ page contentType="text/html; charset=utf-8" %>
<%@ page isErrorPage="true" %>
<%@ page import="com.core.Logger" %>
<%
	Logger.log(exception);
%>
<h1>에러 발생!!</h1>
<h2>Message : <%=exception.getMessage()%></h2>
<h3>Exception : <%=exception.getClass().getName()%></h3>

