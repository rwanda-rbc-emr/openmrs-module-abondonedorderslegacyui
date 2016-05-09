<spring:htmlEscape defaultHtmlEscape="true" />

<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><openmrs:message code="admin.title.short"/></a>
	</li>
	<openmrs:hasPrivilege privilege="Manage Order Types">
		<li <c:if test='<%= request.getRequestURI().contains("orderType") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/abondonedorderslegacyui/orderTypeList.list">
				<openmrs:message code="OrderType.manage"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Manage Order Frequencies">
		<li <c:if test='<%= request.getRequestURI().contains("orderFrequency") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/abondonedorderslegacyui/orderFrequencyList.list">
				<openmrs:message code="abondonedorderslegacyui.OrderFrequency.manage"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="View Orders">
		<li <c:if test='<%= request.getRequestURI().contains("otherDrugOrderAttributesForm") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/abondonedorderslegacyui/otherDrugOrderAttributesForm.form">
				<openmrs:message code="abondonedorderslegacyui.conceptSet.other.objects"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
</ul>