package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Bigint;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype.BigIntDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BigIntResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private BigIntResultSetColumnDataTypeHandler() {}
	private static final BigIntResultSetColumnDataTypeHandler instance = new BigIntResultSetColumnDataTypeHandler();
	public static final BigIntResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Bigint.singleInstance().getSqlType()	// bigint 
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
