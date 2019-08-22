package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.NChar;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.StringDataTypeHandler;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 
 * @author DougLei
 */
public class NCharDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 7373783464160638855L;
	private NCharDBDataTypeHandler() {}
	private static final NCharDBDataTypeHandler instance = new NCharDBDataTypeHandler();
	public static final NCharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return NChar.singleInstance();
	}
	
	@Override
	public boolean isCharacterType() {
		return NChar.singleInstance().isCharacterType();
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
	public ValidatorResult doValidate(Object value, short length, short precision) {
		return StringDataTypeHandler.singleInstance().doValidate(value, length, precision);
	}
}
