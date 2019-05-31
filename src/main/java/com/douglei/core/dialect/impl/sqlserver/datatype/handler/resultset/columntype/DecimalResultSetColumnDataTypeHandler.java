package com.douglei.core.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.core.dialect.impl.sqlserver.datatype.Decimal;
import com.douglei.core.dialect.impl.sqlserver.datatype.handler.dbtype.DecimalDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DecimalResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private DecimalResultSetColumnDataTypeHandler() {}
	private static final DecimalResultSetColumnDataTypeHandler instance = new DecimalResultSetColumnDataTypeHandler();
	public static final DecimalResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Decimal.singleInstance().getSqlType()	// decimal 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return DecimalDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
