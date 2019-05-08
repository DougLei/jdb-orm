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
public final class IntegerDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(IntegerDataTypeHandler.class);
	
	private IntegerDataTypeHandler() {}
	private static final IntegerDataTypeHandler handler = new IntegerDataTypeHandler();
	public static final IntegerDataTypeHandler singleInstance() {
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
			preparedStatement.setNull(parameterIndex, Types.INTEGER);
		}else {
			if(value instanceof Integer) {
				preparedStatement.setInt(parameterIndex, (Integer)value);
			}else {
				preparedStatement.setInt(parameterIndex, Integer.parseInt(value.toString()));
			}
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
