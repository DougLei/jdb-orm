package com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
class DecimalResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private DecimalResultSetColumnDataTypeHandler() {}
	private static final DecimalResultSetColumnDataTypeHandler instance = new DecimalResultSetColumnDataTypeHandler();
	public static final DecimalResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			MySqlDBType.DECIMAL.getSqlType()	// decimal 
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
