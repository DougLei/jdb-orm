package com.douglei.orm.mapping.handler.object;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.configuration.environment.datasource.DataSourceEntity;

/**
 * 
 * @author DougLei
 */
class DBObjectHandlers {
	private DataSourceEntity dataSource;
	private DBConnection connection;
	
	private TableObjectHandler tableObjectHandler;
	private ViewObjectHandler viewObjectHandler;
	private ProcedureObjectHandler procedureObjectHandler;
	
	public DBObjectHandlers(DataSourceEntity dataSource) {
		this.dataSource = dataSource;
	}
	
	// 获取连接实例
	private DBConnection getDBConnection() {
		if(connection == null)
			connection = new DBConnection(dataSource, EnvironmentContext.getEnvironment().getDialect().getSqlStatementHandler());
		return connection;
	}

	/**
	 * 获取表对象处理器
	 * @return
	 */
	public TableObjectHandler getTableObjectHandler() {
		if(tableObjectHandler == null) 
			tableObjectHandler = new TableObjectHandler(getDBConnection());
		return tableObjectHandler;
	}

	/**
	 * 获取视图对象处理器
	 * @return
	 */
	public ViewObjectHandler getViewObjectHandler() {
		if(viewObjectHandler == null) 
			viewObjectHandler = new ViewObjectHandler(getDBConnection());
		return viewObjectHandler;
	}
	
	/**
	 * 获取存储过程对象处理器
	 * @return
	 */
	public ProcedureObjectHandler getProcedureObjectHandler() {
		if(procedureObjectHandler == null) 
			procedureObjectHandler = new ProcedureObjectHandler(getDBConnection());
		return procedureObjectHandler;
	}

	/**
	 * 销毁
	 */
	public void destroy() {
		if(connection != null) {
			connection.close();
			connection = null;
		}
	}
}
