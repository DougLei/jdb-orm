package com.douglei.database.datatype.impl.big_;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * 
 * @author DougLei
 */
public final class BigStringDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(BigStringDataTypeHandler.class);
	
	private BigStringDataTypeHandler() {}
	private static final BigStringDataTypeHandler handler = new BigStringDataTypeHandler();
	public static final BigStringDataTypeHandler singleInstance() {
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
	public Object turnValueToTargetDataType(Object value) {
		// TODO Auto-generated method stub
		return null;
	}
}
