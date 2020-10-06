package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.DataType2;
import com.douglei.orm.dialect.temp.datatype.handler.wrapper.Char;
import com.douglei.orm.dialect.temp.datatype.handler.wrapper.NChar;
import com.douglei.orm.dialect.temp.datatype.handler.wrapper.NString;
import com.douglei.orm.dialect.temp.datatype.handler.wrapper.StringWrapper;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractStringDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -3104635197948557154L;

	@Override
	public String getCode() {
		return DataType2.STRING.getName();
	}
	
	@Override
	public boolean isCharacterType() {
		return true; 
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {String.class, NString.class, Char.class, NChar.class, char.class, Character.class};
	}

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
				if(actualLength > length) 
					return new ValidationResult(validateFieldName, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.overlength", length, actualLength);
			}
			return null;
		}
		return new ValidationResult(validateFieldName, "数据值类型错误, 应为字符类型", "jdb.data.validator.value.datatype.error.string");
	}
}
