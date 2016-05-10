package org.openmrs.module.abondonedorderslegacyui.web.controller.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohorderentrybridge.MoHConcept;
import org.openmrs.web.WebConstants;
import org.springframework.ui.ModelMap;

public class OtherDrugOrderAttributesFormController {
	public void renderManageOtherDrugOrderAttributesPage(ModelMap model) {
		String DOSE_UNITS_SET_UUID = Context.getAdministrationService()
				.getGlobalProperty("order.drugDosingUnitsConceptUuid");

		String QUANTITY_UNITS_SET_UUID = Context.getAdministrationService()
				.getGlobalProperty("order.drugDispensingUnitsConceptUuid");
		String ROUTES_SET_UUID = Context.getAdministrationService().getGlobalProperty("order.drugRoutesConceptUuid");

		Concept dosingUnitsSet = Context.getConceptService().getConceptByUuid(DOSE_UNITS_SET_UUID) != null
				? Context.getConceptService().getConceptByUuid(DOSE_UNITS_SET_UUID) : null;
		Concept quantityUnitsSet = Context.getConceptService().getConceptByUuid(QUANTITY_UNITS_SET_UUID) != null
				? Context.getConceptService().getConceptByUuid(QUANTITY_UNITS_SET_UUID) : null;
		Concept routesSet = Context.getConceptService().getConceptByUuid(ROUTES_SET_UUID) != null
				? Context.getConceptService().getConceptByUuid(ROUTES_SET_UUID) : null;

		model.put("dosingUnits", dosingUnitsSet != null
				? convertConceptListToMoHConceptList(dosingUnitsSet.getSetMembers()) : Collections.emptyList());
		model.put("quantityUnits", quantityUnitsSet != null
				? convertConceptListToMoHConceptList(quantityUnitsSet.getSetMembers()) : Collections.emptyList());
		model.put("routes", routesSet != null ? convertConceptListToMoHConceptList(routesSet.getSetMembers())
				: Collections.emptyList());
		model.put("dosingUnits_GP",
				Context.getAdministrationService().getGlobalPropertyObject("order.drugDosingUnitsConceptUuid"));
		model.put("quantityUnits_GP",
				Context.getAdministrationService().getGlobalPropertyObject("order.drugDispensingUnitsConceptUuid"));
		model.put("routes_GP",
				Context.getAdministrationService().getGlobalPropertyObject("order.drugRoutesConceptUuid"));
		model.put("dosingUnitSet", dosingUnitsSet != null ? new MoHConcept(dosingUnitsSet) : null);
		model.put("quantityUnitsSet", quantityUnitsSet != null ? new MoHConcept(quantityUnitsSet) : null);
		model.put("routesSet", routesSet != null ? new MoHConcept(routesSet) : null);
	}

	private List<MoHConcept> convertConceptListToMoHConceptList(List<Concept> members) {
		List<MoHConcept> mohMembers = new ArrayList<MoHConcept>();

		for (Concept member : members) {
			if (member != null)
				mohMembers.add(new MoHConcept(member));
		}
		return mohMembers;
	}

	public void postManageOtherDrugOrderAttributesPage(HttpServletRequest request) {
		Integer dosingUnitConceptId = StringUtils.isNotBlank(request.getParameter("dosingUnit"))
				? Integer.parseInt(request.getParameter("dosingUnit")) : null;
		Integer quantityUnitConceptId = StringUtils.isNotBlank(request.getParameter("quantityUnit"))
				? Integer.parseInt(request.getParameter("quantityUnit")) : null;
		Integer routeConceptId = StringUtils.isNotBlank(request.getParameter("route"))
				? Integer.parseInt(request.getParameter("route")) : null;
		GlobalProperty dosingUnits_GP = Context.getAdministrationService()
				.getGlobalPropertyObject("order.drugDosingUnitsConceptUuid");
		GlobalProperty quantityUnits_GP = Context.getAdministrationService()
				.getGlobalPropertyObject("order.drugDispensingUnitsConceptUuid");
		GlobalProperty routes_GP = Context.getAdministrationService()
				.getGlobalPropertyObject("order.drugRoutesConceptUuid");

		updateOrderGPConfig(dosingUnits_GP, dosingUnitConceptId);
		updateOrderGPConfig(quantityUnits_GP, quantityUnitConceptId);
		updateOrderGPConfig(routes_GP, routeConceptId);
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully Updated Configurations");
	}

	private void updateOrderGPConfig(GlobalProperty gp, Integer conceptId) {
		if (gp != null) {
			if (conceptId != null && Context.getConceptService().getConcept(conceptId) != null
					&& Context.getConceptService().getConcept(conceptId).isSet()) {
				gp.setPropertyValue(Context.getConceptService().getConcept(conceptId).getUuid());
			} else {
				gp.setPropertyValue("");
			}
			Context.getAdministrationService().saveGlobalProperty(gp);
		}
	}
}
