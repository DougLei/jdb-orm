package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.classtype.OracleStringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class CharDBDataTypeHandler extends DBDataTypeHandler{
	private CharDBDataTypeHandler() {}
	private static final CharDBDataTypeHandler instance = new CharDBDataTypeHandler();
	public static final CharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return OracleDBType.CHAR.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.CHAR.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		OracleStringDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getString(parameterIndex);
	}
}
