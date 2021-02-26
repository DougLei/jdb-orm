package com.douglei.orm.dialect.impl.sqlserver;

import com.douglei.orm.dialect.DatabaseType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.sqlserver.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.sqlserver.sqlhandler.SqlStatementHandlerImpl;
import com.douglei.orm.dialect.sqlhandler.SqlQueryHandler;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;

/**
 * 
 * @author DougLei
 */
public class SqlServerDialect extends AbstractDialect{
	
	@Override
	protected SqlStatementHandler createSqlStatementHandler() {
		return new SqlStatementHandlerImpl();
	}

	@Override
	protected SqlQueryHandler createSqlQueryHandler(SqlStatementHandler sqlStatementHandler) {
		return new SqlQueryHandlerImpl(sqlStatementHandler);
	}
	
	@Override
	public DatabaseType getType() {
		return DatabaseType.SQLSERVER;
	}
}
