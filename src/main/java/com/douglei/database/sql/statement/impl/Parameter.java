package com.douglei.database.sql.statement.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.database.datatype.DataTypeHandlerMapping;

/**
 * 
 * @author DougLei
 */
public class Parameter {
	private static final Logger logger = LoggerFactory.getLogger(Parameter.class);
	
	private Object value;
	private DataTypeHandler dataTypeHandler;
	
	public Parameter(Object value) {
		this(value, DataTypeHandlerMapping.getDefaultDataTypeHandler());
		if(logger.isDebugEnabled()) {
			logger.debug("参数值 {}, 使用默认的数据类型 {}", value, DataTypeHandlerMapping.getDefaultDataTypeHandler().getClass());
		}
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
