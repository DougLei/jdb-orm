package com.douglei.database.datatype.impl.date_;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.utils.DateUtil;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public final class TimestampDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(TimestampDataTypeHandler.class);
	
	private TimestampDataTypeHandler() {}
	private static final TimestampDataTypeHandler handler = new TimestampDataTypeHandler();
	public static final TimestampDataTypeHandler singleInstance() {
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
			preparedStatement.setNull(parameterIndex, Types.TIMESTAMP);
		}else {
			preparedStatement.setTimestamp(parameterIndex, DateUtil.parseSqlTimestamp(value));
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		Timestamp date = resultSet.getTimestamp(columnIndex);
		if(logger.isDebugEnabled()) {
			logger.debug("{} - getValue's value is {}", getClass(), date);
		}
		return date;
	}
}
