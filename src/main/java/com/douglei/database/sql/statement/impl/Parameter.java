package com.douglei.database.sql.statement.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.database.datatype.DataTypeHandlerMapping;

/**
 * 
 * @author DougLei
 */
public class Parameter {
	private Object value;
	private DataTypeHandler dataTypeHandler;
	
	public Parameter(Object value) {
		this(value, DataTypeHandlerMapping.getDefaultDataTypeHandler());
	}
	public Parameter(Object value, DataTypeHandler dataTypeHandler) {
		this.value = value;
		this.dataTypeHandler = dataTypeHandler;
	}

	public void setValue(int index, PreparedStatement preparedStatement) throws SQLException {
		dataTypeHandler.setValue(preparedStatement, index, value);
	}
	
	public Object getValue(int index, ResultSet resultSet) throws SQLException {
		return dataTypeHandler.getValue(resultSet, index);
	}
}
