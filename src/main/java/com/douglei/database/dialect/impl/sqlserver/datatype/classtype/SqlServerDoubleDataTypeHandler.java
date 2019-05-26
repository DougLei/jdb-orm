package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.DoubleDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class SqlServerDoubleDataTypeHandler extends DoubleDataTypeHandler{
	private SqlServerDoubleDataTypeHandler() {}
	private static final SqlServerDoubleDataTypeHandler instance = new SqlServerDoubleDataTypeHandler();
	public static final SqlServerDoubleDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.DECIMAL.getSqlType();
	}
}
