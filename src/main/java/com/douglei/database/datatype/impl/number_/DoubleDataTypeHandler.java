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
public final class DoubleDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(DoubleDataTypeHandler.class);
	
	private DoubleDataTypeHandler() {}
	private static final DoubleDataTypeHandler handler = new DoubleDataTypeHandler();
	public static final DoubleDataTypeHandler singleInstance() {
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
			preparedStatement.setDouble(parameterIndex, Double.parseDouble(value.toString()));
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
