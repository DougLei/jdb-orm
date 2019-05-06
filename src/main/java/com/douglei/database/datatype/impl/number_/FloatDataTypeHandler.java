package com.douglei.database.datatype.impl.number_;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public final class FloatDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(FloatDataTypeHandler.class);
	
	private FloatDataTypeHandler() {}
	private static final FloatDataTypeHandler handler = new FloatDataTypeHandler();
	public static final FloatDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(logger.isDebugEnabled()) {
			logger.debug("{} - setValue's value is {}", getClass(), value);
		}
		if(StringUtil.isEmpty(value)) {
			if(logger.isDebugEnabled()) {
				logger.debug("{} - value的值为空, 数据库做null值处理", getClass(), value);
			}
			preparedStatement.setNull(parameterIndex, Types.DECIMAL);
		}else {
			preparedStatement.setFloat(parameterIndex, Float.parseFloat(value.toString()));
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
