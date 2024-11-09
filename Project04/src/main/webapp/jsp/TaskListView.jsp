<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.ctl.TaskListCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.TaskCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.bean.TaskBean"%>
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
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.bean.TaskBean" scope="request"></jsp:useBean>
	<form action="<%=ORSView.TASK_LIST_CTL%>" method="post">

		<div align="center">
			<h1>Task List</h1>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<%
		List list = ServletUtility.getList(request);
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
		if (list.size() != 0) {
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

		<table style="width: 100%">

			<tr>
				<td align="center">
				
				<label><b>Creation Date :</b></label> <input
					type="text" id="udate" name="createdDateTime"
					value="<%=ServletUtility.getParameter("createdDateTime", request)%>">&nbsp;
				
				<label><b>Title :</b></label> <input
					type="text" name="title"
					value="<%=ServletUtility.getParameter("title", request)%>">&nbsp;
					
					<label><b>Details :</b></label> <input
					type="text" name="details"
					value="<%=ServletUtility.getParameter("details", request)%>">&nbsp;

					<label><b>Assigned To :</b></label> <%
 HashMap<String, String> map = new HashMap<>();
 map.put("Prabhakar Mandloi", "Prabhakar Mandloi");
 map.put("Azam Khan", "Azam Khan");
 map.put("Uday Dabi", "Uday Dabi");
 %> <%=HTMLUtility.getList("assignedTo", bean.getAssignedTo(), map)%>

					&nbsp; <label><b>Status :</b></label> <%
 HashMap<String, String> mapStatus = new HashMap<>();
 mapStatus.put("New", "New");
 mapStatus.put("Started", "Started");
 mapStatus.put("On Hold", "On Hold");
 mapStatus.put("Completed", "Completed");
 mapStatus.put("Closed", "Closed");
 %> <%=HTMLUtility.getList("status", bean.getStatus(), mapStatus)%>

					&nbsp; <input type="submit" name="operation"
					value="<%=TaskListCtl.OP_SEARCH%>"> &nbsp; <input
					type="submit" name="operation" value="<%=TaskListCtl.OP_RESET%>"></td>
			</tr>
		</table>

		<br>

		<table border="1" width="100%" align="center">
			<tr style="background: skyblue">
				<th><input type="checkbox" id="selectall"></th>
				<th>S.No.</th>
				<th>Creation Date</th>
				<th>Title</th>
				<th>Details</th>
				<th>Assigned To</th>
				<th>Status</th>
				<th>Edit</th>
			</tr>
			<%
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TaskBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getCreatedDateTime()%></td>
				<td><%=bean.getTitle()%></td>
				<td><%=bean.getDetails()%></td>
				<td><%=bean.getAssignedTo()%></td>
				<td><%=bean.getStatus()%></td>
				<td><a href="<%=ORSView.TASK_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
			}
			%>
		</table>

		<br>

		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=TaskCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=TaskCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=TaskCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=TaskCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<%
		}
		if (list.size() == 0) {
		%>
		<br>
		<table align="center">
			<tr>
				<td align="right"><input type="submit" name="operation"
					value="<%=TaskCtl.OP_BACK%>"></td>
			</tr>
		</table>
		<%
		}
		%>
	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>