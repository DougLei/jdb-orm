package com.douglei.orm.dialect.impl.oracle.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.oracle.datatype.db.Char;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NChar;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NVarchar2;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Number;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Varchar2;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ObjectResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = -4850042704924722827L;
	private ObjectResultSetColumnDataTypeHandler() {}
	private static final ObjectResultSetColumnDataTypeHandler instance = new ObjectResultSetColumnDataTypeHandler();
	public static final ObjectResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			Number.singleInstance().getSqlType(),	// number 
			Varchar2.singleInstance().getSqlType(), 	// varchar2 
			NVarchar2.singleInstance().getSqlType(), 	// nvarchar2
			Char.singleInstance().getSqlType(),		// char
			NChar.singleInstance().getSqlType()		// nchar
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getObject(columnIndex);
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
