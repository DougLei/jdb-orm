package com.douglei.database.dialect.datatype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 默认的DataTypeHandler 【Object】
 * @author DougLei
 */
public final class DefaultDataTypeHandler extends DataTypeHandler {
	
	@Override
	public String getCode() {
		return "default";
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		preparedStatement.setObject(parameterIndex, value);
	}

	@Override
	public Object turnValueToTargetDataType(Object value) {
		return value;
	}
}
