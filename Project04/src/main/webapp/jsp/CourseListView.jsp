<%@page import="in.co.rays.ctl.CourseListCtl"%>
<%@page import="in.co.rays.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.CourseBean"
			scope="request"></jsp:useBean>

		<%
		List list = ServletUtility.getList(request);
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		%>

		<div align="center">
			<h1>
				<font color="navy">Course List</font>
			</h1>
		</div>
		<table border="1" width="100%" align="center" cellpadding=6px
			cellspacing=".2">
			<tr>
				<th><input type="checkbox" id="selectall"></th>
				<th>S. No.</th>
				<th>Name</th>
				<th>Duration</th>
				<th>Description</th>
				<th>Edit</th>
			</tr>
			<%
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
			%>
			<tr align="center">
				<td><input type="checkbox" id="ids"></td>
				<td><%=index++%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getDuration()%></td>
				<td><%=bean.getDescription()%></td>
				<td><a href="<%=ORSView.COURSE_CTL%>?<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
			}
			%>
		</table>
		<table width="100%">
			<tr>
				<td><input type="button" value="<%=CourseListCtl.OP_PREVIOUS%>"></td>

				<td><input type="button" value="<%=CourseListCtl.OP_NEW%>"></td>

				<td><input type="button" value="<%=CourseListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="button"
					value="<%=CourseListCtl.OP_NEXT%>"></td>
			</tr>
		</table>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>