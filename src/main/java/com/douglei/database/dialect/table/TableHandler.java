package com.douglei.database.dialect.table;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.utils.CloseUtil;

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
			case NONE:
				return null;
			default:
				return new TableCreator(tableMetadata);
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
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			
			
			
			
		}catch (TableCreateException e) {
			// TODO 回滚
			
			
			
		} finally {
			CloseUtil.closeDBConn(statement, connection);
		}
	}
}
