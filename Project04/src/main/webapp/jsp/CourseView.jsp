<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.ctl.CourseCtl"%>
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
	<jsp:useBean id="bean" class="in.co.rays.bean.CourseBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.COURSE_CTL%>" method="post">

		<div align="center">

			<h1>
				<font color="navy">Add Course</font>
			</h1>
			<table>
				<tr>
					<td>Name :</td>
					<th><input type="text" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>">
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
					</th>
				</tr>
				<tr>
					<td>Duration :</td>
					<th><input type="text" name="duration"
						value="<%=DataUtility.getStringData(bean.getDuration())%>">
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("duration", request)%></font></td>


				</tr>
				<tr>
					<td>Description :</td>
					<th><textarea type="text" name="description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></textarea>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>
						</th>
					
				</tr>
				<tr>
					<td></td>
					<th><input type="submit" name="operation"
						value="<%=CourseCtl.OP_SAVE%>"></th>
				</tr>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>