package com.douglei.database.dialect.table;

import com.douglei.database.metadata.table.TableMetadata;

/**
 * 
 * @author DougLei
 */
public abstract class TableHandler {
	
	/**
	 * 组装create table的sql语句
	 * @param tableMetadata
	 */
	public String installCreateSqlStatement(TableMetadata tableMetadata) {
		switch(tableMetadata.getCreateMode()) {
			case CREATE:
				
				
				return null;
			default:
				return null;
		}
	}
}
