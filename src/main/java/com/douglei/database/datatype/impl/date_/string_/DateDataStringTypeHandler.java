package com.douglei.database.datatype.impl.date_.string_;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public final class DateDataStringTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(DateDataStringTypeHandler.class);
	
	private DateDataStringTypeHandler() {}
	private static final DateDataStringTypeHandler handler = new DateDataStringTypeHandler();
	public static final DateDataStringTypeHandler singleInstance() {
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
			preparedStatement.setNull(parameterIndex, Types.DATE);
		}else {
			preparedStatement.setDate(parameterIndex, DateUtil.parseSqlDate(value));
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		Date date = resultSet.getDate(columnIndex);
		if(logger.isDebugEnabled()) {
			logger.debug("{} - getValue's value is {}", getClass(), date);
		}
		if(date != null) {
			return DateUtil.simpleFormat(date);
		}
		return null;
	}
}
