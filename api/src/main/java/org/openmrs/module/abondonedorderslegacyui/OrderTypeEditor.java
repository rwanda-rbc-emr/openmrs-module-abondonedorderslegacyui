package org.openmrs.module.abondonedorderslegacyui;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.OrderType;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.springframework.util.StringUtils;

/**
 * Allows for serializing/deserializing an object to a string so that Spring knows how to pass
 * an object back and forth through an html form or other medium. <br/>
 * <br/>
 * In version 1.9, added ability for this to also retrieve objects by uuid
 * 
 * @see OrderType
 */
public class OrderTypeEditor extends PropertyEditorSupport {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public OrderTypeEditor() {
	}
	
	/**
	 * @should set using id
	 * @should set using uuid
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		OrderService os = Context.getOrderService();
		if (StringUtils.hasText(text)) {
			try {
				setValue(os.getOrderType(Integer.valueOf(text)));
			}
			catch (Exception ex) {
				OrderType orderType = os.getOrderTypeByUuid(text);
				setValue(orderType);
				if (orderType == null) {
					log.error("Error setting text: " + text, ex);
					throw new IllegalArgumentException("Order type not found: " + ex.getMessage());
				}
			}
		} else {
			setValue(null);
		}
	}
	
	public String getAsText() {
		OrderType ot = (OrderType) getValue();
		if (ot == null) {
			return "";
		} else {
			Integer orderTypeId = ot.getOrderTypeId();
			if (orderTypeId == null) {
				return "";
			} else {
				return orderTypeId.toString();
			}
		}
	}
	
}
