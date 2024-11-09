<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.ctl.TaskCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.TASK_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.TaskBean"
			scope="request"></jsp:useBean>
		<div align="center">
			<h1>
				<font color="navy"> <%
 if (bean != null && bean.getId() > 0) {
 %> Update <%
 } else {
 %> Add <%
 }
 %> Task
				</font>
			</h1>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
			<!-- Hidden Fields -->
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDateTime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDateTime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

			<table>
				<tr>
					<th>Title :</th>
					<td><input type="text" name="title" placeholder="Select Title"
						value="<%=DataUtility.getStringData(bean.getTitle())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("title", request)%></font></td>
				</tr>
				<tr>
					<th>Details :</th>
					<td><input type="text" name="details"
						placeholder="Select Details"
						value="<%=DataUtility.getStringData(bean.getDetails())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("details", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Assigned To :<span style="color: red">*</span></th>
					<td>
						<%
						HashMap<String, String> map = new HashMap<>();
						map.put("Prabhakar Mandloi", "Prabhakar Mandloi");
						map.put("Azam Khan", "Azam Khan");
						map.put("Uday Dabi", "Uday Dabi");
						%> <%=HTMLUtility.getList("assignedTo", bean.getAssignedTo(), map)%>
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("assignedTo", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Status :<span style="color: red">*</span></th>
					<td>
						<%
						HashMap<String, String> mapStatus = new HashMap<>();
						mapStatus.put("New", "New");
						mapStatus.put("Started", "Started");
						mapStatus.put("On Hold", "On Hold");
						mapStatus.put("Completed", "Completed");
						mapStatus.put("Closed", "Closed");
						%> <%=HTMLUtility.getList("status", bean.getStatus(), mapStatus)%>
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=TaskCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=TaskCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=TaskCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=TaskCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>