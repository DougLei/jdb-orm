package com.douglei.orm.dialect.impl.mysql.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.mysql.datatype.db.Bigint;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Char;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Decimal;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Int;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Smallint;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Varchar;
import com.douglei.orm.dialect.impl.mysql.datatype.handler.dbtype.VarcharDBDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

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
