package com.douglei.database.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDBType;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.dbtype.VarcharDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class StringResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private StringResultSetColumnDataTypeHandler() {}
	private static final StringResultSetColumnDataTypeHandler instance = new StringResultSetColumnDataTypeHandler();
	public static final StringResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			SqlServerDBType.NVARCHAR.getSqlType(), 	// varchar
			SqlServerDBType.NVARCHAR.getSqlType(), 	// nvarchar
			SqlServerDBType.CHAR.getSqlType(),		// char
			SqlServerDBType.NCHAR.getSqlType()		// nchar
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return VarcharDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
