package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.tools.utils.IdentityUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class UUID36PrimaryKeyHandler implements PrimaryKeyHandler{
	private static final long serialVersionUID = 8524983915983303051L;

	@Override
	public void setValue2ObjectMap(Set<String> primaryKeyColumnCodes, Map<String, Object> objectMap, Object originObject) {
		String uuid;
		for (String code : primaryKeyColumnCodes) {
			if(objectMap.get(code) == null) {
				uuid = IdentityUtil.getUUID();
				objectMap.put(code, uuid);
				IntrospectorUtil.setProperyValue(originObject, code, uuid);
			}
		}
	}
}
