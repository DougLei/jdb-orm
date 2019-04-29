package com.douglei.database.datatype.impl.char_;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * 
 * @author DougLei
 */
public final class CharDataTypeHandler implements DataTypeHandler {
	private CharDataTypeHandler() {}
	private static final CharDataTypeHandler handler = new CharDataTypeHandler();
	public static final CharDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			preparedStatement.setNull(parameterIndex, Types.CHAR);
		}else {
			preparedStatement.setString(parameterIndex, value.toString());
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		return resultSet.getString(columnIndex);
	}
}
