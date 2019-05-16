package com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class IntegerResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final int[] supportColumnTypes = {
			4	// int 
			};
	
	@Override
	public Object getValue(int columnIndex, ResultSet rs) throws SQLException {
		return rs.getInt(columnIndex);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}