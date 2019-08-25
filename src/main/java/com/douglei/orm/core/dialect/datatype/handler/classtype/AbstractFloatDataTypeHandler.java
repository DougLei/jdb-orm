package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractFloatDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -9096009894440617770L;

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
		if(value != null && VerifyTypeMatchUtil.isDouble(value.toString())) {
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
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		Object o = null;
		if(value.getClass() == float.class || value instanceof Float) {
			o = value;
		} else if(VerifyTypeMatchUtil.isDouble(value.toString())) {
			o = Float.parseFloat(value.toString());
		}else {
			return new ValidationResult(validateFieldName) {
				
				@Override
				public String getMessage() {
					return "数据值类型错误, 应为浮点型(float)";
				}
				
				@Override
				protected String getI18nCode() {
					return i18nCodePrefix + "value.datatype.error.float";
				}
			};
		}
		
		String string = o.toString();
		if(string.length() - 1 > length) {// -1是减去小数点的长度
			return new ValidationResult(validateFieldName) {
				
				@Override
				public String getMessage() {
					return "数据值长度超长, 设置长度为" + length +", 实际长度为" + (string.length() - 1);
				}
				
				@Override
				protected String getI18nCode() {
					return i18nCodePrefix + "value.digital.length.overdlength";
				}
			};
		}
		
		short dotIndex = (short)string.indexOf(".");
		if(dotIndex != -1) {
			dotIndex++;
			short pl = 0;
			while(dotIndex < string.length()) {
				dotIndex++;
				pl++;
			}
			if(pl > precision) {
				short apl = pl;
				return new ValidationResult(validateFieldName) {
					
					@Override
					public String getMessage() {
						return "数据值精度超长, 设置精度为" + precision +", 实际精度为" + apl;
					}
					
					@Override
					protected String getI18nCode() {
						return i18nCodePrefix + "value.digital.precision.overdlength";
					}
				};
			}
		}
		return null;
	}
}
