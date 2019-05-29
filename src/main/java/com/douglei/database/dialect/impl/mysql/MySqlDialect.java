package com.douglei.database.dialect.impl.mysql;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.mysql.datatype.handler.MySqlDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.mysql.db.objectname.DBObjectNameHandlerImpl;
import com.douglei.database.dialect.impl.mysql.db.procedure.ProcedureHandlerImpl;
import com.douglei.database.dialect.impl.mysql.db.sql.SqlHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class MySqlDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.MYSQL;
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}
	
	@Override
	protected void initialize() {
		super.sqlHandler = new SqlHandlerImpl();
		super.dbObjectNameHandler = new DBObjectNameHandlerImpl();
		super.procedureHandler = new ProcedureHandlerImpl();
		super.dataTypeHandlerMapping = new MySqlDataTypeHandlerMapping();
	}
}
