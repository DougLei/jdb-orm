package com.douglei.orm.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.oracle.datatype.db.Clob;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype.ClobDBDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ClobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = -8185911214757984597L;
	private ClobResultSetColumnDataTypeHandler() {}
	private static final ClobResultSetColumnDataTypeHandler instance = new ClobResultSetColumnDataTypeHandler();
	public static final ClobResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Clob.singleInstance().getSqlType()	// clob
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
