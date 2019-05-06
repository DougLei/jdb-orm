package com.douglei.database.datatype.impl.char_;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;

/**
 * 
 * @author DougLei
 */
public final class CharDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(CharDataTypeHandler.class);
	
	private CharDataTypeHandler() {}
	private static final CharDataTypeHandler handler = new CharDataTypeHandler();
	public static final CharDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(logger.isDebugEnabled()) {
			logger.debug("{} - setValue's value is {}", getClass(), value);
		}
		if(value == null) {
			preparedStatement.setNull(parameterIndex, Types.CHAR);
		}else {
			preparedStatement.setString(parameterIndex, value.toString());
		}
	}

	@Override
	public Object turnValueToTargetDataType(Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isIOStream() {
		return false;
	}
}
