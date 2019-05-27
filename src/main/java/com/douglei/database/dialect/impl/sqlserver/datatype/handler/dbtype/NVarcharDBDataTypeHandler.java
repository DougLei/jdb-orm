package com.douglei.database.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDBType;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype.StringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NVarcharDBDataTypeHandler extends DBDataTypeHandler{
	private NVarcharDBDataTypeHandler() {}
	private static final NVarcharDBDataTypeHandler instance = new NVarcharDBDataTypeHandler();
	public static final NVarcharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return SqlServerDBType.NVARCHAR.getTypeName();
	}

	@Override
	public int getSqlType() {
		return SqlServerDBType.NVARCHAR.getSqlType();
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
