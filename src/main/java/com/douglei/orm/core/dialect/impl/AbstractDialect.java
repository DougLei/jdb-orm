package com.douglei.orm.core.dialect.impl;

import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.db.feature.DBFeature;
import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	protected AbstractDataTypeHandlerMapping dataTypeHandlerMapping;
	
	protected DBFeature feature;
	protected DBObjectHandler objectHandler;
	
	protected SqlHandler sqlHandler;
	protected SqlStatementHandler sqlStatementHandler;
	
	
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
	public SqlHandler getSqlHandler() {
		return sqlHandler;
	}

	@Override
	public SqlStatementHandler getSqlStatementHandler() {
		return sqlStatementHandler;
	}
}
