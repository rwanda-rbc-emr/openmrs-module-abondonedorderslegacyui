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
package org.openmrs.module.abondonedorderslegacyui.api.impl;

import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.abondonedorderslegacyui.api.AbandonedOrdersLegacyUIService;
import org.openmrs.module.abondonedorderslegacyui.api.db.AbandonedOrdersLegacyUIDAO;

/**
 * It is a default implementation of {@link AbandonedOrdersLegacyUIService}.
 */
public class AbandonedOrdersLegacyUIServiceImpl extends BaseOpenmrsService implements AbandonedOrdersLegacyUIService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private AbandonedOrdersLegacyUIDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(AbandonedOrdersLegacyUIDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public AbandonedOrdersLegacyUIDAO getDao() {
	    return dao;
    }
    
    @Override
    public <Ord extends Order> List<Ord> getOrders(Class<Ord> orderClassType, List<Patient> patients,
	        List<Concept> concepts, List<User> orderers, List<Encounter> encounters,
	        List<OrderType> orderTypes) {
		if (orderClassType == null)
			throw new APIException(
			        "orderClassType cannot be null.  An order type of Order.class or DrugOrder.class is required");
		
		if (patients == null)
			patients = new Vector<Patient>();
		
		if (concepts == null)
			concepts = new Vector<Concept>();
		
		if (orderers == null)
			orderers = new Vector<User>();
		
		if (encounters == null)
			encounters = new Vector<Encounter>();
		
		if (orderTypes == null)
			orderTypes = new Vector<OrderType>();
		
		return dao.getOrders(orderClassType, patients, concepts, orderers, encounters, orderTypes);
	}
    
    public List<Order> getOrdersByUser(User user) throws APIException {
		if (user == null)
			throw new APIException("Unable to get orders if I am not given a user");
		
		List<User> users = new Vector<User>();
		users.add(user);
		
		return getOrders(Order.class, null, null, users, null, null);
	}

	@Override
	public List<OrderType> getAllOrderTypes(boolean includeRetired) throws DAOException {
		return dao.getAllOrderTypes(includeRetired);
	}
    
}