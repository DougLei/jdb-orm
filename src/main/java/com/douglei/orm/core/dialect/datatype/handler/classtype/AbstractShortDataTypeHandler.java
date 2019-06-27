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
	private static final long serialVersionUID = 6484637321538308380L;

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
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		if(value.getClass() == short.class || value instanceof Short || ValidationUtil.isInteger(value.toString())) {
			long l = Long.parseLong(value.toString());
			if(l > Short.MAX_VALUE || l < Short.MIN_VALUE) {
				return "数据值大小异常, 应在["+Short.MIN_VALUE+"]至["+Short.MAX_VALUE+"]范围内";
			}
			return null;
		}
		return "数据值类型错误, 应为短整型(short)";
	}
}
