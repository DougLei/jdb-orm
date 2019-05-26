package com.douglei.database.dialect.datatype.classtype.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class IntegerDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return "integer";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {int.class, Integer.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isInteger(value)) {
			if(value.getClass() == int.class || value instanceof Integer) {
				preparedStatement.setInt(parameterIndex, (int)value);
			}else {
				preparedStatement.setInt(parameterIndex, Integer.parseInt(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
}
