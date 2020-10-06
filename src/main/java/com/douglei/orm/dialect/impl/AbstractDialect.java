package com.douglei.orm.dialect.impl;

import com.douglei.orm.dialect.Dialect;
import com.douglei.orm.dialect.feature.DBFeature;
import com.douglei.orm.dialect.object.DBObjectHandler;
import com.douglei.orm.dialect.sql.SqlQueryHandler;
import com.douglei.orm.dialect.sql.SqlStatementHandler;
import com.douglei.orm.dialect.temp.datatype.handler.AbstractDataTypeHandlerMapping;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	protected AbstractDataTypeHandlerMapping dataTypeHandlerMapping;
	
	protected DBFeature feature;
	protected DBObjectHandler objectHandler;
	
	protected SqlStatementHandler sqlStatementHandler;
	protected SqlQueryHandler sqlQueryHandler;
	
	@Override
	public AbstractDataTypeHandlerMapping getDataTypeHandlerMapping() {
		return dataTypeHandlerMapping;
	}
	
	@Override
	public DBFeature getFeature() {
		return feature;
	}
	
	@Override
	public DBObjectHandler getObjectHandler() {
		return objectHandler;
	}

	@Override
	public SqlStatementHandler getSqlStatementHandler() {
		return sqlStatementHandler;
	}

	@Override
	public SqlQueryHandler getSqlQueryHandler() {
		return sqlQueryHandler;
	}
}
