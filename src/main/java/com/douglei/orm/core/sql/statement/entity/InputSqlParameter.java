package com.douglei.orm.core.sql.statement.entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;

/**
 * 输入的sql参数对象
 * @author DougLei
 */
public class InputSqlParameter {
	private static final Logger logger = LoggerFactory.getLogger(InputSqlParameter.class);
	
	private Object value;
	private DataTypeHandler dataTypeHandler;
	
	public InputSqlParameter(Object value) {
		this.value = value;
		this.dataTypeHandler = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByClassType(value);
		if(logger.isDebugEnabled()) {
			logger.debug("参数值 {} 没有指定DataTypeHandler, 使用系统解析出的数据类型 {}", value, dataTypeHandler.getClass());
		}
	}
	public InputSqlParameter(Object value, DataTypeHandler dataTypeHandler) {
		this.value = value;
		this.dataTypeHandler = dataTypeHandler;
	}

	public void setValue(short index, PreparedStatement preparedStatement) throws SQLException {
		dataTypeHandler.setValue(preparedStatement, index, value);
	}
	
	@Override
	public String toString() {
		return "value="+value+", dataTypeHandler="+dataTypeHandler.getCode();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataTypeHandler == null) ? 0 : dataTypeHandler.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		InputSqlParameter other = (InputSqlParameter) obj;
		if (value == null) {
			if(other.value == null) {
				return true;
			}
			return false;
		} 
		return value.equals(other.value);
	}
}
