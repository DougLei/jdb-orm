package com.douglei.core.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.core.dialect.impl.oracle.datatype.Char;
import com.douglei.core.dialect.impl.oracle.datatype.NChar;
import com.douglei.core.dialect.impl.oracle.datatype.NVarchar2;
import com.douglei.core.dialect.impl.oracle.datatype.Varchar2;
import com.douglei.core.dialect.impl.oracle.datatype.handler.dbtype.Varchar2DBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class StringResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private StringResultSetColumnDataTypeHandler() {}
	private static final StringResultSetColumnDataTypeHandler instance = new StringResultSetColumnDataTypeHandler();
	public static final StringResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Varchar2.singleInstance().getSqlType(), 	// varchar2 
			NVarchar2.singleInstance().getSqlType(), 	// nvarchar2
			Char.singleInstance().getSqlType(),		// char
			NChar.singleInstance().getSqlType()		// nchar
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return Varchar2DBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
