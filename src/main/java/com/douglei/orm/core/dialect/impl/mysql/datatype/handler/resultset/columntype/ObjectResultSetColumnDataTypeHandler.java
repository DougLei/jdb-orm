package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Bigint;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Char;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Decimal;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Int;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Smallint;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Varchar;
import com.douglei.orm.core.dialect.impl.mysql.datatype.handler.dbtype.VarcharDBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ObjectResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = 3688935417174885408L;
	private ObjectResultSetColumnDataTypeHandler() {}
	private static final ObjectResultSetColumnDataTypeHandler instance = new ObjectResultSetColumnDataTypeHandler();
	public static final ObjectResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
				Varchar.singleInstance().getSqlType(), 	// varchar
				Char.singleInstance().getSqlType(),		// char
				Smallint.singleInstance().getSqlType(),	 
				Int.singleInstance().getSqlType(),	// int 
				Decimal.singleInstance().getSqlType(),	// decimal
				Bigint.singleInstance().getSqlType()	// bigint 
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
