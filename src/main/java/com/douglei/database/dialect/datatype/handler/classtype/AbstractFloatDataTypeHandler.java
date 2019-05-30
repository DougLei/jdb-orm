package com.douglei.database.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractFloatDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return "float";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {float.class, Float.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isDouble(value)) {
			if(value.getClass() == float.class || value instanceof Float) {
				preparedStatement.setDouble(parameterIndex, (float)value);
			}else {
				preparedStatement.setDouble(parameterIndex, Float.parseFloat(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
}
