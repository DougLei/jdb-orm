package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class StringClassDataTypeHandler extends ClassDataTypeHandler{
	private static final Class<?>[] supportClasses = {String.class};
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			preparedStatement.setNull(parameterIndex, 12);
		}else {
			preparedStatement.setString(parameterIndex, value.toString());
		}
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return supportClasses;
	}
}
