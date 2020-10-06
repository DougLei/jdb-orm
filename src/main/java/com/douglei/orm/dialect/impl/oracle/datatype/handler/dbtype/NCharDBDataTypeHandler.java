package com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NChar;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype.StringDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class NCharDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 582408779473427649L;
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
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return StringDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}
