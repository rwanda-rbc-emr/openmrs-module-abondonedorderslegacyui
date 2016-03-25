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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.OrderType;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.web.WebConstants;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;

public class OrderTypeFormController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	public ModelMap get_orderTypeForm(ModelMap model, HttpServletRequest request) {
		OrderType orderType = null;

		if (Context.isAuthenticated()) {
			OrderService os = Context.getOrderService();
			String orderTypeId = request.getParameter("orderTypeId");
			if (orderTypeId != null)
				orderType = os.getOrderType(Integer.valueOf(orderTypeId));
		}

		if (orderType == null)
			orderType = new OrderType();

		model.addAttribute("orderType", orderType);

		return model;
	}

	public void post_orderTypeForm(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();

		if (Context.isAuthenticated()) {
			String name = request.getParameter("name");
			String className = request.getParameter("className");
			String description = request.getParameter("description");

			if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(className)) {
				try {
					Class.forName(className);
					OrderType orderType = new OrderType();
					
					orderType.setName(name);
					orderType.setJavaClassName(className);
					orderType.setDescription(description);
					
					Context.getOrderService().saveOrderType(orderType);
					httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "OrderType.saved");
				} catch (ClassNotFoundException e) {
					httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, className + "Doesn't doesn't exisit");
				} catch (Exception e) {
					httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, e.getMessage());
				}
			} else {
				httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Name and Class Name are required");
			}
		}
	}

}
