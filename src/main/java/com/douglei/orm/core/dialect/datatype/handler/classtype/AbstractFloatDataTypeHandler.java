package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.tools.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractFloatDataTypeHandler extends ClassDataTypeHandler{
	
	@Override
	public String getCode() {
		return DataType.FLOAT.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {float.class, Float.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && ValidationUtil.isDouble(value.toString())) {
			if(value.getClass() == float.class || value instanceof Float) {
				preparedStatement.setFloat(parameterIndex, (float)value);
			}else {
				preparedStatement.setFloat(parameterIndex, Float.parseFloat(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		if(value.getClass() == float.class || value instanceof Float || ValidationUtil.isDouble(value.toString())) {
			
			// TODO
			
			return null;
		}
		return "数据值类型错误, 应为浮点型(float)";
	}
}
