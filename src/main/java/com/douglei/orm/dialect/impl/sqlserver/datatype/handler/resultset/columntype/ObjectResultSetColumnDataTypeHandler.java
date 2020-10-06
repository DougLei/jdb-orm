package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.resultset.columntype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Bigint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Char;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Decimal;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Int;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.NChar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.NVarchar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Numeric;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Smallint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Text;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varchar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varcharmax;
import com.douglei.orm.dialect.temp.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

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
