package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.classtype.StringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NVarchar2DBDataTypeHandler extends DBDataTypeHandler{
	private NVarchar2DBDataTypeHandler() {}
	private static final NVarchar2DBDataTypeHandler instance = new NVarchar2DBDataTypeHandler();
	public static final NVarchar2DBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return OracleDBType.NVARCHAR2.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.NVARCHAR2.getSqlType();
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
