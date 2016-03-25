/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.abondonedorderslegacyui.web.controller.order;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PersonName;
import org.openmrs.api.APIException;
import org.openmrs.api.OrderService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.abondonedorderslegacyui.api.AbandonedOrdersLegacyUIService;
import org.openmrs.module.mohorderentrybridge.api.MoHOrderEntryBridgeService;
import org.openmrs.web.WebConstants;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

public class OrderListByPatientController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	public ModelMap get_orderListByPatient(ModelMap model, HttpServletRequest request) {
		Map<String, Object> refData = new HashMap<String, Object>();

		// default empty Object
		List<DrugOrder> orderList = new Vector<DrugOrder>();
		Integer patientId;
		try {
			patientId = ServletRequestUtils.getIntParameter(request, "patientId");

			boolean showAll = ServletRequestUtils.getBooleanParameter(request, "showAll", false);

			// only fill the Object is the user has authenticated properly
			if (Context.isAuthenticated()) {
				if (patientId != null) {
					// this is the default
					// this.setFormView("/admin/orders/orderListByPatient");
					PatientService ps = Context.getPatientService();
					Patient p = ps.getPatient(patientId);

					if (p != null) {
						orderList = Context.getService(MoHOrderEntryBridgeService.class).getDrugOrdersByPatient(p);
					} else {
						log.error("Could not get a patient corresponding to patientId [" + patientId
								+ "], thus could not get drug orders.");
						throw new ServletException();
					}
				} else {
					if (showAll) {
						// this.setFormView("/admin/orders/orderDrugList");
						AbandonedOrdersLegacyUIService as = Context.getService(AbandonedOrdersLegacyUIService.class);
						// orderList = os.getDrugOrders();
						orderList = as.getOrders(DrugOrder.class, null, null, null, null, null);
					} else {
						// this.setFormView("/admin/orders/choosePatient");
					}
				}

			}
			// Load international concept names so we can show the correct drug
			// name
			Map<Integer, String> conceptNames = new HashMap<Integer, String>();

			for (Order order : orderList) {
				Concept c = order.getConcept();
				String cName = c.getBestName(Context.getLocale()).getName();
				conceptNames.put(c.getConceptId(), cName);
			}

			model.addAttribute("conceptNames", conceptNames);

			Patient p = null;

			if (Context.isAuthenticated()) {
				if (patientId != null) {
					PatientService ps = Context.getPatientService();
					p = ps.getPatient(patientId);

					Set<PersonName> PersonNames = p.getNames();
					Iterator i = PersonNames.iterator();
					PersonName pm = (PersonName) i.next();

					model.addAttribute("patient", p);
					model.addAttribute("PersonName", pm);
				}
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

		return model;
	}

	public void post_orderListByPatient(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();

		if (Context.isAuthenticated()) {
			String[] orderList = ServletRequestUtils.getStringParameters(request, "orderId");
			OrderService os = Context.getOrderService();

			String success = "";
			String error = "";

			MessageSourceService msa = Context.getMessageSourceService();
			String deleted = msa.getMessage("general.deleted");
			String notDeleted = msa.getMessage("general.cannot.delete");
			String ord = msa.getMessage("Order.title");
			String voidReason;
			try {
				voidReason = ServletRequestUtils.getRequiredStringParameter(request, "voidReason");

				if (!StringUtils.hasLength(voidReason)) {
					httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "general.voidReason.empty");
					// return showForm(request, response, errors);
				}
				for (String p : orderList) {
					try {
						os.voidOrder(os.getOrder(Integer.valueOf(p)), voidReason);
						if (!success.equals(""))
							success += "<br/>";
						success += ord + " " + p + " " + deleted;
					} catch (APIException e) {
						log.warn("Error deleting order", e);
						if (!error.equals(""))
							error += "<br/>";
						error += ord + " " + p + " " + notDeleted;
					}
				}

				if (ServletRequestUtils.getIntParameter(request, "patientId") != null)
					// view += "?patientId=" +
					// ServletRequestUtils.getIntParameter(request,
					// "patientId");
					if (!success.equals(""))
						httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, success);
				if (!error.equals(""))
					httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, error);
			} catch (ServletRequestBindingException e1) {
				e1.printStackTrace();
			}
		}

	}
}
