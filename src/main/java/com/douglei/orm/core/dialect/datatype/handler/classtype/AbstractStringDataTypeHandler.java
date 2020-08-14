package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.Char;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.NChar;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.NString;
import com.douglei.orm.core.dialect.datatype.handler.wrapper.StringWrapper;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractStringDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -5546733496863017903L;

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
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value instanceof String || value.getClass() == char.class || value instanceof Character || value instanceof StringWrapper) {
			if(length != DBDataType.NO_LIMIT) {
				int actualLength = StringUtil.computeStringLength(value.toString());
				if(actualLength > length) {
					return new ValidationResult(validateFieldName) {
						
						@Override
						public String getOriginMessage() {
							return "数据值长度超长, 设置长度为%d, 实际长度为%d";
						}
						
						@Override
						public String getCode() {
							return codePrefix + "value.overlength";
						}

						@Override
						public Object[] getParams() {
							return new Object[] { length, actualLength };
						}
					};
				}
			}
			return null;
		}
		return new ValidationResult(validateFieldName) {
			
			@Override
			public String getOriginMessage() {
				return "数据值类型错误, 应为字符类型";
			}
			
			@Override
			public String getCode() {
				return codePrefix + "value.datatype.error.string";
			}
		};
	}
}
