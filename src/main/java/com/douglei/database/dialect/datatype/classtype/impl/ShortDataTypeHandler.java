package com.douglei.database.dialect.datatype.classtype.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class ShortDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return "short";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {short.class, Short.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isShort(value)) {
			if(value.getClass() == short.class || value instanceof Short) {
				preparedStatement.setShort(parameterIndex, (short)value);
			}else {
				preparedStatement.setShort(parameterIndex, Short.parseShort(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
}
