
<%@page import="in.co.rays.bean.MarksheetBean"%>
<%@page import="in.co.rays.model.MarksheetModel"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">
		

		<jsp:useBean id="bean" class="in.co.rays.bean.MarksheetBean"
			scope="request"></jsp:useBean>

		<%
		List list = ServletUtility.getList(request);
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		%>

		<div align="center">
			<h1>
				<font color="navy">Marksheet List</font>
			</h1>
		</div>

		<table border="1%" style="width: 100%">
			<tr>
				<th><input type="checkbox"></th>
				<th>S.No.</th>
				<th>RollNo</th>
				<th>Name</th>
				<th>Physics</th>

				<th>Chemistry</th>
				<th>Maths</th>
				<th>Edit</th>
			</tr>
			<%
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
			%>
			<tr align="center">
				<td><input type="checkbox"></td>
				<td><%=bean.getId()%></td>
				<td><%=bean.getRollNo()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getPhysics()%></td>
				<td><%=bean.getChemistry()%></td>
				<td><%=bean.getMaths()%></td>

				<td><a href="<%=ORSView.MARKSHEET_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
			}
			%>
		</table>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>
