package com.douglei.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.douglei.core.dialect.datatype.DataType;
import com.douglei.utils.datatype.DateTypeUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDateDataTypeHandler extends ClassDataTypeHandler{

	@Override
	public String getCode() {
		return DataType.DATE.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {Date.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && ValidationUtil.isDate(value.toString())) {
			preparedStatement.setTimestamp(parameterIndex, DateTypeUtil.parseSqlTimestamp(value));
		} else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
}
