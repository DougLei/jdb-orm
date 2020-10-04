package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Bigint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Char;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Decimal;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Int;
import com.douglei.orm.dialect.impl.sqlserver.datatype.NChar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.NVarchar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Numeric;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Smallint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Text;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Varchar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Varcharmax;

/**
 * 
 * @author DougLei
 */
public class ObjectResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final long serialVersionUID = 3781886472335580112L;
	private ObjectResultSetColumnDataTypeHandler() {}
	private static final ObjectResultSetColumnDataTypeHandler instance = new ObjectResultSetColumnDataTypeHandler();
	public static final ObjectResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
				Bigint.singleInstance().getSqlType(),	// bigint 
				Decimal.singleInstance().getSqlType(),	// decimal
				Numeric.singleInstance().getSqlType(),	// numeric
				Int.singleInstance().getSqlType(),	// int 
				Varchar.singleInstance().getSqlType(), 		// varchar
				NVarchar.singleInstance().getSqlType(),		// nvarchar
				Char.singleInstance().getSqlType(),			// char
				NChar.singleInstance().getSqlType(),		// nchar
				Varcharmax.singleInstance().getSqlType(),	// varchar(max)
				Text.singleInstance().getSqlType(),		// text
				Smallint.singleInstance().getSqlType()	
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
