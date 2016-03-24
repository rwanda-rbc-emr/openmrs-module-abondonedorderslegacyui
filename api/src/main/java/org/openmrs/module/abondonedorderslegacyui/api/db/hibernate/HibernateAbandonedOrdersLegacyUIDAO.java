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
package org.openmrs.module.abondonedorderslegacyui.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.abondonedorderslegacyui.api.db.AbandonedOrdersLegacyUIDAO;

/**
 * It is a default implementation of  {@link AbandonedOrdersLegacyUIDAO}.
 */
public class HibernateAbandonedOrdersLegacyUIDAO implements AbandonedOrdersLegacyUIDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }
    
    /**
	 * Bringing this method back after being unscrewed out in 1.10.x
	 */
	public <Ord extends Order> List<Ord> getOrders(Class<Ord> orderClassType, List<Patient> patients,
	        List<Concept> concepts, List<User> orderers, List<Encounter> encounters,
	        List<OrderType> orderTypes) {
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(orderClassType);
		
		if (patients.size() > 0)
			crit.add(Expression.in("patient", patients));
		
		if (concepts.size() > 0)
			crit.add(Expression.in("concept", concepts));
		
		// we are not checking the other status's here because they are 
		// algorithm dependent  
		
		if (orderers.size() > 0)
			crit.add(Expression.in("orderer", orderers));
		
		if (encounters.size() > 0)
			crit.add(Expression.in("encounter", encounters));
		
		if (orderTypes.size() > 0)
			crit.add(Expression.in("orderType", orderTypes));
		
		return crit.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<OrderType> getAllOrderTypes(boolean includeRetired) throws DAOException {
		log.debug("getting all order types");
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(OrderType.class);
		
		if (includeRetired == false) {
			// TODO implement OrderType.retired
			crit.add(Expression.eq("retired", false));
		}
		
		return crit.list();
	}
	
}