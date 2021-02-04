package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.mapping.handler.object.procedure.ProcedureObjectHandler;
import com.douglei.orm.mapping.handler.object.table.TableObjectHandler;
import com.douglei.orm.mapping.handler.object.view.ViewObjectHandler;

/**
 * 
 * @author DougLei
 */
class ObjectHandlerPackage {
	private DataSourceWrapper dataSourceWrapper;
	
	private DBConnection connection;
	
	private TableObjectHandler tableObjectHandler;
	private ViewObjectHandler viewObjectHandler;
	private ProcedureObjectHandler procedureObjectHandler;
	
	public ObjectHandlerPackage(DataSourceWrapper dataSourceWrapper) {
		this.dataSourceWrapper = dataSourceWrapper;
	}
	
	private DBConnection getConnection() {
		if(connection == null)
			connection = new DBConnection(dataSourceWrapper, EnvironmentContext.getDialect().getSqlStatementHandler(), EnvironmentContext.getDialect().getSqlQueryHandler());
		return connection;
	}

	public TableObjectHandler getTableObjectHandler() throws SQLException {
		if(tableObjectHandler == null) 
			tableObjectHandler = new TableObjectHandler(getConnection());
		return tableObjectHandler;
	}

	public ViewObjectHandler getViewObjectHandler() {
		if(viewObjectHandler == null) 
			viewObjectHandler = new ViewObjectHandler(getConnection());
		return viewObjectHandler;
	}
	
	public ProcedureObjectHandler getProcedureObjectHandler() {
		if(procedureObjectHandler == null) 
			procedureObjectHandler = new ProcedureObjectHandler(getConnection());
		return procedureObjectHandler;
	}

	public void destroy() {
		if(connection != null)
			connection.close();
	}
}
