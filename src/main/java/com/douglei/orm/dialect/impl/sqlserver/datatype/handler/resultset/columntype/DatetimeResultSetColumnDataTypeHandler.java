package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Datetime;
import com.douglei.orm.dialect.impl.sqlserver.datatype.handler.dbtype.DatetimeDBDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DatetimeResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = 7311496047520564742L;
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
