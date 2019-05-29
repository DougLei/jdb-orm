package com.douglei.database.dialect.impl;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.db.features.FeaturesHolder;
import com.douglei.database.dialect.db.objectname.DBObjectNameHandler;
import com.douglei.database.dialect.db.sql.SqlHandler;
import com.douglei.database.dialect.db.table.TableHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	protected static final TableHandler tableHandler = new TableHandler();
	protected SqlHandler sqlHandler;
	protected DBObjectNameHandler dbObjectNameHandler;
	protected FeaturesHolder procedureHandler;
	protected AbstractDataTypeHandlerMapping dataTypeHandlerMapping;
	
	private boolean isInitialized;
	public AbstractDialect() {
		if(!isInitialized) {
			initialize();
			isInitialized = true;
		}
	}
	
	/**
	 * 初始化
	 */
	protected abstract void initialize();
	
	@Override
	public TableHandler getTableHandler() {
		return tableHandler;
	}
	
	@Override
	public SqlHandler getSqlHandler() {
		return sqlHandler;
	}
	
	@Override
	public DBObjectNameHandler getDBObjectNameHandler() {
		return dbObjectNameHandler;
	}

	@Override
	public FeaturesHolder getProcedureHandler() {
		return procedureHandler;
	}

	@Override
	public AbstractDataTypeHandlerMapping getDataTypeHandlerMapping() {
		return dataTypeHandlerMapping;
	}
}
