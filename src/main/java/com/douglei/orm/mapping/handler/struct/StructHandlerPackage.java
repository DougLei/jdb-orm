package com.douglei.orm.mapping.handler.struct;

import java.sql.SQLException;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.environment.datasource.DataSourceWrapper;
import com.douglei.orm.mapping.handler.struct.procedure.ProcedureStructHandler;
import com.douglei.orm.mapping.handler.struct.table.TableStructHandler;
import com.douglei.orm.mapping.handler.struct.view.ViewStructHandler;

/**
 * 
 * @author DougLei
 */
class StructHandlerPackage {
	private DataSourceWrapper dataSourceWrapper;
	
	private DBConnection connection;
	
	private TableStructHandler tableStructHandler;
	private ViewStructHandler viewStructHandler;
	private ProcedureStructHandler procStructHandler;
	
	public StructHandlerPackage(DataSourceWrapper dataSourceWrapper) {
		this.dataSourceWrapper = dataSourceWrapper;
	}
	
	private DBConnection getConnection() {
		if(connection == null)
			connection = new DBConnection(dataSourceWrapper, EnvironmentContext.getDialect().getSqlStatementHandler(), EnvironmentContext.getDialect().getSqlQueryHandler());
		return connection;
	}

	public TableStructHandler getTableStructHandler() throws SQLException {
		if(tableStructHandler == null) 
			tableStructHandler = new TableStructHandler(getConnection());
		return tableStructHandler;
	}

	public ViewStructHandler getViewStructHandler() {
		if(viewStructHandler == null) 
			viewStructHandler = new ViewStructHandler(getConnection());
		return viewStructHandler;
	}
	
	public ProcedureStructHandler getProcStructHandler() {
		if(procStructHandler == null) 
			procStructHandler = new ProcedureStructHandler(getConnection());
		return procStructHandler;
	}

	public void destroy() {
		if(connection != null)
			connection.close();
	}
}
