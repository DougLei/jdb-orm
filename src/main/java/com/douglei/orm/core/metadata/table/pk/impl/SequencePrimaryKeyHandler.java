package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;

/**
 * 
 * @author DougLei
 */
public class SequencePrimaryKeyHandler implements PrimaryKeyHandler{
	private static final long serialVersionUID = 7575709557481341033L;

	@Override
	public boolean supportProcessMultiPKColumns() {
		return false;
	}
	
	@Override
	public void setValue2ObjectMap(Set<String> primaryKeyColumnCodes, Map<String, Object> objectMap, Object originObject) {
		PrimaryKeySequence primaryKeySequence = (PrimaryKeySequence) originObject;
		objectMap.put(primaryKeyColumnCodes.iterator().next(), primaryKeySequence.unuse()?null:primaryKeySequence);
	}
}
