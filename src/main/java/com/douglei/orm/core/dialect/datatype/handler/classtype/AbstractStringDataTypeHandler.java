package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.Char;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.NChar;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.NString;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.StringWrapper;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractStringDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return DataType.STRING.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {String.class, NString.class, Char.class, NChar.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
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
		preparedStatement.setNull(parameterIndex, getSqlType());
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		// TODO 
		return null;
	}
}
