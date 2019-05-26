package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.LongDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class SqlServerLongDataTypeHandler extends LongDataTypeHandler{
	private SqlServerLongDataTypeHandler() {}
	private static final SqlServerLongDataTypeHandler instance = new SqlServerLongDataTypeHandler();
	public static final SqlServerLongDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.BIGINT.getSqlType();
	}
}
