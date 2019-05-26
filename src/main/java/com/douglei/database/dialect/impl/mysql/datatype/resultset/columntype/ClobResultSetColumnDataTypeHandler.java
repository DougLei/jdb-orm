package com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.dbtype.ClobDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class ClobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private ClobResultSetColumnDataTypeHandler() {}
	private static final ClobResultSetColumnDataTypeHandler instance = new ClobResultSetColumnDataTypeHandler();
	public static final ClobResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			MySqlDBType.TEXT.getSqlType()	// text...各种
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return ClobDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
