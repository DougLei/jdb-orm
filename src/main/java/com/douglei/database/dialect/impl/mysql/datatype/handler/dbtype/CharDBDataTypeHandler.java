package com.douglei.database.dialect.impl.mysql.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.handler.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.handler.classtype.StringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class CharDBDataTypeHandler extends DBDataTypeHandler{
	private static final CharDBDataTypeHandler instance = new CharDBDataTypeHandler();
	public static final CharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return MySqlDBType.CHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.CHAR.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		StringDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
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
