package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Char;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.NChar;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.NVarchar;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Text;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Varchar;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Varcharmax;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype.VarcharDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class StringResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = 2603202502718512220L;
	private StringResultSetColumnDataTypeHandler() {}
	private static final StringResultSetColumnDataTypeHandler instance = new StringResultSetColumnDataTypeHandler();
	public static final StringResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Varchar.singleInstance().getSqlType(), 		// varchar
			NVarchar.singleInstance().getSqlType(),		// nvarchar
			Char.singleInstance().getSqlType(),			// char
			NChar.singleInstance().getSqlType(),		// nchar
			Varcharmax.singleInstance().getSqlType(),	// varchar(max)
			Text.singleInstance().getSqlType()			// text
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return VarcharDBDataTypeHandler.singleInstance().getValue(columnIndex, rs);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
