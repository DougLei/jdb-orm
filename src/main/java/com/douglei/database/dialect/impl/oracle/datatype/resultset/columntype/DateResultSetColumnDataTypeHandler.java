package com.douglei.database.dialect.impl.oracle.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.dbtype.DateDBDataTypeHandler;

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
			OracleDBType.DATE.getSqlType()	// date 
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
