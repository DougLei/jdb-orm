package com.douglei.database.dialect.impl.oracle.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class NumberResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final int[] supportColumnTypes = {
			2	// number 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		if(rs.getMetaData().getScale(columnIndex) == 0) {
			long value = rs.getLong(columnIndex);
			if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
				return (int)value;
			}
			return value;
		}
		return rs.getDouble(columnIndex);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
