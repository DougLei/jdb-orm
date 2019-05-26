package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.classtype.IntegerDataTypeHandler;

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
		return MySqlDBType.INT.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.INT.getSqlType();
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
}
