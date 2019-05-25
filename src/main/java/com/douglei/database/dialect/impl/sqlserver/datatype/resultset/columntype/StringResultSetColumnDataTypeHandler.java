package com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class StringResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final int[] supportColumnTypes = {
			12, 	// varchar
			-9, 	// nvarchar
			1,		// char
			-15		// nchar
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getString(columnIndex);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
