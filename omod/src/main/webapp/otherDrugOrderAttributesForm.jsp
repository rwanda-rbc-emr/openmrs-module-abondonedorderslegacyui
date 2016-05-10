<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Order Frequencies" otherwise="/login.htm" redirect="/admin/orders/orderFrequencyList.list" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="template/localHeader.jsp"%>

<h2><openmrs:message code="abondonedorderslegacyui.conceptSet.other.objects"/></h2>	

<openmrs:message code="abondonedorderslegacyui.conceptSet.other.help"/>
<br />
<openmrs:message code="abondonedorderslegacyui.conceptSet.other.configuration.setMembers.help"/>
<br />
<br />

<h3><openmrs:message code="abondonedorderslegacyui.conceptSet.other.configuration"/></h3>

<form method="post">
	<table>
		<tr>
			<th><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute"/></th>
			<th><openmrs:message code="abondonedorderslegacyui.conceptSet.other.configuration.gp"/></th>
			<th><openmrs:message code="abondonedorderslegacyui.conceptSet.other.set"/></th>
			<th><openmrs:message code="abondonedorderslegacyui.conceptSet.other.configuration.edit"/></th>
		</tr>
		<tr>
			<td><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute.dosingUnits"/></td>
			<td>${dosingUnits_GP.property}</td>
			<td><openmrs:fieldGen formFieldName="dosingUnit" type="org.openmrs.Concept" val="${dosingUnitSet.concept.conceptId}"/></td>
			<td><a href="../../dictionary/concept.form?conceptId=${dosingUnitSet.concept.conceptId}">${dosingUnitSet.name}</a></td>
		</tr>
		<tr>
			<td><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute.quantityUnits"/></td>
			<td>${quantityUnits_GP.property}</td>
			<td><openmrs:fieldGen formFieldName="quantityUnit" type="org.openmrs.Concept" val="${quantityUnitsSet.concept.conceptId}"/></td>
			<td><a href="../../dictionary/concept.form?conceptId=${quantityUnitsSet.concept.conceptId}">${quantityUnitsSet.name}</a></td>
		</tr>
		<tr>
			<td><openmrs:message code="abondonedorderslegacyui.conceptSet.other.attribute.routes"/></td>
			<td>${routes_GP.property}</td>
			<td><openmrs:fieldGen formFieldName="route" type="org.openmrs.Concept" val="${routesSet.concept.conceptId}"/></td>
			<td><a href="../../dictionary/concept.form?conceptId=${routesSet.concept.conceptId}">${routesSet.name}</a></td>
		</tr>
	</table>
	<input type="submit" value='<openmrs:message code="general.submit"/>'>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>