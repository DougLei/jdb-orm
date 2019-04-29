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
public final class IntegerDataTypeHandler implements DataTypeHandler {
	private static final Logger logger = LoggerFactory.getLogger(IntegerDataTypeHandler.class);
	
	private IntegerDataTypeHandler() {}
	private static final IntegerDataTypeHandler handler = new IntegerDataTypeHandler();
	public static final IntegerDataTypeHandler singleInstance() {
		return handler;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isInteger(value)) {
			preparedStatement.setInt(parameterIndex, Integer.parseInt(value.toString().trim()));
		}else {
			if(logger.isDebugEnabled()) {
				logger.debug("{} - value的值为[{}], 不是integer类型, 数据库做null值处理", getClass(), value);
			}
			preparedStatement.setNull(parameterIndex, Types.INTEGER);
		}
	}

	@Override
	public Object getValue(ResultSet resultSet, int columnIndex) throws SQLException {
		return resultSet.getString(columnIndex);
	}
}
