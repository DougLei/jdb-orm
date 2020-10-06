package com.douglei.orm.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.oracle.datatype.db.Blob;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype.BlobDBDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BlobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = -6184324136870019823L;
	private BlobResultSetColumnDataTypeHandler() {}
	private static final BlobResultSetColumnDataTypeHandler instance = new BlobResultSetColumnDataTypeHandler();
	public static final BlobResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Blob.singleInstance().getSqlType()	// blob 
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
