package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.UniqueConstraint;

/**
 * 
 * @author DougLei
 */
public class UniqueValue {
	private Object uniqueValue;
	
	public UniqueValue(Map<String, Object> propertyMap, UniqueConstraint uniqueConstraint) {
		if(uniqueConstraint.isMultiColumns()) {
			List<Object> uniqueValues = new ArrayList<Object>(uniqueConstraint.size());
			uniqueConstraint.getCodes().forEach(code -> uniqueValues.add(propertyMap.get(code)));
			uniqueValue = uniqueValues;
			return;
		}
		uniqueValue = propertyMap.get(uniqueConstraint.getCode());
	}

	@Override
	public boolean equals(Object obj) {
		return uniqueValue.equals(((UniqueValue) obj).uniqueValue);
	}

	public Object getValue() {
		return uniqueValue;
	}
}
