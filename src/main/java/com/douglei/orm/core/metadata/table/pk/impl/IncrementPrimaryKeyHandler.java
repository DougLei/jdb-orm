package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeySequence;

/**
 * 
 * @author DougLei
 */
public class IncrementPrimaryKeyHandler extends PrimaryKeyHandler{
	private PrimaryKeySequence primaryKeySequence;
	
	@Override
	public void setValue2EntityMap(String code, ColumnMetadata column, Map<String, Object> entityMap) {
		entityMap.put(code, primaryKeySequence);
	}
	
	/**
	 * 设置主键序列
	 * @param primaryKeySequence
	 */
	public void setPrimaryKeySequence(PrimaryKeySequence primaryKeySequence) {
		this.primaryKeySequence = primaryKeySequence;
	}
}
