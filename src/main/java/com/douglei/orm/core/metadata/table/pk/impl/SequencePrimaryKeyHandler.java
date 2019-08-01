package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;

/**
 * 
 * @author DougLei
 */
public class SequencePrimaryKeyHandler extends PrimaryKeyHandler{
	private static final long serialVersionUID = 8613215141130789619L;

	@Override
	public boolean supportProcessMultiPKColumns() {
		return false;
	}
	
	@Override
	public void setValue2EntityMap(Set<String> primaryKeyColumnCodes, TableMetadata table, Map<String, Object> entityMap, PrimaryKeySequence primaryKeySequence) {
		entityMap.put(primaryKeyColumnCodes.iterator().next(), (primaryKeySequence == null || primaryKeySequence.getNextvalSql() == null)?null:primaryKeySequence);
	}
	
	@Override
	public String getName() {
		return "sequence";
	}
}
