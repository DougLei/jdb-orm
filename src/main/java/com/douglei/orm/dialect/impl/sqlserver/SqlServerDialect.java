package com.douglei.orm.dialect.impl.sqlserver;

import com.douglei.orm.dialect.DatabaseType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.sqlserver.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class SqlServerDialect extends AbstractDialect{
	
	public SqlServerDialect() {
		super.sqlStatementHandler = new SqlStatementHandlerImpl();
	}

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.SQLSERVER;
	}
}
