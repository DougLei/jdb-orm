package com.douglei.database.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractShortDataTypeHandler extends ClassDataTypeHandler{
	
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
				preparedStatement.setInt(parameterIndex, (short)value);
			}else {
				preparedStatement.setInt(parameterIndex, Short.parseShort(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
}
