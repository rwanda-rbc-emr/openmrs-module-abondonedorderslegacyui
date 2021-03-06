<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Order Types" otherwise="/login.htm" redirect="/admin/orders/orderType.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<h2><openmrs:message code="OrderType.title"/></h2>

<spring:hasBindErrors name="orderType">
	<openmrs:message code="fix.error"/>
	<br />
</spring:hasBindErrors>
<form method="post">
<table>
	<tr>
		<td><openmrs:message code="general.name"/></td>
		<td>
			<spring:bind path="orderType.name">
				<input type="text" name="name" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td><openmrs:message code="abondonedorderslegacyui.class.name"/></td>
		<td>
			<spring:bind path="orderType.javaClassName">
				<input type="text" name="className" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top"><openmrs:message code="general.description"/></td>
		<td valign="top">
			<spring:bind path="orderType.description">
				<textarea name="description" rows="3" cols="40">${status.value}</textarea>
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<c:if test="${!(orderType.creator == null)}">
		<tr>
			<td><openmrs:message code="general.createdBy" /></td>
			<td>
				${orderType.creator.personName} -
				<openmrs:formatDate date="${orderType.dateCreated}" type="long" />
			</td>
		</tr>
	</c:if>
</table>
<br />
<input type="submit" value="<openmrs:message code="OrderType.save"/>" onclick="jQuery('#form-action').val('Save')">
<input type="submit" value="Delete" onclick="jQuery('#form-action').val('Delete')">
<input id="form-action" name="formAction" type="hidden" value="Save">
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>