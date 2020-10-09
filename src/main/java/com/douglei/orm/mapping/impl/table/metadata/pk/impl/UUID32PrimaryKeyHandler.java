package com.douglei.orm.mapping.impl.table.metadata.pk.impl;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.mapping.impl.table.metadata.pk.PrimaryKeyHandler;
import com.douglei.tools.utils.IdentityUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class UUID32PrimaryKeyHandler implements PrimaryKeyHandler{
	private static final long serialVersionUID = -3031122238010386270L;

	@Override
	public void setValue2ObjectMap(Set<String> primaryKeyColumnCodes, Map<String, Object> objectMap, Object originObject) {
		String uuid;
		for (String code : primaryKeyColumnCodes) {
			if(objectMap.get(code) == null) {
				uuid = IdentityUtil.get32UUID();
				objectMap.put(code, uuid);
				IntrospectorUtil.setProperyValue(originObject, code, uuid);
			}
		}
	}
}
