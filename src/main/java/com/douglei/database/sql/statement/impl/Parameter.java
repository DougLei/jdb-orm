package com.douglei.database.sql.statement.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class Parameter {
	private static final Logger logger = LoggerFactory.getLogger(Parameter.class);
	
	private Object value;
	private DataTypeHandler dataTypeHandler;
	
	public Parameter(Object value) {
		this.value = value;
		this.dataTypeHandler = LocalDialect.getDialect().getDataTypeHandler(value);
		if(logger.isDebugEnabled()) {
			logger.debug("参数值 {} 没有指定DataTypeHandler, 使用系统解析出的数据类型 {}", value, dataTypeHandler.getClass());
		}
	}
	public Parameter(Object value, DataTypeHandler dataTypeHandler) {
		this.value = value;
		this.dataTypeHandler = dataTypeHandler;
	}

	public void setValue(int index, PreparedStatement preparedStatement) throws SQLException {
		dataTypeHandler.setValue(preparedStatement, index, value);
	}
}
