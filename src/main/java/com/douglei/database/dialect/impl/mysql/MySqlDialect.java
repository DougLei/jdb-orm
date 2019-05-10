package com.douglei.database.dialect.impl.mysql;

import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.mysql.datatype.DataTypeHandlerMapping;
import com.douglei.database.dialect.impl.mysql.sql.SqlHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class MySqlDialect extends AbstractDialect{
	public static final String DATABASE_CODE = "MYSQL";
	
	@Override
	protected void initialize() {
		super.sqlHandler = new SqlHandlerImpl();
		super.dataTypeHandlerMapping = new DataTypeHandlerMapping();
	}
}
