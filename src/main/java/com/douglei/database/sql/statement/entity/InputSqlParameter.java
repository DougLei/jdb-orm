package com.douglei.database.sql.statement.entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.sessions.LocalRunDialectHolder;

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
		this.dataTypeHandler = LocalRunDialectHolder.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByClassType(value);
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
		return "value="+value+", dataTypeHandler="+dataTypeHandler;
	}
}
