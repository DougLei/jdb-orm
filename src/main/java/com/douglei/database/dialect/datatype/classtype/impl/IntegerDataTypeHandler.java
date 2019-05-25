package com.douglei.database.dialect.datatype.classtype.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class IntegerDataTypeHandler extends ClassDataTypeHandler{
	private IntegerDataTypeHandler() {}
	private static final IntegerDataTypeHandler instance = new IntegerDataTypeHandler();
	public static final IntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
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
			preparedStatement.setNull(parameterIndex, 2);
		}
	}
}
