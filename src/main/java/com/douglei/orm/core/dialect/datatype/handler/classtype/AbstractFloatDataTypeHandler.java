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
public abstract class AbstractFloatDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -3700441946650313358L;

	@Override
	public String getCode() {
		return DataType.FLOAT.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {float.class, Float.class};
	}

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
			return new ValidationResult(validateFieldName, "数据值类型错误, 应为浮点型(float)", "jdb.data.validator.value.datatype.error.float");
		}
		
		String string = o.toString();
		if(length != DBDataType.NO_LIMIT && string.length() - 1 > length) // -1是减去小数点的长度
			return new ValidationResult(validateFieldName, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.digital.length.overlength", length, (string.length() - 1));
		
		if(precision != DBDataType.NO_LIMIT) {
			short dotIndex = (short)string.indexOf(".");
			if(dotIndex != -1) {
				dotIndex++;
				short pl = 0;
				while(dotIndex < string.length()) {
					dotIndex++;
					pl++;
				}
				if(pl > precision) 
					return new ValidationResult(validateFieldName, "数据值精度超长, 设置精度为%d, 实际精度为%d", "jdb.data.validator.value.digital.precision.overlength", precision, pl);
			}
		}
		return null;
	}
}
