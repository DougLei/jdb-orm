package com.douglei.database.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.Varbinary;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.dbtype.VarbinaryDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BlobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private BlobResultSetColumnDataTypeHandler() {}
	private static final BlobResultSetColumnDataTypeHandler instance = new BlobResultSetColumnDataTypeHandler();
	public static final BlobResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Varbinary.singleInstance().getSqlType()	// varbinary 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return VarbinaryDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
