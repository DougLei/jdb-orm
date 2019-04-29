package com.douglei.database.datatype.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * object类型, 作为系统的默认类型
 * @author DougLei
 */
public final class ObjectDataTypeHandler implements DataTypeHandler {
	private ObjectDataTypeHandler() {}
	private static final ObjectDataTypeHandler handler = new ObjectDataTypeHandler();
	public static final ObjectDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
		preparedStatement.setObject(index, value);
	}

	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return resultSet.getObject(index);
	}
}
