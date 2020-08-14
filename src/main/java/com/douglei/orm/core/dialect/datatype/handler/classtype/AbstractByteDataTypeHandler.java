package com.douglei.orm.core.dialect.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractByteDataTypeHandler extends AbstractShortDataTypeHandler{
	private static final long serialVersionUID = -1235558610422760425L;

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
			String string = value.toString();
			
			long l = Long.parseLong(string);
			if(l > Byte.MAX_VALUE || l < Byte.MIN_VALUE) {
				return new ValidationResult(validateFieldName) {
					
					@Override
					public String getOriginMessage() {
						return "数据值大小异常, 应在%d至%d范围内";
					}
					
					@Override
					public String getCode() {
						return codePrefix + "value.digital.range.overflow";
					}

					@Override
					public Object[] getParams() {
						return new Object[]{ Byte.MIN_VALUE, Byte.MAX_VALUE };
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
						return codePrefix + "value.digital.length.overlength";
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
				return "数据值类型错误, 应为字节型(byte)";
			}
			
			@Override
			public String getCode() {
				return codePrefix + "value.datatype.error.byte";
			}
		};
	}
}
