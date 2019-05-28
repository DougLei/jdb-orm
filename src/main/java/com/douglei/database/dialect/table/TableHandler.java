package com.douglei.database.dialect.table;

import java.util.List;

import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.database.metadata.table.TableMetadata;

/**
 * 
 * @author DougLei
 */
public abstract class TableHandler {
	
	/**
	 * 
	 * @param tableMetadata
	 */
	public TableCreator getTableCreator(TableMetadata tableMetadata) {
		switch(tableMetadata.getCreateMode()) {
			case CREATE:
				return new TableCreator();// TODO
			default:
				return null;
		}
	}
	
	/**
	 * 执行create表操作
	 * @param dataSourceWrapper
	 * @param tableCreators
	 */
	public void executeCreate(DataSourceWrapper dataSourceWrapper, List<TableCreator> tableCreators) {
		if(tableCreators == null) {
			return;
		}
		
	}
}
