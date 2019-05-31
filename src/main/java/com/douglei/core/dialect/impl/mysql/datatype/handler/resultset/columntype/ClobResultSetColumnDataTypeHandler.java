package com.douglei.core.dialect.impl.mysql.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.core.dialect.impl.mysql.datatype.Mediumtext;
import com.douglei.core.dialect.impl.mysql.datatype.handler.dbtype.ClobDBDataTypeHandler;

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
			Mediumtext.singleInstance().getSqlType()	// text...各种
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
