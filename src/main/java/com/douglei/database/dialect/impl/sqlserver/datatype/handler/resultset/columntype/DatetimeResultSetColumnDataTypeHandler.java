package com.douglei.database.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.Datetime;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.dbtype.DatetimeDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DatetimeResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private DatetimeResultSetColumnDataTypeHandler() {}
	private static final DatetimeResultSetColumnDataTypeHandler instance = new DatetimeResultSetColumnDataTypeHandler();
	public static final DatetimeResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Datetime.singleInstance().getSqlType()	// datetime 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return DatetimeDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
