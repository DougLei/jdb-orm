package com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
class DatetimeResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private DatetimeResultSetColumnDataTypeHandler() {}
	private static final DatetimeResultSetColumnDataTypeHandler instance = new DatetimeResultSetColumnDataTypeHandler();
	public static final DatetimeResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			SqlServerDBType.DATETIME.getSqlType()	// datetime 
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
