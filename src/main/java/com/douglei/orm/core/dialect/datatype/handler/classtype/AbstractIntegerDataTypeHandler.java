package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractIntegerDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = 4700387498231968711L;

	@Override
	public String getCode() {
		return DataType.INTEGER.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {int.class, Integer.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && VerifyTypeMatchUtil.isLimitInteger(value.toString())) {
			if(value.getClass() == int.class || value instanceof Integer) {
				preparedStatement.setInt(parameterIndex, (int)value);
			}else {
				preparedStatement.setInt(parameterIndex, Integer.parseInt(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value.getClass() == int.class || value instanceof Integer || VerifyTypeMatchUtil.isInteger(value.toString())) {
			String string = value.toString();
			
			long l = Long.parseLong(string);
			if(l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
				return new ValidationResult(validateFieldName) {
					
					@Override
					public String getOriginMessage() {
						return "数据值大小异常, 应在%d至%d范围内";
					}
					
					@Override
					public String getCode() {
						return "jdb.data.validator.value.digital.range.overflow";
					}

					@Override
					public Object[] getParams() {
						return new Object[]{ Integer.MIN_VALUE, Integer.MAX_VALUE };
					}
				};
			}
			
			if(length != DBDataType.NO_LIMIT && string.length() > length) {
				return new ValidationResult(validateFieldName) {
					
					@Override
					public String getOriginMessage() {
						return "数据值长度超长, 设置长度为%d, 实际长度为%d";
					}
					
					@Override
					public String getCode() {
						return "jdb.data.validator.value.digital.length.overlength";
					}

					@Override
					public Object[] getParams() {
						return new Object[] { length, string.length() };
					}
				};
			}
			return null;
		}
		return new ValidationResult(validateFieldName) {
			
			@Override
			public String getOriginMessage() {
				return "数据值类型错误, 应为整型(int)";
			}
			
			@Override
			public String getCode() {
				return "jdb.data.validator.value.datatype.error.int";
			}
		};
	}
}
