package com.douglei.orm.dialect.impl.mysql;

import com.douglei.orm.dialect.DatabaseType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.mysql.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class MySqlDialect extends AbstractDialect{

	public MySqlDialect() {
		super.sqlStatementHandler = new SqlStatementHandlerImpl();
	}

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.MYSQL;
	}
}
