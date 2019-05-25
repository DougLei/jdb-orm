package com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DateResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final int[] supportColumnTypes = {
			93	// datetime 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getTimestamp(columnIndex);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
