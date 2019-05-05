package com.douglei.database.datatype.impl.string_;

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
public final class NStringDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(NStringDataTypeHandler.class);
	
	private NStringDataTypeHandler() {}
	private static final NStringDataTypeHandler handler = new NStringDataTypeHandler();
	public static final NStringDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(logger.isDebugEnabled()) {
			logger.debug("{} - setValue's value is {}", getClass(), value);
		}
		if(value == null) {
			preparedStatement.setNull(parameterIndex, Types.NVARCHAR);
		}else {
			preparedStatement.setNString(parameterIndex, value.toString());
		}
	}

	@Override
	public Object turnValueToTargetDataType(Object value) {
		// TODO Auto-generated method stub
		return null;
	}
}
