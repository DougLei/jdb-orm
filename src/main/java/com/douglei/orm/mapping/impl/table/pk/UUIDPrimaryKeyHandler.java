package com.douglei.orm.mapping.impl.table.pk;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class UUIDPrimaryKeyHandler implements PrimaryKeyHandler{
	
	@Override
	public String getName() {
		return "UUID";
	}
	
	@Override
	public boolean supportMultiColumns() {
		return true;
	}

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
