package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Int;
import com.douglei.orm.core.dialect.impl.mysql.datatype.handler.classtype.IntegerDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class IntDBDataTypeHandler extends DBDataTypeHandler{
	private IntDBDataTypeHandler() {}
	private static final IntDBDataTypeHandler instance = new IntDBDataTypeHandler();
	public static final IntDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Int.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Int.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		IntegerDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getInt(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getInt(columnIndex);
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		return IntegerDataTypeHandler.singleInstance().doValidate(value, length, precision);
	}
}
