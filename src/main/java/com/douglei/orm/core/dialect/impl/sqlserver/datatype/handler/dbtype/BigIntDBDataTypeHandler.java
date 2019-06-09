package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Bigint;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.LongDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BigIntDBDataTypeHandler extends DBDataTypeHandler{
	private BigIntDBDataTypeHandler() {}
	private static final BigIntDBDataTypeHandler instance = new BigIntDBDataTypeHandler();
	public static final BigIntDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Bigint.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Bigint.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		LongDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getLong(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getLong(columnIndex);
	}
}
