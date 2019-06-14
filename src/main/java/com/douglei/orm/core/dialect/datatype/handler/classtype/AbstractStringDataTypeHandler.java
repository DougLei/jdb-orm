package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.Char;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.NChar;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.NString;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.StringWrapper;
import com.douglei.tools.utils.StringUtil;

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
	public boolean isCharacterType() {
		return true; 
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {String.class, NString.class, Char.class, NChar.class, char.class, Character.class};

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
		if(value instanceof String || value.getClass() == char.class || value instanceof Character || value instanceof StringWrapper) {
			int actualLength = 0;
			if((actualLength = StringUtil.calcStringLength(value.toString())) > length) {
				return "数据值长度超长, 设置长度为" + length +", 实际长度为" + actualLength;
			}
			return null;
		}
		return "数据值类型错误, 应为字符类型";
	}
}
