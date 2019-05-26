package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;
import com.douglei.database.dialect.impl.sqlserver.datatype.classtype.LongDataTypeHandler;

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
		return SqlServerDBType.BIGINT.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.BIGINT.getSqlType();
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
