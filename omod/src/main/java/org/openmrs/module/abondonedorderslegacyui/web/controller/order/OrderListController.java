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
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.api.APIException;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.abondonedorderslegacyui.api.AbandonedOrdersLegacyUIService;
import org.openmrs.web.WebConstants;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

public class OrderListController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	public ModelMap get_orderList(ModelMap model) {
		// default empty Object
		List<Order> orderList = new Vector<Order>();

		// only fill the Object is the user has authenticated properly
		if (Context.isAuthenticated()) {
			AbandonedOrdersLegacyUIService as = Context.getService(AbandonedOrdersLegacyUIService.class);
			// orderList = os.getOrders();
			orderList = as.getOrders(Order.class, null, null, null, null, null);
		}
		Map<Integer, String> conceptNames = new HashMap<Integer, String>();

		for (Order order : orderList) {
			Concept c = order.getConcept();
			String cName = c.getBestName(Context.getLocale()).getName();
			conceptNames.put(c.getConceptId(), cName);
		}

		Map<String, Object> refData = new HashMap<String, Object>();

		model.addAttribute("conceptNames", conceptNames);
		model.addAttribute("orderList", orderList);

		return model;
	}

	public void post_orderList(HttpServletRequest request, HttpServletResponse response, BindException errors) {
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
			} catch (ServletRequestBindingException e1) {
				e1.printStackTrace();
			}
			if (!success.equals(""))
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, success);
			if (!error.equals(""))
				httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, error);
		}
	}
}