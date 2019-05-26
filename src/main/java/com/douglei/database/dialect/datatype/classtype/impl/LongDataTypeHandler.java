package com.douglei.database.dialect.datatype.classtype.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class LongDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return "long";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {long.class, Long.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isLong(value)) {
			if(value.getClass() == long.class || value instanceof Long) {
				preparedStatement.setLong(parameterIndex, (long)value);
			}else {
				preparedStatement.setLong(parameterIndex, Long.parseLong(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
}
