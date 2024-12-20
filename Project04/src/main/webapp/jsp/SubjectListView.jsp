<%@page import="in.co.rays.ctl.SubjectListCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.bean.SubjectBean"%>
<%@page import="in.co.rays.bean.CourseBean"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.management.modelmbean.ModelMBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Subject List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

</script>
</head>
<body>
	<%@include file="Header.jsp"%>
	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">Subject
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<jsp:useBean id="bean" class="in.co.rays.bean.SubjectBean"
			scope="request"></jsp:useBean>
		<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			@SuppressWarnings("unchecked")
			List<CourseBean> clist = (List<CourseBean>) request.getAttribute("courseList");
			@SuppressWarnings("unchecked")
			List<SubjectBean> slist = (List<SubjectBean>) request.getAttribute("subjectList");
			@SuppressWarnings("unchecked")
			List<SubjectBean> list = (List<SubjectBean>) ServletUtility.getList(request);
			Iterator<SubjectBean> it = list.iterator();

			if (list.size() != 0) {
			%>


			<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Subject Name :</b></label> <%=HTMLUtility.getList("subjectId", String.valueOf(bean.getId()), slist)%>&emsp;
						<label><b>Course Name :</b></label> <%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), clist)%>&emsp;
						<input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_SEARCH%>">&nbsp; <input
						type="submit" name="operation"
						value="<%=SubjectListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table  border="1" width="100%" align="center" cellpadding=7px
			cellspacing=".2">
				<tr style="background: skyblue">
					<th><input type="checkbox" id="selectall" name="select"></th>
					<th >S.No</th>
					<th >Subject Name</th>
					<th >Course Name</th>
					<th >Description</th>
					<th >Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="case" name="ids"
						value="<%=bean.getId()%>"></td>
					<td ><%=index++%></td>
					<td ><%=bean.getName()%></td>
					<td ><%=bean.getCourseName()%></td>
					<td ><%=bean.getDescription()%></td>
					<td ><a
						href="SubjectCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
				}
				%>
			</table>
			<table style="width: 100%">
				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=SubjectListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=SubjectListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=SubjectListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>

			</table>
			<%
			}
			if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
			}
			%>
		</form>
	</div>
	</br>
	</br>
	</br>
	</br>
	<%@include file="Footer.jsp"%>
</body>
</html>