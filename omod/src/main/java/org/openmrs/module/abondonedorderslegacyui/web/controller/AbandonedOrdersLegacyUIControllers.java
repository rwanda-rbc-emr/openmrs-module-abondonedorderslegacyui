/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.abondonedorderslegacyui.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderDrugListController;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderListByPatientController;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderListController;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderTypeFormController;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderTypeListController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller, connects to the rest of the controllers.
 */
@Controller
public class  AbandonedOrdersLegacyUIControllers {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	OrderListController orderListController = new OrderListController();

	private OrderTypeListController orderTypeListController = new OrderTypeListController();
	
	private OrderDrugListController orderDrugListController = new OrderDrugListController();
	
	private OrderListByPatientController orderListByPatientController = new OrderListByPatientController();
	
	private OrderTypeFormController orderTypeFormController = new OrderTypeFormController();
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderList", method = RequestMethod.GET)
	public void orderList(ModelMap model) {
		model = orderListController.get_orderList(model);
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderList", method = RequestMethod.POST)
	public void saveOrderList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		orderListController.post_orderList(request, response);
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeList", method = RequestMethod.GET)
	public void orderTypeList(ModelMap model) {
		model = orderTypeListController.get_orderTypeList(model);
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeList", method = RequestMethod.POST)
	public void saveOrderTypeList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		orderTypeListController.post_orderTypeList(request, response);
		redirectTo(response, "orderTypeList.list");
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeForm", method = RequestMethod.GET)
	public void orderTypeForm(ModelMap model, HttpServletRequest request) {
		model = orderTypeFormController.get_orderTypeForm(model, request);
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeForm", method = RequestMethod.POST)
	public void saveOrderTypeForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		orderTypeFormController.post_orderTypeForm(request, response);
		redirectTo(response, "orderTypeForm.form");
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderDrugList", method = RequestMethod.GET)
	public void orderDrugList(ModelMap model, HttpServletRequest request) {
		model = orderDrugListController.get_orderDrugList(model, request);
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderDrugList", method = RequestMethod.POST)
	public void saveOrderDrugList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		orderDrugListController.post_orderDrugList(request, response);
		redirectTo(response, "orderDrugList.list");
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderListByPatient", method = RequestMethod.GET)
	public void orderListByPatient(ModelMap model, HttpServletRequest request) {
		model = orderListByPatientController.get_orderListByPatient(model, request);
	}
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderListByPatient", method = RequestMethod.POST)
	public void saveOrderListByPatient(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		orderListByPatientController.post_orderListByPatient(request, response);
		redirectTo(response, "orderListByPatient.list");
	}
	
	private void redirectTo(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
