package org.openmrs.module.abondonedorderslegacyui.web.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.OrderFrequency;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohorderentrybridge.MoHOrderFrequency;
import org.openmrs.web.WebConstants;
import org.springframework.ui.ModelMap;

public class OrderFrequenciesController {

	public ModelMap listAllOrderFrequencies(ModelMap model) {
		List<MoHOrderFrequency> mohOrderFreqs = new ArrayList<MoHOrderFrequency>();
		List<OrderFrequency> orderFreqs = Context.getOrderService().getOrderFrequencies(false);

		for (OrderFrequency of : orderFreqs) {
			mohOrderFreqs.add(new MoHOrderFrequency(of));
		}
		model.addAttribute("orderFrequencies", mohOrderFreqs);

		return model;
	}

	public ModelMap getOrderFrequencyForm(ModelMap model, HttpServletRequest request) {
		String orderFrequencyId = request.getParameter("orderFrequencyId");

		if (StringUtils.isNotBlank(orderFrequencyId)
				&& Context.getOrderService().getOrderFrequency(Integer.parseInt(orderFrequencyId)) != null) {
			model.addAttribute("orderFrequency", new MoHOrderFrequency(
					Context.getOrderService().getOrderFrequency(Integer.parseInt(orderFrequencyId))));
		}
		return model;
	}

	public void postOrderFrequencyForm(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("formAction").equals("Save")) {
			if (StringUtils.isBlank(request.getParameter("name"))
					&& StringUtils.isBlank(request.getParameter("textName"))) {
				request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR,
						"Name which should be a concept of class Frequency or Plain Text is required");
			} else {
				Integer orderFrequencyId = StringUtils.isNotBlank(request.getParameter("orderFrequencyId"))
						? Integer.parseInt(request.getParameter("orderFrequencyId")) : null;
				OrderFrequency orderFrequency;

				if (orderFrequencyId == null || Context.getOrderService().getOrderFrequency(orderFrequencyId) == null) {
					orderFrequency = new OrderFrequency();

					orderFrequency.setCreator(Context.getAuthenticatedUser());
					orderFrequency.setDateCreated(new Date());
				} else {
					orderFrequency = Context.getOrderService().getOrderFrequency(orderFrequencyId);
				}
				orderFrequency.setDescription(request.getParameter("description"));
				orderFrequency.setFrequencyPerDay(StringUtils.isNotBlank(request.getParameter("frequencyPerDay"))
						? Double.parseDouble(request.getParameter("frequencyPerDay")) : null);

				Concept concept = StringUtils.isNotBlank(request.getParameter("name"))
						&& Context.getConceptService()
								.getConcept(Integer.parseInt(request.getParameter("name"))) != null
						&& Context.getConceptService().getConcept(Integer.parseInt(request.getParameter("name")))
								.getConceptClass().getName().equals("Frequency")
										? Context.getConceptService()
												.getConcept(Integer.parseInt(request.getParameter("name")))
										: createOrderFrequencyConcept(request.getParameter("textName"));

				orderFrequency.setConcept(concept);

				try {
					Context.getOrderService().saveOrderFrequency(orderFrequency);
					request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "You have successfully Saved: " + concept.getName().getName() + " Order Frequency");
				} catch (Exception e) {
					request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, e.getMessage());
				}
			}
		} else if (request.getParameter("formAction").equals("Delete")) {
			deleteOrderFrequency(request, response);
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "You have successfully Deleted The Order Frequency");
		}
	}

	public void deleteOrderFrequency(HttpServletRequest request, HttpServletResponse response) {
		Integer orderFrequencyId = StringUtils.isNotBlank(request.getParameter("orderFrequencyId"))
				? Integer.parseInt(request.getParameter("orderFrequencyId")) : null;

		if (orderFrequencyId == null || Context.getOrderService().getOrderFrequency(orderFrequencyId) == null) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR,
					"The Order Frequency You are trying to delete doesn't exist");
		} else {
			Context.getOrderService()
					.purgeOrderFrequency((Context.getOrderService().getOrderFrequency(orderFrequencyId)));
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Successfully deleted Order Frequency");
		}
	}

	private Concept createOrderFrequencyConcept(String name) {
		Concept concept = new Concept();
		ConceptName cn = new ConceptName(name, Context.getLocale());

		cn.setDateCreated(new Date());
		cn.setCreator(Context.getAuthenticatedUser());
		concept.addName(cn);
		concept.setDatatype(Context.getConceptService().getConceptDatatypeByName("N/A"));
		concept.setConceptClass(Context.getConceptService().getConceptClassByName("Frequency"));
		concept.setCreator(Context.getAuthenticatedUser());
		concept.setRetired(false);

		return Context.getConceptService().saveConcept(concept);
	}

	public void deleteSelectedOrderFrequencies(String[] orderFrequencyIdsList) {
		if (orderFrequencyIdsList != null && orderFrequencyIdsList.length > 0) {
			for (int i = 0; i < orderFrequencyIdsList.length; i++) {
				OrderFrequency oFreq = Context.getOrderService()
						.getOrderFrequency(Integer.parseInt(orderFrequencyIdsList[i]));

				Context.getOrderService().purgeOrderFrequency(oFreq);
			}
		}
	}
}
