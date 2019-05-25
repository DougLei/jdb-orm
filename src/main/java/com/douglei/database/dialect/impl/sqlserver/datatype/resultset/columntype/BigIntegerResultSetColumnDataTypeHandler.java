package com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BigIntegerResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final int[] supportColumnTypes = {
			-5	// bigint 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getLong(columnIndex);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
