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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.DrugOrder;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.web.WebConstants;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

public class OrderDrugFormController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	public ModelMap get_orderDrugForm(ModelMap model, HttpServletRequest request) {
		OrderService os = Context.getOrderService();

		DrugOrder order = null;

		try {
			if (Context.isAuthenticated()) {
				Integer orderId = ServletRequestUtils.getIntParameter(request, "orderId");
				if (orderId != null)
					order = (DrugOrder) os.getOrder(orderId);
			}

			// if this is a new order, let's see if the user has picked a type
			// yet
			if (order == null) {
				order = new DrugOrder();
				Integer orderTypeId;
				orderTypeId = ServletRequestUtils.getIntParameter(request, "orderTypeId");

				if (orderTypeId != null) {
					OrderType ot = os.getOrderType(orderTypeId);
					order.setOrderType(ot);
				}

				Integer patientId = ServletRequestUtils.getIntParameter(request, "patientId");
				if (patientId != null) {
					Patient patient = Context.getPatientService().getPatient(patientId);
					if (patient != null) {
						order.setPatient(patient);
					}
				}
			}
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		model.addAttribute("order", order);

		return model;
	}

	public void post_orderDrugForm(HttpServletRequest request, HttpServletResponse response, BindException errors) {
		DrugOrder order = null;// = (DrugOrder) obj;//TODO

		// TODO: for now, orderType will have to be hard-coded?
		if (order.getOrderType() == null) {
			order.setOrderType(Context.getOrderService().getOrderType(OpenmrsConstants.ORDERTYPE_DRUG));
		}

		boolean ok = executeCommand(order, request);
		if (ok) {
			int patientId = order.getPatient().getPatientId();
			// view = getSuccessView() + "?patientId=" + patientId;
		} else {
			// return showForm(request, response, errors);
		}
	}

	protected boolean executeCommand(Order order, HttpServletRequest request) {
		if (!Context.isAuthenticated()) {
			return false;
		}

		OrderService orderService = Context.getOrderService();

		try {
			if (request.getParameter("saveOrder") != null) {
				orderService.saveOrder(order, null);
				request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Order.saved");
			} else if (request.getParameter("voidOrder") != null) {
				orderService.voidOrder(order, order.getVoidReason());
				request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Order.voidedSuccessfully");
			} else if (request.getParameter("unvoidOrder") != null) {
				orderService.unvoidOrder(order);
				request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Order.unvoidedSuccessfully");
			} else if (request.getParameter("discontinueOrder") != null) {
				try {
					orderService.discontinueOrder(order, order.getOrderReason(), order.getEffectiveStopDate(),
							order.getOrderer(), order.getEncounter());
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Order.discontinuedSuccessfully");
			} else if (request.getParameter("undiscontinueOrder") != null) {
				// orderService.undiscontinueOrder(order);//TODO Not supported
				request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Order.undiscontinuedSuccessfully");
			}
		} catch (APIException ex) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, ex.getMessage());
			return false;
		}

		return true;
	}
}
