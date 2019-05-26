package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.DateDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class SqlServerDateDataTypeHandler extends DateDataTypeHandler{
	private SqlServerDateDataTypeHandler() {}
	private static final SqlServerDateDataTypeHandler instance = new SqlServerDateDataTypeHandler();
	public static final SqlServerDateDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.DATETIME.getSqlType();
	}
}
