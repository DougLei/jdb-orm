package com.douglei.database.dialect.datatype.classtype.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataHandler;

/**
 * 
 * @author DougLei
 */
public class StringClassDataTypeHandler extends ClassDataHandler {

	@Override
	public Class<?> supportClass() {
		return String.class;
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
