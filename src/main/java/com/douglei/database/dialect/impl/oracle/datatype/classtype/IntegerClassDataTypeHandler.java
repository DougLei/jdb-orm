package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class IntegerClassDataTypeHandler extends ClassDataTypeHandler{
	private static final Class<?>[] supportClasses = {int.class, Integer.class};
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
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
	
	@Override
	public Class<?>[] supportClasses() {
		return supportClasses;
	}
}
