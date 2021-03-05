package com.douglei.orm.dialect.impl.oracle;

import com.douglei.orm.dialect.DatabaseType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.oracle.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class OracleDialect extends AbstractDialect{

	public OracleDialect() {
		super.sqlStatementHandler = new SqlStatementHandlerImpl();
	}

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.ORACLE;
	}
}
