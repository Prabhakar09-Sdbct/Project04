<%@page import="in.co.rays.ctl.ShoppingCartListCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.ShoppingCartCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.bean.ShoppingCartBean"%>
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
	<jsp:useBean id="bean" class="in.co.rays.bean.ShoppingCartBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.SHOPPINGCART_LIST_CTL%>" method="post">

		<div align="center">
			<h1>Shopping Cart List</h1>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<%
		List list = ServletUtility.getList(request);
		List shoppingList = (List) request.getAttribute("shoppingList");
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
				<td align="center"><label><b>Name :</b></label> <input
					type="text" name="name"
					value="<%=ServletUtility.getParameter("name", request)%>">&nbsp;
					<label><b>Product :</b></label> <%=HTMLUtility.getList("id", DataUtility.getStringData(bean.getId()), shoppingList)%>&nbsp;
					<input type="submit" name="operation"
					value="<%=ShoppingCartListCtl.OP_SEARCH%>"> &nbsp; <input
					type="submit" name="operation"
					value="<%=ShoppingCartListCtl.OP_RESET%>"></td>
			</tr>
		</table>

		<br>

		<table border="1" width="100%" align="center">
			<tr style="background: skyblue">
				<th><input type="checkbox" id="selectall"></th>
				<th>S.No.</th>
				<th>Name</th>
				<th>product</th>
				<th>date</th>
				<th>quantity</th>
				<th>Edit</th>
			</tr>
			<%
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (ShoppingCartBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getProduct()%></td>
				<td><%=bean.getDate()%></td>
				<td><%=bean.getQuantity()%></td>
				<td><a
					href="<%=ORSView.SHOPPINGCART_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
			}
			%>
		</table>

		<br>

		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=ShoppingCartCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=ShoppingCartCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=ShoppingCartCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=ShoppingCartCtl.OP_NEXT%>"
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
					value="<%=ShoppingCartCtl.OP_BACK%>"></td>
			</tr>
		</table>
		<%
		}
		%>
	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>