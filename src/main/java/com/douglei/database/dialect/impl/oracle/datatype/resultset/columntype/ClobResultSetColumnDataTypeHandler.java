package com.douglei.database.dialect.impl.oracle.datatype.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.dbtype.ClobDBDataTypeHandler;

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
			OracleDBType.CLOB.getSqlType()	// clob
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
