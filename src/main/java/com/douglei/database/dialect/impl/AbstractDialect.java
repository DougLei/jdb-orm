package com.douglei.database.dialect.impl;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.sql.SqlHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	protected SqlHandler sqlHandler;
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
	public SqlHandler getSqlHandler() {
		return sqlHandler;
	}

	@Override
	public AbstractDataTypeHandlerMapping getDataTypeHandlerMapping() {
		return dataTypeHandlerMapping;
	}
}
