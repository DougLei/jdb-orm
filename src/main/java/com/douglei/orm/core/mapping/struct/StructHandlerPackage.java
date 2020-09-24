package com.douglei.orm.core.mapping.struct;

import java.sql.SQLException;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.mapping.struct.table.TableStructHandler;

/**
 * 
 * @author DougLei
 */
class StructHandlerPackage {
	private DataSourceWrapper dataSourceWrapper;
	
	private DBConnection connection;
	private StructHandler structHandler;
	private TableStructHandler tableStructHandler;
	
	public StructHandlerPackage(DataSourceWrapper dataSourceWrapper) {
		this.dataSourceWrapper = dataSourceWrapper;
	}
	
	private DBConnection getConnection() {
		if(connection == null)
			connection = new DBConnection(dataSourceWrapper);
		return connection;
	}

	public TableStructHandler getTableStructHandler() throws SQLException {
		if(tableStructHandler == null) {
			DBConnection connection = getConnection();
			TableSqlStatementHandler handler = EnvironmentContext.getDialect().getTableSqlStatementHandler();
			connection.init4TableStructHandler(handler.queryTableExistsSql());
			
			tableStructHandler = new TableStructHandler(connection, handler);
		}
		return tableStructHandler;
	}

	public StructHandler getStructHandler() {
		if(structHandler == null)
			structHandler = new StructHandler(getConnection());
		return structHandler;
	}

	public void destroy() {
		if(connection != null)
			connection.close();
	}
}
