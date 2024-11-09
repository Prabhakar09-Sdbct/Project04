<%@page import="in.co.rays.ctl.ShoppingCartCtl"%>
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
	<form action="<%=ORSView.SHOPPINGCART_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.ShoppingCartBean"
			scope="request"></jsp:useBean>
		<div align="center">
			<h1>
				<font color="navy"> <%
 if (bean != null && bean.getId() > 0) {
 %> Update <%
 } else {
 %> Add <%
 }
 %> Shopping Cart
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
					<th>Name :</th>
					<td><input type="text" name="name" placeholder="Select Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Product :</th>
					<td><input type="text" name="product" placeholder="Select Product"
						value="<%=DataUtility.getStringData(bean.getProduct())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("product", request)%></font></td>
				</tr>
				<tr>
					<th>Date :</th>
					<td><input type="text" id="udate" name="date" placeholder="Select Date"
						value="<%=DataUtility.getDateString(bean.getDate())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("date", request)%></font></td>
				</tr>
				<tr>
					<th>Quantity :</th>
					<td><input type="text" name="quantity" placeholder="Select Quantity"
						value="<%= DataUtility.getStringData(bean.getQuantity()).equals("0") ? "" : DataUtility.getStringData(bean.getQuantity()) %>">
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("quantity", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ShoppingCartCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=ShoppingCartCtl.OP_CANCEL%>"> <%
 } else {
 %>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ShoppingCartCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=ShoppingCartCtl.OP_RESET%>"> <%
 }
 %>
				</tr>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>