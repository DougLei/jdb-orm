package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class DateDataTypeHandler extends ClassDataTypeHandler{

	@Override
	public String getCode() {
		return "double";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {Date.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isDouble(value)) {
			if(value.getClass() == double.class || value instanceof Double) {
				preparedStatement.setDouble(parameterIndex, (double)value);
			}else {
				preparedStatement.setDouble(parameterIndex, Double.parseDouble(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, 2);
		}
	}
}
