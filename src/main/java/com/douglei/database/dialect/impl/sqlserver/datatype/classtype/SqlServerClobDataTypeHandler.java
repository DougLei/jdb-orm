package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.ClobDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class SqlServerClobDataTypeHandler extends ClobDataTypeHandler{
	private SqlServerClobDataTypeHandler() {}
	private static final SqlServerClobDataTypeHandler instance = new SqlServerClobDataTypeHandler();
	public static final SqlServerClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.TEXT.getSqlType();
	}
}
