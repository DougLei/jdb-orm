package com.douglei.database.datatype.impl.number_;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

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
		if(ValidationUtil.isNumber(value)) {
			preparedStatement.setFloat(parameterIndex, Float.parseFloat(value.toString().trim()));
		}else {
			if(logger.isDebugEnabled()) {
				logger.debug("{} - value的值为[{}], 不是float类型, 数据库做null值处理", getClass(), value);
			}
			preparedStatement.setNull(parameterIndex, Types.DECIMAL);
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		Float value = resultSet.getFloat(columnIndex);
		if(logger.isDebugEnabled()) {
			logger.debug("{} - getValue's value is {}", getClass(), value);
		}
		return value;
	}
}
