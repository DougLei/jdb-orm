package com.douglei.orm.core.mapping.struct;

import java.sql.SQLException;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.mapping.struct.proc.ProcStructHandler;
import com.douglei.orm.core.mapping.struct.table.TableStructHandler;
import com.douglei.orm.core.mapping.struct.view.ViewStructHandler;

/**
 * 
 * @author DougLei
 */
class StructHandlerPackage {
	private DataSourceWrapper dataSourceWrapper;
	
	private DBConnection connection;
	private TableStructHandler tableStructHandler;
	private ViewStructHandler viewStructHandler;
	private ProcStructHandler procStructHandler;
	
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

	public ViewStructHandler getViewStructHandler() {
		if(viewStructHandler == null)
			viewStructHandler = new ViewStructHandler(getConnection());
		return viewStructHandler;
	}
	
	public ProcStructHandler getProcStructHandler() {
		if(procStructHandler == null)
			procStructHandler = new ProcStructHandler(getConnection());
		return procStructHandler;
	}

	public void destroy() {
		if(connection != null)
			connection.close();
	}
}
