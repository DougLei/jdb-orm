package com.douglei.database.datatype.impl.big_;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * 
 * @author DougLei
 */
public final class BigByteDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(BigByteDataTypeHandler.class);
	
	private BigByteDataTypeHandler() {}
	private static final BigByteDataTypeHandler handler = new BigByteDataTypeHandler();
	public static final BigByteDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(logger.isDebugEnabled()) {
			logger.debug("{} - setValue's value is {}", getClass(), value);
		}
		// TODO
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		Object value = resultSet.getObject(columnIndex);
		if(logger.isDebugEnabled()) {
			logger.debug("{} - getValue's value is {}", getClass(), value);
		}
		// TODO
		return value;
	}
}
