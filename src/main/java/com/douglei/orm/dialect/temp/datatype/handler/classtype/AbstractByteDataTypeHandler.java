package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.DataType2;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractByteDataTypeHandler extends AbstractShortDataTypeHandler{
	private static final long serialVersionUID = -5522645400402393337L;

	@Override
	public String getCode() {
		return DataType2.BYTE.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {byte.class, Byte.class};
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value.getClass() == byte.class || value instanceof Byte || VerifyTypeMatchUtil.isInteger(value.toString())) {
			String string = value.toString();
			
			long l = Long.parseLong(string);
			if(l > Byte.MAX_VALUE || l < Byte.MIN_VALUE) 
				return new ValidationResult(validateFieldName, "数据值大小异常, 应在%d至%d范围内", "jdb.data.validator.value.digital.range.overflow", Byte.MIN_VALUE, Byte.MAX_VALUE);
			
			if(length != DBDataType.NO_LIMIT && string.length() > length) 
				return new ValidationResult(validateFieldName, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.digital.length.overlength", length, string.length());
			
			return null;
		}
		return new ValidationResult(validateFieldName, "数据值类型错误, 应为字节型(byte)", "jdb.data.validator.value.datatype.error.byte");
	}
}
