package com.douglei.database.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.Number;
import com.douglei.database.dialect.impl.oracle.datatype.handler.dbtype.NumberDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NumberResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private NumberResultSetColumnDataTypeHandler() {}
	private static final NumberResultSetColumnDataTypeHandler instance = new NumberResultSetColumnDataTypeHandler();
	public static final NumberResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Number.singleInstance().getSqlType()	// number 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return NumberDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
