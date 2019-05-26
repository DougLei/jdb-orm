package com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;
import com.douglei.database.dialect.impl.sqlserver.datatype.dbtype.TextDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ClobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private ClobResultSetColumnDataTypeHandler() {}
	private static final ClobResultSetColumnDataTypeHandler instance = new ClobResultSetColumnDataTypeHandler();
	public static final ClobResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			SqlServerDBType.TEXT.getSqlType()	// text
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return TextDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
