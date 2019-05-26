package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.classtype.ClobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ClobDBDataTypeHandler extends DBDataTypeHandler{
	private ClobDBDataTypeHandler() {}
	private static final ClobDBDataTypeHandler instance = new ClobDBDataTypeHandler();
	public static final ClobDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return MySqlDBType.TEXT.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.TEXT.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		ClobDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return getClobValue(callableStatement.getCharacterStream(parameterIndex));
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return getClobValue(rs.getCharacterStream(columnIndex));
	}
}
