package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.NVarchar2;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype.StringDataTypeHandler;

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
		return NVarchar2.singleInstance().getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return NVarchar2.singleInstance().getSqlType();
	}
	
	@Override
	public boolean isCharacterType() {
		return NVarchar2.singleInstance().isCharacterType();
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
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		return StringDataTypeHandler.singleInstance().doValidate(value, length, precision);
	}
}
