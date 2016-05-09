<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Order Frequencies" otherwise="/login.htm" redirect="/admin/orders/orderFrequencyList.list" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<h2><openmrs:message code="abondonedorderslegacyui.conceptSet.other.objects"/></h2>	

<openmrs:message code="abondonedorderslegacyui.conceptSet.other.help"/>

<h3><openmrs:message code="abondonedorderslegacyui.conceptSet.other.configuration"/></h3>
<form>
	<table>
		<tr>
			<th><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute"/></th>
			<th><openmrs:message code="abondonedorderslegacyui.conceptSet.other.configuration.edit"/></th>
			<th><openmrs:message code="general.value"/></th>
		</tr>
		<tr>
			<td><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute.dosingUnits"/></td>
			<td><a href="../../dictionary/concept.form?conceptId=${dosingUnitSet.concept.conceptId}">${dosingUnitSet.name}</a></td>
			<td><openmrs:fieldGen formFieldName="${dosingUnits_GP.property}" type="org.openmrs.Concept" val="${dosingUnits_GP.propertyValue}"/></td>
		</tr>
		<tr>
			<td><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute.quantityUnits"/></td>
			<td><a href="../../dictionary/concept.form?conceptId=${quantityUnitsSet.concept.conceptId}">${quantityUnitsSet.name}</a></td>
			<td><openmrs:fieldGen formFieldName="${quantityUnits_GP.property}" type="org.openmrs.Concept" val="${quantityUnits_GP.propertyValue}"/></td>
		</tr>
		<tr>
			<td><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute.routes"/></td>
			<td><a href="../../dictionary/concept.form?conceptId=${routesSet.concept.conceptId}">${routesSet.name}</a></td>
			<td><openmrs:fieldGen formFieldName="${routes_GP.property}" type="org.openmrs.Concept" val="${routes_GP.propertyValue}"/></td>
		</tr>
	</table>
	<input type="submit" value='<openmrs:message code="general.submit"/>'>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>