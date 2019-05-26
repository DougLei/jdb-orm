package com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.dbtype.IntDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class IntResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private IntResultSetColumnDataTypeHandler() {}
	private static final IntResultSetColumnDataTypeHandler instance = new IntResultSetColumnDataTypeHandler();
	public static final IntResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			MySqlDBType.INT.getSqlType()	// int 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return IntDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
