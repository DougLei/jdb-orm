package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.tools.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractLongDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = 1631712457355966838L;

	@Override
	public String getCode() {
		return DataType.LONG.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {long.class, Long.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && ValidationUtil.isInteger(value.toString())) {
			if(value.getClass() == long.class || value instanceof Long) {
				preparedStatement.setLong(parameterIndex, (long)value);
			}else {
				preparedStatement.setLong(parameterIndex, Long.parseLong(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		if(value.getClass() == long.class || value instanceof Long || ValidationUtil.isInteger(value.toString())) {
			// long变量的值, 一般不会超过最大值、也不会低于最小值, 用isInteger方法判断即可满足, 所以这里就暂时不做多余的判断, 上面setValue时同理, 也用isInteger方法判断
			return null;
		}
		return "数据值类型错误, 应为长整型(long)";
	}
}
