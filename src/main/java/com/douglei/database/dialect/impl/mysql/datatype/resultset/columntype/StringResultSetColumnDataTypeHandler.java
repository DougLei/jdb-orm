package com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.dbtype.VarcharDBDataTypeHandler;

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
			MySqlDBType.VARCHAR.getSqlType(), 	// varchar
			MySqlDBType.CHAR.getSqlType(),		// char
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
