package com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.dbtype.BigIntDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class BigIntResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private BigIntResultSetColumnDataTypeHandler() {}
	private static final BigIntResultSetColumnDataTypeHandler instance = new BigIntResultSetColumnDataTypeHandler();
	public static final BigIntResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			MySqlDBType.BIGINT.getSqlType()	// bigint 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return BigIntDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
