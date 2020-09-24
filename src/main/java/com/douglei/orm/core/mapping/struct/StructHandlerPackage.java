package com.douglei.orm.core.mapping.struct;

import java.sql.SQLException;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;
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
	
	private DBConnection getConnection(SqlStatementHandler handler) {
		if(connection == null)
			connection = new DBConnection(dataSourceWrapper, handler);
		return connection;
	}

	public TableStructHandler getTableStructHandler() throws SQLException {
		if(tableStructHandler == null) 
			tableStructHandler = new TableStructHandler(getConnection(EnvironmentContext.getDialect().getSqlStatementHandler()));
		return tableStructHandler;
	}

	public ViewStructHandler getViewStructHandler() {
		if(viewStructHandler == null) 
			viewStructHandler = new ViewStructHandler(getConnection(EnvironmentContext.getDialect().getSqlStatementHandler()));
		return viewStructHandler;
	}
	
	public ProcStructHandler getProcStructHandler() {
		if(procStructHandler == null) 
			procStructHandler = new ProcStructHandler(getConnection(EnvironmentContext.getDialect().getSqlStatementHandler()));
		return procStructHandler;
	}

	public void destroy() {
		if(connection != null)
			connection.close();
	}
}
