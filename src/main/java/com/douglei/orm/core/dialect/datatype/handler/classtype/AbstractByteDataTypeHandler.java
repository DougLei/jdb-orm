package com.douglei.orm.core.dialect.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractByteDataTypeHandler extends AbstractShortDataTypeHandler{
	private static final long serialVersionUID = 6313837660106407765L;

	@Override
	public String getCode() {
		return DataType.BYTE.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {byte.class, Byte.class};
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value.getClass() == byte.class || value instanceof Byte || VerifyTypeMatchUtil.isInteger(value.toString())) {
			long l = Long.parseLong(value.toString());
			if(l > Byte.MAX_VALUE || l < Byte.MIN_VALUE) {
				return new ValidationResult(validateFieldName) {
					
					@Override
					public String getMessage() {
						return "数据值大小异常, 应在["+Byte.MIN_VALUE+"]至["+Byte.MAX_VALUE+"]范围内";
					}
					
					@Override
					protected String getI18nCode() {
						return i18nCodePrefix + "value.digital.range.overflow";
					}
				};
			}
			return null;
		}
		return new ValidationResult(validateFieldName) {
			
			@Override
			public String getMessage() {
				return "数据值类型错误, 应为字节型(byte)";
			}
			
			@Override
			protected String getI18nCode() {
				return i18nCodePrefix + "value.datatype.error.byte";
			}
		};
	}
}
