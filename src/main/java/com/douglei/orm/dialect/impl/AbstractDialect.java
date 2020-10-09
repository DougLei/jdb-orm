package com.douglei.orm.dialect.impl;

import com.douglei.orm.dialect.Dialect;
import com.douglei.orm.dialect.datatype.DataTypeContainer;
import com.douglei.orm.dialect.object.DBObjectHandler;
import com.douglei.orm.dialect.sqlhandler.SqlQueryHandler;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	protected DataTypeContainer dataTypeContainer = new DataTypeContainer();
	protected DBObjectHandler objectHandler;
	protected SqlStatementHandler sqlStatementHandler;
	protected SqlQueryHandler sqlQueryHandler;
	
	@Override
	public final DataTypeContainer getDataTypeContainer() {
		return dataTypeContainer;
	}
	
	@Override
	public final DBObjectHandler getObjectHandler() {
		return objectHandler;
	}

	@Override
	public final SqlStatementHandler getSqlStatementHandler() {
		return sqlStatementHandler;
	}

	@Override
	public final SqlQueryHandler getSqlQueryHandler() {
		return sqlQueryHandler;
	}
}
