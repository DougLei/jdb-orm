package com.douglei.database.dialect.impl.mysql.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.handler.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.handler.dbtype.BlobDBDataTypeHandler;

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
			MySqlDBType.BLOB.getSqlType()	// blob...各种 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return BlobDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
