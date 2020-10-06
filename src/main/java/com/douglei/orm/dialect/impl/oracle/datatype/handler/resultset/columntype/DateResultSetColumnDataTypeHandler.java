package com.douglei.orm.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.oracle.datatype.db.Date;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype.DateDBDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DateResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = 5577738969865541219L;
	private DateResultSetColumnDataTypeHandler() {}
	private static final DateResultSetColumnDataTypeHandler instance = new DateResultSetColumnDataTypeHandler();
	public static final DateResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Date.singleInstance().getSqlType()	// date 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return DateDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
