package com.douglei.database.dialect.impl.sqlserver.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;
import com.douglei.database.dialect.impl.sqlserver.datatype.classtype.SqlServerStringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
class VarcharDBDataTypeHandler extends DBDataTypeHandler{
	private VarcharDBDataTypeHandler() {}
	private static final VarcharDBDataTypeHandler instance = new VarcharDBDataTypeHandler();
	public static final VarcharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return SqlServerDBType.VARCHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.VARCHAR.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		SqlServerStringDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getString(parameterIndex);
	}
}
