package com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.dialect.impl.oracle.datatype.Date;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype.DateDataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class DateDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 4854888968429470844L;
	private DateDBDataTypeHandler() {}
	private static final DateDBDataTypeHandler instance = new DateDBDataTypeHandler();
	public static final DateDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Date.singleInstance();
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		DateDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getTimestamp(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getTimestamp(columnIndex);
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return DateDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}
