package com.douglei.database.datatype.impl.number_;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

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
		if(ValidationUtil.isNumber(value)) {
			preparedStatement.setDouble(parameterIndex, Double.parseDouble(value.toString().trim()));
		}else {
			if(logger.isDebugEnabled()) {
				logger.debug("{} - value的值为[{}], 不是double类型, 数据库做null值处理", getClass(), value);
			}
			preparedStatement.setNull(parameterIndex, Types.DECIMAL);
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		String value = resultSet.getString(columnIndex);
		if(logger.isDebugEnabled()) {
			logger.debug("{} - getValue's value is {}", getClass(), value);
		}
		if(StringUtil.isEmpty(value)) {
			return null;
		}
		return Double.parseDouble(value.toString());
	}
}
