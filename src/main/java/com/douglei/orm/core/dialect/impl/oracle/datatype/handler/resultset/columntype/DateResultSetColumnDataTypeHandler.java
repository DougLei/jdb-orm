package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Date;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype.DateDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DateResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private DateResultSetColumnDataTypeHandler() {}
	private static final DateResultSetColumnDataTypeHandler instance = new DateResultSetColumnDataTypeHandler();
	public static final DateResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Date.singleInstance().getSqlType()	// date 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return DateDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
