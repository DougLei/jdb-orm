package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.classtype.MySqlDoubleDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class DecimalDBDataTypeHandler extends DBDataTypeHandler{
	private DecimalDBDataTypeHandler() {}
	private static final DecimalDBDataTypeHandler instance = new DecimalDBDataTypeHandler();
	public static final DecimalDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return MySqlDBType.DECIMAL.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.DECIMAL.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		MySqlDoubleDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getDouble(parameterIndex);
	}
}
