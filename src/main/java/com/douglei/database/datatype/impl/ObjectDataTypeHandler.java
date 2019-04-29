package com.douglei.database.datatype.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * string类型
 * @author DougLei
 */
public class ObjectDataTypeHandler implements DataTypeHandler {

	@Override
	public void setValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
		preparedStatement.setObject(index, value);
	}

	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return resultSet.getObject(index);
	}
}
