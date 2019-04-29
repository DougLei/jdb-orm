package com.douglei.database.datatype.impl.boolean_;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public final class BooleanDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(BooleanDataTypeHandler.class);
	
	private BooleanDataTypeHandler() {}
	private static final BooleanDataTypeHandler handler = new BooleanDataTypeHandler();
	public static final BooleanDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(logger.isDebugEnabled()) {
			logger.debug("{} - setValue's value is {}", getClass(), value);
		}
		if(StringUtil.isEmpty(value) || !ValidationUtil.isBoolean(value)) {
			preparedStatement.setString(parameterIndex, "false");
		}else {
			preparedStatement.setString(parameterIndex, value.toString().trim().toLowerCase());
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		String value = resultSet.getString(columnIndex);
		if(logger.isDebugEnabled()) {
			logger.debug("{} - getValue's value is {}", getClass(), value);
		}
		if(StringUtil.isEmpty(value)) {
			return false;
		}
		return Boolean.parseBoolean(value.toString());
	}
}
