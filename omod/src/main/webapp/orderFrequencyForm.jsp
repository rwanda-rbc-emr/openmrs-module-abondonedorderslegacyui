<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Order Frequencies" otherwise="/login.htm" redirect="/admin/orders/orderFrequencyForm.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<h2><openmrs:message code="abondonedorderslegacyui.OrderFrequency"/></h2>

<spring:hasBindErrors name="orderFrequency">
	<openmrs:message code="fix.error"/>
	<br />
</spring:hasBindErrors>
<form method="post">
<table>
	<tr>
		<td><openmrs:message code="abondonedorderslegacyui.OrderFrequency.general.name"/></td>
		<td>
			<openmrs_tag:conceptField formFieldName="name" includeClasses="Frequency" initialValue="${orderFrequency.orderFrequency.concept.conceptId}"/>
			<input type="text" name="textName" placeholder="Plain Text Name">
		</td>
	</tr>
	<tr>
		<td><openmrs:message code="abondonedorderslegacyui.frequencyPerDay"/></td>
		<td>
			<input type="number" name="frequencyPerDay" value="${orderFrequency.orderFrequency.frequencyPerDay}" size="35" />
		</td>
	</tr>
	<tr>
		<td valign="top"><openmrs:message code="general.description"/></td>
		<td valign="top">
			<textarea name="description" rows="3" cols="40">${orderFrequency.orderFrequency.description}</textarea>
		</td>
	</tr>
</table>
<br />
<input type="submit" value="<openmrs:message code="abondonedorderslegacyui.OrderFrequency.save"/>" onclick="jQuery('#form-action').val('Save')">
<input type="submit" value="Delete" onclick="jQuery('#form-action').val('Delete')">
<input id="form-action" name="formAction" type="hidden" value="Save">
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>