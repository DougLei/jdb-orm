package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.wrapper.Char;
import com.douglei.database.dialect.datatype.wrapper.NChar;
import com.douglei.database.dialect.datatype.wrapper.NString;
import com.douglei.database.dialect.datatype.wrapper.StringWrapper;

/**
 * 
 * @author DougLei
 */
public class StringDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return "string";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {String.class, NString.class, Char.class, NChar.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			setNullString(preparedStatement, parameterIndex);
		}else {
			String string = value.toString();
			if(value instanceof StringWrapper) {
				if(string == null) {
					setNullString(preparedStatement, parameterIndex);
					return;
				}
			}
			preparedStatement.setString(parameterIndex, string);
		}
	}
	
	private void setNullString(PreparedStatement preparedStatement, int parameterIndex) throws SQLException {
		preparedStatement.setNull(parameterIndex, 12);
	}
}
