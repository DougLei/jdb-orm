package com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DecimalResultSetColumnDataTypeHandler2 extends ResultSetColumnDataTypeHandler{
	private static final int[] supportColumnTypes = {
			3	// decimal 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getDouble(columnIndex);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
