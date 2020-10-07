package com.douglei.orm.dialect.impl;

import com.douglei.orm.dialect.Dialect;
import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.dialect.datatype.DataTypeContainer;
import com.douglei.orm.dialect.object.DBObjectHandler;
import com.douglei.orm.dialect.sql.SqlQueryHandler;
import com.douglei.orm.dialect.sql.SqlStatementHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	private DialectType type;
	protected DataTypeContainer dataTypeContainer = new DataTypeContainer();
	protected DBObjectHandler objectHandler;
	protected SqlStatementHandler sqlStatementHandler;
	protected SqlQueryHandler sqlQueryHandler;
	
	@Override
	public final DialectType getType() {
		return type;
	}

	@Override
	public final void setType(DialectType type) {
		this.type = type;
	}

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
