package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Int;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype.IntDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class IntResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = 6682415289743961069L;
	private IntResultSetColumnDataTypeHandler() {}
	private static final IntResultSetColumnDataTypeHandler instance = new IntResultSetColumnDataTypeHandler();
	public static final IntResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Int.singleInstance().getSqlType()	// int 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return IntDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
