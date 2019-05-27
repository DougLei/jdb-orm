package com.douglei.database.dialect.impl.mysql.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.handler.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.handler.dbtype.IntDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class IntResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
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
