package com.douglei.orm.mapping.impl.table.metadata.pk.impl;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.dialect.impl.oracle.db.object.pk.sequence.OraclePrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.pk.PrimaryKeyHandler;

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
		objectMap.put(primaryKeyColumnCodes.iterator().next(), (primaryKeySequence instanceof OraclePrimaryKeySequence)?primaryKeySequence:null);
	}
}
