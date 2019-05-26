package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;
import com.douglei.database.dialect.impl.sqlserver.datatype.classtype.SqlServerStringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class CharDBDataTypeHandler extends DBDataTypeHandler{
	private CharDBDataTypeHandler() {}
	private static final CharDBDataTypeHandler instance = new CharDBDataTypeHandler();
	public static final CharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return SqlServerDBType.CHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.CHAR.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		SqlServerStringDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getString(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getString(columnIndex);
	}
}
