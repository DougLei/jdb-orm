package com.douglei.database.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.handler.dbtype.Varchar2DBDataTypeHandler;

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
			OracleDBType.VARCHAR2.getSqlType(), 	// varchar2 
			OracleDBType.NVARCHAR2.getSqlType(), 	// nvarchar2
			OracleDBType.CHAR.getSqlType(),		// char
			OracleDBType.NCHAR.getSqlType()		// nchar
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return Varchar2DBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
