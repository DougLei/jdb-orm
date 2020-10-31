package com.douglei.orm.dialect.impl.mysql;

import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.mysql.object.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.mysql.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.mysql.sqlhandler.SqlStatementHandlerImpl;
import com.douglei.orm.dialect.object.DBObjectHandler;
import com.douglei.orm.dialect.sqlhandler.SqlQueryHandler;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;

/**
 * 
 * @author DougLei
 */
public class MySqlDialect extends AbstractDialect{
	
	@Override
	protected DBObjectHandler createObjectHandler() {
		return new DBObjectHandlerImpl();
	}

	@Override
	protected SqlStatementHandler createSqlStatementHandler() {
		return new SqlStatementHandlerImpl();
	}

	@Override
	protected SqlQueryHandler createSqlQueryHandler(SqlStatementHandler sqlStatementHandler) {
		return new SqlQueryHandlerImpl(sqlStatementHandler);
	}
	
	@Override
	public DialectType getType() {
		return DialectType.MYSQL;
	}
}
