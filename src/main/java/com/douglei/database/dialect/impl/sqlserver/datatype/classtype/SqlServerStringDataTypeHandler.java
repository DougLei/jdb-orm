package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.StringDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class SqlServerStringDataTypeHandler extends StringDataTypeHandler{
	private SqlServerStringDataTypeHandler() {}
	private static final SqlServerStringDataTypeHandler instance = new SqlServerStringDataTypeHandler();
	public static final SqlServerStringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.VARCHAR.getSqlType();
	}
}
