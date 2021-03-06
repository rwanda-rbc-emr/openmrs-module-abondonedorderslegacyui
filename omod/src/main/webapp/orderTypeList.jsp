<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Order Types" otherwise="/login.htm" redirect="/admin/orders/orderTypeList.list" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<h2><openmrs:message code="OrderType.manage.title"/></h2>	

<a href="orderTypeForm.form"><openmrs:message code="OrderType.add"/></a> <br />

<br />

<b class="boxHeader"><openmrs:message code="OrderType.list.title"/></b>
<form method="post" class="box">
	<table>
		<tr>
			<th><input type="checkbox" onchange="jQuery(':checkbox').prop('checked', this.checked);"></th>
			<th> <openmrs:message code="general.name" /> </th>
			<th> <openmrs:message code="general.description" /> </th>
		</tr>
		<c:forEach var="orderType" items="${orderTypeList}">
			<tr <c:if test="${orderType.retired}">class="retired"</c:if>>
				<td valign="top"><input type="checkbox" name="orderTypeId" value="${orderType.orderTypeId}"></td>
				<td valign="top">
					<a href="orderTypeForm.form?orderTypeId=${orderType.orderTypeId}">
					   ${orderType.name}
					</a>
				</td>
				<td valign="top">${orderType.description}</td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" value="<openmrs:message code="OrderType.delete"/>" name="action">
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>