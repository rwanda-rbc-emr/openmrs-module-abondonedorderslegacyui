<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Order Frequencies" otherwise="/login.htm" redirect="/admin/orders/orderFrequencyList.list" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<h2><openmrs:message code="abondonedorderslegacyui.OrderFrequency.manage"/></h2>	

<a href="orderFrequencyForm.form"><openmrs:message code="abondonedorderslegacyui.OrderFrequency.add"/></a> <br />

<br />

<b class="boxHeader"><openmrs:message code="abondonedorderslegacyui.OrderFrequency.list.title"/></b>
<form method="post" class="box">
	<table>
		<tr>
			<th><input type="checkbox" onchange="jQuery(':checkbox').prop('checked', this.checked);"></th>
			<th> <openmrs:message code="general.name" /> </th>
			<th> <openmrs:message code="abondonedorderslegacyui.frequencyPerDay" /> </th>
		</tr>
		<c:forEach var="orderFrequency" items="${orderFrequencies}">
			<tr>
				<td valign="top"><input type="checkbox" name="orderFrequencyId" value="${orderFrequency.orderFrequency.orderFrequencyId}"></td>
				<td valign="top">
					<a href="orderFrequencyForm.form?orderFrequencyId=${orderFrequency.orderFrequency.orderFrequencyId}">
					   ${orderFrequency.name}
					</a>
				</td>
				<td valign="top">${orderFrequency.orderFrequency.frequencyPerDay}</td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" value="<openmrs:message code="abondonedorderslegacyui.OrderFrequency.delete"/>" name="action">
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>