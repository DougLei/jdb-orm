package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Datetime;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.DateDataTypeHandler;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 
 * @author DougLei
 */
public class DatetimeDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 7148001508513575398L;
	private DatetimeDBDataTypeHandler() {}
	private static final DatetimeDBDataTypeHandler instance = new DatetimeDBDataTypeHandler();
	public static final DatetimeDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Datetime.singleInstance();
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
	public ValidatorResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return DateDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}
