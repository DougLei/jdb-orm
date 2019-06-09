package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.tools.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractShortDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return DataType.SHORT.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {short.class, Short.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && ValidationUtil.isLimitShort(value.toString())) {
			if(value.getClass() == short.class || value instanceof Short) {
				preparedStatement.setShort(parameterIndex, (short)value);
			}else {
				preparedStatement.setShort(parameterIndex, Short.parseShort(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
}
