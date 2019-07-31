package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeySequence;

/**
 * 
 * @author DougLei
 */
public class SequencePrimaryKeyHandler extends PrimaryKeyHandler{
	
	@Override
	public boolean supportProcessMultiPKColumns() {
		return false;
	}
	
	@Override
	public boolean setValue2EntityMap(String code, ColumnMetadata column, Map<String, ColumnMetadata> primaryKeyColumns, TableMetadata table, Map<String, Object> entityMap, PrimaryKeySequence primaryKeySequence) {
		entityMap.put(code, primaryKeySequence);
		return true;
	}
	
	@Override
	public String getName() {
		return "sequence";
	}
}
