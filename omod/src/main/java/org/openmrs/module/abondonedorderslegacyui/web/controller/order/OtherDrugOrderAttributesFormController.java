package org.openmrs.module.abondonedorderslegacyui.web.controller.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohorderentrybridge.MoHConcept;
import org.springframework.ui.ModelMap;

public class OtherDrugOrderAttributesFormController {

	private static String DOSE_UNITS_SET_UUID = Context.getAdministrationService()
			.getGlobalProperty("order.drugDosingUnitsConceptUuid");

	private static String QUANTITY_UNITS_SET_UUID = Context.getAdministrationService()
			.getGlobalProperty("order.drugDispensingUnitsConceptUuid");

	private static String ROUTES_SET_UUID = Context.getAdministrationService()
			.getGlobalProperty("order.drugRoutesConceptUuid");

	public void renderManageOtherDrugOrderAttributesPage(ModelMap model) {
		Concept dosingUnitsSet = Context.getConceptService().getConceptByUuid(DOSE_UNITS_SET_UUID) != null ? Context.getConceptService().getConceptByUuid(DOSE_UNITS_SET_UUID) : null;
		Concept quantityUnitsSet = Context.getConceptService().getConceptByUuid(QUANTITY_UNITS_SET_UUID) != null ? Context.getConceptService().getConceptByUuid(QUANTITY_UNITS_SET_UUID) : null;
		Concept routesSet = Context.getConceptService().getConceptByUuid(ROUTES_SET_UUID) != null ? Context.getConceptService().getConceptByUuid(ROUTES_SET_UUID) : null;
		
		model.put("dosingUnits", dosingUnitsSet != null ? convertConceptListToMoHConceptList(dosingUnitsSet.getSetMembers()) : Collections.emptyList());
		model.put("quantityUnits", quantityUnitsSet != null ? convertConceptListToMoHConceptList(quantityUnitsSet.getSetMembers()) : Collections.emptyList());
		model.put("routes", routesSet != null ? convertConceptListToMoHConceptList(routesSet.getSetMembers()) : Collections.emptyList());
		model.put("dosingUnits_GP", Context.getAdministrationService().getGlobalPropertyObject("order.drugDosingUnitsConceptUuid"));
		model.put("quantityUnits_GP", Context.getAdministrationService().getGlobalPropertyObject("order.drugDispensingUnitsConceptUuid"));
		model.put("routes_GP", Context.getAdministrationService().getGlobalPropertyObject("order.drugRoutesConceptUuid"));
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
}
