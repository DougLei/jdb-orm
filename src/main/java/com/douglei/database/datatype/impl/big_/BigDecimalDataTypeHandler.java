package com.douglei.database.datatype.impl.big_;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
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
public final class BigDecimalDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(BigDecimalDataTypeHandler.class);
	
	private BigDecimalDataTypeHandler() {}
	private static final BigDecimalDataTypeHandler handler = new BigDecimalDataTypeHandler();
	public static final BigDecimalDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(logger.isDebugEnabled()) {
			logger.debug("{} - setValue's value is {}", getClass(), value);
		}
		if(ValidationUtil.isNumber(value)) {
			preparedStatement.setBigDecimal(parameterIndex, new BigDecimal(value.toString()));
		}else {
			if(logger.isDebugEnabled()) {
				logger.debug("{} - value的值为[{}], 不是BigDecimal类型, 数据库做null值处理", getClass(), value);
			}
			preparedStatement.setNull(parameterIndex, Types.DECIMAL);
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
