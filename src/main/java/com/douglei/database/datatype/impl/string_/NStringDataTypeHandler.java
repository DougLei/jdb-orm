package com.douglei.database.datatype.impl.string_;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * 
 * @author DougLei
 */
public final class NStringDataTypeHandler implements DataTypeHandler {
	private NStringDataTypeHandler() {}
	private static final NStringDataTypeHandler handler = new NStringDataTypeHandler();
	public static final NStringDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			preparedStatement.setNull(parameterIndex, Types.NVARCHAR);
		}else {
			preparedStatement.setNString(parameterIndex, value.toString());
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		return resultSet.getString(columnIndex);
	}
}
