package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Smallint;
import com.douglei.orm.core.dialect.impl.mysql.datatype.handler.dbtype.SmallIntDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class SmallIntResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = -6124965490022622515L;
	private SmallIntResultSetColumnDataTypeHandler() {}
	private static final SmallIntResultSetColumnDataTypeHandler instance = new SmallIntResultSetColumnDataTypeHandler();
	public static final SmallIntResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Smallint.singleInstance().getSqlType()	 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return SmallIntDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
