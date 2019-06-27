package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Number;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype.NumberDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NumberResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = -8353064359719387554L;
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
