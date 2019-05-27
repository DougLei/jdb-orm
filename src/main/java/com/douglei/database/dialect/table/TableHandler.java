package com.douglei.database.dialect.table;

import com.douglei.database.metadata.table.TableMetadata;

/**
 * 
 * @author DougLei
 */
public abstract class TableHandler {
	
	/**
	 * 执行create
	 * @param tableMetadata
	 */
	public void executeCreate(TableMetadata tableMetadata) {
		switch(tableMetadata.getCreateMode()) {
			case NONE:
				return;
			case CREATE:
				
				
				
				
				return;
		}
	}
}
