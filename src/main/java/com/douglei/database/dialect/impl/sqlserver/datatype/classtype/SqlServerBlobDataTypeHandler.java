package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.BlobDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class SqlServerBlobDataTypeHandler extends BlobDataTypeHandler{
	private SqlServerBlobDataTypeHandler() {}
	private static final SqlServerBlobDataTypeHandler instance = new SqlServerBlobDataTypeHandler();
	public static final SqlServerBlobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.VARBINARY.getSqlType();
	}
}
