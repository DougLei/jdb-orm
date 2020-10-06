package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varbinary;
import com.douglei.orm.dialect.impl.sqlserver.datatype.handler.dbtype.VarbinaryDBDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BlobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = 78188935857684331L;
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
