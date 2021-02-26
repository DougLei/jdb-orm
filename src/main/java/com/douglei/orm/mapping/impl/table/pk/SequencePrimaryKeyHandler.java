package com.douglei.orm.mapping.impl.table.pk;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.dialect.impl.oracle.object.pk.sequence.OraclePrimaryKeySequence;
import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;

/**
 * 
 * @author DougLei
 */
public class SequencePrimaryKeyHandler implements PrimaryKeyHandler{
	
	@Override
	public String getType() {
		return "SEQUENCE";
	}
	
	@Override
	public void setValue2ObjectMap(Set<String> primaryKeyColumnCodes, Map<String, Object> objectMap, Object originObject) {
		PrimaryKeySequence primaryKeySequence = (PrimaryKeySequence) originObject;
		objectMap.put(primaryKeyColumnCodes.iterator().next(), (primaryKeySequence instanceof OraclePrimaryKeySequence)?primaryKeySequence:null);
	}
}
