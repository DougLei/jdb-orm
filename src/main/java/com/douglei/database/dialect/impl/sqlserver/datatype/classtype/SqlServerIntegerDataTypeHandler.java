package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.IntegerDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class SqlServerIntegerDataTypeHandler extends IntegerDataTypeHandler{
	private SqlServerIntegerDataTypeHandler() {}
	private static final SqlServerIntegerDataTypeHandler instance = new SqlServerIntegerDataTypeHandler();
	public static final SqlServerIntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.INT.getSqlType();
	}
}
