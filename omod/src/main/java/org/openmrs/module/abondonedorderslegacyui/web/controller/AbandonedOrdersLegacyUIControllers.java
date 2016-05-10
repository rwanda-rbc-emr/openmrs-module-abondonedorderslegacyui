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
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderFrequenciesController;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderTypeFormController;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OrderTypeListController;
import org.openmrs.module.abondonedorderslegacyui.web.controller.order.OtherDrugOrderAttributesFormController;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller, connects to the rest of the controllers.
 */
@Controller
public class AbandonedOrdersLegacyUIControllers {

	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeList", method = RequestMethod.GET)
	public void orderTypeList(ModelMap model) {
		OrderTypeListController orderTypeListController = new OrderTypeListController();
		
		model = orderTypeListController.get_orderTypeList(model);
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeList", method = RequestMethod.POST)
	public void saveOrderTypeList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		OrderTypeListController orderTypeListController = new OrderTypeListController();
		
		orderTypeListController.post_orderTypeList(request, response);
		redirectTo(response, "orderTypeList.list");
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeForm", method = RequestMethod.GET)
	public void orderTypeForm(ModelMap model, HttpServletRequest request) {
		OrderTypeFormController orderTypeFormController = new OrderTypeFormController();
		
		model = orderTypeFormController.get_orderTypeForm(model, request);
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/orderTypeForm", method = RequestMethod.POST)
	public void postOrderTypeForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		OrderTypeFormController orderTypeFormController = new OrderTypeFormController();
		
		if (request.getParameter("formAction").equals("Save")) {
			orderTypeFormController.post_orderTypeForm(request, response);
			redirectTo(response, "orderTypeList.list");
		} else if (request.getParameter("formAction").equals("Delete")) {
			orderTypeFormController.delete_orderTypeForm(request, response);
			redirectTo(response, "orderTypeList.list");
		}
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/orderFrequencyList", method = RequestMethod.GET)
	public void orderFrequenciesList(ModelMap model) {
		OrderFrequenciesController orderFrequenciesController = new OrderFrequenciesController();
		
		model = orderFrequenciesController.listAllOrderFrequencies(model);
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/orderFrequencyList", method = RequestMethod.POST)
	public void deleteFromOrderFrequenciesList(HttpServletRequest request, HttpServletResponse response) {
		String[] orderFrequencyIdsList = request.getParameterValues("orderFrequencyId");
		OrderFrequenciesController orderFrequenciesController = new OrderFrequenciesController();
		
		orderFrequenciesController.deleteSelectedOrderFrequencies(orderFrequencyIdsList);
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,
				"Successfully deleted Selected Order Frequencies");
		redirectTo(response, "orderFrequencyList.list");
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/orderFrequencyForm", method = RequestMethod.GET)
	public void getOrderFrequenciesForm(ModelMap model, HttpServletRequest request) {
		OrderFrequenciesController orderFrequenciesController = new OrderFrequenciesController();
		
		model = orderFrequenciesController.getOrderFrequencyForm(model, request);
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/orderFrequencyForm", method = RequestMethod.POST)
	public void postOrderFrequenciesForm(HttpServletRequest request, HttpServletResponse response) {
		OrderFrequenciesController orderFrequenciesController = new OrderFrequenciesController();
		
		orderFrequenciesController.postOrderFrequencyForm(request, response);
		redirectTo(response, "orderFrequencyList.list");
	}

	private void redirectTo(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/otherDrugOrderAttributesForm", method = RequestMethod.GET)
	public void renderManageOtherDrugOrderAttributesPage(ModelMap model) {
		OtherDrugOrderAttributesFormController otherDrugOrderAttributesFormController = new OtherDrugOrderAttributesFormController();
		
		otherDrugOrderAttributesFormController.renderManageOtherDrugOrderAttributesPage(model);
	}

	@RequestMapping(value = "/module/abondonedorderslegacyui/otherDrugOrderAttributesForm", method = RequestMethod.POST)
	public void submitManageOtherDrugOrderAttributesPage(HttpServletRequest request, HttpServletResponse response) {
		OtherDrugOrderAttributesFormController otherDrugOrderAttributesFormController = new OtherDrugOrderAttributesFormController();
		
		otherDrugOrderAttributesFormController.postManageOtherDrugOrderAttributesPage(request);
		redirectTo(response, "otherDrugOrderAttributesForm.form");
	}
}
