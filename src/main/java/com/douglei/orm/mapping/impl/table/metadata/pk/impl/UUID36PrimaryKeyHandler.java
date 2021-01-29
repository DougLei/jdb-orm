package com.douglei.orm.mapping.impl.table.metadata.pk.impl;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.douglei.orm.mapping.impl.table.metadata.pk.PrimaryKeyHandler;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class UUID36PrimaryKeyHandler implements PrimaryKeyHandler{

	@Override
	public void setValue2ObjectMap(Set<String> primaryKeyColumnCodes, Map<String, Object> objectMap, Object originObject) {
		String uuid;
		for (String code : primaryKeyColumnCodes) {
			if(objectMap.get(code) == null) {
				uuid = UUID.randomUUID().toString();
				objectMap.put(code, uuid);
				IntrospectorUtil.setProperyValue(originObject, code, uuid);
			}
		}
	}
}
